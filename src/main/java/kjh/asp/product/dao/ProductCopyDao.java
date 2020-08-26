package kjh.asp.product.dao;

import kjh.common.dto.CamelMap;

public interface ProductCopyDao {

    public int productCopyProductDetail(CamelMap model) throws Exception;
    public int productCopyProductStock(CamelMap model) throws Exception; 
    public int productCopyCategoryDetail(CamelMap model) throws Exception;
    public int productCopyCategoryProduct(CamelMap model) throws Exception;
    public int productCopyCategorySeq1(CamelMap model) throws Exception;
    public int productCopyCategorySeq2(CamelMap model) throws Exception;
    public int productCopyProductOptionGroup(CamelMap model) throws Exception;
    public int productCopyOptionGroupProduct(CamelMap model) throws Exception ;
    public int productCopyOptionGroupDetail(CamelMap model) throws Exception; 
}
