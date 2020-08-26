package kjh.common.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductSearch extends Model {
    private MultipartFile uploadFile;

    private String fname ;
    private String categoryId;                     // 카테고리 아이디
    private String productId;                      // 상품 아이디
    private String headOfficeId;                   // 본사 아이디
    private String franchiseId;                    // 가맹점 아이디
    private String productMgrName;                 // 상품 관리명
    private String productType;                    // 상품구분
    private String productKr;                      // 상품명(한국어)
    private String productEn;                      // 상품명(영어)
    private String productJp;                      // 상품명(일본어)
    private String productCn;                      // 상품명(중국어)
    private String saleStatus;                     // 판매상태 - ACTIVE:판매 , INACTIVE:판매중지 , SOLDOUT:품절
    private String visibility;                     // 화면 노출여부 - VISIBLE:노출, HIDDEN:비노출
    private String salesTarget;                    // 판매 대상( G:일반 S:직원 )
    private BigDecimal primeCost;                  // 상품원가
    private BigDecimal storePrice;                 // 매장가
    private BigDecimal packingPrice;               // 포장가
    private String useLimit;                       // 한정수량 여부
    private String soldoutYn;                      // 품절여부
    private BigDecimal limitQty;                   // 한정수량 재고
    private BigDecimal baseSaleQty;                // 기본수량
    private BigDecimal maxSaleQty;                 // 최대수량
    private String useTax;                         // 과세여부
    private String textBackgroundStyle;            // 텍스트 배경색상
    private String textFontColor;                  // 텍스트 글자 색상
    private String textFontSize;                   // 텍스트 사이즈
    private String fileId;                         // 이미지ID
    private String productExpKr;                   // 상품명 설명(한국어)
    private String productExpEn;                   // 상품명 설명(영어)
    private String productExpJp;                   // 상품명 설명(일본어)
    private String productExpCn;                   // 상품명 설명(중국어)
    private String couponCd;                       // 쿠폰 코드
    private String barcodeCd;                      // 바코드
    private String extrCd;                         // 기타외부코드
    private String extr2Cd;                        // smartro 상품코드
    private String extr3Cd;                        // smartro 첨가물코드
    private String touchingUse;                    // 터칭제휴 적립
    private String touchingSave;                   // 터칭제휴 할인
    private String dangolUse;                      // 단골 적립
    private String dangolSave;                     // 단골 할인
    private String productTags;                    // 상품TAG
    private String productPrintName;               // 프린터출력명
    private String exchangePrintQty;               // 교환권 프린터 출력갯수
    private String isStore;                        // 매장가
    private String isPacking;                      // 포장가
    private String isStore2;                       // 매장가2
    private String isPacking2;                     // 포장가2
    private String isStore3;                       // 매장가2
    private String isPacking3;                     // 포장가2

    private String textSalesTarget;                //텍스트 탭 판매대상
    private String imgSalesTarget;                 //이미지 탭 판매대상
    
    private List<String> categoryIdList;                        //카테고리 선택정보
    private List<Map<String, Object>> productOptionGroupList;   //상품옵션그룹 선택 정보
    private List<String> printerList;                           //프린터 선택정보
    private List<String> franchiseIdList;                       //가맹점 선택 정보
    private List<String> etrPrintList;                          //외부 프린트 선택 정보
    private List<String> itrPrintList;                          //내부 프린트 선택 정보
    
    private String deletedYn;                      //삭제여부
    private String createdAt;                      //등록일시
    private String createdBy;                      //등록자
    private String updatedAt;                      //수정일시
    private String updatedBy;                      //수정자
    
    private String keywordType;                    //검색타입
    private String keyword;                        //검색어
    private String realFilePath;                   //파일실제경로
    private String oriFileName;                    //실제파일이름
    private String printId;                        //프린터 ID
    
    
   
}
