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
     * �� �� ÷������ ��Ͻ� �������� (���� ������ �ϳ��� ���)
     * ���ϸ��� ���ξ� + yyyyMMddhhmmssSSS
     * @param file
     * @param KeyStr    �������ξ�
     * @param fileStorePath �������� ��Ʈ���
     * @param storePath �������� �߰����
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
        // �� ���ϸ��� ���� ��� ó��
        // (÷�ΰ� ���� ���� input file type)
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
     * ���� �� ÷������ ��Ͻ� ��������  (���� ������ �ϳ��̻� ���)
     * ���ϸ��� ���ξ� + yyyyMMddhhmmssSSS + seq(��Ƽ�ϰ��)
     * @param files
     * @param KeyStr    �������ξ�
     * @param fileStorePath �������� ��Ʈ���
     * @param storePath �������� �߰����
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
            // �� ���ϸ��� ���� ��� ó��
            // (÷�ΰ� ���� ���� input file type)
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
     * ���ϸ����� ����ϱ� ���� �и����������
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
        logger.info("���ϻ������=["+fullPath+"]");
        File saveFolder = new File(fullPath);
        saveFolder.delete();
    }
    
    /**
     * ���� ���ε� ��ƿ
     * @param uploadPath
     * @param originalName
     * @param fileData
     * @return
     * @throws Exception
     */
    public static String uploadFile(String uploadPath, String originalName, byte[] fileData,String uniqId) throws Exception {
        
        String ext             = "";//Ȯ����
        File   target          = null;
        int    lastDotPosition = 0;
        
        try {
            //���ϱ����ڷ� ����
            if(originalName.indexOf(Constant.DOT) > -1 &&
               originalName.split("\\.").length   >= 2
            ) {
                lastDotPosition = originalName.lastIndexOf(Constant.DOT);
                ext             = originalName.substring(  lastDotPosition+1);//Ȯ����
            }else{
                return null;
            }
            
            //�������ϸ� ����
            String savedName = uniqId +Constant.DOT+ext;
            
            //������ ������ ���� ����
            if(uploadPath != null){
                makeDir(uploadPath);//���� ������ ����    
            }

            //������ �����غ�
            target = new File(uploadPath, savedName);
            
            //������ ����
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
    
    //���� ���� �Լ�
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
    
    //���� ��� ���� ����
    public static void makeDir(String uploadPath) {
        //���ε� ��� ���� �ؽ�Ʈ ������ ������
        if(uploadPath != null) {
            //���ε� ����� ������ ������
            if(new File(uploadPath).exists()) {
                return;
            }//if
            
            //������ ȯ��, ������ ȯ�� ����ó��---------ST
            String preFix = "";
            boolean chkuploadPath = (uploadPath.indexOf(Constant.SLASH) > -1);
            if(chkuploadPath){
                preFix = Constant.SLASH;
            }else {
                preFix = Constant.BACK_SLASH;
            }
            //������ ȯ��, ������ ȯ�� ����ó��---------ED
            
            //���ε� ��� �ִ��� üũ
            String uploadPathArray[] = uploadPath.split(preFix);
            String checkPath         = "";
            
            if(uploadPathArray != null) {
                //���ϸ� üũ (���� ��ΰ� �����ϴ��� ������ġ���� üũ)
                int forSize = uploadPathArray.length;
                for(int i=0;forSize > i; i++) {
                    
                    //���������� ��θ� üũ
                    checkPath += uploadPathArray[i];
                    
                    //ROOT�� üũ PASS �ϰ� ��θ� üũ�Ѵ�.
                    if(!new File(checkPath).exists() &&
                            i != 0
                            ){
                        File dirPath = new File(checkPath);
                        dirPath.setExecutable(false, true);
                        dirPath.setReadable(true);
                        dirPath.setWritable(false, true);
                        dirPath.mkdir();
                    }
                    
                    //������(���ϸ�)�� �����ϰ� ���üũ �� preFix�� ���δ�  
                    if(uploadPathArray.length-1 != i) {
                        checkPath += preFix;
                    }
                }
            }
        }else {
            return;
            
        }
        
    }
    
    //���� ���� �Լ�
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
    
    //��??? ������? �̹��� ������ �ƴѰ��  ������� ���?
    @SuppressWarnings("unused")
    private static String makeIcon(String uploadPath, String path, String fileName) throws Exception{
        String iconName = uploadPath + path + File.separator + fileName;
        return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }
    
    //����� �̹��� ����
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
     * sha256 ���� hash
     * @param fileName
     * @return
     * @throws Exception
     */
    public static StringBuffer getSha2Hash(String fileName) throws Exception{
        MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
        return calculateHash(sha2, fileName);
    }
 
    /**
     * ���� read Hash
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
     * ���� �̵� (temp���� ���εɶ� �Բ�ó��)
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
                if (chkDir) { //���� ������ ���� ����
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
            
            logger.info("���ϰ��="+beforeRootPath);
            logger.info("�̵����="+afterFileRootPath);
            
            if(file.renameTo(new File(afterFileRootPath))){ //���� �̵�
                logger.info("���Ͼ��ε�1");
                return filePath; //������ ���� ���� ��� return
            }else{
                //���� �۾��� ������ �߻��� ��� ��õ�
                logger.info("���Ͼ��ε�2");
                if(file.renameTo(new File(afterFileRootPath))) {
                    return filePath; //������ ���� ���� ��� return
                }else {
                    //�׷��� ������ ��� �̵��� �ƴ� copy (�ٸ������� ������ ����ִ� ��찡 �ִ°� ����...)
                    logger.info("���� �̵� ���� copy�� ��ü");
                    // ������ ��� ������ �������ش�.
                    File copyFile = new File(beforeRootPath);
                    
                    // ����� ������ ��ġ�� �������ش�.
                    input  = new FileInputStream(copyFile);
                    output = new FileOutputStream(new File(afterFileRootPath));
                                 
                    int readBuffer = 0;
                    byte [] buffer = new byte[512];
                    while((readBuffer = input.read(buffer)) != -1) {
                        output.write(buffer, 0, readBuffer);
                    }
                    copyFile.delete();//TEMP ���� ����
                    logger.info("������ ����Ǿ����ϴ�.");
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
        } else if (browser.equals("Trident")) {       // IE11 ���ڿ� ���� ����
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
       } else if (header.indexOf("Trident") > -1) {   // IE11 ���ڿ� ���� ����
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

    // 2018-01-08 Ư�� �̹����� ������¡ �� �� �ֵ��� �Լ� ����
    public static byte[] convertToPngFormat(MultipartFile file, boolean resizeImage) throws Exception 
    {
        String contentType = file.getContentType();

        if (!MediaType.IMAGE_GIF_VALUE.equals(contentType)
                && !MediaType.IMAGE_JPEG_VALUE.equals(contentType)
                && !MediaType.IMAGE_PNG_VALUE.equals(contentType)) 
        {
            throw new Exception("�� �� ���� �̹��� �����Դϴ�.");
        }
        
        BufferedImage inputImage = ImageIO.read(file.getInputStream());

        // 2017-11-03 ���� �̹����� �����ϱ�� �����Ǿ�, �̹��� ������¡ ����� ����
        // BufferedImage outputImage = new BufferedImage(ScaleWidth, ScaleHeight, inputImage.getType());
        // Graphics2D g2 = outputImage.createGraphics();
        // g2.drawImage(inputImage, 0, 0, ScaleWidth, ScaleHeight, null);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        
        // 2018-01-08 �̹��� ������ ���ؼ� ©�� ;; ����
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
     * File������ MultipartFile�� ��ȯ
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
     * �ؽ�Ʈ Ư����ȣ ����
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

