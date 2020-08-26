package kjh.common.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kjh.common.dto.Constant;
import kjh.common.dto.Token;

public class LogUtils 
{
    public static String toHeader()
    {
        HttpServletRequest request = null ;

        try
        {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        }
        catch( Exception e )
        {
        }

        return toHeader( request ) ;
    }

    public static String toHeader( HttpServletRequest request )
    {
        StringBuffer buf = new StringBuffer() ;

        if( request == null )
        {
            return buf.toString() ;
        }

        String accessId     = (String) request.getAttribute( Constant.ACCESS_ID  ) ;
        String accessUri    = (String) request.getAttribute( Constant.ACCESS_URI ) ;
        String accessIp     = (String) request.getAttribute( Constant.ACCESS_IP  ) ;
        String httpMethod   = request.getMethod() ;
        
        String workGroupOfficeId        = (String) request.getAttribute( Constant.WORK_GROUP_OFFICE_ID  ) ;
        String workDealerId             = (String) request.getAttribute( Constant.WORK_DEALER_ID        ) ;
        String workHeadOfficeId         = (String) request.getAttribute( Constant.WORK_HEAD_OFFICE_ID   ) ;
        String workFranchiseId          = (String) request.getAttribute( Constant.WORK_FRANCHISE_ID     ) ;
        String workDeviceId             = (String) request.getAttribute( Constant.WORK_DEVICE_ID        ) ;
        
        String trxNo                    = (String) request.getAttribute( Constant.TRX_NO                ) ;

        
        Token token = (Token)request.getAttribute( Constant.ACCESS_TOKEN );
        if( token != null )
        {
            String  tokenId                 = token.getTokenId() ;
            String  tokenUserId             = token.getTokenUserId() ;
            String  tokenUserGroup          = token.getTokenUserGroup() ;
            String  tokenUserRole           = token.getTokenUserRole() ;
            String  tokenLoginType          = token.getTokenLoginType() ;
            String  tokenDealerId           = token.getTokenDealerId() ;
            String  tokenDealerName         = token.getTokenDealerName() ;
            String  tokenGroupOfficeId      = token.getTokenGroupOfficeId() ;
            String  tokenGroupOfficeName    = token.getTokenGroupOfficeName() ;
            String  tokenHeadOfficeId       = token.getTokenHeadOfficeId() ;
            String  tokenHeadOfficeName     = token.getTokenHeadOfficeName() ;
            String  tokenFranchiseId        = token.getTokenFranchiseId() ;
            String  tokenFranchiseName      = token.getTokenFranchiseName() ;
            String  tokenDeviceId           = token.getTokenDeviceId() ;
            Integer tokenDeviceSeq          = token.getTokenDeviceSeq() ;
            // String  trxNo                   = "" ;

            if( ! StringUtil.isEmpty( accessId ))
            {
                buf.append( "호출ID=" + accessId ) ;
            }

            if( ! StringUtil.isEmpty( accessUri ))
            {
                buf.append( ", 호출URI=" + accessUri ) ;
            }

            if( ! StringUtil.isEmpty( httpMethod ))
            {
                buf.append( ", METHOD=" + httpMethod ) ;
            }

            if( ! StringUtil.isEmpty( accessIp ))
            {
                buf.append( ", 접속IP=" + accessIp ) ;
            }

            if( ! StringUtil.isEmpty( tokenId ))
            {
                buf.append( ", 토큰ID=" + tokenId ) ;
            }

            if( ! StringUtil.isEmpty( tokenUserId ))
            {
                buf.append( ", 사용자=" + String.format( "%s(%s:%s:%s)", tokenUserId, tokenUserGroup, tokenUserRole, tokenLoginType ) ) ;
            }

            if( ! StringUtil.isEmpty( tokenDealerId ))
            {
                buf.append( ", 대리점=" + String.format( "%s(%s)", tokenDealerId, tokenDealerName ) ) ;
            }

            if( ! StringUtil.isEmpty( tokenGroupOfficeId ))
            {
                buf.append( ", 그룹사=" + String.format( "%s(%s)", tokenGroupOfficeId, tokenGroupOfficeName ) ) ;
            }

            if( ! StringUtil.isEmpty( tokenHeadOfficeId ))
            {
                buf.append( ", 본사=" + String.format( "%s(%s)", tokenHeadOfficeId, tokenHeadOfficeName ) ) ;
            }

            if( ! StringUtil.isEmpty( tokenFranchiseId ))
            {
                buf.append( ", 가맹점=" + String.format( "%s(%s)", tokenFranchiseId, tokenFranchiseName ) ) ;
            }

            if( ! StringUtil.isEmpty( tokenDeviceId ))
            {
                buf.append( ", 기기=" + String.format( "%s(%d)", tokenDeviceId, tokenDeviceSeq ) ) ;
            }

            if( ! StringUtil.isEmpty( trxNo ))
            {
                buf.append( ", 호출추적번호=" + trxNo ) ;
            }
            
        }
        else
        {
            if( ! StringUtil.isEmpty( accessId ))
            {
                buf.append( "호출ID=" + accessId ) ;
            }

            if( ! StringUtil.isEmpty( accessUri ))
            {
                buf.append( ", 호출URI=" + accessUri ) ;
            }

            if( ! StringUtil.isEmpty( httpMethod ))
            {
                buf.append( ", METHOD=" + httpMethod ) ;
            }

            if( ! StringUtil.isEmpty( accessIp ))
            {
                buf.append( ", 접속Ip=" + accessIp ) ;
            }
        }

        StringBuffer workBuf = new StringBuffer() ;
        if( ! StringUtil.isEmpty( workGroupOfficeId ))
        {
            workBuf.append( "작업그룹사=" + workGroupOfficeId ) ; 
        }

        if( ! StringUtil.isEmpty( workDealerId ))
        {
            if( workBuf.length() > 0 )
            {
                workBuf.append( ", " ) ;
            }
            workBuf.append( "작업대리점=" + workDealerId ) ;
        }

        if( ! StringUtil.isEmpty( workHeadOfficeId ))
        {
            if( workBuf.length() > 0 )
            {
                workBuf.append( ", " ) ;
            }

            workBuf.append( "작업본사=" + workHeadOfficeId ) ;
        }

        if( ! StringUtil.isEmpty( workFranchiseId ))
        {
            if( workBuf.length() > 0 )
            {
                workBuf.append( ", " ) ;
            }
            
            workBuf.append( "작업가맹점=" + workFranchiseId ) ;
        }
        
        if( ! StringUtil.isEmpty( workDeviceId ))
        {
            if( workBuf.length() > 0 )
            {
                workBuf.append( ", " ) ;
            }

            workBuf.append( "작업기기=" + workDeviceId ) ;
        }

        if( workBuf.length() > 0 )
        {
            buf.append( ", " + workBuf.toString() ) ;
        }
        
        String params = parseParam(request ) ; 
        if( ! StringUtil.isEmpty( params ))
        {
            buf.append( ", PARAM=(" + params + ")" ) ;
        }

        if( ! StringUtil.isEmpty( trxNo ))
        {
            buf.append( ", TRX_NO=(" + trxNo + ")" ) ;
        }

        String logHeader = buf.toString();

        return logHeader ;
    }
    
    public static String toReqHeader( HttpServletRequest request )
    {
        String head = toHeader( request ) ;
        
        if( head.startsWith( "," ))
        {
            head = head.substring( 1 ).trim();
        }
        
        head = "[◀" + head + "]" ;
        return head ;
    }
    
    public static String toResHeader( HttpServletRequest request )
    {
        String head = toHeader( request ) ;
        

        Long st = (Long) request.getAttribute( Constant.ACCESS_TIME ) ;
        if( st != null )
        {
            long ed = System.currentTimeMillis() ;
            long elaps = ed - st.longValue() ;
            
            head = head + ( ", 소요시간=" + String.format( "%d (ms)", elaps)) ;
        }
        
        if( head.startsWith( "," ))
        {
            head = head.substring( 1 ).trim() ;
        }

        head = "[▶" + head + "]" ;
        return head ;
    }

    public static String parseHeader( HttpServletRequest request )
    {
        StringBuffer buf = new StringBuffer() ;

        if( request == null )
        {
            return buf.toString();
        }

        String values = (String) request.getAttribute( Constant.REQUEST_HTTP_HEADERS ) ;
        if( values != null )
        {
            return values ;
        }

        Enumeration<String> enm = request.getHeaderNames() ;
        while( enm.hasMoreElements() )
        {
            String key = enm.nextElement() ;
            Object val = request.getHeader( key ) ;

            buf.append( key + "=" + val.toString() + "\n" ) ;
        }

        values = buf.toString() ; 
        request.setAttribute( Constant.REQUEST_HTTP_HEADERS, values ) ;
        return values ;
    }

    public static String parseHeader( ResponseEntity<String> entity )
    {
        StringBuffer buf = new StringBuffer() ;
        
        if( entity == null )
        {
            return buf.toString() ;
        }
        
        HttpHeaders headers = entity.getHeaders() ;
        Iterator itr = headers.keySet().iterator() ;

        while( itr.hasNext() )
        {
            Object key = itr.next() ;
            Object val = headers.get( key ) ;
            buf.append( key.toString() + "=" + val.toString() + "\n" ) ;
        }

        return buf.toString() ;
    }

    public static String parseParam( HttpServletRequest request )
    {
        StringBuffer buf = new StringBuffer() ;
        if( request == null )
        {
            return buf.toString();
        }
        
        String values = (String) request.getAttribute( Constant.REQUEST_PARAM_VALUES ) ;
        if( values != null )
        {
            return values ;
        }
        
        Enumeration<String> enm = request.getParameterNames() ;
        while( enm.hasMoreElements() )
        {
            String key = enm.nextElement() ;

            String[] vals = request.getParameterValues( key ) ;

            for( int i = 0 ; i < vals.length ; i ++ )
            {
                if( buf.length() > 0 )
                {
                    buf.append( "&" ) ;
                }

                buf.append( key + "=" + vals[i] ) ;
            }
        }

        values = buf.toString() ; 
        request.setAttribute( Constant.REQUEST_PARAM_VALUES, values ) ;
        return values ;
    }

    public static String toReqBody(HttpServletRequest request) throws IOException 
    {
        if( request == null )
        {
            return "" ;
        }

        String body = (String) request.getAttribute( Constant.REQUEST_BODY_DATA ) ;
        if( StringUtil.isEmpty( body ))
        {
            body = "" ;
        }

        return body ;
    }

}
