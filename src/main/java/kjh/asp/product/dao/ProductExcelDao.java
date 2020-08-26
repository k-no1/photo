package kjh.asp.product.dao;

import kjh.common.dto.CamelMap;

public interface ProductExcelDao {
    
    public int productExcelInsert(CamelMap param) throws Exception;
    public int productExcelDetailInsert(CamelMap param) throws Exception;
    public int productExcelStockInsert(CamelMap param) throws Exception;

}
