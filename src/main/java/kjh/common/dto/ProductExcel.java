package kjh.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductExcel extends Model {

   private String productId                     ;
   private String headOfficeId                 ;
   private String productMgrName               ;
   private String productType                   ;
   private String productSubject                ;
   private String productOwnerId               ;

   private String productDetailId              ;
   private String franchiseId                   ;
   private String productDetail                 ;
   private String productKr                     ;
   private String productEn                     ;
   private String productJp                     ;
   private String productCn                     ;
   private String visibility                     ;
   private String sale_status                    ;
   private String primeCost                     ;
   private String price                          ;
   private String isStore                       ;
   private String storePrice                    ;
   private String isPacking                     ;
   private String packingPrice                  ;
   private String baseSaleQty                  ;
   private String maxSaleQty                   ;
   private String useTax                        ;
   private String salesTarget                   ;
   private String textBackgroundStyle          ;
   private String textFontSize                 ;
   private String textFontColor                ;
   private String fileId                        ;
   private String productExpKr                 ;
   private String productExpEn                 ;
   private String productExpJp                 ;
   private String productExpCn                 ;
   private String couponCd                      ;
   private String barcodeCd                     ;
   private String extrCd                        ;
   private String extr2Cd                       ;
   private String extr3Cd                       ;
   private String touching                       ;
   private String dangol                         ;
   private String productTags                   ;
   private String productPrintName             ;
   private String exchangePrintQty             ;
   private String viewStatusYn                 ;
   private String productStockId               ;
   private String useLimit                      ;
   private String limitQty                      ;
   private String soldoutYn                     ;
   private String categoryMgrNameArray        ;
   private String realPath                      ;
   private String deletedYn;
   private String createdAt;
   private String createdBy;
   private String updatedAt;
   private String updatedBy;
   private String keywordType;
   private String keyword;
   private String realFilePath;
   private String oriFileName;
   private String outputQty;
   
public void createErrorList(int i, int j, String string)
{
    // TODO Auto-generated method stub
    
}

	
	
}