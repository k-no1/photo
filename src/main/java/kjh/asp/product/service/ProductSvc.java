package kjh.asp.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kjh.asp.product.dao.ProductAllCopyDao;
import kjh.asp.product.dao.ProductCopyDao;
import kjh.asp.product.dao.ProductDao;
import kjh.asp.product.dao.ProductDetailHeadOfficeDao;
import kjh.asp.product.dao.ProductExcelDao;
import kjh.common.dao.FileDao;
import kjh.common.dto.CamelMap;
import kjh.common.dto.CommonDto;
import kjh.common.dto.CommonForm;
import kjh.common.dto.Constant;
import kjh.common.dto.ObjectMap;
import kjh.common.dto.Product;
import kjh.common.dto.ProductExcel;
import kjh.common.dto.Token;
import kjh.common.dto.UniqId;
import kjh.common.util.CommonUtil;
import kjh.common.util.FileUtil;
import kjh.common.util.LogUtils;
import kjh.common.util.ObjectUtils;
import kjh.common.util.PagingUtil;
import kjh.common.util.StringUtil;

@Service
public class ProductSvc {
    
    @Autowired ProductDao productDao;
    @Autowired ProductCopyDao productCopyDao;
    @Autowired ProductExcelDao productExcelDao;
    @Autowired ProductAllCopyDao productAllCopyDao;
    @Autowired ProductDetailHeadOfficeDao productDetailHeadOfficeDao;
    @Autowired FileDao fileDao;

    @Value("${spring.staticFilePath}") private String staticFilePath;
    @Value("${spring.staticFileRootPath}") private String staticFileRootPath;
    @Value("${spring.staticFileTempPath}") private String staticFileTempPath;
    
    private static final Logger logger = LoggerFactory.getLogger(ProductSvc.class);
    DateFormat dateFormat              = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DecimalFormat fmt                  = new DecimalFormat("0.###");

    /**
     * �긽�뭹 �긽�꽭�젙蹂� 議고쉶
     * @param model
     * @return
     * @throws Exception
     */
    public CamelMap selectGroupDetailInfo(Product model) throws Exception 
    {
        
        CamelMap result = new CamelMap(); 
        try {
            CamelMap param  = ObjectUtils.to(model);
            
            result.put("manageInfo", productDao.selectManageInfo(param));
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return result;
    }
    
    /**
     * 蹂몄궗 �긽�뭹 �긽�꽭�젙蹂� 議고쉶
     * @param model
     * @return
     * @throws Exception
     */
    public CamelMap prodcutDetailHeadOfficeSelect(CamelMap model) throws Exception 
    {
        CamelMap returnCamel = new CamelMap(); 
        try {
            returnCamel = productDetailHeadOfficeDao.prodcutDetailHeadOfficeSelect(model);
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return returnCamel;
    }
    
    /**
     * 移댄뀒怨좊━ 由ъ뒪�듃
     * @param model
     * @return
     * @throws Exception
     */
    public ObjectMap categoryList(Product model) throws Exception 
    {
        
        ObjectMap result = new ObjectMap(); 
        
        try {
            
            CamelMap param              = ObjectUtils.to(model);
            List<CamelMap> categoryList = productDao.getCategoryList(param);
            
            result.put("categoryList"   , categoryList);
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return result;
    }
    
    /**
     * �긽�뭹 紐⑸줉�쓣 議고쉶�빀�땲�떎.
     * 
     * @param model.headOfficeId - �넗�겙 蹂몄궗 �븘�씠�뵒
     * @param model.[franchiseId] - �넗�겙 媛�留뱀젏 �븘�씠�뵒
     */
    public ObjectMap getProductList(Product map) throws Exception {
        
        CommonDto commonDto   = new CommonDto();
        CommonForm commonForm = new CommonForm();
        ObjectMap result      = new ObjectMap();
        
        try {
            
            CamelMap model        = ObjectUtils.to(map);
            int totalCnt = productDao.getProductListCnt(model);
            
            commonForm.setFunctionName("f_goPage");
            
            if (0 < totalCnt) {//議고쉶�뜲�씠�꽣 議댁옱�떆�뿉留� 湲�踰덊샇瑜� �옉�꽦�븳�떎.
                
                commonForm.setCurrentPageNo(map.getCurrentPageNo());
                commonForm.setCountPerPage(map.getCountPerPage());
                commonForm.setCountPerList(map.getCountPerList());
                commonForm.setTotalListCount(totalCnt);
                
                commonDto = PagingUtil.setPageUtil(commonForm);
                
                model.put("limit", commonDto.getLimit());
                model.put("offset",commonDto.getOffset());
                
            }else {
                
                model.put("limit", 10);
                model.put("offset",0);
                
            }
            
            List<CamelMap> dataList     = productDao.getProductList(model);
            List<CamelMap> categoryList = productDao.getCategoryList(model);
            
            result.put("paging"        ,  commonDto.getPagination());
            result.put("currentPageNo" ,  commonForm.getCurrentPageNo());
            result.put("countPerPage"  ,  commonForm.getCountPerPage());
            result.put("countPerList"  ,  commonForm.getCountPerList());
            result.put("totalPageCount",  commonDto.getTotalPageCount());
            result.put("dataList"       , dataList);
            result.put("categoryList"   , categoryList);
            result.put("cnt"            , totalCnt);
            
        }catch(Exception e) {
            throw new Exception(e.getMessage(),e);
        }
        return result;
    }
    
    /**
     * �긽�뭹,�긽�뭹�긽�꽭 議곌굔�뿉 �빐�떦�븯�뒗 �쟾泥대궡�슜 議고쉶
     * @param map
     * @return
     * @throws Exception
     */
    public List<CamelMap> getProductAllList(Product map) throws Exception {
        
        CamelMap model                 = new CamelMap(); 
        List<CamelMap> returnCamelList = new ArrayList<CamelMap>(); 
        
        try {
            model           = ObjectUtils.to(map);
            returnCamelList = productDao.getProductAllList(model);
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        
        return returnCamelList;
    }
    
    /**
     * �긽�뭹 �긽�꽭瑜� 議고쉶�빀�땲�떎.
     * 
     * @param model.headOfficeId - �넗�겙 蹂몄궗 �븘�씠�뵒
     * @param model.[franchiseId] - �넗�겙 媛�留뱀젏 �븘�씠�뵒
     */
    public CamelMap productDetailSearch(CamelMap model) throws Exception {

        CamelMap returnCamel = new CamelMap(); 
        try {
            returnCamel = productDao.productDetailSearch(model);
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return returnCamel;
    }
    
    /**
     * 媛�留뱀젏 �꽑�깮 由ъ뒪�듃 議고쉶 (�긽�뭹蹂듭궗)
     * @param model
     * @return
     * @throws Exception
     */
    public List<CamelMap> productCopyFranchiseChoiceSelect(CamelMap model) throws Exception {
        List<CamelMap> returnCamelList = new ArrayList<CamelMap>(); 
        
        try {
            returnCamelList = productDao.productCopyFranchiseChoiceSelect(model);
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }

        return returnCamelList;
    }
    
    /**
     * 移댄뀒怨좊━ �꽑�깮 由ъ뒪�듃 議고쉶 (�긽�뭹蹂듭궗)
     * @param model
     * @return
     * @throws Exception
     */
    public List<CamelMap> productCopyCategoryChoiceSelect(CamelMap model) throws Exception {
        List<CamelMap> returnCamelList = new ArrayList<CamelMap>(); 
        
        try {
            returnCamelList = productDao.productCopyCategoryChoiceSelect(model);
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        
        return returnCamelList;
    }
    
    /**
     * �긽�뭹 �쟾泥� 蹂듭궗 - ���옣
     * @param model
     * @return
     * @throws Exception
     */
    public int productAllCopy(CamelMap model) throws Exception{
        StringBuffer sb = new StringBuffer();
        long startTime  = System.currentTimeMillis();
        long endTime    = 0;

        try {
            for(Map.Entry<String, Object> elem : model.entrySet()){
                if(elem.getValue() != null) {
                    sb.append("{"+elem.getKey().toString()+" : "+elem.getValue().toString()+"},");
                }
            }
            
            if(model.get("franchiseId") == null) {
                model.put("franchiseId",""); 
            }
            
            //-------------------------------------------------------------------------------------------------
            int ProductDetailCnt      = productAllCopyDao.productAllCopyProductDetail(model);      //�긽�뭹�긽�꽭   
            int productStockCnt       = productAllCopyDao.productAllCopyProductStock(model);       //�긽�뭹�쁽�솴
            int categoryProductCnt    = productAllCopyDao.productAllCopyCategoryProduct(model);    //移댄뀒怨좊━�긽�뭹
            int categoryDetailCnt     = productAllCopyDao.productAllCopyCategoryDetail(model);     //移댄뀒怨좊━ �긽�꽭
            int categorySeqCnt        = productAllCopyDao.productAllCopyCategorySeq(model);        //移댄뀒怨좊━ �닚�꽌
            int productOptionGroupCnt = productAllCopyDao.productAllCopyProductOptionGroup(model); //�긽�뭹�샃�뀡洹몃９
            int optionGroupProductCnt = productAllCopyDao.productAllCopyOptionGroupProduct(model); //�샃�뀡洹몃９�긽�뭹
            int optionGroupDetailCnt  = productAllCopyDao.productAllCopyOptionGroupDetail(model);  //�샃�뀡洹몃９�긽�꽭
            //-------------------------------------------------------------------------------------------------
            
            endTime = System.currentTimeMillis();

            logger.debug(
                    "****�긽�뭹 �쟾泥� 蹂듭궗 ST**************************************\n"+
                    "�떆�옉�떆媛�=["+startTime+"]\n"+
                    "醫낅즺�떆媛�=["+endTime+"]\n"+
                    "�떎�뻾�떆媛�=["+(long) ((endTime - startTime) / 1000.0)+"]珥�\n"+
                    "parameter            =["+sb.toString().replace("},]", "}]")+"]"+"]\n"+
                    "productDetailCnt     =["+ProductDetailCnt+"]嫄�\n"+
                    "productStockCnt      =["+productStockCnt+"]嫄�\n"+
                    "categoryProductCnt   =["+categoryProductCnt+"]嫄�\n"+
                    "categoryDetailCnt    =["+categoryDetailCnt+"]嫄�\n"+
                    "categorySeqCnt       =["+categorySeqCnt+"]嫄�\n"+
                    "productOptionGroup   =["+productOptionGroupCnt+"]嫄�\n"+
                    "optionGroupProduct   =["+optionGroupProductCnt+"]嫄�\n"+
                    "optionGroupDetail    =["+optionGroupDetailCnt+"]嫄�\n"+
                    "****�긽�뭹 �쟾泥� 蹂듭궗 ED**************************************"
                        );
            
        }catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(e.getMessage(),e);
        }
        return 1;
    }
    
    /**
     * �긽�뭹蹂듭궗 - ���옣
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int productCopyChoiceSave(CamelMap model) throws Exception{
        
        StringBuffer sb = new StringBuffer();
        
        long startTime  = System.currentTimeMillis();
        long endTime    = 0;
        
        int categoryProductCnt     = 0;       //移댄뀒怨좊━�긽�뭹
        int categorySeq1Cnt        = 0;       //移댄뀒怨좊━ �닚�꽌
        int categorySeq2Cnt        = 0;       //移댄뀒怨좊━ �닚�꽌 �젙�젹
        int printDeleteCnt         = 0;       //�봽由고듃 �궘�젣
        int printInsertCnt         = 0;       //�봽由고듃 �벑濡�
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        
        try {
            for(Map.Entry<String, Object> elem : model.entrySet()){
                if(elem.getValue() != null) {
                    sb.append("{"+elem.getKey().toString()+" : "+elem.getValue().toString()+"},");
                }
            }
            
            if(model.get("franchiseId") == null) {
                model.put("franchiseId",""); 
            }
            
            int ProductDetailCnt      = productCopyDao.productCopyProductDetail(model);      //�긽�뭹�긽�꽭   
            int productStockCnt       = productCopyDao.productCopyProductStock(model);       //�긽�뭹�쁽�솴
            
            //蹂듭궗�븷 移댄뀒怨좊━ �꽑�깮�떆�뿉留� 移댄뀒怨좊━ �꽑�깮�떆�뿉留� �룞�옉�븳�떎.
            if("true".equalsIgnoreCase(model.get("categoryCopyYn").toString() )) {
                categoryProductCnt    = productCopyDao.productCopyCategoryProduct(model);    //移댄뀒怨좊━�긽�뭹
                categorySeq1Cnt       = productCopyDao.productCopyCategorySeq1(model);       //移댄뀒怨좊━ �닚�꽌
                categorySeq2Cnt       = productCopyDao.productCopyCategorySeq2(model);       //移댄뀒怨좊━ �닚�꽌 �젙�젹
            }
            int productOptionGroupCnt = productCopyDao.productCopyProductOptionGroup(model); //�긽�뭹�샃�뀡洹몃９
            int optionGroupProductCnt = productCopyDao.productCopyOptionGroupProduct(model); //�샃�뀡洹몃９�긽�뭹
            int optionGroupDetailCnt  = productCopyDao.productCopyOptionGroupDetail(model);  //�샃�뀡洹몃９�긽�꽭
            
            //�봽由고듃 �궘�젣 / �벑濡�,�닔�젙
            if(model.get("franchiseId")  != null) {       //媛�留뱀젏 �꽑�깮�떆�뿉留�
                List<String> itrPrintList = new ArrayList<String>();
                List<String> etrPrintList = new ArrayList<String>();
                
                if(model.get("itrPrintList") != null) {
                itrPrintList = (List<String>)model.get("itrPrintList");
                }
                
                if(model.get("etrPrintList") != null) {
                etrPrintList = (List<String>)model.get("etrPrintList");
                }

                String itrPrint = "";
                String etrPrint = "";
                
                for(int i=0;i<itrPrintList.size();i++) {
                    if(i == 0) {
                        itrPrint +=     itrPrintList.get(i);
                    }else {
                        itrPrint += ","+itrPrintList.get(i);
                    }
                }

                for(int i=0;i<etrPrintList.size();i++) {
                    if(i == 0) {
                        etrPrint +=     etrPrintList.get(i);
                    }else {
                        etrPrint += ","+etrPrintList.get(i);
                    }
                }
                
                itrPrint = StringUtil.StringArrayTextChange(itrPrint, ",");
                etrPrint = StringUtil.StringArrayTextChange(etrPrint, ",");

                model.put("printId", model.get("printId"));
                model.put("targetId", model.get("productId"));
                model.put("targetType", "P");
                model.put("itrPrint", itrPrint);
                model.put("etrPrint", etrPrint);

            }
            
            endTime = System.currentTimeMillis();

            logger.debug      (
                    "{}\n****�긽�뭹 蹂듭궗 ST**************************************\n"+
                    "parameter            =["+sb.toString().replace("},]", "}]")+"]"+"]\n"+
                    "productDetailCnt     =["+ProductDetailCnt+"]嫄�\n"+
                    "productStockCnt      =["+productStockCnt+"]嫄�\n"+
                    "categoryProductCnt   =["+categoryProductCnt+"]嫄�\n"+
                    "categorySeq1Cnt      =["+categorySeq1Cnt+"]嫄�\n"+
                    "categorySeq2Cnt      =["+categorySeq2Cnt+"]嫄�\n"+
                    "productOptionGroup   =["+productOptionGroupCnt+"]嫄�\n"+
                    "optionGroupProduct   =["+optionGroupProductCnt+"]嫄�\n"+
                    "optionGroupDetail    =["+optionGroupDetailCnt+"]嫄�\n"+
                    "printDeleteCnt       =["+printDeleteCnt+"]嫄�\n"+
                    "printInsertCnt       =["+printInsertCnt+"]嫄�\n"+
                    "****�긽�뭹 蹂듭궗 ED**************************************", LogUtils.toHeader(request)
                           );
            
        }catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(e.getMessage(),e);
        }

        return 1;
    }
    
    /**
     * �긽�뭹�젙蹂� �벑濡�
     * 
     **�벑濡� 諛� 蹂�寃� �뀒�씠釉� 
     **T_PRODUCT
     **T_PRODUCT_DETAIL
     **T_PRODUCT_OPTION_GROUP
     **T_CATEGORY_PRODUCT
     **@param PRODUCT Product 
     */
    public int productInsert(Product param) throws Exception {

        int returnVal              = 1;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        
        //�삁�쇅 耳��씠�뒪 泥섎━ �씠由꾩쑝濡� productID �솗�씤  ----------------------------------------ST 
        //1.�떎瑜� 媛�留뱀젏�쑝濡� 媛숈� �씠由꾩씠 �긽�뭹�씠 �꽑�벑濡앸릺�엳�뒗 �긽�깭
        //2.�옱�벑濡앹떆 �떊洹� UID 梨꾨쾲 �릺吏�留� �궎以묐났�쑝濡� T_PRODUCT�뿉�뒗 �벑濡앹씠 �븞�맖
        try {
            String uid      = "";
            CamelMap model  = ObjectUtils.to( param );
            long startTime  = System.currentTimeMillis();
            long endTime    = 0;
            StringBuffer sb = new StringBuffer();
            String chk      = productDao.productNameChk(model);
            
            if("Y".equalsIgnoreCase(chk)) {
               throw new Exception("�씠誘� �벑濡앸맂 愿�由щ챸�씠 �엳�뒿�땲�떎.");
            }
            
            //愿�由щ챸 湲곗〈 �벑濡앹뿬遺� 議고쉶
            CamelMap chkMap = productDao.productUidChk(model);
            
            if(CommonUtil.isNotEmpty(chkMap)) {
                uid             = (String)chkMap.get("productId");
            }else {
                uid             = null;
            }
            //uid 援먯껜 (湲곗〈 �벑濡앸맂 �뜲�씠�뀒 �씪 寃쎌슦) 湲곗〈 uid濡�
            if(uid != null) {
                logger.info("�엫�떆 梨꾨쾲�맂 productId="     +(String)param.getProductId());
                logger.info("�씠誘� �벑濡앸맂 productId濡� 援먯껜="+uid);
                model.put("productId", uid);
            }
            
            //�삁�쇅 耳��씠�뒪 泥섎━ �씠由꾩쑝濡� productID �솗�씤  ----------------------------------------ED

            CamelMap sendMap      = new CamelMap();
            CamelMap result       = new CamelMap();
            CamelMap shaSearchMap = new CamelMap();
            
            //�뙆�씪泥댄겕
            for(int i=0;i<5;i++) {
                
                sendMap      = new CamelMap();
                shaSearchMap = new CamelMap();
                
                shaSearchMap.put("headOfficeId" , param.getHeadOfficeId());
                shaSearchMap.put("productId"    , param.getProductId());
                
                sendMap.put("uploadGroup"   , "TEST");
                sendMap.put("uploadGroupSub", param.getHeadOfficeId());
                sendMap.put("masterTable"   , "T_PRODUCT_DETAIL");
                sendMap.put("masterId"      , param.getProductId());
                
                if(i == 0){
                    //�뙆�씪 �씠誘몄� 泥댄겕
                    sendMap.put     ("fileId"       , param.getFileId());
                    shaSearchMap.put("fileId"       , param.getFileId());
                }else if(i == 1){
                    //�긽�뭹蹂� �븳援��뼱 泥댄겕
                    sendMap.put     ("fileId"       , param.getSoundKr());
                    shaSearchMap.put("fileId"       , param.getSoundKr());
                }else if(i == 2) {
                    //�긽�뭹蹂� �쁺�뼱 泥댄겕
                    sendMap.put     ("fileId"       , param.getSoundEn());
                    shaSearchMap.put("fileId"       , param.getSoundEn());
                }else if(i == 3){
                    //�긽�뭹蹂� �씪蹂몄뼱 泥댄겕
                    sendMap.put     ("fileId"       , param.getSoundJp());
                    shaSearchMap.put("fileId"       , param.getSoundJp());
                }else if(i == 4){
                    //�긽�뭹蹂� 以묎뎅�뼱 泥댄겕
                    sendMap.put     ("fileId"       , param.getSoundCn());
                    shaSearchMap.put("fileId"       , param.getSoundCn());
                }
                
                result = new CamelMap();
                result = fileDao.getFile(shaSearchMap);
                
                //�긽�뭹 �뙆�씪 以묐났 泥댄겕
                String fileDuplchkId = productDao.productFileDuplChk(shaSearchMap);
                
                //以묐났�씪�떆
                if(!"none".equalsIgnoreCase(fileDuplchkId)) {
                    //�엫�떆�뙆�씪 �궘�젣
                    FileUtil.deleteFileInf(staticFileRootPath+staticFileTempPath+Constant.SLASH+shaSearchMap.get("fileId"));
                    fileDao.deleteFile(shaSearchMap);
                    //湲곗〈 �뙆�씪 ID濡� 援먯껜
                    logger.info("湲곗〈 �벑濡앸맂 �씠誘몄�濡� 援먯껜"+shaSearchMap.get("fileId")+"->"+fileDuplchkId);
                    if(i == 0){
                        model.put("fileId" , fileDuplchkId);
                    }else if(i == 1) {
                        model.put("soundKr", fileDuplchkId);
                    }else if(i == 2) {
                        model.put("soundEn", fileDuplchkId);
                    }else if(i == 3) {
                        model.put("soundJp", fileDuplchkId);
                    }else if(i == 4) {
                        model.put("soundCn", fileDuplchkId);
                    }
                    
                }else {//�븘�땺�떆
                    
                    //媛� �빐�떦 �뙆�씪�씠 議댁옱 �떆
                    if(!CommonUtil.isEmpty(result)) {
                        //�뙆�씪 誘몃텇瑜� �씪�떆 �뙆�씪�씠�룞 泥섎━
                        if("TEMP".equalsIgnoreCase(result.get("uploadGroup").toString())) {
                            
                            String fileName       = result.get("sysFileName").toString(); //諛붾�� �씠由�
                            String beforeFilePath = result.get("filePath").toString()+Constant.SLASH+result.get("sysFileName").toString(); //�삷湲� ���긽 寃쎈줈
                            String afterFilePath  = staticFilePath+Constant.SLASH+"TEST"+Constant.SLASH+param.getHeadOfficeId(); //�삷寃⑥쭏 寃쎈줈
                            
                            logger.info("filePath 蹂�寃쎌쟾=["+beforeFilePath+"]");
                            logger.info("filePath 蹂�寃쏀썑=["+afterFilePath+"]");
    
                            sendMap.put("filePath"        ,afterFilePath);
                            
                            FileUtil.moveFile(staticFileRootPath,fileName,beforeFilePath,afterFilePath);//�뙆�씪�씠�룞
                        }
                        fileDao.fileMatchingUpdate(sendMap);//�빐�떦 �뙆�씪 �뿰寃곗젙蹂� �닔�젙
                    }
                }
            }
            
            int result1 = productDao.productInsert(model);      //�긽�뭹�젙蹂� ���옣
            int result2 = productDao.productDetailInsert(model);//�긽�뭹�긽�꽭�젙蹂� ���옣
            int result3 = productDao.productStockInsert(model); //�긽�뭹 STOCK ���옣
            int result4 = 0;                                    //�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾩궘�젣
            int result5 = 0;                                    //�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾨벑濡�
            int result7 = 0;                                    //�긽�뭹�벑濡앹떆  T_CATEGORY_PRODUCT �씪愿꾨벑濡�
            int result8 = 0;                                    //�봽由고꽣 �궘�젣
            int result9 = 0;                                    //�봽由고꽣 Merge �벑濡�
            
            //�긽�뭹�샃�뀡 留ㅽ븨 �꽑�깮�떆�뿉留� ���옣
            if(param.getProductOptionGroupList() != null) {
                result4 = productDao.productOptionGroupDelete( model);//�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾩궘�젣
                result5 = productDao.productOptionGroupInsert( model);//�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾨벑濡�
            }
            
            //移댄뀒怨좊━ 留ㅽ븨 �꽑�깮�떆�뿉留� ���옣
            if(param.getCategoryIdList().size() != 0) {
                result7 = productDao.categoryProductInsert( model);   //�긽�뭹�벑濡앹떆  T_CATEGORY_PRODUCT �씪愿꾨벑濡�
            }
            
            //�봽由고듃 �궘�젣 / �벑濡�,�닔�젙
            if(param.getFranchiseId()  != null) {       //媛�留뱀젏 �꽑�깮�떆�뿉留�
                
                List<String> itrPrintList = new ArrayList<String>();
                List<String> etrPrintList = new ArrayList<String>();
                
                if(param.getItrPrintList() != null) {
                    itrPrintList = (List<String>)param.getItrPrintList();
                }
                
                if(param.getEtrPrintList() != null) {
                    etrPrintList = (List<String>)param.getEtrPrintList();
                }
                String itrPrint = "";
                String etrPrint = "";
                
                for(int i=0;i<itrPrintList.size();i++) {
                    if(i == 0) {
                        itrPrint +=     itrPrintList.get(i);
                    }else {
                        itrPrint += ","+itrPrintList.get(i);
                    }
                }

                for(int i=0;i<etrPrintList.size();i++) {
                    if(i == 0) {
                        etrPrint +=     etrPrintList.get(i);
                    }else {
                        etrPrint += ","+etrPrintList.get(i);
                    }
                }
                
                model.put("printId"   , model.get("printId"));
                model.put("targetId"  , model.get("productId"));
                model.put("targetType", "P");
                
                itrPrint = StringUtil.StringArrayTextChange(itrPrint, ",");
                etrPrint = StringUtil.StringArrayTextChange(etrPrint, ",");
                
                model.put("itrPrint", itrPrint);
                model.put("etrPrint", etrPrint);
                
            }
            
            for(Map.Entry<String, Object> elem : model.entrySet()){
                if(CommonUtil.isNotEmpty(elem.getValue())) {
                    sb.append("{"+elem.getKey().toString()+" : "+elem.getValue().toString()+"},");
                }
            }
            
            endTime = System.currentTimeMillis();
            
            logger.debug(
                    "{} \n****�긽�뭹 �젙蹂� �벑濡� ST**************************************\n"+
                    "�떆�옉�떆媛�=["+startTime+"]\n"+
                    "醫낅즺�떆媛�=["+endTime+"]\n"+
                    "�떎�뻾�떆媛�=["+(long) ((endTime - startTime) / 1000.0)+"]珥�\n"+
                    "parameter                =["+sb.toString().replace("},]", "}]")+"]"+"]\n"+
                    "productCnt               =["+result1+"]嫄�\n"+
                    "productDetailCnt         =["+result2+"]嫄�\n"+
                    "productStockCnt          =["+result3+"]嫄�\n"+
                    "productOptionDeleteCnt   =["+result4+"]嫄�\n"+
                    "productOptionInsertCnt   =["+result5+"]嫄�\n"+
                    "categoryProductDeleteCnt =["+0+"]嫄�\n"+
                    "categoryProductInsertCnt =["+result7+"]嫄�\n"+
                    "printDeleteCnt           =["+result8+"]嫄�\n"+
                    "printMergeCnt            =["+result9+"]嫄�\n"+
                    "****�긽�뭹 �젙蹂� �벑濡� ED**************************************\n", LogUtils.toHeader(request)
                   );
            
        }catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(e.getMessage(),e);
        }

        return returnVal;
    }
    
    /**
     * �긽�뭹 �젙蹂� 蹂�寃�
     * @param param
     * @return
     * @throws Exception
     */
    public int productUpdate(Product param) throws Exception {
        
        long startTime             = System.currentTimeMillis();
        long endTime               = 0;
        int returnVal              = 1;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        
        StringBuffer sb = new StringBuffer();
        
        try {
                CamelMap model        = ObjectUtils.to( param );
                CamelMap sendMap      = new CamelMap();
                CamelMap result       = new CamelMap();
                CamelMap shaSearchMap = new CamelMap();

                //�뙆�씪泥댄겕
                for(int i=0;i<5;i++) {
                    
                    shaSearchMap = new CamelMap();
                    sendMap      = new CamelMap();
                    
                    shaSearchMap.put("headOfficeId" , param.getHeadOfficeId());
                    shaSearchMap.put("productId"    , param.getProductId());
                    
                    //T_FILES SETTING ST --------------------------------------
                    sendMap.put("uploadGroup"   , "TEST");
                    sendMap.put("uploadGroupSub", param.getHeadOfficeId());
                    sendMap.put("masterTable"   , "T_PRODUCT_DETAIL");
                    sendMap.put("masterId"      , param.getProductId());
                    //T_FILES SETTING ED --------------------------------------
                    
                    if(i == 0){
                        //�뙆�씪 �씠誘몄� 泥댄겕
                        sendMap.put     ("fileId"       , param.getFileId());
                        shaSearchMap.put("fileId"       , param.getFileId());
                    }else if(i == 1){
                        //�긽�뭹蹂� �븳援��뼱 泥댄겕
                        sendMap.put     ("fileId"       , param.getSoundKr());
                        shaSearchMap.put("fileId"       , param.getSoundKr());
                    }else if(i == 2) {
                        //�긽�뭹蹂� �쁺�뼱 泥댄겕
                        sendMap.put     ("fileId"       , param.getSoundEn());
                        shaSearchMap.put("fileId"       , param.getSoundEn());
                    }else if(i == 3){
                        //�긽�뭹蹂� �씪蹂몄뼱 泥댄겕
                        sendMap.put     ("fileId"       , param.getSoundJp());
                        shaSearchMap.put("fileId"       , param.getSoundJp());
                    }else if(i == 4){
                        //�긽�뭹蹂� 以묎뎅�뼱 泥댄겕
                        sendMap.put     ("fileId"       , param.getSoundCn());
                        shaSearchMap.put("fileId"       , param.getSoundCn());
                    }
                    
                    result  = new CamelMap();
                    result  = fileDao.getFile(shaSearchMap);//�뙆�씪�젙蹂� 媛��졇�삤湲�
                    
                    //�긽�뭹 �뙆�씪 以묐났 泥댄겕
                    String fileDuplchkId = productDao.productFileDuplChk(shaSearchMap);
                    
                    //以묐났�씪�떆
                    if(!"none".equalsIgnoreCase(fileDuplchkId)) {
                        //�엫�떆�뙆�씪 �궘�젣
                        FileUtil.deleteFileInf(staticFileRootPath+staticFileTempPath+Constant.SLASH+shaSearchMap.get("fileId"));
                        fileDao.deleteFile(shaSearchMap);
                        //湲곗〈 �뙆�씪 ID濡� 援먯껜
                        logger.info("湲곗〈 �벑濡앸맂 �뙆�씪濡� 援먯껜"+shaSearchMap.get("fileId")+"->"+fileDuplchkId);
                        
                        if(i == 0){
                            model.put("fileId" , fileDuplchkId);
                        }else if(i == 1) {
                            model.put("soundKr", fileDuplchkId);
                        }else if(i == 2) {
                            model.put("soundEn", fileDuplchkId);
                        }else if(i == 3) {
                            model.put("soundJp", fileDuplchkId);
                        }else if(i == 4) {
                            model.put("soundCn", fileDuplchkId);
                        }
                        
                    }else {//�븘�땺�떆
                        
                        //媛� �빐�떦 �뙆�씪�씠 議댁옱 �떆
                        if(!CommonUtil.isEmpty(result)) {
                            //�뙆�씪 誘몃텇瑜� �씪�떆
                            if("TEMP".equalsIgnoreCase(result.get("uploadGroup").toString())) {
                                
                                String fileName       = result.get("sysFileName").toString(); //諛붾�� �씠由�
                                String beforeFilePath = result.get("filePath").toString()+Constant.SLASH+result.get("sysFileName").toString(); //�삷湲� ���긽 寃쎈줈
                                String afterFilePath  = staticFilePath+Constant.SLASH+"TEST"+Constant.SLASH+param.getHeadOfficeId(); //�삷寃⑥쭏 寃쎈줈
                                
                                logger.info("filePath 蹂�寃쎌쟾=["+beforeFilePath+"]");
                                logger.info("filePath 蹂�寃쏀썑=["+afterFilePath+"]");
        
                                sendMap.put("filePath"        ,afterFilePath);
                                
                                FileUtil.moveFile(staticFileRootPath,fileName,beforeFilePath,afterFilePath);//�뙆�씪�씠�룞
                            }
                            fileDao.fileMatchingUpdate(sendMap);//�빐�떦 �뙆�씪 �뿰寃곗젙蹂� �닔�젙
                        }
                    }
                }
            
                int result1 = productDao.productUpdate(model);                //�긽�뭹�젙蹂� �닔�젙
                int result2 = productDao.productDetailUpdate(model);          //�긽�뭹 �긽�꽭 �닔�젙 (蹂몄궗 �뜲�씠�꽣留� 泥섎━)
                
                //蹂몄궗 泥섎━ �떆
                if(CommonUtil.isEmpty(param.getFranchiseId())) {
                    result2 += productDao.productDetailExceptionUpdate(model); //�긽�뭹 �긽�꽭 �닔�젙 (蹂몄궗 �쇅 �뜲�씠�꽣留� 泥섎━) - 
                }
    
                int result3 = productDao.productStockUpdate(model);       //�긽�뭹 STOCK �닔�젙 (蹂몄궗�뒗 蹂몄궗留�, 媛�留뱀젏�� �빐�떦 媛�留뱀젏 �뜲�씠�꽣留� 泥섎━)
                int result4 = 0;                                          //�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾩궘�젣
                int result5 = 0;                                          //�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾨벑濡�
                int result6 = 0;                                          //�긽�뭹 �벑濡앹떆 T_CATEGORY_PRODUCT �빐�떦�젙蹂� �궘�젣
                int result7 = 0;                                          //�긽�뭹�벑濡앹떆  T_CATEGORY_PRODUCT �씪愿꾨벑濡�
                int result8 = 0;                                          //�봽由고꽣 �궘�젣
                int result9 = 0;                                          //�봽由고꽣 Merge �벑濡�
    
                //�긽�뭹�샃�뀡 留ㅽ븨 �꽑�깮�떆�뿉留� ���옣
                result4 = productDao.productOptionGroupDelete( model);//�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾩궘�젣
    
                if(param.getProductOptionGroupList() != null) {
                    result5 = productDao.productOptionGroupInsert( model);//�긽�뭹�샃�뀡洹몃９�젙蹂� �씪愿꾨벑濡�
                }
                
                
                //移댄뀒怨좊━ 留ㅽ븨 �꽑�깮�떆�뿉留� ���옣
                if(param.getCategoryIdList().size() != 0) {
                    model.put("noRoof", "N");
                    result6 = productDao.categoryProductDelete( model);   //�긽�뭹 �벑濡앹떆 T_CATEGORY_PRODUCT �빐�떦�젙蹂� �궘�젣
                    result7 = productDao.categoryProductInsert( model);   //�긽�뭹�벑濡앹떆  T_CATEGORY_PRODUCT �씪愿꾨벑濡�
                }else {
                    model.put("noRoof", "Y");
                    result6 = productDao.categoryProductDelete( model);   //�긽�뭹 �벑濡앹떆 T_CATEGORY_PRODUCT �빐�떦�젙蹂� �궘�젣
                }
    
                //�봽由고듃 �궘�젣 / �벑濡�,�닔�젙
                if(param.getFranchiseId()  != null) {       //媛�留뱀젏 �꽑�깮�떆�뿉留�
                    List<String> itrPrintList = new ArrayList<String>();
                    List<String> etrPrintList = new ArrayList<String>();
                    
                    if(param.getItrPrintList() != null) {
                        itrPrintList = (List<String>)param.getItrPrintList();
                    }
                    
                    if(param.getEtrPrintList() != null) {
                        etrPrintList = (List<String>)param.getEtrPrintList();
                    }
                    
                    String itrPrint = "";
                    String etrPrint = "";
                    
                    for(int i=0;i<itrPrintList.size();i++) {
                        if(i == 0) {
                            itrPrint +=     itrPrintList.get(i);
                        }else {
                            itrPrint += ","+itrPrintList.get(i);
                        }
                    }
    
                    for(int i=0;i<etrPrintList.size();i++) {
                        if(i == 0) {
                            etrPrint +=     etrPrintList.get(i);
                        }else {
                            etrPrint += ","+etrPrintList.get(i);
                        }
                    }
                    
                    model.put("printId"   , param.getPrintId());
                    model.put("targetId"  , param.getProductId());
                    model.put("targetType", "P");

                }
                
                endTime = System.currentTimeMillis();
                
                for(Map.Entry<String, Object> elem : model.entrySet()){
                    if(elem.getValue() != null) {
                        sb.append("{"+elem.getKey().toString()+" : "+elem.getValue().toString()+"},");
                    }
                }
                
    
                logger.debug(
                        "{} \n****�긽�뭹 �젙蹂� 蹂�寃� ST**************************************\n"+
                        "�떆�옉�떆媛�=["+startTime+"]\n"+
                        "醫낅즺�떆媛�=["+endTime+"]\n"+
                        "�떎�뻾�떆媛�=["+(long) ((endTime - startTime) / 1000.0)+"]珥�\n"+
                        "parameter                =["+sb.toString().replace("},]", "}]")+"]"+"]\n"+
                        "productCnt               =["+result1+"]嫄�\n"+
                        "productDetailCnt         =["+result2+"]嫄�\n"+
                        "productStockCnt          =["+result3+"]嫄�\n"+
                        "productOptionDeleteCnt   =["+result4+"]嫄�\n"+
                        "productOptionInsertCnt   =["+result5+"]嫄�\n"+
                        "categoryProductDeleteCnt =["+result6+"]嫄�\n"+
                        "categoryProductInsertCnt =["+result7+"]嫄�\n"+
                        "printDeleteCnt           =["+result8+"]嫄�\n"+
                        "printMergeCnt            =["+result9+"]嫄�\n"+
                        "****�긽�뭹 �젙蹂� 蹂�寃� ED**************************************\n", LogUtils.toHeader(request)
                       );
        }catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(e.getMessage(),e);
        }
        
        return returnVal;
    }

    /**
     * �긽�뭹�젙蹂� �궘�젣
     * @param param
     * @return
     * @throws Exception
     */
    public int productDelete(Product param) throws Exception {
        int returnVal = 1; 
        try {
            CamelMap model = ObjectUtils.to( param );
            returnVal += productDao.printProductDelete(model);
            returnVal += productDao.productDelete(model);
        }catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(e.getMessage(),e);
        }
        return returnVal;
    }

    /**
     * �뿊�� �긽�뭹 �벑濡� 
     * @param excelList
     * @return
     * @throws Exception 
     */
    public int productExcelInsert(Token token,List<HashMap<String, String>> excelList,String realPath) throws Exception
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        
        String productMgrName       = ""; //�긽�뭹 愿�由щ챸
        String headOfficeId         = ""; //蹂몄궗ID
        String franchiseId          = ""; //媛�留뱀젏ID
        String productId            = ""; //�긽�뭹ID

        CamelMap idReturnMap        = new CamelMap(); // �긽�뭹 ID return
        CamelMap checkMap           = new CamelMap(); // �긽�뭹 ID parameter
        
        int productInsertCnt        = 0;
        int productDetailCnt        = 0;
        int productStockCnt         = 0;
        int result                  = 1;  //寃곌낵 嫄댁닔 (珥덇린媛�)

        try {
            //吏꾪뻾以묒씠�뜕 �궡�슜源뚯��뒗 �벑濡앹씠 �릺�룄濡� 泥섎━瑜� �쐞�빐 嫄대퀎 �벑濡� �봽濡쒖꽭�뒪 援ы쁽
            //湲곕벑濡� �긽�뭹�씠�뜕吏� �븘�땲�뜕吏� �긽愿��뾾�씠 �슂泥� �옄猷뚯뿉 �벑濡�/�닔�젙�쓣 吏꾪뻾�븳�떎.
            if(excelList.size() != 0) {
                for(int i=0;i<excelList.size();i++) {
                    
                    productMgrName = excelList.get(i).get("productMgrName");//�긽�뭹紐�
                    headOfficeId   = excelList.get(i).get("headOfficeId");  //蹂몄궗ID
                    franchiseId    = excelList.get(i).get("franchiseId");   //媛�留뱀젏 ID
                    
                    //PRODUCT_ID,PRODUCT_DETAIL_ID 泥댄겕 諛� ID 梨꾨쾲 ------------ST
                    checkMap = new CamelMap();
                    
                    checkMap.put("productMgrName", productMgrName);
                    checkMap.put("headOfficeId"  , headOfficeId);
                    checkMap.put("franchiseId"   , franchiseId);
                    
                    //ID 議고쉶
                    idReturnMap = productDao.productIdCheck(checkMap);
                    
                    //�긽�뭹ID �젙蹂닿� �뾾�쓣�떆
                    if(CommonUtil.isEmpty(idReturnMap)) {
                        productId = UniqId.getID();//�떊洹�
                    }else {
                        productId = (String)idReturnMap.get("productId");//湲곗〈 (媛�留뱀젏�� �떊洹쒖씪�닔�룄 �엳�떎) 
                    }
                    excelList.get(i).put("productId", productId);//梨꾨쾲 留ㅽ븨 (�떊洹�/湲곗〈)
                    //PRODUCT_ID,PRODUCT_DETAIL_ID 泥댄겕 諛� ID 梨꾨쾲 ------------ED

                    
                    CamelMap param  = new CamelMap();
                    param.putAll(excelList.get(i));

                    //�긽�뭹�벑濡�
                    productInsertCnt += productExcelDao.productExcelInsert(param);
                    
                    //�씠誘몄�  �벑濡�(�씠誘몄�媛� �엳�뒗 寃쎌슦)
                    String fileId = uploadExcelProductImage(productId,headOfficeId,token,realPath,excelList.get(i),i+1);//諛곗뿴�떆�옉怨� �뿊�� ROW �떆�옉�씠 �떖�씪�꽌 i+1
                    param.put("fileId", fileId);
                    //�긽�뭹�긽�꽭 �벑濡�
                    productDetailCnt += productExcelDao.productExcelDetailInsert(param);
                    
                    //�긽�뭹�쁽�솴 �벑濡�
                    productStockCnt  += productExcelDao.productExcelStockInsert(param);

                }
                
            }else {
                result = 0;
            }
            
            logger.info("{} \n�긽�뭹 �뿊�� �씪愿꾨벑濡� 珥�"+excelList.size()+"嫄� #####ST", LogUtils.toHeader(request));
            logger.info("{} \nproductInsertCnt="+productInsertCnt+"嫄�", LogUtils.toHeader(request));
            logger.info("{} \nproductDetailCnt="+productDetailCnt+"嫄�", LogUtils.toHeader(request));
            logger.info("{} \nproductStockCnt ="+productStockCnt+"嫄�", LogUtils.toHeader(request));
            logger.info("{} \n�긽�뭹 �뿊�� �씪愿꾨벑濡� 珥�"+excelList.size()+"嫄� #####ED", LogUtils.toHeader(request));
            
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(e.getMessage(),e);
        }
        return result;
    }

    /**
     * �뿊�� 嫄대퀎 �벑濡�  �씠誘몄� �룷�븿
     * @param token
     * @param param
     * @return
     * @throws Exception
     */
    public int productExcelOneRecordInsert(Token token,HashMap<String, String> param) throws Exception {

        HttpServletRequest request       = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        CamelMap model                   = new CamelMap();
        int result                       = 0;
        String fileId                    = null;

        try {
        	request.setAttribute(Constant.TRX_NO, param.get("jobId"));
        	model.putAll(param);
        	
            String excelPosition = param.get("excelPosition").toString();

            //�긽�뭹�벑濡�
            productExcelDao.productExcelInsert(model);
            
            logger.info("{} �뿊�� �뾽濡쒕뱶 嫄대퀎�벑濡� :"+param.toString());

            //�긽�뭹�긽�꽭 �벑濡�
            result = productExcelDao.productExcelDetailInsert(model);
            
            //�긽�뭹�쁽�솴 �벑濡�
            productExcelDao.productExcelStockInsert(model);
            
            logger.info("{} �긽�뭹 �뿊�� 嫄대퀎 �벑濡�"   +param.get("excelPosition")+"/"+param.get("excelSize")+"嫄� #####", LogUtils.toHeader(request));

        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("{} �삤瑜�"+model.toString(), LogUtils.toHeader(request),e);
            
            Exception topsExcep = null ;
            if( e instanceof Exception )
            {
                topsExcep = (Exception) e ;
            }
            else
            {
                topsExcep = new Exception( "productExcelOneRecordInsert error", e ) ;
            }
            
            throw topsExcep ;
        }
        return result;
    }

    /**
     * �긽�뭹 �씠誘몄�瑜� �뾽濡쒕뱶 �빀�땲�떎.
     * 
     * @param token - �넗�겙 
     * @return 
     */
    public String uploadExcelProductImage(String productId,String headOfficeId,Token token,String fullPath, HashMap<String, String> excelProd,int row) throws Exception {

        String uniqId          = null;
        String fileId          = null;
        FileOutputStream out   = null; 
        FileOutputStream fos   = null;
        FileInputStream imgFis = null;
        try {
            
            imgFis = new FileInputStream(fullPath);
            @SuppressWarnings("resource")
            XSSFWorkbook imgWorkbook = new XSSFWorkbook(imgFis);
                
            XSSFSheet imgSheet  = (XSSFSheet) imgWorkbook.getSheetAt(0); //�긽�뭹SHEET
            XSSFDrawing drawing = imgSheet.createDrawingPatriarch(); 

            for (XSSFShape shape : drawing.getShapes()) {

                 if (shape instanceof XSSFPicture) {
                     XSSFPicture picture = (XSSFPicture) shape;
                      
                     if (picture.getPictureData()==null) {
                         continue;
                     }
                     XSSFPictureData xssfPictureData = picture.getPictureData();
                     ClientAnchor anchor             = picture.getPreferredSize();
                     int row1                        = anchor.getRow1();
                     int col1                        = anchor.getCol1();
                     
                     if(col1 != 20)   { continue;} //20踰덉㎏ �뿴�뿉 �엳�뒗 �씠誘몄�留� �씫�뼱�삩�떎.
                     if(row  != row1) { continue;}
                     
                     uniqId = UniqId.getID();
                     
                     byte[] data = xssfPictureData.getData();
                     String ext  = xssfPictureData.suggestFileExtension();

                     //�씠誘몄� 異붿텧 寃쎈줈
                     String filename    = uniqId;
                     String dir         = staticFileRootPath+staticFilePath+Constant.SLASH+"TEST"+Constant.SLASH+headOfficeId;
                     String picturePath = dir+Constant.SLASH+filename+Constant.DOT+ext;
                     
                     FileUtil.makeDir(dir);//�뤃�뜑 �뾾�쓣�떆 �깮�꽦

                     out = new FileOutputStream( picturePath );

                     out.write(data);
                     out.close();
                     
                     byte[] convertedImageBuffer = FileUtil.convertToPngFormat(FileUtil.fileToMultipartFile(new File( picturePath )), true);
                     
                     if(!ext.equals("png")) {
                         ext = "png";
                        String renamePicturePath = staticFileRootPath+staticFilePath+Constant.SLASH+"TEST"+Constant.SLASH+headOfficeId+Constant.SLASH+filename+Constant.DOT+"png";
                        
                        File willBeSavedFile = new File(renamePicturePath);
                        
                        fos = new FileOutputStream(willBeSavedFile);
                        fos.write(convertedImageBuffer);
                        fos.close();

                        //湲곗〈 �뙆�씪 媛앹껜 �깮�꽦
                        File file = new File(picturePath);
                        
                        if(file != null) {
                            
                            boolean chkFile = file.exists();

                            //湲곗〈 �뙆�씪 �궘�젣 (�뙆�씪 議댁옱�떆�뿉留� �궘�젣)
                            if(chkFile){
                                file.delete();
                            }
                        }
                        
                        
                        picturePath = renamePicturePath;
                     }
                     
                     //�씠誘몄� �뾽濡쒕뱶
                     //�뙆�씪�뾽濡쒕뱶 (�뾽濡쒕뱶 �븳 寃쎈줈 由ы꽩)
                     String realPath = picturePath;
                     CamelMap map    = new CamelMap();
                     
//                     logger.info("{} realPath="+realPath, LogUtils.toHeader(request));

                     //db寃쎈줈 移섑솚 (濡쒖뺄 �솚寃� �삁�쇅泥섎━ 諛� staticFilePath寃쎈줈濡� DB ���옣 staticFileRootPath �젣�쇅泥섎━)
                     String dbPath   = realPath.replace(staticFileRootPath,"")
                                               .replace(Constant.BACK_SLASH,Constant.SLASH);

                     //�뙆�씪 而щ읆 醫뚰몴
                     int filePos           = dbPath.lastIndexOf(Constant.SLASH);
                     
                     //sha2 hash
                     StringBuffer sha2Value = FileUtil.getSha2Hash(realPath);
                     
                     CamelMap shaSearchMap = new CamelMap();
                     
                     shaSearchMap.put("headOfficeId" , headOfficeId);
                     shaSearchMap.put("productId"    , productId);
                     shaSearchMap.put("fileId"       , uniqId+Constant.DOT+ext);
                     
                     //�긽�뭹 �뙆�씪 以묐났 泥댄겕
                     String fileDuplchkId = productDao.productFileDuplChk(shaSearchMap);
                     
                     //T_FILE MAPPING ST ----------------------------------------
                     map.put("fileId",           uniqId+Constant.DOT+ext);
                     map.put("uploadGroup",      "product");
                     map.put("uploadGroupSub",    productId);
                     map.put("masterTable",      "TEST_TABLE");
                     map.put("uploadGroup"   ,   "TEST");
                     map.put("uploadGroupSub",   headOfficeId);
                     map.put("masterTable"   ,   "T_PRODUCT_DETAIL");
                     map.put("masterId"      ,   productId);
                     map.put("orgFileName",      filename);
                     map.put("sysFileName",      dbPath.substring(filePos+1));
                     map.put("fileSize",         0);
                     map.put("masterSeq",        1 );
                     map.put("filePath",         dbPath.substring(0, filePos) );
                     map.put("fileExt",          ext  );
                     map.put("elementNm",        sha2Value  );
                     map.put("createdBy",        token.getTokenUserId()  );
                     map.put("updatedBy",        token.getTokenUserId()  );
                     //T_FILE MAPPING ED ----------------------------------------
//                     int result     = fileDao.insertFile(map);

                     //以묐났�씪�떆
                     if(!"none".equalsIgnoreCase(fileDuplchkId)) {
                         //�엫�떆�뙆�씪 �궘�젣
//                         FileUtil.deleteFileInf(staticFileRootPath+staticFileTempPath+Constant.SLASH+uniqId+Constant.DOT+ext);
//                         logger.info("{} 湲곗〈 �벑濡앸맂 �뙆�씪濡� 援먯껜"+uniqId+Constant.DOT+ext+"->"+fileDuplchkId, LogUtils.toHeader(request));
//                         fileDao.deleteFile(shaSearchMap);//�뙆�씪 �궘�젣 泥섎━
                         //湲곗〈 �뙆�씪 ID濡� 援먯껜
                         fileId = fileDuplchkId;
                     }else {
//                         logger.info("{} ���옣嫄댁닔="+result, LogUtils.toHeader(request));
                         fileId         = uniqId+Constant.DOT+ext;
                     }
                 }
            }
        }catch(Exception e) {
            return fileId;
            
        }finally {
            if(out != null) {
                out.close();
            }
            if(fos != null) {
                fos.close();
            }
            
            imgFis.close();
        }

        return fileId;
    }

    /**
     * �긽�뭹 �뿊�� �뀥�뵆由� �떎�슫濡쒕뱶
     * @param map
     * @return
     * @throws Exception
     */
    public List<CamelMap> getProductTemplete(Product map) throws Exception{
        CamelMap model = ObjectUtils.to(map);
        return productDao.getProductTemplete(model);
    }
    
    /**
     * 泥ル쾲吏� SHEET (移댄뀒怨좊━) �젙蹂� �씫�뼱�삤湲�
     * 
     * @param excelUploadResult - �뿉�윭 寃곌낵 由ъ뒪�듃
     * @param HashMap<String, String> item - param 
     * @param XSSFCell cell - �� 媛�
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public HashMap<String, String> setSheet1Column(ProductExcel excelUploadResult , HashMap<String, String> item, XSSFCell cell, int cellIdx,List <CamelMap> pointTypeCode) throws Exception {

        String value = "";
        
        try {

            

            if (cell == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
                value = "";
            else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                value = cell.getStringCellValue();
            else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                value = fmt.format(cell.getNumericCellValue()) + "";

            switch (cellIdx) {
                case 1: // �긽�뭹 援щ텇
                    value = value.trim().equals("�긽�뭹") ? "P" : 
                            value.equals("�샃�뀡")        ? "O" : 
                            value.equals("�씠誘몄�")      ?  "I" : 
                            value.equals("�뀓�뒪�듃")      ?  "T" 
                            :"";
                     item.put("productType", value);
                    break;

                case 2: // 愿�由щ챸
                    item.put("productMgrName", value); 
                    break;
                    
                case 3: // �긽�뭹紐� (�븳援��뼱)
                    item.put("productKr", value);
                    break;

                case 4: // �긽�뭹紐� (�쁺�뼱)
                    item.put("productEn", value);
                    break;
        
                case 5: // �긽�뭹紐�(�씪蹂몄뼱)
                    item.put("productJp", value);
                    break;
        
                case 6: // �긽�뭹紐�(以묎뎅�뼱)
                    item.put("productCn", value);
                    break;
                
                case 7: // �뙋留ㅼ긽�깭
                    value = value.trim().equals("�젙�긽") ? "ACTIVE" : "INACTIVE";
                    item.put("saleStatus", value);
                    break;
                    
                case 8: // �뭹�젅�뿬遺�
                    value = value.trim().equals("Y") ? "TRUE" : "FALSE";
                    item.put("soldoutYn", value);
                    break;
                    
                case 9: // �솕硫댁뿬遺�
                    value = value.trim().equals("�끂異�") ? "VISIBLE" : "HIDDEN";
                    item.put("visibility", value);
                    break;
                case 10: // �뙋留ㅻ떎�긽
                    value = value.trim().equals("�씪諛�") ? "G" : "S";
                    item.put("salesTarget", value);
                    break;
                    
                case 11: // �썝媛�
                    if( StringUtil.isEmpty( value ))
                    {
                        value = "0" ;
                    }
                    
                    item.put("primeCost", value);
                    break;
                    
                case 12: // 留ㅼ옣媛�
                    if("0".equalsIgnoreCase(value) ||
                       "".equalsIgnoreCase(value)
                    ) {
                        item.put("storePrice", "0");
                    }else {
                        item.put("storePrice", value);
                    }
                    break;
                case 13: // 留ㅼ옣媛� �궗�슜�뿬遺�
                    value = value.trim().equals("Y") ? "TRUE" : "FALSE";
                    item.put("isStore"   , value);
                    break;
                case 14: // �룷�옣媛�
                    if("0".equalsIgnoreCase(value) ||
                       "".equalsIgnoreCase(value)
                     ) {
                         item.put("packingPrice", "0");
                     }else {
                         item.put("packingPrice", value);
                     }
                    break;
                case 15: // �룷�옣媛� �궗�슜 �뿬遺�
                    value = value.trim().equals("Y") ? "TRUE" : "FALSE";
                    item.put("isPacking"   , value);
                    break;
                    
                case 16: // 湲곕낯�닔�웾
                	if("0".equalsIgnoreCase(value) ||
                	   "".equalsIgnoreCase(value)
                    ) {
                		item.put("baseSaleQty", "1");
                    }else {
                    	item.put("baseSaleQty", value);
                    }
                    
                    break;
                    
                case 17: // 理쒕��닔�웾
                    item.put("maxSaleQty", value);
                    break;
                    
                case 18: // 異쒕젰媛��닔
                    item.put("outputQty", value);
                    break;
                    
                case 19: // 怨쇱꽭�뿬遺�(�꽭湲�)
                    value = value.trim().equals("怨쇱꽭") ? "TRUE" : "FALSE";
                    item.put("useTax", value);
                    break;
                    
                case 20: //�씠誘몄� 而щ읆
                    break;
                
                case 21: //�긽�뭹�꽕紐� �븳援��뼱
                    item.put("productExpKr", value);
                    break;
                    
                case 22: //�긽�뭹�꽕紐� �쁺�뼱
                    item.put("productExpEn", value);
                    break;
                    
                case 23: //�긽�뭹�꽕紐� �씪蹂몄뼱
                    item.put("productExpJp", value);
                    break;
                    
                case 24: //�긽�뭹�꽕紐� 以묎뎅�뼱
                    item.put("productExpCn", value);
                    break;
                    
                case 25: //荑좏룿肄붾뱶
                    item.put("couponCd", value);
                    break;
                    
                case 26: //�긽�뭹肄붾뱶
                    item.put("extr2Cd", value);
                    break;
                    
                case 27: //泥④�臾쇱퐫�뱶
                    item.put("extr3Cd", value);
                    break;
                    
                case 28: //湲고��쇅遺�肄붾뱶
                    item.put("extrCd", value);
                    break;
                    
                case 29: //諛붿퐫�뱶
                    item.put("barcodeCd", value);
                    break;
                case 30: //�꽣移��젣�쑕->�룷�씤�듃 �궗�슜�쑝濡� 蹂�寃� 2019.10.14
                    item.put("pointUse",StringUtil.textArrayCodeChange(pointTypeCode,value));
                    break;
                case 31: //�떒怨⑥젣�쑕->�룷�씤�듃 �쟻由쎌쑝濡� 蹂�寃� 2019.10.14
                    item.put("pointSave",StringUtil.textArrayCodeChange(pointTypeCode,value));
                    break;
                case 32: //�긽�뭹�깭洹�
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
}
