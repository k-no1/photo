package kjh.common.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kjh.common.dto.Constant;
import kjh.common.dto.FileDto;


public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String allow_file;
    private static String deny_file;
    
    public static String getFileExtension(String fileName) {
        String[] file = fileName.split("[.]");
        logger.debug("getFileExtension" + file.length);
        return (file.length > 1) ? file[1] : "";        
    }

    /**
     * 단 건 첨부파일 등록시 파일정보 (폼에 파일이 하나인 경우)
     * 파일명은 접두어 + yyyyMMddhhmmssSSS
     * @param file
     * @param KeyStr    파일접두어
     * @param fileStorePath 파일저장 루트경로
     * @param storePath 파일저장 추가경로
     * @return
     * @throws Exception
     */
    public static FileDto parseFileInf(MultipartFile file, String KeyStr, String fileStorePath, String storePath)
            throws Exception {
        if (CommonUtil.isEmpty(file)) {
            return null;
        }
        String orginFileName = file.getOriginalFilename();
        // --------------------------------------
        // 원 파일명이 없는 경우 처리
        // (첨부가 되지 않은 input file type)
        // --------------------------------------
        if (CommonUtil.isEmpty(orginFileName)) { return null; }

        logger.debug("FILESTOREPATH:" + fileStorePath);
        logger.debug("STOREPATH:" + storePath);
        
        String savePath = filePathBlackList(fileStorePath + storePath);
        File saveFolder = new File(savePath);

        if (!saveFolder.exists() || saveFolder.isFile()) {
            saveFolder.setExecutable(false, true);
            saveFolder.setReadable(true);
            saveFolder.setWritable(false, true);
            saveFolder.mkdirs();
        }

        int index = orginFileName.lastIndexOf(".");
        String fileExt = orginFileName.substring(index + 1);
        String newName = KeyStr + getTimeStamp() + "." + fileExt;
        long size = file.getSize();
        String filePath = savePath + "/" + newName;
        logger.debug("filePath" + filePath);
        file.transferTo(new File(filePath));

        FileDto fleVO = new FileDto();
        fleVO.setFile_ext(fileExt); 
        fleVO.setFile_path(storePath);
        fleVO.setFile_size(String.valueOf(size));
        fleVO.setOrg_file_name(orginFileName);
        fleVO.setSys_file_name(newName);
        
        return fleVO;
    }
    
    /**
     * 다중 건 첨부파일 등록시 파일정보  (폼에 파일이 하나이상 경우)
     * 파일명은 접두어 + yyyyMMddhhmmssSSS + seq(멀티일경우)
     * @param files
     * @param KeyStr    파일접두어
     * @param fileStorePath 파일저장 루트경로
     * @param storePath 파일저장 추가경로
     * @return
     * @throws Exception
     */
    public static List<FileDto> parseFileInf(Map<String, MultipartFile> files, String KeyStr, String fileStorePath, String storePath) throws Exception {
        if (CommonUtil.isEmpty(files)) {
            return null;
        }
        int fileKey = 0;

        String savePath = filePathBlackList(fileStorePath + storePath);
        File saveFolder = new File(savePath);

        if (!saveFolder.exists() || saveFolder.isFile()) {
            saveFolder.setExecutable(false, true);
            saveFolder.setReadable(true);
            saveFolder.setWritable(false, true);
            saveFolder.mkdirs();
        }

        Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file;
        String filePath = "";
        List<FileDto> result = new ArrayList<FileDto>();
        FileDto fleVO = new FileDto();

        while (itr.hasNext()) {
            Entry<String, MultipartFile> entry = itr.next();
            
            file = entry.getValue();
            String orginFileName = file.getOriginalFilename();

            // --------------------------------------
            // 원 파일명이 없는 경우 처리
            // (첨부가 되지 않은 input file type)
            // --------------------------------------
            if (CommonUtil.isEmpty(orginFileName)) {
                continue;
            }

            int index = orginFileName.lastIndexOf(".");
            String fileExt = orginFileName.substring(index + 1);

            String newName = "";
            newName = KeyStr + getTimeStamp() + fileKey + "." + fileExt;

            logger.debug("entry.getKey():"+entry.getKey());
            logger.debug("KeyStr:"+KeyStr);
            logger.debug("newName:"+newName);
            
            long size = file.getSize();

            filePath = savePath + "/" + newName;
            file.transferTo(new File(filePathBlackList(filePath)));

            fleVO = new FileDto();
            fleVO.setFile_ext(fileExt); 
            fleVO.setFile_path(storePath);
            fleVO.setFile_size(String.valueOf(size));
            fleVO.setOrg_file_name(orginFileName);
            fleVO.setSys_file_name(newName);
            fleVO.setElement_nm(entry.getKey());
            result.add(fleVO);

            fileKey++;
        }

        if (result.size() == 0) {
            result = null;
        }
        return result;
    }   
    
    private static String filePathBlackList(String value) {
        String returnValue = value;
        if (returnValue == null || returnValue.trim().equals("")) {
            return "";
        }

        returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
        returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

        return returnValue;
    }

    /**
     * 파일명으로 사용하기 위해 밀리세컨드까지
     * @return
     */
    private static String getTimeStamp() {
        String rtnStr = null;
        String pattern = "yyyyMMddhhmmssSSS";
        SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        rtnStr = sdfCurrent.format(ts.getTime());
        return rtnStr;
    }
    
    
    public static boolean checkFileExtension(MultipartHttpServletRequest multiRequest) {

        String file_extension;
        String filename = "";
        int rtn = 0;
        // String deny_file_extension="jsp,asp,php,java,sh";
        // String
        // allow_file_extension="txt,doc,docx,ppt,pptx,xls,xlsx,hwp,pdf,png,gif,jpeg";
        // logger.debug("### checkFileExtension
        // allow_file:["+allow_file+"]"+multiRequest.getContentType());

        List<String> allowFileExtension = Arrays.asList(allow_file.split(","));
        List<String> denyFileExtension = Arrays.asList(deny_file.split(","));

        Iterator<String> names = multiRequest.getFileNames();

        // logger.debug("### checkFileExtension
        // multiRequest.getFileMap().size():["+multiRequest.getFileMap().size()+"]");

        if (multiRequest.getFileMap().size() == 0) {
            rtn++;
        }
        while (names.hasNext()) {
            filename = (String) names.next();
            // logger.debug("### checkFileExtension filename:["+filename+"]");
            List<MultipartFile> files3 = multiRequest.getFiles(filename);

            for (MultipartFile mfile : files3) {
                // logger.debug("### checkFileExtension
                // ["+mfile.getName()+"],OriginalFilename:["+mfile.getOriginalFilename()+"],file
                // Extension["+FilenameUtils.getExtension(mfile.getOriginalFilename())+"]");
                if (mfile.getOriginalFilename() == null || mfile.getOriginalFilename().equals("")) {
                    rtn++;
                }

                file_extension = FilenameUtils.getExtension(mfile.getOriginalFilename());
                if (allowFileExtension.size() > 0 && allowFileExtension.contains(file_extension.toLowerCase())) {
                    // logger.debug("### checkFileExtension"+(new
                    // StringBuilder(String.valueOf(file_extension))).append("
                    // file type is allowed to be uploaded!").toString());
                    rtn++;
                }
                if (denyFileExtension.size() > 0 && denyFileExtension.contains(file_extension.toLowerCase())) {
                    // logger.debug("### checkFileExtension"+(new
                    // StringBuilder(String.valueOf(file_extension))).append("
                    // file type is not allowed to be uploaded!").toString());
                    rtn = -2;
                }
            }
            logger.debug("### checkFileExtension rtn :" + rtn);
        }
        logger.debug("### checkFileExtension b_rtn:[" + rtn + "]");
        return (rtn > 0) ? true : false;
    }

    public static void deleteFileInf(String sysFileName, String fileStorePath, String storePath) throws Exception {
        String savePath = filePathBlackList(fileStorePath + storePath +"/"+ sysFileName);
        File saveFolder = new File(savePath);
        saveFolder.delete();
    }
    
    public static void deleteFileInf(String fullPath) throws Exception {
        if(fullPath.indexOf("c:/") > -1 ||
           fullPath.indexOf("d:/") > -1
        ){
            fullPath = fullPath.replaceAll("/","\\\\");
        }
        logger.info("파일삭제경로=["+fullPath+"]");
        File saveFolder = new File(fullPath);
        saveFolder.delete();
    }
    
    /**
     * 파일 업로드 유틸
     * @param uploadPath
     * @param originalName
     * @param fileData
     * @return
     * @throws Exception
     */
    public static String uploadFile(String uploadPath, String originalName, byte[] fileData,String uniqId) throws Exception {
        
        String ext             = "";//확장자
        File   target          = null;
        int    lastDotPosition = 0;
        
        try {
            //파일구분자로 구분
            if(originalName.indexOf(Constant.DOT) > -1 &&
               originalName.split("\\.").length   >= 2
            ) {
                lastDotPosition = originalName.lastIndexOf(Constant.DOT);
                ext             = originalName.substring(  lastDotPosition+1);//확장자
            }else{
                return null;
            }
            
            //저장파일명 조립
            String savedName = uniqId +Constant.DOT+ext;
            
            //파일을 저장할 폴더 생성
            if(uploadPath != null){
                makeDir(uploadPath);//폴더 없을시 생성    
            }

            //저장할 파일준비
            target = new File(uploadPath, savedName);
            
            //파일을 저장
            FileCopyUtils.copy(fileData, target);
            
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return target.getPath();
    }
    
    public static String uploadFile(String uploadPath, String originalName, byte[] fileData,String uniqId,String flg) throws Exception {
        if(flg.equalsIgnoreCase("Y")) {
            originalName = uniqId;
        }

        return uploadFile(uploadPath, originalName, fileData,uniqId);
    }//
    
    //폴더 생성 함수
    @SuppressWarnings("unused")
    private static String calcPath(String uploadPath) {
        
        Calendar cal = Calendar.getInstance();
        
        String yearPath = File.separator + cal.get(Calendar.YEAR);
        
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
        
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
        
        makeDir(uploadPath, yearPath, monthPath, datePath);
        
        logger.info(datePath);
        
        return datePath;
    }//calcPath
    
    //지정 경로 폴더 생성
    public static void makeDir(String uploadPath) {
        //업로드 경로 파일 텍스트 정보가 없을시
        if(uploadPath != null) {
            //업로드 경로의 파일이 없을시
            if(new File(uploadPath).exists()) {
                return;
            }//if
            
            //원도우 환경, 리눅스 환경 예외처리---------ST
            String preFix = "";
            boolean chkuploadPath = (uploadPath.indexOf(Constant.SLASH) > -1);
            if(chkuploadPath){
                preFix = Constant.SLASH;
            }else {
                preFix = Constant.BACK_SLASH;
            }
            //원도우 환경, 리눅스 환경 예외처리---------ED
            
            //업로드 경로 있는지 체크
            String uploadPathArray[] = uploadPath.split(preFix);
            String checkPath         = "";
            
            if(uploadPathArray != null) {
                //파일명 체크 (파일 경로가 존재하는지 시작위치부터 체크)
                int forSize = uploadPathArray.length;
                for(int i=0;forSize > i; i++) {
                    
                    //순차적으로 경로를 체크
                    checkPath += uploadPathArray[i];
                    
                    //ROOT는 체크 PASS 하고 경로를 체크한다.
                    if(!new File(checkPath).exists() &&
                            i != 0
                            ){
                        File dirPath = new File(checkPath);
                        dirPath.setExecutable(false, true);
                        dirPath.setReadable(true);
                        dirPath.setWritable(false, true);
                        dirPath.mkdir();
                    }
                    
                    //마지막(파일명)을 제외하고 경로체크 후 preFix를 붙인다  
                    if(uploadPathArray.length-1 != i) {
                        checkPath += preFix;
                    }
                }
            }
        }else {
            return;
            
        }
        
    }
    
    //폴더 생성 함수
    private static void makeDir(String uploadPath, String... paths) {
        
        if(new File(uploadPath + paths[paths.length -1]).exists()) {
            return;
        }//if
        
        for(String path : paths) {
            
            File dirPath = new File(uploadPath + path);
            
            if(!dirPath.exists()) {
                dirPath.setExecutable(false, true);
                dirPath.setReadable(true);
                dirPath.setWritable(false, true);
                dirPath.mkdir();
            }//if
            
        }//for
        
    }//makeDir
    
    //음??? 아이콘? 이미지 파일이 아닌경우  썸네일을 대신?
    @SuppressWarnings("unused")
    private static String makeIcon(String uploadPath, String path, String fileName) throws Exception{
        String iconName = uploadPath + path + File.separator + fileName;
        return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }
    
    //썸네일 이미지 생성
    @SuppressWarnings("unused")
    private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
//        BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
//        BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
//        String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;

//        File newFile = new File(thumbnailName);
//        String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
//        
//        ImageIO.write(destImg, formatName.toUpperCase(), newFile);
        return null;
//        return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }
    
    /**
     * sha256 파일 hash
     * @param fileName
     * @return
     * @throws Exception
     */
    public static StringBuffer getSha2Hash(String fileName) throws Exception{
        MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
        return calculateHash(sha2, fileName);
    }
 
    /**
     * 파일 read Hash
     * @param algorithm
     * @param fileName
     * @return
     * @throws Exception
     */

    public static StringBuffer calculateHash(MessageDigest algorithm, String fileName) throws Exception
    {
        FileInputStream     fis = new FileInputStream(new File(fileName));
        StringBuffer sb = new StringBuffer();
        
        try 
        {
            int nread               = 0;
            byte[] dataBytes        = new byte[1024];
            // read the file and update the hash calculation
            while((nread = fis.read(dataBytes)) != -1) 
            {
                algorithm.update(dataBytes,0,nread);
            };
    
            // get the hash value as byte array
            byte[] hash     = algorithm.digest();
            
            for(int i = 0; i < hash.length;i++) 
            {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            if( logger.isDebugEnabled() )
            {
                logger.debug("sha2 hash="+sb);
            }
        }
        catch (Exception e) 
        {
            // logger.error(e.toString());
            throw e ;
        }
        finally 
        {
            fis.close();
        }
        
        return sb;
    }
    
    /**
     * 파일 이동 (temp에서 매핑될때 함께처리)
     * @param folderName
     * @param fileName
     * @param beforeFilePath
     * @param afterFilePath
     * @return
     */
    public static String moveFile(String staticFilePath,String fileName, String beforeFilePath, String afterFilePath) throws Exception {

        FileInputStream input     = null;
        FileOutputStream output   = null;
        String path               = afterFilePath;
        String filePath           = path+Constant.SLASH+fileName;
        String folder             = staticFilePath+path;
        String beforeRootPath     = staticFilePath+beforeFilePath;
        String afterFileRootPath  = staticFilePath+filePath;

        try{
            File dir        = new File(folder);
            if(dir != null) {
                
                boolean chkDir = !dir.exists();
                if (chkDir) { //폴더 없으면 폴더 생성
                    dir.setExecutable(false, true);
                    dir.setReadable(true);
                    dir.setWritable(false, true);
                    dir.mkdirs();
                }
            }
            
            if(beforeRootPath.indexOf("c:/") > -1 ||
               beforeRootPath.indexOf("d:/") > -1
            ) {
                beforeRootPath = beforeRootPath.replaceAll("/","\\\\");
            }
            
            String chkFileRootPath = "";
            
            if(afterFileRootPath != null) {
                
                if(afterFileRootPath.indexOf("c:/") > -1 ||
                   afterFileRootPath.indexOf("d:/") > -1) {
                    chkFileRootPath   = afterFileRootPath.replaceAll("/","\\\\");
                    afterFileRootPath = chkFileRootPath;
                }
            }
            
            File file =new File(beforeRootPath);
            
            logger.info("파일경로="+beforeRootPath);
            logger.info("이동경로="+afterFileRootPath);
            
            if(file.renameTo(new File(afterFileRootPath))){ //파일 이동
                logger.info("파일업로드1");
                return filePath; //성공시 성공 파일 경로 return
            }else{
                //최초 작업시 오류가 발생할 경우 재시도
                logger.info("파일업로드2");
                if(file.renameTo(new File(afterFileRootPath))) {
                    return filePath; //성공시 성공 파일 경로 return
                }else {
                    //그래도 오류일 경우 이동이 아닌 copy (다른곳에서 파일을 잡고있는 경우가 있는거 같음...)
                    logger.info("파일 이동 실패 copy로 대체");
                    // 복사할 대상 파일을 지정해준다.
                    File copyFile = new File(beforeRootPath);
                    
                    // 복사된 파일의 위치를 지정해준다.
                    input  = new FileInputStream(copyFile);
                    output = new FileOutputStream(new File(afterFileRootPath));
                                 
                    int readBuffer = 0;
                    byte [] buffer = new byte[512];
                    while((readBuffer = input.read(buffer)) != -1) {
                        output.write(buffer, 0, readBuffer);
                    }
                    copyFile.delete();//TEMP 파일 삭제
                    logger.info("파일이 복사되었습니다.");
                    return filePath;
                }
            }
 
        }catch(Exception e){
            return null;
        }finally {
            if(input != null) {
                try {
                    input.close();
                }catch(Exception e) {
                    logger.error( e.toString() ) ;
                }
            }
            
            if(output != null) {
                try {
                    output.close();
                }catch(Exception e) {
                    logger.error( e.toString() ) ;
                }
            }
            
        }
    }
    
    @SuppressWarnings("deprecation")
    public static void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String browser           = getBrowser(request);
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename   = null;
        logger.debug("================================="+browser+"=============================");

        if (browser.equals("MSIE")) {
               encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Trident")) {       // IE11 문자열 깨짐 방지
               encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Firefox")) {
               encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
               encodedFilename = URLDecoder.decode(encodedFilename);
        } else if (browser.equals("Opera")) {
               encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
           StringBuffer sb = new StringBuffer();
           for (int i = 0; i < filename.length(); i++) {
              char c = filename.charAt(i);
              if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
              } else {
                    sb.append(c);
              }
           }
               encodedFilename = sb.toString();
        } else if (browser.equals("Safari")){
               encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
               encodedFilename = URLDecoder.decode(encodedFilename);
        }
        else {
               encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
        }
        response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

        if ("Opera".equals(browser)){
           response.setContentType("application/octet-stream;charset=UTF-8");
        }
    }
    
    
    public static String getBrowser(HttpServletRequest request) {

       String header = request.getHeader("User-Agent");
    
       if (header.indexOf("MSIE") > -1) {
           return "MSIE";
       } else if (header.indexOf("Trident") > -1) {   // IE11 문자열 깨짐 방지
           return "Trident";
       } else if (header.indexOf("Chrome") > -1) {
           return "Chrome";
       } else if (header.indexOf("Opera") > -1) {
           return "Opera";
       } else if (header.indexOf("Safari") > -1) {
       return "Safari";
       }
       return "Firefox";
    }
    // private static final int ScaleWidth = 256;
    // private static final int ScaleHeight = 256; 

    public static boolean isImageFile(MultipartFile file) 
    {
        String contentType = file.getContentType();

        if (MediaType.IMAGE_GIF_VALUE.equals(contentType)) 
        {
            return true;
        }
        else if (MediaType.IMAGE_JPEG_VALUE.equals(contentType)) 
        {
            return true;
        }
        else if (MediaType.IMAGE_PNG_VALUE.equals(contentType)) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    // 2018-01-08 특정 이미지만 리사이징 될 수 있도록 함수 변경
    public static byte[] convertToPngFormat(MultipartFile file, boolean resizeImage) throws Exception 
    {
        String contentType = file.getContentType();

        if (!MediaType.IMAGE_GIF_VALUE.equals(contentType)
                && !MediaType.IMAGE_JPEG_VALUE.equals(contentType)
                && !MediaType.IMAGE_PNG_VALUE.equals(contentType)) 
        {
            throw new Exception("알 수 없는 이미지 포맷입니다.");
        }
        
        BufferedImage inputImage = ImageIO.read(file.getInputStream());

        // 2017-11-03 원본 이미지를 저장하기로 결정되어, 이미지 리사이징 기능은 제거
        // BufferedImage outputImage = new BufferedImage(ScaleWidth, ScaleHeight, inputImage.getType());
        // Graphics2D g2 = outputImage.createGraphics();
        // g2.drawImage(inputImage, 0, 0, ScaleWidth, ScaleHeight, null);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        
        // 2018-01-08 이미지 스케일 안해서 짤림 ;; 수정
        BufferedImage outputImage = null;
        Graphics2D g2 = null;
        
        if (resizeImage) 
        {
            float rate = (float)(width > height ? width : height) / 350;
            int nw = (int)(width / rate);
            int nh = (int)(height / rate);
            
            outputImage = new BufferedImage(nw, nh, inputImage.getType());
            g2 =  outputImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(inputImage, 0, 0, nw, nh, 0, 0, width, height, null);
        }
        else 
        {
            outputImage = new BufferedImage(width, height, inputImage.getType());
            g2 =  outputImage.createGraphics();
            g2.drawImage(inputImage, 0, 0, width, height, null);
        }
        
        g2.dispose();
        
        ByteArrayOutputStream baos = null;
        try {
            
            baos = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "png", baos);
            
        }catch(IOException e) {
            logger.error( e.toString() ) ;
        }finally {
            baos.close();
        }

        return baos.toByteArray();
    }

    public static byte[] convertToPngFormat(MultipartFile file) throws Exception 
    {
        return convertToPngFormat(file, false);
    }

    /**
     * File형식을 MultipartFile로 변환
     * 
     * @param File file
     */
    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        FileItem fileItem = null;
        InputStream inputStream = null;
        try {
            if(file != null) {
                
                String fileNames = file.getName();
                fileItem = new DiskFileItem(null, "image/png", false, fileNames, (int) file.length(), file.getParentFile());
                
                inputStream = new FileInputStream(file);
                OutputStream outputStream = fileItem.getOutputStream();
                IOUtils.copy(inputStream, outputStream);
            }
            
        }
        catch (FileNotFoundException e) 
        {
            logger.error( e.toString() ) ;
        }
        catch (IOException e) 
        {
            logger.error( e.toString() ) ;
        }finally {
            inputStream.close();
        }
        
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        
        return multipartFile;
    }
    
    /**
     * 텍스트 특수기호 제거
     * @param str
     * @return
     */
    public static String textSpecialSymbolsDel (String str) {

        StringBuffer sb = new StringBuffer();

        for(int i=0 ; i< str .length(); i++)
        {
            if(Character.isLetterOrDigit(str .charAt(i)))
                sb.append(str .charAt(i));
        }

        return sb.toString();
    }
}

