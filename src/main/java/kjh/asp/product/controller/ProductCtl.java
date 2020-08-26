package kjh.asp.product.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kjh.asp.product.service.ProductSvc;
import kjh.common.dto.CamelMap;
import kjh.common.dto.Constant;
import kjh.common.dto.ObjectMap;
import kjh.common.dto.ProductExcel;
import kjh.common.dto.ProductSearch;
import kjh.common.dto.Token;
import kjh.common.dto.UniqId;
import kjh.common.service.FileSvc;
import kjh.common.util.CommonUtil;
import kjh.common.util.ExcelReadOption;
import kjh.common.util.FileUtil;
import kjh.common.util.LogUtils;
import kjh.common.util.StringUtil;
import net.sf.json.JSONObject;
import kjh.common.dto.Product;




@Controller
@RequestMapping(value="/asp/pd")
public class ProductCtl {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductCtl.class);
    DecimalFormat fmt                  = new DecimalFormat("0.###");

    @Autowired ProductSvc              productSvc;
    @Autowired FileSvc                 fileSvc;
    @Value("${spring.staticFileRootPath}") private String staticFileRootPath;
    @Value("${spring.staticFilePath}")     private String staticFilePath;
    @Value("${spring.staticFileTempPath}") private String staticFileTempPath;
    @Value("${operation.mode}")            private String operationMode;
    
    
    /**
     * 리스트 조회
     * @param request
     * @param response
     * @param session
     * @param search
     * @param ListChkSelection
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pdms010")
    public ModelAndView ProductListView(HttpServletRequest request, HttpServletResponse response, HttpSession session
                                       ,@ModelAttribute ProductSearch search
                                       ) throws Exception  
    {

        ModelAndView mv      = new ModelAndView();        
        ObjectMap map        = new ObjectMap();        
        ObjectMap productMap = new ObjectMap();
        ArrayList list       = new ArrayList<>();

        try {

            //test data
            productMap.put("productId"      , "1");
            productMap.put("productMgrName" , "달콤커피");
            productMap.put("productName"    , "달달커피");
            productMap.put("categoryMgrName", "커피");
            productMap.put("productType"    , "상품타입");
            productMap.put("soldoutYn"      , "Y");
            productMap.put("cnt"            , 1);

            list.add(0, productMap);
            
            map.put("dataList", list);
            
            mv.addObject("auth", "ADM");
            mv.addObject("data", map);
            
            if(null != (String)request.getParameter("franchiseId") && !"".equals((String)request.getParameter("franchiseId"))) {            
                mv.addObject("franchiseId", (String)request.getParameter("franchiseId"));
            }
        
        } catch (Exception e) {
            logger.error( "{}", LogUtils.toHeader(request), e ) ;
            //throw new Exception(e.getMessage(),e);
        }

        mv.addObject("search", search);
        mv.setViewName("/product/product/product_list");
        
        return mv;
    }
    
    
    /**
     * @param request
     * @param headOfficeId
     * @param franchiseId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pdmi090")
    public ModelAndView productExcelUpload(MultipartHttpServletRequest multiRequest) throws Exception {

        ModelAndView mav          = new ModelAndView();
        mav.setViewName("jsonView");
        
        Token token                                = (Token) multiRequest.getAttribute(Constant.ACCESS_TOKEN);
        
        JSONObject json                            = new JSONObject();
        JSONObject returnjson                      = new JSONObject();
        
        ProductExcel productExcel                  = new ProductExcel();
        
        String uniqId                              = UniqId.getID();
        String realPath                            = "";
        String errorMsg                            = "";
        
        FileInputStream fis                        = null;
        
        List<HashMap<String,String>> excelList     = new ArrayList<>();

        CommonUtil.getReturnCodeFail(returnjson);
        
        try {
                Iterator<String> fileIterator = multiRequest.getFileNames(); 
                while (fileIterator.hasNext()) {

                    MultipartFile mFile = multiRequest.getFile((String) fileIterator.next());
                    
                    logger.info("{} getContentType="+mFile.getContentType(), LogUtils.toHeader(multiRequest));
                    logger.info("{} getOriginalFilename="+mFile.getOriginalFilename(), LogUtils.toHeader(multiRequest));
                    logger.info("{} name:"+mFile.getName(), LogUtils.toHeader(multiRequest));
                    logger.info("{} size:"+mFile.getSize(), LogUtils.toHeader(multiRequest));

                    String fix_data = staticFileRootPath+staticFileTempPath;//property �슫�쁺 媛쒕컻
                    
                    if (mFile.getSize() > 0) {  
                        
                        //파일 업로드
                        realPath = FileUtil.uploadFile(fix_data, mFile.getOriginalFilename(), mFile.getBytes(),uniqId);
                        CamelMap map    = new CamelMap();
                        
                        logger.info("{} realPath="+realPath, LogUtils.toHeader(multiRequest));

                        String dbPath   = realPath.replace(staticFileRootPath.replace(Constant.SLASH, Constant.BACK_SLASH),"")
                                                  .replace(staticFileRootPath,"")
                                                  .replace(Constant.BACK_SLASH,Constant.SLASH);

                       
                        int pos               = dbPath.lastIndexOf(Constant.DOT);
                        int filePos           = dbPath.lastIndexOf(Constant.SLASH);
                        
                        //확장자
                        String ext            = dbPath.substring( pos + 1 );
                        
                        //sha2 hash
                        StringBuffer sha2Value = FileUtil.getSha2Hash(realPath);
                        
                    }
                } 

                ExcelReadOption excelReadOption    = new ExcelReadOption();
                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
                Date currentTime                   = new Date ();
                excelReadOption.setFilePath(realPath);

                fis = new FileInputStream(realPath);
                @SuppressWarnings("resource")
                XSSFWorkbook workbook = new XSSFWorkbook(fis);

                int totalSheet = workbook.getNumberOfSheets();
                logger.info("{} totalSheet="+totalSheet, LogUtils.toHeader(multiRequest));
                
                //�뿊���떆�듃 媛��닔 �삁�쇅泥섎━
                if(totalSheet!=1) { throw new NotOfficeXmlFileException("**** 시트가 존재하지 않습니다. **** "); } 
                
                //SHEET �떆�옉----------------------------------------------------------------------------------------------------------------ST
                for (int curSheetI = 0; curSheetI < totalSheet; curSheetI++) {
                    XSSFSheet curSheet = workbook.getSheetAt(curSheetI);
                    int totalRow       = curSheet.getPhysicalNumberOfRows(); 
                    //濡쒖슦(ROW) �떆�옉 ------------------------------------------------------------ST
                    for (int curRowI = 1; curRowI < totalRow; curRowI++) { 
                        XSSFRow curRow = curSheet.getRow(curRowI); 
                        if (curRow == null ) { continue; } // ROW
                        
                        if (curRow.getCell(1) == null)  { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(2) == null)  { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(3) == null)  { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(7) == null)  { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(8) == null)  { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(9) == null)  { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(10) == null) { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(12) == null) { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(16) == null) { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(17) == null) { errorMsg = curRowI+"th"; break; }
                        if (curRow.getCell(19) == null) { errorMsg = curRowI+"th"; break; }
                        
                        HashMap<String, String> item = new HashMap<String, String>();
 
                        int totalCell = 32;//셀 갯수
                        
                        //Cell 생성 ----------------------------------------ST
                        for (int curColumnI = 1; curColumnI <= totalCell; curColumnI++) 
                        { 
                            XSSFCell curCell = curRow.getCell(curColumnI);
                            
                            if (curSheetI == 0) 
                            {
                                setSheet1Column(productExcel, item, curCell, curColumnI); 
                            }
                        }
                        //Cell 생성 -----------------------------------------ED

                        //泥ル쾲吏� �떆�듃�쓽 媛� LIST �떞湲�
                        if (curSheetI == 0) {
                            item.put("headOfficeId", multiRequest.getParameter("headOfficeId"));
                            item.put("franchiseId" , multiRequest.getParameter("franchiseId") == null ? "" : multiRequest.getParameter("franchiseId").toString());
                            item.put("createdBy"   , token.getTokenUserId());
                            item.put("updatedBy"   , token.getTokenUserId());
                            excelList.add(item); 
                        }
                        
                    } // Row 생성 ---------------------------------------------------------------ED
                    
                } // SHEET 생성 --------------------------------------------------------------------------------------------------------------ED
            
                
                currentTime = new Date();
                logger.info("{} QUERY END : "+ mSimpleDateFormat.format(currentTime).toString(), LogUtils.toHeader(multiRequest));

                CommonUtil.getReturnCodeSuc(json);
                CommonUtil.getReturnCodeSuc(returnjson);
        } catch (Exception e) {
            logger.error( "{}", LogUtils.toHeader(multiRequest), e ) ;
            if("".equals(errorMsg)) {
                errorMsg = "정의되지 않은 오류가 발생했습니다.";
            }
            CommonUtil.getReturnCodeFail(json);
        }finally{
            if(fis != null) {
                try
                {
                    fis.close();
                } catch (IOException e)
                {
                    logger.error( "{}", LogUtils.toHeader(multiRequest), e ) ;
                    throw new Exception(e.getMessage(),e);
                }
            }
        }
        
        json.put("returnjson"     , returnjson);
        json.put("errorMsg"       , errorMsg);
        mav.addObject("resultJson", json);

        return mav;
    }
    
    /**
     * 첫번째 SHEET (카테고리) 정보 읽어오기
     * 
     * @param excelUploadResult - 에러 결과 리스트
     * @param HashMap<String, String> item - param 
     * @param XSSFCell cell - 셀 값
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private HashMap<String, String> setSheet1Column(ProductExcel excelUploadResult , HashMap<String, String> item, XSSFCell cell, int cellIdx) throws Exception {

        String value = "";
        
        try {

            

            if (cell == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
                value = "";
            else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                value = cell.getStringCellValue();
            else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                value = fmt.format(cell.getNumericCellValue()) + "";

            switch (cellIdx) {
                case 1: // 상품 구분
                    value = value.trim().equals("상품") ? "P" : 
                            value.equals("옵션")        ? "O" : 
                            value.equals("이미지")      ?  "I" : 
                            value.equals("텍스트")      ?  "T" 
                            :"";
                     item.put("productType", value);
                    break;

                case 2: // 관리명
                    item.put("productMgrName", value); 
                    break;
                    
                case 3: // 상품명 (한국어)
                    item.put("productKr", value);
                    break;

                case 4: // 상품명 (영어)
                    item.put("productEn", value);
                    break;
        
                case 5: // 상품명(일본어)
                    item.put("productJp", value);
                    break;
        
                case 6: // 상품명(중국어)
                    item.put("productCn", value);
                    break;
                
                case 7: // 판매상태
                    value = value.trim().equals("정상") ? "ACTIVE" : "INACTIVE";
                    item.put("saleStatus", value);
                    break;
                    
                case 8: // 품절여부
                    value = value.trim().equals("Y") ? "TRUE" : "FALSE";
                    item.put("soldoutYn", value);
                    break;
                    
                case 9: // 화면여부
                    value = value.trim().equals("노출") ? "VISIBLE" : "HIDDEN";
                    item.put("visibility", value);
                    break;
                case 10: // 판매다상
                    value = value.trim().equals("일반") ? "G" : "S";
                    item.put("salesTarget", value);
                    break;
                    
                case 11: // 원가
                    if( StringUtil.isEmpty( value ))
                    {
                        value = "0" ;
                    }
                    
                    item.put("primeCost", value);
                    break;
                    
                case 12: // 매장가
                    if("0".equalsIgnoreCase(value) ||
                       "".equalsIgnoreCase(value)
                    ) {
                        item.put("storePrice", "0");
                    }else {
                        item.put("storePrice", value);
                    }
                    break;
                case 13: // 매장가 사용여부
                    value = value.trim().equals("Y") ? "TRUE" : "FALSE";
                    item.put("isStore"   , value);
                    break;
                case 14: // 포장가
                    if("0".equalsIgnoreCase(value) ||
                       "".equalsIgnoreCase(value)
                     ) {
                         item.put("packingPrice", "0");
                     }else {
                         item.put("packingPrice", value);
                     }
                    break;
                case 15: // 포장가 사용 여부
                    value = value.trim().equals("Y") ? "TRUE" : "FALSE";
                    item.put("isPacking"   , value);
                    break;
                    
                case 16: // 기본수량
                	if("0".equalsIgnoreCase(value) ||
                	   "".equalsIgnoreCase(value)
                    ) {
                		item.put("baseSaleQty", "1");
                    }else {
                    	item.put("baseSaleQty", value);
                    }
                    
                    break;
                    
                case 17: // 최대수량
                    item.put("maxSaleQty", value);
                    break;
                    
                case 18: // 출력갯수
                    item.put("outputQty", value);
                    break;
                    
                case 19: // 과세여부(세금)
                    value = value.trim().equals("과세") ? "TRUE" : "FALSE";
                    item.put("useTax", value);
                    break;
                    
                case 20: //이미지 컬럼
                    break;
                
                case 21: //상품설명 한국어
                    item.put("productExpKr", value);
                    break;
                    
                case 22: //상품설명 영어
                    item.put("productExpEn", value);
                    break;
                    
                case 23: //상품설명 일본어
                    item.put("productExpJp", value);
                    break;
                    
                case 24: //상품설명 중국어
                    item.put("productExpCn", value);
                    break;
                    
                case 25: //쿠폰코드
                    item.put("couponCd", value);
                    break;
                    
                case 26: //상품코드
                    item.put("extr2Cd", value);
                    break;
                    
                case 27: //첨가물코드
                    item.put("extr3Cd", value);
                    break;
                    
                case 28: //기타외부코드
                    item.put("extrCd", value);
                    break;
                    
                case 29: //바코드
                    item.put("barcodeCd", value);
                    break;
                case 30: //터칭제휴->포인트 사용
                    item.put("pointUse",value);
                    break;
                case 31: 
                    item.put("pointSave",value);
                    break;
                case 32: //상품태그
                    if(value.indexOf("#") > -1) {
                        item.put("productTags", value);
                    }else {
                        item.put("productTags", "");
                    }
                    break;
                default:break;
            }
        }catch(Exception e) {
            throw new Exception(e.getMessage(),e);
        }
        return item;
    }
    
    /**
     * 상품관리 엑셀 템플릿 다운로드
     * 
     * @param product
     * @param request
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(value="/pdms100")
    public void productExcelTempleteDownload(@ModelAttribute Product product, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        
        SimpleDateFormat formtter = new SimpleDateFormat("yyyyMMdd");
        Date date                 = new Date();
        
        String str                = formtter.format(date);
        String excelFileName      = "";
        String excelSheetTitle    = "";
        String layoutName         = "";
        
        // 엑셀생성자
        Map<String, Object> excelData = new HashMap<>();
        List<CamelMap> resultList     = new ArrayList<>();
        
        try {
                resultList = productSvc.getProductTemplete(product);
                
                // 파일명이나 탭명에 / 있으면 생성중 오류발생해서 꼭 치환해줘야함
                excelFileName   = "상품관리템플릿";
                excelSheetTitle = "상품관리템플릿";
                layoutName      = "product";
                
                logger.info("excelFileName:"+ excelFileName+"_"+str);
                
                excelData.put("excelName", excelFileName+"_"+str);
                excelData.put("systemFileName", "excelDownload_"+CommonUtil.getNowTime());
                excelData.put("colValue", resultList);

                // excel 생성
                fileSvc.excelDownload(layoutName,excelSheetTitle,excelData, request, response);
                
        } catch (Exception e) {
            logger.error( "{}", LogUtils.toHeader(request), e ) ;
            throw new Exception(e.getMessage(),e);
        }
    }
}
