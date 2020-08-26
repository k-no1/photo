package kjh.common.util;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kjh.common.dto.CamelMap;
import net.sf.json.JSONObject;


public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * �꽦怨�
	 * @param json
	 */
	public static void getReturnCodeSuc(JSONObject json) {
		json.put("rCode", "0000");
		json.put("rMsg", "�셿猷뚮릺�뿀�뒿�땲�떎.");
	}

	/**
	 * �꽦怨듭씠硫� �듅蹂꾪븳 硫붿꽭吏�媛� �븘�슂�븳 寃쎌슦
	 * @param json
	 * @param rMsg
	 */
	public static void getReturnCodeSuc(JSONObject json, String rMsg) {
		json.put("rCode", "0000");
		json.put("rMsg", rMsg);
	}

	/**
	 * �떎�뙣
	 * @param json
	 */
	public static void getReturnCodeFail(JSONObject json) {
		json.put("rCode", "9999");
		json.put("rMsg", "泥섎━以� 臾몄젣媛� 諛쒖깮�븯���뒿�땲�떎.");
	}

	/**
	 * �떎�뙣吏�留� �듅蹂꾪븳 硫붿꽭吏�媛� �븘�슂�븳 寃쎌슦
	 * @param json
	 * @param str
	 */
	public static void getReturnCodeFail(JSONObject json, String str) {
		json.put("rCode", "9999");
		json.put("rMsg", str);
	}

	/**
	 * 寃곌낵�� 硫붿꽭吏�瑜� 媛곴컖 �꽕�젙 �븷 寃쎌슦
	 * @param json
	 * @param rCode
	 * @param rMsg
	 */
	public static void getReturnCodeEtc(JSONObject json, String rCode, String rMsg) {
		json.put("rCode", rCode);
		json.put("rMsg", rMsg);
	}

	/**
	 * client ip �젙蹂�
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")) {
			clientIp = request.getHeader("REMOTE_ADDR");
		}

		if (null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) 
	{
		if (obj instanceof String)
		{
			return obj == null || "".equals(obj.toString().trim());
		}
		else if (obj instanceof List)
		{
			return obj == null || ((List) obj).isEmpty();
		}
		else if (obj instanceof Map)
		{
			return obj == null || ((Map) obj).isEmpty();
		}
		else if (obj instanceof Object[])
		{
			return obj == null || Array.getLength(obj) == 0;
		}
		else
		{
			return obj == null;
		}
	}

	public static boolean isNotEmpty(Object s) {
		return !isEmpty(s);
	}

	public static void close(Closeable... resources) {
		for (Closeable resource : resources) {
			if (resource != null) {
				try {
					resource.close();
				} catch (Exception ignore) {
				    logger.error(ignore.toString());
				}
			}
		}
	}
	
	public static String generateState() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	/**
	 * size留뚰겮�쓽 random value return
	 * @param size
	 * @return
	 */
	public static String getRandomData(int size){
		Random rd = new Random();
		
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<size;i++){
			int r = rd.nextInt(10);
			sb.append(String.valueOf(r));
		}
		
		return sb.toString();
	}

	/**
	 * �꽌踰� �샇�뒪�듃紐� 媛��졇�삤湲�
	 *
	 * @Method Name : getServerName
	 * @�옉�꽦�씪 : 2019. 1. 13.
	 * @�옉�꽦�옄 : s1212919
	 * @蹂�寃쎌씠�젰 : 
	 * @Method �꽕紐� : 
	 * @return
	 * @throws UnknownHostException
	 *
	 */
	public static String getServerName () throws UnknownHostException {
		String result		= "";

		String	hostName		= InetAddress.getLocalHost().getHostName();

		if("VHOST1".equals(hostName) || "VHOST2".equals(hostName)){
			result		= "REAL";
		} else if("WS16E".equals(hostName)) {
			result		= "ACE";
		}else if("dwas01".equals(hostName)){
			result		= "DEV";
		}else{
			result		= "LOCAL";
		}
		
		logger.debug("�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾  whatServer   �쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾");
		logger.debug("hostName:"+hostName);
		logger.debug("�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾"+result+"�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾�쁾");
		return result;
	}
	
	
	public static String getNowTime() {
		// �쁽�옱�떆媛�
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		Date currentTime = new Date ();
	
		return mSimpleDateFormat.format ( currentTime );
	}
	
	public static String paramDecodeUTF8(String param) throws Exception {
		
		String result = "";
		if(isNotEmpty(param)){
			
			try {
				result = URLDecoder.decode(param, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	            logger.error( "{}", LogUtils.toHeader(request), e) ;
				logger.debug("paramDecodeUTF8 E:" + e.toString());
			}
			
		}
		return result;
	}
	
	
}
