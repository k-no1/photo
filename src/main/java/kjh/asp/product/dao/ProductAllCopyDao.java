package kjh.asp.product.dao;

import kjh.common.dto.CamelMap;

public interface ProductAllCopyDao {

    public int productAllCopyProductDetail(CamelMap model) throws Exception;
    public int productAllCopyProductStock(CamelMap model) throws Exception;
    public int productAllCopyCategoryProduct(CamelMap model) throws Exception;
    public int productAllCopyCategorySeq(CamelMap model) throws Exception;
    public int productAllCopyCategoryDetail(CamelMap model) throws Exception;
    public int productAllCopyProductOptionGroup(CamelMap model) throws Exception;
    public int productAllCopyOptionGroupProduct(CamelMap model) throws Exception;
    public int productAllCopyOptionGroupDetail(CamelMap model) throws Exception;
}
