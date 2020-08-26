package kjh.asp.product.dao;

import java.util.List;

import kjh.common.dto.CamelMap;

public interface ProductDetailHeadOfficeDao {
    public CamelMap prodcutDetailHeadOfficeSelect(CamelMap param);
    public List<CamelMap> updateTargetFranchiseSelect(CamelMap model);
    public CamelMap productTableColumnSearch(CamelMap model);
    public int productHeadOfficeProductTypeUpdate(CamelMap model);
    public int productHeadOfficeCommonUpdate(CamelMap model);
    public List<CamelMap> updateTargetFranchiseCategorySelect(CamelMap model);
    public int productHeadOfficeCategoryMerge(CamelMap model);
    public int categoryProductProcessDelete(CamelMap model);
    public int categoryProductProcessInsert(CamelMap model);
    public List<CamelMap> updateTargetFranchiseOptionSelect(CamelMap model);
    public int productOptionGroupProcessDelete(CamelMap model);
    public int productOptionGroupProcessInsert(CamelMap model);
}
