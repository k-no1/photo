package kjh.common.service;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import kjh.common.dto.Constant;
import kjh.common.dao.FileDao;
import kjh.common.dto.CamelMap;
import kjh.common.dto.FileDto;
import kjh.common.util.CommonUtil;
import kjh.common.util.FileUtil;
import kjh.common.util.LogUtils;
import kjh.common.util.StringUtil;


@Service
public class FileSvc {
    private static final Logger logger = LoggerFactory.getLogger(FileSvc.class);
    @Value("${spring.staticFileRootPath}") private String staticFileRootPath;
    @Value("${spring.staticFilePath}") private String staticFilePath;
    @Value("${spring.staticFileTempPath}") private String staticFileTempPath;
    @Autowired private FileDao fileDao;

    public List<CamelMap> getFileList(CamelMap model) throws Exception{
        return null;
    }
    public CamelMap getFile(CamelMap model) throws Exception{
        return fileDao.getFile(model);
    }
    public int insertFile(CamelMap model) throws Exception{
        return fileDao.insertFile(model);
    }
    public int deleteFile(CamelMap model) throws Exception{
        
        return 0;
    }
    public int deleteCancelFile(CamelMap model) throws Exception{
        
        return 0;
    }
    public int deleteAllFileList(CamelMap model) throws Exception{
        return 0;
    }
    public int deleteFileList(CamelMap model) throws Exception{
        return 0;
    }
    
    /**
     * 파일다운로드 서비스
     *
     * @Method Name : fileDownLoad
     * @작성일 : 2019. 2. 14.
     * @작성자 : s1212919
     * @변경이력 : 
     * @Method 설명 : 
     * @param fileVO
     * @param request
     * @param response
     * @throws Exception
     *
     */
    public void fileDownLoad(FileDto fileDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.setProperty("java.awt.headless", "true"); 
        try {
            String filePath = fileDto.getFile_path();
            if(filePath != null && !"".equals(filePath)) {
                filePath = filePath.replace(".", " ");
                filePath = filePath.replace("\\", "");
                filePath = filePath.replace("&", " ");
                filePath = filePath.replaceAll(" ", "");
                fileDto.setFile_path(filePath);
            }
        
            
            String sysFilePath = fileDto.getSys_file_name();
            if(sysFilePath != null && !"".equals(sysFilePath)) {
                sysFilePath = sysFilePath.replace("\\.\\.", " ");
                sysFilePath = sysFilePath.replace("\\", "");
                sysFilePath = sysFilePath.replace("&", " ");
                sysFilePath = sysFilePath.replaceAll(" ", "");
                fileDto.setSys_file_name(sysFilePath);
            }
            
            logger.info("{} file path="+staticFileRootPath + fileDto.getFile_path()+"/"+fileDto.getSys_file_name(), LogUtils.toHeader(request));
            
            File uFile = new File(staticFileRootPath + fileDto.getFile_path()+"/"+fileDto.getSys_file_name());
            int fSize = (int) uFile.length();
    
            logger.debug("{} FILE DOWNLOAD:"+fSize, LogUtils.toHeader(request));
            logger.debug(staticFileRootPath);
            logger.debug(fileDto.getFile_path());
            logger.debug(fileDto.getSys_file_name());
            logger.debug(fileDto.getOrg_file_name());
    
            
            if (fSize > 0) {
                String mimetype = "application/x-msdownload";
                response.setContentType(mimetype);
                response.setContentLength(fSize);
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileDto.getOrg_file_name() + "\";");
                response.setHeader("Content-Transfer-Encoding", "binary");
                FileUtil.setDisposition(fileDto.getOrg_file_name(),request,response);
                BufferedInputStream in   = null;
                BufferedOutputStream out = null;
    
                try {
                    in = new BufferedInputStream(new FileInputStream(uFile));
                    out = new BufferedOutputStream(response.getOutputStream());
    
                    FileCopyUtils.copy(in, out);
                    out.flush();
                } catch (IOException ex) {
                    logger.error( "{}", LogUtils.toHeader(request), ex) ;
                } finally {
                    CommonUtil.close(in, out);
                    
                    // 파일 삭제
                    //FileUtil.deleteFileInf(fileDto.getSys_file_name(), staticFileRootPath, staticFileTempPath);
                    
                }
    
            } else {
                response.setContentType("application/x-msdownload");
    
                PrintWriter printwriter = response.getWriter();
    
                printwriter.println("<html>");
                printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fileDto.getOrg_file_name() + "</h2>");
                printwriter.println("</html>");
    
                printwriter.flush();
                printwriter.close();
            }
        }catch(Exception e) {
            logger.error( "{}", LogUtils.toHeader(request), e) ;
        }
    }
    
    /**
     * 상품 엑셀 다운로드
     * @param layoutName
     * @param excelSheetTitle
     * @param excelData
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void excelDownload(
            String layoutName,
            String excelSheetTitle,
            Map<String, Object> excelData, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {

        System.setProperty("java.awt.headless", "true");
        
        String fileExt = ".xlsx";//파일 확장자
        OutputStream os = null;
        
        try {
            // 실제파일 생성해서 저장
            String orginFileName       = (String) excelData.get("excelName") + fileExt;
            String systemFileName      = (String) excelData.get("systemFileName") + fileExt;
            String savePath            = staticFileRootPath + staticFileTempPath;
            
            //콤보박스 내용 선언
            String visibility[]        = {"노출","미노출"};
            String productType[]       = {"상품","옵션", "이미지", "텍스트"};
            String saleStatus[]        = {"정상","중지"};
            String YN[]                = {"Y","N"};
            String salesTarget[]       = {"일반","직원"};
            String useTax[]            = {"과세","면세"};
//            String saleAndAccumulate[] = {"미제휴","적립","할인","적립/할인"};
            
            //포인트 코드 처리용 선언 변수 ---------------------------------------- ST
            String pointUse                  = "";  //각 items 변수 대입
            String pointSave                 = "";  //각 items 변수 대입
            List<Object> pointUseArray       = null;//포인트 사용 초기화 선언 (영문코드 한글 치환 roof용)
            List<Object> pointSaveArray      = null;//포인트 저장 초기화 선언 (영문코드 한글 치환 roof용)
            String preFix                    = "";  // , 구분자 사용여부
            String pointUseChgYn             = "N"; //포인트 사용 단건 한글 치환 여부
            String pointSaveChgYn            = "N"; //포인트 저장 단건 한글 치환 여부
            //포인트 코드 처리용 선언 변수 ---------------------------------------- ED
            
            
            //파일 경로 존재 유무 체크 및 생성 ----ST
            File saveFolder = new File(savePath);
            
            if (!saveFolder.exists() || saveFolder.isFile()) {
                saveFolder.setExecutable(false, true);
                saveFolder.setReadable(true);
                saveFolder.setWritable(false, true);
                saveFolder.mkdirs();
            }
            //파일 경로 존재 유무 체크 및 생성 ----ED
    
            String filePath = savePath + Constant.SLASH + systemFileName;
            //system.out.println("======"+filePath);
            logger.debug("{} ===================================:"+filePath, LogUtils.toHeader(request));
            os =new FileOutputStream(filePath);
            // streaming workbook 생성 // 100 row마다 파일로 flush
            @SuppressWarnings("resource")
            Workbook wb = new SXSSFWorkbook(100);
            
            // S: 스타일
            // 헤더 스타일
            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 테두리 설정
            headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            Font font = wb.createFont(); // 폰트 조정 인스턴스 생성
            font.setBoldweight((short) 700);
            headerStyle.setFont(font);
            
            // 바디 스타일
            CellStyle bodyStyle = wb.createCellStyle();
            bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            // E: 스타일
            
            //헤더
            List<String> colName = new ArrayList<String>();
            colName              = colNameTitleLayout(layoutName);//layoutName 셋팅
            
            
            // 시트 생성
            String sheetName = excelSheetTitle;
    
            Sheet sheet = wb.createSheet(sheetName);
            
            // 타이틀부분 구현
            Row heading = sheet.createRow(0);
            
            for(int i = 0; i < colName.size(); i++) {
                sheet.setColumnWidth(i, 256 * 20);
                if(i == 0) {
                    Cell cell = heading.createCell(i);
                }
                Cell cell = heading.createCell(i+1);//1번째는 주석 자리
                cell.setCellStyle(headerStyle);
                cell.setCellValue(colName.get(i));
            }
            
            List<Map<String, Object>> colValue = (List<Map<String, Object>>) excelData.get("colValue");
            logger.debug("{} colValue.size:"+colValue.size(), LogUtils.toHeader(request));
            Map<String, Object> item = new HashMap<String, Object>();
            
             //틀고정
             sheet.createFreezePane(1, 1);
    
             //툴팁 comment 생성 ----------------------------------------------------------------------------------------------ST

             //포인트 코드 설명 text 생성
             CamelMap pointParam = new CamelMap();
             pointParam.put("groupId", "point_type");
             List <CamelMap> pointTypeCode = new ArrayList<>();
             
             String pointCodeInfo = "*포인트 코드종류:\n";
             
             for(int i=0;i<pointTypeCode.size();i++) {
                 pointCodeInfo += "※"+i+"th 코드설명 \n";
             }
             
             pointCodeInfo += "※"+0+"th 코드설명 \n";
             pointCodeInfo += "※"+1+"th 코드설명 \n";
             
             //툴팁 생성 텍스트
             String message  = "test:\n";
                    message += "셀을 서식복사하여 등록해주시고\n";
                    message += "(*) 표시는 반드시 입력 혹은 선택해야\n";
                    message += "하는 셀입니다.\n";
                    message += "\n";
                    message += "1.상품 구분(*): 상품,옵션, 이미지, 텍\n";
                    message += "스트 선택\n";
                    message += "2.관리명(*) : 텍스트 입력\n";
                    message += "3. 상품명(한국어(*),영어, 일본어, 중국\n";
                    message += "어): 텍스트 입력\n";
                    message += "4.판매상태(*): 정상,중지 선택\n";
                    message += "5.화면(*) : 노출, 미노출 선택\n";
                    message += "6.판매대상(*) : 일반,직원 선택\n";
                    message += "7.품절: Y(품절),N(품절 아님) 선택\n";
                    message += "8.원가, 매장가(*), 포장가 : 숫자 입력\n";
                    message += "9.매장가여부, 포장가여부 : Y(사용),N(미사용) 선택\n";
                    message += "10. 기본수량(*) : 숫자 입력\n";
                    message += "11. 최대 수량(*) : 숫자 입력\n";
                    message += "12. 티켓(식권) 출력갯수(*) : 숫자 입력\n";
                    message += "13. 세금(*) : 과세, 면세 선택\n";
                    message += "14. 이미지 : 이미지 삽입\n";
                    message += "15. 상품설명(한국어,영어 , 일본어, 중국어): 텍스트 입력\n";
                    message += "16. 쿠폰코드 : 텍스트 입력\n";
                    message += "17. 상품코드(SMARTO) : 텍스트 입력\n";
                    message += "18. 첨가물코드(SMARTO) : 텍스트 입력\n";
                    message += "19. 기타외부코드 : 텍스트 입력\n";
                    message += "20. 바코드 : 숫자 입력\n";
                    message += "21. 포인트 사용 : 아래내용 참조\n여러개 입력은\n,로 구분\n";
                    message += pointCodeInfo;
                    message += "22. 포인트 적립 : 아래코드 참조\n여러개 입력은\n,로 구분\n";
                    message += pointCodeInfo;
                    message += "23. 상품태그 : #텍스트 입력 #텍스트 입력\n";
                    message += "(데이터 없을 시, 기본 템플릿은 100줄까지 제공됩니다.)\n";
             
             Row row = sheet.createRow(1);//상품구분
                 row.createCell(0);
                 
             for(int i = 0 ; i > 5;i++) {
                 row.createCell(i+1);
             }
             Drawing drawing        = row.getSheet().createDrawingPatriarch();
             CreationHelper factory = row.getSheet().getWorkbook().getCreationHelper();
             ClientAnchor anchor    = factory.createClientAnchor();
             
             anchor.setCol1(0);
             anchor.setCol2(1);
             anchor.setRow1(1);
             anchor.setRow2(63);
             
             
             Comment comment        = drawing.createCellComment(anchor);
             RichTextString str     = factory.createRichTextString(message);
             
             comment.setVisible(true);
             comment.setString(str);
             comment.setRow(2);
             //comment 생성 ----------------------------------------------------------------------------------------------ED
             
             
             //drop down box 구성 ------------------------------------------------------------------------------------------ST
             if(colValue.size() > 0) {
                 //상품구분 select box 구성
                 DataValidationHelper dvHelper = sheet.getDataValidationHelper();
                 XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(productType);
                 CellRangeAddressList addressList = new CellRangeAddressList(1, colValue.size(), 1, 1);
                 XSSFDataValidation validation= (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //판매상태 select box 구성
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(saleStatus);
                 addressList  = new CellRangeAddressList(1, colValue.size(), 7, 7);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //품절여부
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(YN);
                 addressList  = new CellRangeAddressList(1, 100, 8, 8);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //화면
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(visibility);
                 addressList  = new CellRangeAddressList(1, colValue.size(), 9, 9);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //판매대상 select box 구성
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(salesTarget);
                 addressList  = new CellRangeAddressList(1, colValue.size(), 10, 10);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //매장가 여부
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(YN);
                 addressList  = new CellRangeAddressList(1, 100, 13, 13);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //포장가 여부
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(YN);
                 addressList  = new CellRangeAddressList(1, 100, 15, 15);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //세금 select box 구성
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(useTax);
                 addressList  = new CellRangeAddressList(1, colValue.size(), 19, 19);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 sheet.addValidationData(validation);
                 
             }else { //템플릿으로 다운받을 경우
               //상품구분 select box 구성
                 DataValidationHelper dvHelper = sheet.getDataValidationHelper();
                 XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(productType);
                 CellRangeAddressList addressList = new CellRangeAddressList(1, 100, 1, 1);
                 XSSFDataValidation validation= (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //판매상태 select box 구성
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(saleStatus);
                 addressList  = new CellRangeAddressList(1, 100, 7, 7);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //품절여부
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(YN);
                 addressList  = new CellRangeAddressList(1, 100, 8, 8);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //화면
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(visibility);
                 addressList  = new CellRangeAddressList(1, 100, 9, 9);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //판매대상 select box 구성
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(salesTarget);
                 addressList  = new CellRangeAddressList(1, 100, 10, 10);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //매장가 여부
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(YN);
                 addressList  = new CellRangeAddressList(1, 100, 13, 13);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //포장가 여부
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(YN);
                 addressList  = new CellRangeAddressList(1, 100, 15, 15);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 validation.createErrorBox("오류", "오류");
                 validation.setShowErrorBox(true);
                 sheet.addValidationData(validation);
                 
                 //세금 select box 구성
                 dvHelper = sheet.getDataValidationHelper();
                 dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(useTax);
                 addressList  = new CellRangeAddressList(1, 100, 19, 19);
                 validation   = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                 sheet.addValidationData(validation);
                 
             }
            //drop down box 구성 ------------------------------------------------------------------------------------------ED
             
            //DATA 리스트 구성
            for(int i=0; i<colValue.size(); i++){
                item     = colValue.get(i);
                row      = sheet.createRow(1 + i);
                int nCnt = 0;
                
                for(int j=0; j<colName.size(); j++){
                    sheet.setColumnWidth(j, 256 * 20);
                }
                
                //이미지업로드,
                
                // 번호
    //            row.createCell(nCnt++).setCellValue(i+1);
                // 문자
                //row.createCell(nCnt++).setCellValue(String.valueOf(item.get("SAMPLE_ID")));
                // 숫자
    //            row.createCell(nCnt++).setCellValue((StringUtil.nvl(new BigDecimal(String.valueOf(item.get("SAMPLE_ID"))), bdnum)).doubleValue());

                nCnt++;//1번째열은 건너뛴다.
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productType")));   //상품구분 1
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productMgrName")));//관리명 2
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productKr")));     //상품명 한국어 3
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productEn")));     //상품명 영어 4
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productJp")));     //상품명 일본어 5
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productCn")));     //상품명 중국어 6
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("saleStatus")));    //판매상태 7
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("soldoutYn")));     //품절여부 8
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("visibility")));    //화면여부 9
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("salesTarget")));   //판매대상 10
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("primeCost")));     //원가 11
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("storePrice")));    //매장가 12
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("isStore")));       //매장가 여부 13 (new)
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("packingPrice")));  //포장가 14
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("isPacking")));     //포장가 여부 15 (new)
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("baseSaleQty")));   //기본수량 16
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("maxSaleQty")));    //최대수량 17
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("outputQty")));     //최대수량 18
                
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("useTax")));        //과세여부(세금) 19
                
                //이미지 처리
                row.createCell(nCnt++).setCellValue("");//이미지 들어갈 공간 객체 생성
    
                row.setHeight((short) 2500);
                sheet.setColumnWidth(nCnt,2500);
                
                String realPath = String.format( "%s%s/%s", staticFileRootPath, (String) item.get( "filePath" ), (String) item.get( "sysFileName")) ;

                File getFile = new File( realPath );

                if(item.get("realPath") != null) 
                {
                    logger.info("{} 이미지 경로"+i+"="+realPath, LogUtils.toHeader(request));
                }

                if (getFile.exists()) 
                {
                    if (getFile.canRead()) {
    
                        byte[] image    = Files.readAllBytes(getFile.toPath());
                        Path source     = Paths.get(realPath);
                        String mimeType = Files.probeContentType(source);
                        int pictureType = 0; 
                        
                        if(mimeType.indexOf("image") > -1) {
                            if(mimeType.equalsIgnoreCase("image/gif")) {
                                pictureType = Workbook.PICTURE_TYPE_JPEG;
                                logger.info("{} gif...", LogUtils.toHeader(request));
                            }else if(mimeType.equalsIgnoreCase("image/png")) {
                                pictureType = Workbook.PICTURE_TYPE_PNG;
                                logger.info("{} png...", LogUtils.toHeader(request));
                            }else if(mimeType.equalsIgnoreCase("image/jpeg")) {
                                pictureType = Workbook.PICTURE_TYPE_JPEG;
                                logger.info("{} jpg...", LogUtils.toHeader(request));
                            }else if(mimeType.equalsIgnoreCase("image/bmp")) {
                                pictureType = Workbook.PICTURE_TYPE_DIB;
                                logger.info("{} bmp...", LogUtils.toHeader(request));
                            }else if(mimeType.equalsIgnoreCase("image/emf")) {
                                pictureType = Workbook.PICTURE_TYPE_EMF;
                                logger.info("{} emf...", LogUtils.toHeader(request));
                            }else {
                                logger.info("{} 여기로 타면 미분류임...", LogUtils.toHeader(request));
                                pictureType = Workbook.PICTURE_TYPE_JPEG;
                            }
                        }
    
                        int pictureIdx         = wb.addPicture(image, pictureType);

                        //로컬 환경 예외처리
                        if(staticFileRootPath.indexOf(":/") > -1) {
                            realPath = realPath.replaceAll("/", "\\\\");
                            logger.info("{} realPath 로컬 path="+realPath, LogUtils.toHeader(request));
                        }
                        
                        int w = 150; //넓이
                        int h = 150; //높이
    
                        CreationHelper helper  = wb.getCreationHelper();
                        Drawing drawingImg     = sheet.createDrawingPatriarch();
                        ClientAnchor imgAnchor = helper.createClientAnchor();
                        
                        imgAnchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);
                        imgAnchor.setDx1(0);
                        imgAnchor.setDx2(1000);
                        imgAnchor.setDy1(0);
                        imgAnchor.setDy2(1000);
                        imgAnchor.setRow1(row.getRowNum());
                        imgAnchor.setRow2(row.getRowNum());
                        imgAnchor.setCol1(nCnt-1);
                        imgAnchor.setCol2(nCnt-1);
                        
                        Picture pict = drawingImg.createPicture(imgAnchor, pictureIdx);
                        pict.resize(w/100, h/100);
    
                        logger.info("{} 정상적으로 그렸음 ^______^", LogUtils.toHeader(request));
                    }
                }
                else 
                {
                    if(item.get("realPath") != null) 
                    {
                        //로컬 환경 예외처리
                        if(staticFileRootPath.indexOf(":/") > -1) 
                        {
                            realPath = realPath.replaceAll("/", "\\\\");
                            logger.info("{} realPath 로컬 path="+realPath, LogUtils.toHeader(request));
                        }
                        logger.info("{} 이미지 찾을수 없음 ~ㅠ/ㅠ~"+i+"="+realPath, LogUtils.toHeader(request));
                    }
                }
                
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productExpKr")));  //상품설명 - 한국어
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productExpEn")));  //상품설명 - 영문
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productExpJp")));  //상품설명 - 일본어
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productExpCn")));  //상품설명 - 중국어
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("couponCd")));      //쿠폰코드
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("extr2Cd")));       //상품코드
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("extr3Cd")));       //첨가물코드
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("extrCd")));        //기타외부코드
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("barcodeCd")));     //바코드
                
                //포인트 사용 코드 한글 치환 로직 삽입 2019.10.17
                pointUse = StringUtil.codeArrayTextChange(pointTypeCode, String.valueOf(item.get("pointUse")));
                
                row.createCell(nCnt++).setCellValue(pointUse);//(터칭제휴->포인트 사용) 으로 변경 2019.10.14
                
                //포인트 적립 코드 한글 치환 로직 삽입 2019.10.17
                pointSave = StringUtil.codeArrayTextChange(pointTypeCode, String.valueOf(item.get("pointSave")));

                row.createCell(nCnt++).setCellValue(pointSave);//(터칭제휴->포인트 적립) 으로 변경 2019.10.14
                
                row.createCell(nCnt++).setCellValue(String.valueOf(item.get("productTags")));   //상품태그
            }
    
            logger.info("{} Excel 포 끝", LogUtils.toHeader(request));
            
            // 반드시 종료
            wb.write(os);
            os.close();
            ((SXSSFWorkbook)wb).dispose();
            
            // 생성한 파일을 다운로드 함
            FileDto fileDto = new FileDto();
            
            fileDto.setFile_path(staticFileTempPath);
            fileDto.setOrg_file_name(orginFileName);
            fileDto.setSys_file_name(systemFileName);
            
            // 파일 다운
            fileDownLoad(fileDto, request, response);
        }catch (Exception e) {
            logger.error("{}", LogUtils.toHeader(request), e );
        }finally {
            os.close();
        }
    }
    
    
    public List<String> colNameTitleLayout(String code){
        List<String> colName = new ArrayList<String>();
        
        if("product".equalsIgnoreCase(code)) {
            colName.add("상품 구분(*)");       
            colName.add("관리명(*)");         
            colName.add("상품명(한국어*)");    
            colName.add("상품명(영어)");        
            colName.add("상품명(일본어)");      
            colName.add("상품명(중국어)");      
            colName.add("판매상태(*)");        
            colName.add("품절(*)");           
            colName.add("화면(*)");           
            colName.add("판매대상(*)");         
            colName.add("원가");
            colName.add("매장가(*)");
            colName.add("매장가 사용 여부");
            colName.add("포장가");
            colName.add("포장가 사용 여부");
            colName.add("기본수량(*)");
            colName.add("최대수량(*)");
            colName.add("티켓(식권) 출력갯수(*)");
            colName.add("세금(*)");
            colName.add("이미지");
            colName.add("상품설명(한국어)");
            colName.add("상품설명(영어)");
            colName.add("상품설명(일본어)");
            colName.add("상품설명(중국어)");
            colName.add("쿠폰코드");
            colName.add("상품코드(SMARTRO)");
            colName.add("첨가물코드(SMARTRO)");
            colName.add("기타외부코드");
            colName.add("바코드");
            colName.add("포인트사용");//터칭제휴->포인트사용 으로 변경 2019.10.14
            colName.add("포인트적립");//단골제휴->포인트적립 으로 변경 2019.10.14
            colName.add("상품태그");
        }
        return colName;
    }
    
    public List<String> colColumnLayout(String code){
        List<String> colName = new ArrayList<String>();
        
        if("product".equalsIgnoreCase(code)) {
       
        }
        return colName;
    }
    
    
    /**
     * File형식을 MultipartFile로 변환
     * 
     * @param File file
     */
    public MultipartFile fileToMultipartFile(File file) throws Exception, IOException {
        FileItem fileItem       = null;
        InputStream inputStream = null;
        try {
            fileItem = new DiskFileItem(null, "image/png", false, file.getName(), (int) file.length(), file.getParentFile());
       
            inputStream = new FileInputStream(file);
            OutputStream outputStream = fileItem.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            
        }
        catch (FileNotFoundException e) 
        {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), e) ;
        }
        catch (IOException e) 
        {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), e) ;
        }finally {
            inputStream.close();
        }
        
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        
        return multipartFile;
    }
	public String fileRealPathSelect(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}
        
}
