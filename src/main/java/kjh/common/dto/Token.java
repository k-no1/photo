package kjh.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Token 
{

    private String tokenId ;
    private String tokenUserId ;
    private String tokenLoginType;
    private String  tokenUserName ;
    private String  tokenUserGroup ;
    private String  tokenUserRole ;
    private String  tokenDealerId ;
    private String  tokenDealerName ;
    private String  tokenGroupOfficeId ;
    private String  tokenGroupOfficeName ;
    private String  tokenHeadOfficeId ;
    private String  tokenHeadOfficeName ;
    private String  tokenFranchiseId ;
    private String  tokenFranchiseName ;
    private String  tokenDeviceId ;
    private String  tokenDeviceName ;
    private String  tokenDeviceType ;
    private String  tokenDeviceModel ;
    private Integer tokenDeviceSeq ;
    private String  tokenDeviceNo ;
    private String  tokenDeviceSerial ;
    private String  tokenIssuer ;
    private String  tokenIssueIp ;
    private String  tokenIssueAt ;
    private String  tokenExpireAt ;
    private String  accessId ;
    private String  accessIp ;
    private String  accessAt ;
    private String  requestUri ;
    private String trxNo ;
    private String gwId ;
    private String tokenGroId4LoginUser ;
    private String tokenChannelType ;
    private boolean tokenSaveSales = true ;
    private String TOKEN_SESSION_INFO;

    private String accessAtSkipYn ;

    private String  keyword;
    private Integer page;
    private Integer pageSize;
    private Integer limit;
    private Integer offset;
    String functionName;
    private Integer currentPageNo   = 1;    //default 媛�
    private Integer countPerPage    = 10;   //default 媛�
    private Integer countPerList    = 10;   //default 媛�
    private Integer totalPageCount;
    private Integer totalListCount;
    // For ordering
    private String orderColumn;
    private String orderMethod;

}
