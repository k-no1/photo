package kjh.asp.product.dao;

import java.util.List;

import kjh.common.dto.CamelMap;

public interface ProductDao {
    
    public List<CamelMap> selectManageInfo(CamelMap map) throws Exception;
    public int getProductListCnt(CamelMap model) throws Exception;
    public List<CamelMap> getProductList(CamelMap model) throws Exception; 
    public List<CamelMap> getCategoryList(CamelMap model) throws Exception;
    public CamelMap productDetailSearch(CamelMap model) throws Exception;
    public int productInsert(CamelMap model) throws Exception;
    public int productDetailInsert(CamelMap model) throws Exception;
    public int productOptionGroupDelete(CamelMap model) throws Exception;
    public int productOptionGroupInsert(CamelMap model) throws Exception;
    public int categoryProductDelete(CamelMap model) throws Exception;
    public int categoryProductInsert(CamelMap model) throws Exception;
    public int productUpdate(CamelMap model) throws Exception;
    public int productDetailUpdate(CamelMap model) throws Exception;
    public int productStockInsert(CamelMap model) throws Exception;
    public String tx_isolationSelect(CamelMap model) throws Exception;
    public String productNameChk(CamelMap model) throws Exception;
    public int productDelete(CamelMap model) throws Exception;
    public int productStockUpdate(CamelMap model) throws Exception;
    public List<CamelMap> getProductAllList(CamelMap model) throws Exception;
    public List<CamelMap> productCopyFranchiseChoiceSelect(CamelMap model) throws Exception;
    public List<CamelMap> productCopyCategoryChoiceSelect(CamelMap model) throws Exception;
    public int productCopyChoiceSave(CamelMap model) throws Exception;
    public CamelMap productUidChk(CamelMap model) throws Exception;
    public int productDetailDelete(CamelMap model) throws Exception;
    public int productStockDelete(CamelMap model) throws Exception;
    public int printProductDelete(CamelMap model) throws Exception;
    public CamelMap productIdCheck(CamelMap model) throws Exception;
    public List<CamelMap> getProductTemplete(CamelMap map) throws Exception;
    public String productFileDuplChk(CamelMap shaSearchMap);
    public int productDetailExceptionUpdate(CamelMap model);
    
}
