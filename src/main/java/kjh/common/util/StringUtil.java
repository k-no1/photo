package kjh.common.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kjh.common.dto.CamelMap;
import kjh.common.util.CommonUtil;
import kjh.common.util.LogUtils;




public class StringUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    
	/**
	 * �깭洹몃�� �깭洹명몴�쁽�떇�쑝濡� 諛붽퓞
	 * @param str
	 * @return str(String)
	 */
	public static String HTMLDecoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("\r\n", "<br>");
		return str;
	}

	public static String unescape(String s) {
		if (s == null)
			return "";
		String s1 = replace(s, "\\", "");
		return s1;
	}

	/**
	 * �깭洹명몴�쁽�떇�쓣 �깭洹몃줈 諛붽퓞
	 * @param str
	 * @return str(String)
	 */
	public static String HTMLEncoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("<br>", "\r\n");
		return str;
	}

	/**
	 * �뿏�꽣瑜� br �깭洹몃줈 諛붽퓞
	 * @param str
	 * @return str(String)
	 */
	public static String CarriageReturnDecoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("\r\n", "<br>");
		return str;
	}

    public static String replace(String s, String s1, String s2)
    {
        if(s == null || s1 == null || s2 == null)
            return s;
        StringBuffer stringbuffer = new StringBuffer();
        int i = s1.length();
        int j = 0;
        for(int k = 0; (k = s.indexOf(s1, j)) != -1;)
        {
            stringbuffer.append(s.substring(j, k)).append(s2);
            j = k + i;
        }

        stringbuffer.append(s.substring(j));
        return stringbuffer.toString();
    }


    /**
	 * br �깭洹몃�� �뿏�꽣濡� 諛붽퓞
	 * @param str
	 * @return str(String)
	 */
	public static String CarriageReturnEncoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("<br>", "\r\n");
		return str;
	}

	/**
	 * �뿏�꽣瑜� �뾾�빊
	 * @param str
	 * @return str(String)
	 */
	public static String CarriageReturnDelete(String str) {
		if(str==null) str="";
		str = str.replaceAll("\r\n", "");
		return str;
	}

	/**
	 * �떛湲�荑쇳듃瑜� �쑀�땲肄붾뱶濡� �쑝濡� 蹂��솚
	 * @param str
	 * @return str(String)
	 */
	public static String SingleQuotToUniCode(String str) {
		if(str==null) str="";
		str = str.replaceAll("'", "&#39");
		return str;
	}

	/**
     * null媛� �삉�뒗 鍮� 臾몄옄�쓣 泥댄겕�빐�꽌 吏��젙�맂 臾몄옄�뿴濡� ��泥�
     * @param str - 蹂��솚�븷 臾몄옄�뿴
     * @param newStr - 蹂��솚�맂 臾몄옄�뿴
     * @return �꼸 �삉�뒗 鍮� 臾몄옄��泥� 臾몄옄�뿴
     */
    public static String nvl(String str, String newStr) {
        if(str == null || str.trim().equals("")) {
            return newStr;
        } else {
            return str;
        }
    }

    /**
     * BigDecimal�쓽 null媛믪쓣 泥댄겕�빐�꽌 吏��젙�맂 BigDecimal�쓽 ��泥�
     * @param bigDecimal
     * @param newBigDecimal
     * @return �꼸 ��泥� BigDecimal
     */
    public static BigDecimal nvl(BigDecimal bigDecimal, BigDecimal newBigDecimal) {
    	if(bigDecimal == null) {
    		return newBigDecimal;
    	} else {
    		return bigDecimal;
    	}
    }

    /**
     * null媛� �삉�뒗 鍮� 臾몄옄�쓣 泥댄겕�빐�꽌 吏��젙�맂 臾몄옄�뿴濡� ��泥�
     * @param str
     * @return 鍮덈Ц�옄 ""
     */
    public static String nvl(String str) {
        if(str == null || str.trim().equals("")) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * BigDecimal�쓽 null媛믪쓣 泥댄겕�빐�꽌 吏��젙�맂 BigDecimal�쓽 ��泥�
     * @param bigDecimal
     * @return �꼸 ��泥� BigDecimal(0)
     */
    public static BigDecimal nvl(BigDecimal bigDecimal) {
    	if(bigDecimal == null) {
    		return new BigDecimal(0);
    	} else {
    		return bigDecimal;
    	}
    }

    /**
     * null媛� �삉�뒗 鍮� �닽�옄瑜� 泥댄겕�빐�꽌 吏��젙�맂 �닽�옄濡� ��泥�
     * @param str - 蹂��솚�븷 �닽�옄
     * @param newStr - 蹂��솚�맂 臾몄옄�뿴
     * @return �꼸 �삉�뒗 鍮� 臾몄옄��泥� 臾몄옄�뿴
     */
    public static int nvl(Integer iVar, String newStr) {
        if(iVar == null) {
            return new Integer(newStr).intValue();
        } else {
            return iVar;
        }
    }

    /**
     * null媛� �삉�뒗 鍮� �닽�옄瑜� 泥댄겕�빐�꽌 吏��젙�맂 �닽�옄濡� ��泥�
     * @param str - 蹂��솚�븷 �닽�옄
     * @param newStr - 蹂��솚�맂 臾몄옄�뿴
     * @return �꼸 �삉�뒗 鍮� 臾몄옄��泥� 臾몄옄�뿴
     */
    public static long nvl(Long iVar, String newStr) {
        if(iVar == null) {
            return new Long(newStr).longValue();
        } else {
            return iVar;
        }
    }

    /**
     * null媛� �삉�뒗 鍮� �닽�옄瑜� 泥댄겕�빐�꽌 吏��젙�맂 �닽�옄濡� ��泥�
     * @param iVar - 蹂��솚�븷 �닽�옄
     * @param newVar - 蹂��솚�맂 �닽�옄
     * @return �꼸 �삉�뒗 鍮� 臾몄옄��泥� 臾몄옄�뿴
     */
    public static long nvl(Long iVar, long newVar) {
        if(iVar == null) {
            return new Long(newVar);
        } else {
            return iVar;
        }
    }

    /**
     * �넗�겙�쑝濡� 臾몄옄�뿴�쓣 異붿텧�썑 吏��젙�븳 �쐞移섏뿉 �엳�뒗 臾몄옄�뿴�쓣 異붿텧
     * @param str
     * @param token
     * @param position
     * @return String 遺꾨━�맂 臾몄옄�뿴
     */
    public static String tokenPosition(String str, String token, int position) {
    	int i = 0;
    	String result = "";
    	StringTokenizer st = new StringTokenizer(str, token);
    	while(st.hasMoreTokens()) {
    		if(i == position) {
    			result = st.nextToken();
    			break;
    		} else {
    			st.nextToken();
    		}
    		i++;
    	}
    	return result;
    }

    /**
     * String 臾몄옄�뿴 諛곗뿴�쓣 援щ텇�옄瑜� �룷�븿�븯�뿬 �븯�굹�쓽 �떒�씪 臾몄옄濡� 蹂��솚 �썑 諛섑솚
	 * �삁) String[] array = {"aaa","bbb","ccc"}
     * String result = combineStringArray(array,",");
     * return "aaa,bbb,ccc"
     * @param   strArray
     * @param   delim
     * @return  �빐�떦 臾몄옄�뿴�쓣 議고빀�븳 �깉濡쒖슫 臾몄옄�뿴
     */
    public static String combineStringArray(String[] strArray, String delim) {
        String result = "";
        if (strArray != null) {
            for (int k = 0; k < strArray.length; k++) {
                result += strArray[k];
                if (strArray.length != k + 1) {
                    result += delim;
                }
            }
            return result;
        } else {
        	return null;
        }
    }

    /**
     * �꽆�뼱�삩 臾몄옄�뿴�쓣 �빐�떦 援щ텇�옄濡� �옒�씪 諛곗뿴濡� 諛섑솚�븳�떎
	 * �삁) String[] result = divideStringArray("A01,B01,C01", ",");
	 * return "A01,B01,C01"
     * @param    str
     * @param    delim
     * @return   遺꾨━�맂 臾몄옄�뿴 諛곗뿴
     */
    public static String[] divideStringArray(String str, String delim) {
        if (str == null) return null;
        String[] result = null;
        StringTokenizer st = new StringTokenizer(str, delim);
        result = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++)
            result[i] = st.nextToken();
        return result;
    }

    /**
	 * �꽆�뼱�삩 臾몄옄�뿴�쓣 length留뚰겮�뵫 �옒�씪 諛곗뿴濡� 諛섑솚�븳�떎<br>
	 * �삁) String[] result = divideStringLength("A01B01C01", 3);<br>
	 *     return {"A01","B01","C01"}
	 * @param str
	 * @param len
	 * @return 遺꾨━�맂 臾몄옄�뿴 諛곗뿴
	 */
	public static String[] divideStringLength(String str, int len){

		if( str == null ) return null;

		int array_count = str.length()/len;
		String[] result = new String[array_count];
		int start = 0;
		int end   = 0;
		for(int i=0; i<array_count; i++){
			start = i*len;
			end   = (i+1)*len;
			result[i] = str.substring(start,end);
		}
		return result;
	}

    /**
     * 臾몄옄�뿴 諛곗뿴�쓣 �떛�겢荑쇳듃 援щ텇�옄 �삎�깭�쓽 臾몄옄濡� 蹂��솚 �썑 諛섑솚
	 * �삁)<br> String[] array = {"aaa","bbb","ccc"}
     * String result = getSingleQuotSection(array)
     * return "'aaa','bbb','ccc'"
     * @param   array
     * @return  �빐�떦 臾몄옄�뿴�쓣 議고빀�븳 �깉濡쒖슫 臾몄옄�뿴
     */
    public static String getSingleQuotSection(String[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append("'").append(array[i]).append("'").append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * �빐�떦臾몄옄媛� 紐뉕컻媛� �엳�뒗吏� 媛��닔 諛섑솚
     * @param   str
     * @param   searchStr
     * @return  寃��깋 移댁슫�꽣
     */
    public static int getSearchStringCount(String str, String searchStr) {
    	StringTokenizer st = new StringTokenizer(str, searchStr);
    	return st.countTokens();
    }

    /**
	 * �븳湲�媛믪쓣 url 二쇱냼濡� �꽆寃⑥쨪 �닔 �엳寃� �씤肄붾뵫 �븿
	 * @param str
	 * @return �씤肄붾뵫�맂 臾몄옄�뿴
	 */
	public static String URLEncoder(String str) {
		String encodeStr = "";
		try {
			if(str==null) {
				str = "";
			}
			encodeStr = URLEncoder.encode(str, "UTF-8");
		} catch(UnsupportedEncodingException ex) {
		    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), ex );
			//ex.printStackTrace(System.out);
			return null;
		}
		return encodeStr;
	}

	/**
	 * �븳湲�媛믪쓣 url 二쇱냼濡� 諛쏆쓣 �닔 �엳寃� �뵒肄붾뵫 �븿
	 * @param str
	 * @return �뵒肄붾뵫�맂 臾몄옄�뿴
	 */
	public static String URLDecoder(String str) {
		String decodeStr = "";
		try {
			if(str==null) {
				str = "";
			}
			decodeStr = URLDecoder.decode(str, "UTF-8");
		} catch(UnsupportedEncodingException ex) {
		    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), ex );
			//ex.printStackTrace(System.out);
			return null;
		}
		return decodeStr;
	}

	/**
	 * 8859_1�쓣 KSC5601(EUC_KR)濡� 蹂��솚
	 * @param ascii - 8859_1濡� 議고빀�맂 臾몄옄�뿴
	 * @return EUC_KR濡� 議고빀�맂 臾몄옄�뿴
	 */
	public static String toEUC_KR(String ascii){
		if (ascii == null)
			return null;
		try{
			ascii = new String ( ascii.getBytes ("8859_1"), "EUC_KR" );
		}catch(Exception e){
		    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), e);
			//e.printStackTrace(System.out);
			return null;
		}
		return ascii;
	}

	/**
	 * KSC5601(EUC_KR)�쓣 8859_1濡� 蹂��솚
	 * @param euc_kr - EUC_KR濡� 議고빀�맂 臾몄옄�뿴
	 * @return 8859_1濡� 議고빀�맂 臾몄옄�뿴
	 */
	public static String toEn(String euc_kr){
		if (euc_kr == null)
			return null;
		try{
			euc_kr = new String ( euc_kr.getBytes ("EUC_KR"), "8859_1" );
		}catch(Exception e){
		    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), e);
			//e.printStackTrace(System.out);
			return null;
		}
		return euc_kr;
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씤吏�留� 寃��궗�븯�뿬 留욎쑝硫� true �븘�땲硫� false諛섑솚
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 �뿬遺�
	 */
	public static boolean isNull(String str){
		return (str == null) ? true : false;
	}

	/**
	 * �빐�떦 媛앹껜媛� null�씤吏�留� 寃��궗�븯�뿬 留욎쑝硫� true �븘�땲硫� false諛섑솚
	 * @param obj - 寃��궗�븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 �뿬遺�
	 */
	public static boolean isNull(Object obj){
		return (obj == null) ? true : false;
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씠嫄곕굹 怨듬갚�씠硫� true �븘�땲硫� false諛섑솚
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 �뿬遺�
	 */
	public static boolean isEmptyString(String str){
		return (str == null || "".equals(str)) ? true : false;
	}

	/**
	 * �빐�떦 媛앹껜媛� null �씠嫄곕굹 empty �씪寃쎌슦 true瑜� 諛섑솚
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		return (obj == null || "".equals(obj.toString())) ? true : false;
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씠嫄곕굹 怨듬갚�씠硫� 0�쓣 洹몃젃吏� �븡�쑝硫� �빐�떦 臾몄옄�쓽 int媛믪쓣 諛섑솚�븳�떎
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 �닽�옄
	 */
	public static int toInt(String str){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? 0 : Integer.parseInt(str);
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씠嫄곕굹 怨듬갚�씠硫� 0�쓣 洹몃젃吏� �븡�쑝硫� �빐�떦 臾몄옄�쓽 char媛믪쓣 諛섑솚�븳�떎
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 臾몄옄
	 */
	public static char toChar(String str){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString || str.length() > 1) ? 0 : str.charAt(0);
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씠嫄곕굹 怨듬갚�씠硫� 0媛믪쓣 洹몃젃吏� �븡�쑝硫� �빐�떦 臾몄옄�뿴 BigDecimal媛믪쓣 諛섑솚
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 �닽�옄
	 */
	public static BigDecimal toBigDecimal(String str){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? new BigDecimal(0) : new BigDecimal(str);
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씠嫄곕굹 怨듬갚�씠硫� ��泥� 臾몄옄�뿴�쓣 諛섑솚�븳�떎
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @param newStr - ��泥댄븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 臾몄옄�뿴
	 */
	public static String toBlank(String str, String newStr){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? newStr : str;
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씠嫄곕굹 怨듬갚�씠硫� ��泥댄븷 臾몄옄�뿴 �닽�옄瑜� 諛섑솚�븳�떎
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @param newStr - ��泥댄븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 �닽�옄
	 */
	public static int toInt(String str, String newStr){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? Integer.parseInt(newStr) : Integer.parseInt(str);
	}

	/**
	 * �빐�떦 臾몄옄媛� null�씠嫄곕굹 怨듬갚�씠硫� �빐�떦 臾몄옄�쓽 char媛믪쓣 諛섑솚�븳�떎
	 * @param str - 寃��궗�븷 臾몄옄�뿴
	 * @param newStr - ��泥댄븷 臾몄옄�뿴
	 * @return 寃��궗 寃곌낵 臾몄옄
	 */
	public static char toChar(String str, char newStr){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString || str.length() > 1) ? newStr : str.charAt(0);
	}

	/**
	 * 臾몄옄�쓽 援щ텇�옄�쓽 援щ텇�븳 媛믪쨷 留덉�留� 臾몄옄
	 * @param s	- 臾몄옄�뿴
	 * @param delim - 援щ텇�옄
	 * @return
	 */
	public static String getLast(String s, String delim) {
		if (isNull(s))
			return "null";
		if (!contains(s, delim))
			return s;
		StringTokenizer st = new StringTokenizer(s, delim);
		String last  = null;
		while (st.hasMoreTokens()) {
			last = st.nextToken();
		}
		return last;
	}

	/**
	 *
	 * @param s
	 * @param in
	 * @return
	 */
	public static boolean contains(String s, String in) {
		if (isNull(s))
				return false;
		return s.indexOf(in) > -1 ? true : false;
	}

	/**
	 *
	 * @param s
	 * @param in
	 * @return
	 */
	public static boolean contains(String s, String[] in) {
		boolean flag = false;
		if (isNull(in))
			return false;
		for (int i=0; i<in.length; i++) {
			if (equals(s, in[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}

    public static boolean equals(String s, String s1) {
        if(s == null && s1 == null)
            return true;
        if(s == null || s1 == null)
            return false;
        else
            return s.equals(s1);
    }

    public static boolean equalsIgnoreCase(String s, String s1) {
        if(s == null && s1 == null)
            return true;
        if(s == null || s1 == null)
            return false;
        else
            return s.equalsIgnoreCase(s1);
    }

    public static boolean equals(int i, int j) {
        return i == j;
    }

	public static String toUpperCase(String s) {
		if (isNull(s))
			return s;
		return s.toUpperCase();
	}

	public static String toLowerCase(String s) {
		if (isNull(s))
			return s;
		return s.toLowerCase();
	}

    public static int getByte(String s)
    {
        int i = 1;
        if(s != null)
        {
            for(int j = 0; j < s.length(); j++)
            {
                char c = s.charAt(j);
                if(isHalf(c))
                    i++;
                else
                    i += 2;
            }

        }
        return i;
    }

    private static boolean isHalf(char c)
    {
        return ' ' <= c && c < '\177';
    }

    public static String cut(String s, int max)
	{
		String tBuff = null;
		byte[] abTmp = null;
		int    nIdx  = 0;

		if ( s == null ) {
			return "";
		}


		abTmp = s.getBytes();
		if ( abTmp.length > max ) {
			for( int i = 0; i< i; i++ ) {
				if ( abTmp[i] < 0 ) {
					nIdx++;
				}
			}

			if ( nIdx % 2 == 0 ) {
				tBuff = new String( abTmp, 0, max );
			} else {
				tBuff = new String( abTmp, 0, max - 1 ) + " ";
			}
		}
		else {
			tBuff = s;
		}
		return tBuff;
	}

    // Right Left a String with a specified character.
    public static String lpad(String s, int n , String replace )
    {
        return  StringUtils.leftPad(s, n, replace);
    }

    // Right pad a String with a specified character.
    public static String rpad(String s, int n , String replace )
    {
        return  StringUtils.rightPad(s, n, replace);
    }

    // +1 => Left pad a String with a specified character.
    public static String increaseLpad(String s, int n , String replace )
    {
        long tempValue = Long.parseLong(s);
        tempValue = tempValue + 1;
        s = Long.toString(tempValue);
        return  lpad(s, n, replace);
    }

    /**
     * �떆�떚�븘�씠�뵒瑜� 寃��궗�븯�뿬 鍮덈Ц�옄 �씪寃쎌슦 default 濡� 諛섑솚
     * @param citySeq
     * @return citySeq
     */
    public static long nvlCitySeq(String citySeq) {
    	long lCitySeq = 0;
    	if(citySeq==null) {
    	} else {
    		lCitySeq = new Long(citySeq).longValue();
    	}
    	return lCitySeq;
    }

    /**
     * �벑濡앹옄 �븘�씠�뵒 寃��궗�븯�뿬 鍮덈Ц�옄 �씪寃쎌슦 system �쑝濡� 諛섑솚
     * @param registId
     * @return registId
     */
    public static String nvlRegistId(String registId) {
    	if(registId==null || registId.equals("")) {
    		registId = "admin";
    	}
    	return registId;
    }

    /**
     * �벑濡앹옄 �씠由� 寃��궗�븯�뿬 鍮덈Ц�옄 �씪寃쎌슦 �떆�뒪�뀥 �쑝濡� 諛섑솚
     * @param registName
     * @return registName
     */
    public static String nvlRegistName(String registName) {
    	if(registName==null || registName.equals("")) {
    		registName = "愿�由ъ옄";
    	}
    	return registName;
    }

    /**
     * �벑濡앹옄 �븘�씠�뵾 寃��궗�븯�뿬 鍮덈Ц�옄 �씪寃쎌슦 127.0.0.1 �쑝濡� 諛섑솚
     * @param nvlRegistIp
     * @return nvlRegistIp
     */
    public static String nvlRegistIp(String nvlRegistIp) {
    	if(nvlRegistIp==null || nvlRegistIp.equals("")) {
    		nvlRegistIp = "127.0.0.1";
    	}
    	return nvlRegistIp;
    }

    /**
     * @MethodName : getSessionCityId
     * @�옉�꽦�씪	   : 2010. 4. 15.
     * @�옉�꽦�옄	   : �끂�긽�슜
     * @蹂�寃쎌씠�젰   :
     * @Method�꽕紐� : �꽭�뀡 �떆�떚 �븘�씠�뵒 諛섑솚
     * @param request
     * @return
     */
    public static long getSessionCityId(HttpServletRequest request) {
    	HttpSession httpSession = request.getSession();
		return nvlCitySeq((String) httpSession.getAttribute("citySeq"));
    }

    /**
     * @MethodName : setCitySession
     * @�옉�꽦�씪	   : 2010. 4. 15.
     * @�옉�꽦�옄	   : �끂�긽�슜
     * @蹂�寃쎌씠�젰   :
     * @Method�꽕紐� : �삤釉뚯젥�듃�뿉 �떆�떚�씤�뜳�뒪�� 濡쒓렇 �뜲�씠��瑜� 吏��젙�븳�떎
     * @param request
     * @param target
     */
    public static void setCitySession(HttpServletRequest request, Object target) {

    	long cityId = getSessionCityId(request);

    	setCityIdInfo(cityId, target);

    	setSessionLogInfo(request, target);
    }

    /**
     * @MethodName : setCityIdInfo
     * @�옉�꽦�씪	   : 2010. 4. 15.
     * @�옉�꽦�옄	   : �끂�긽�슜
     * @蹂�寃쎌씠�젰   :
     * @Method�꽕紐� : �삤釉뚯젥�듃�뿉 �떆�떚�씤�뜳�뒪瑜� 吏��젙�븳�떎
     * @param cityId
     * @param target
     */
    @SuppressWarnings("unchecked")
	public static void setCityIdInfo(long cityId, Object target) {
    	if(target instanceof HashMap<?, ?>) {
    		((HashMap)target).put("citySeq", cityId);
    	} else {
    		Class clazz = target.getClass();
    		Field[] field = clazz.getDeclaredFields();
    		String fieldName = null;
    		String tmpString;

    		for(int i = 0; i < field.length; i++) {
    			fieldName = field[i].getName();
    			String fieldType = field[i].getType().getName();

    			if(fieldName.equals("citySeq")) {
    				tmpString = new String(String.valueOf(cityId));
    				field[i].setAccessible(true);

    				if(fieldType.equals("long")) {
    					try {
							field[i].setLong(target, Long.parseLong(tmpString));
						} catch (NumberFormatException e) {
						    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				            logger.error( "{}", LogUtils.toHeader(request), e);
						} catch (IllegalArgumentException e) {
						    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				            logger.error( "{}", LogUtils.toHeader(request), e);
						} catch (IllegalAccessException e) {
						    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				            logger.error( "{}", LogUtils.toHeader(request), e);
						}
    				} else if(fieldType.equals("java.lang.Long")) {
    					try {
							field[i].set(target, Long.valueOf(tmpString));
						} catch (NumberFormatException e) {
						    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				            logger.error( "{}", LogUtils.toHeader(request), e);
						} catch (IllegalArgumentException e) {
						    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				            logger.error( "{}", LogUtils.toHeader(request), e);
						} catch (IllegalAccessException e) {
						    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				            logger.error( "{}", LogUtils.toHeader(request), e);
						}
    				}

    			}
    		}
    	}
    }

    /**
     * @MethodName : setSessionLogInfo
     * @�옉�꽦�씪	   : 2010. 4. 15.
     * @�옉�꽦�옄	   : �끂�긽�슜
     * @蹂�寃쎌씠�젰   :
     * @Method�꽕紐� : �삤釉뚯젥�듃�뿉 濡쒓렇 �뜲�씠��瑜� 吏��젙�븳�떎
     * @param request
     * @param target
     */
    @SuppressWarnings("unchecked")
	public static void setSessionLogInfo(HttpServletRequest request, Object target) {

    	HttpSession httpSession = request.getSession();

    	String registId = nvlRegistId((String) httpSession.getAttribute("registId"));
    	String registName = nvlRegistName((String) httpSession.getAttribute("registName"));
    	String registIp = request.getRemoteAddr();

    	if(target instanceof HashMap<?, ?>) {
    		((HashMap)target).put("registId", registId);
    		((HashMap)target).put("registName", registName);
    		((HashMap)target).put("registIp", registIp);
    	} else {
    		Class clazz = target.getClass();
    		Field[] field = clazz.getDeclaredFields();
    		String fieldName = null;
    		String tmpString;

    		for(int i = 0; i < field.length; i++) {
    			fieldName = field[i].getName();

    			if(fieldName.equals("registId")) {
    				tmpString = new String(registId);
    				field[i].setAccessible(true);
    				try {
						field[i].set(target, tmpString);
					} catch (IllegalArgumentException e) {
			            logger.error( "{}", LogUtils.toHeader(request), e);
					} catch (IllegalAccessException e) {
			            logger.error( "{}", LogUtils.toHeader(request), e);
					}
    			}

    			if(fieldName.equals("registName")) {
    				tmpString = new String(registName);
    				field[i].setAccessible(true);
    				try {
						field[i].set(target, tmpString);
					} catch (IllegalArgumentException e) {
			            logger.error( "{}", LogUtils.toHeader(request), e);
					} catch (IllegalAccessException e) {
			            logger.error( "{}", LogUtils.toHeader(request), e);
					}
    			}

    			if(fieldName.equals("registIp")) {
    				tmpString = new String(registIp);
    				field[i].setAccessible(true);
    				try {
						field[i].set(target, tmpString);
					} catch (IllegalArgumentException e) {
			            logger.error( "{}", LogUtils.toHeader(request), e);
					} catch (IllegalAccessException e) {
			            logger.error( "{}", LogUtils.toHeader(request), e);
					}
    			}
    		}
    	}
    }

    /**
	 * �뙆�씪�씠由꾨쭔 �븣�븘�깂
	 * @param fileName
	 * @return fileName(String)
	 */
	@SuppressWarnings("unused")
	public static String getFileName(String fileName) {
		int idx = fileName.lastIndexOf("\\");
		if(idx == -1) {
			idx = fileName.lastIndexOf("/");
		}

		String filePath_ = "";
		String fileName_ = "";

		if(idx == -1) {
			//ie8
			fileName_ = fileName;
		} else {
			//other
			filePath_ = fileName.substring(0, idx);
			fileName_ = fileName.substring(idx + 1);
		}

		return fileName_;
	}

	/**
     * @MethodName : HtmlTagClean
     * @�옉�꽦�씪	   : 2010. 4. 16.
     * @�옉�꽦�옄	   : �끂�긽�슜
     * @蹂�寃쎌씠�젰   :
     * @Method�꽕紐� : html �깭洹� �젣嫄�
     * @param s
     * @return String
     */
    public static String HtmlTagClean(String s) {
    	if (s == null) {
    		return null;
    	}

    	Matcher m;

    	m = Patterns.SCRIPTS.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.STYLE.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.TAGS.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.ENTITY_REFS.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.WHITESPACE.matcher(s);
    	s = m.replaceAll(" ");

    	return s;
    }

    /**
     * @FileName	 : StringUtil.java
     * @Project      : cla-madame-core
     * @Date         : 2010. 4. 16.
     * @�옉�꽦�옄       : �끂�긽�슜
     * @蹂�寃쎌씠�젰	 :
     * @�봽濡쒓렇�옩�꽕紐� : HTML �젣嫄� 蹂�寃� �뙣�꽩
     */
    private interface Patterns {
    	// javascript tags and everything in between
    	public static final Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);

    	public static final Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
    	// HTML/XML tags

    	public static final Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");

		@SuppressWarnings("unused")
		public static final Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
    	// entity references
    	public static final Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
    	// repeated whitespace
    	public static final Pattern WHITESPACE = Pattern.compile("\\s\\s+");
    }




	/**
	 * Object媛� null �씪寃쎌슦 湲곕낯臾몄옄�뿴�쓣 由ы꽩�븳�떎.
	 *
	 * @param obj
	 *            Object
	 * @param def
	 *            湲곕낯 臾몄옄�뿴
	 * @return String
	 * @sample StringUtil.convNullObj(obj, "");
	 */
	public static String convNullObj(Object obj, String def) {
            if(def == null){
                def = "";
            }
            if (obj==null || "".equals(obj)){
                    return def;
            }
            return obj.toString().trim();
	}

    /**
     * Object媛� null �씪寃쎌슦 ""�쓣 由ы꽩�븳�떎.
     *
     * @param obj
     *            Object
     * @return String obj媛� 蹂��솚�맂 String. null �씪寃쎌슦�뒗 "".
     * @sample StringUtil.convNullObj(obj);
     */
    public static String convNullObj(Object obj) {
        if (obj==null){
            return "";
        }
        return obj.toString();
    }


    /**
     * @Method Name	: telNoFormat
     * @�옉�꽦�씪	    : 2011. 1. 27.
     * @�옉�꽦�옄	    : nohjh
     * @Method �꽕紐�	: �쟾�솕踰덊샇�뿉 援щ텇�옄(-)瑜� �꽔�뼱 �몴�쁽�븳�떎.
     * @param String telNo
     * @return String obj媛� 蹂��솚�맂 String. null �씪寃쎌슦�뒗 "".
     */
    public static String telNoFormat(String param){
    	String telNo = "";
    	if(param==null){
    		telNo = "";
    	}else if(param.length()<9 || param.length()>11){
    		telNo = param;
    	}else if(param.length()==9){					// 02-123-1234
			telNo = param.substring(0, 2)+"-"+param.substring(2, 5)+"-"+param.substring(5);
    	}else if(param.length()==10){
    		if(param.startsWith("02")){				// 02-1234-1234
    			telNo = param.substring(0, 2)+"-"+param.substring(2, 6)+"-"+param.substring(6);
    		}else{												// 011-123-1234
    			telNo = param.substring(0, 3)+"-"+param.substring(3, 6)+"-"+param.substring(6);
    		}
    	}else if(param.length()==11){				// 032-1234-1234, 010-1234-1234
    		telNo = param.substring(0, 3)+"-"+param.substring(3, 7)+"-"+param.substring(7);
		}
    	return telNo;
    }

    /**
     * @Method Name	: containsHangul
     * @�옉�꽦�씪	    : 2011. 2. 12.
     * @�옉�꽦�옄	    : DOLLY217
     * @Method �꽕紐�	: �뒪�듃留곸뿉 �룷�븿�맂 �븳湲��쓣 substring�쑝濡� �옄瑜닿린 �쐞�빐 泥섎━�븯�뒗 �븿�닔
     * @param str
     * @return
     */
    public static String containsHangul(String str) {
		String returnValue = "";
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if (Character.UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock)
					|| Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
							.equals(unicodeBlock)
					|| Character.UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock))
				returnValue = (new StringBuilder()).append(returnValue).append(
						ch).append(" ").toString();
			else
				returnValue = (new StringBuilder()).append(returnValue).append(
						ch).toString();
		}

		return returnValue;
	}

    /**
     * @Method Name	: maskEmail
     * @�옉�꽦�씪	    : 2011. 2. 12.
     * @�옉�꽦�옄	    : DOLLY217
     * @Method �꽕紐�	: �씠硫붿씪 二쇱냼�쓽 �씪遺�瑜� 留덉뒪�겕 泥섎━�븳�떎. dolly2**@famz.co.kr
     * @param str
     * @return
     */
    public static String maskEmail(String str) {
		String returnValue = "";

		StringTokenizer token = new StringTokenizer(str, "@");
		if(token != null){
			String email1= 	token.nextToken();
			String email2 = token.nextToken();
			returnValue = email1.substring(0,email1.length()-2)+"**@"+email2;
		}

		return returnValue;
	}

    /**
     * @Method Name	: maskPhoneNumber
     * @�옉�꽦�씪	    : 2011. 2. 12.
     * @�옉�꽦�옄	    : DOLLY217
     * @Method �꽕紐�	: �쟾�솕踰덊샇�쓽 �씪遺�瑜� 留덉뒪�겕 泥섎━�븳�떎. 010-****-2172
     * @param str
     * @return
     */
    public static String maskPhoneNumber(String str) {
		String returnValue = "";

		StringTokenizer token = new StringTokenizer(str, "-");
		if(token != null){
			String number1= 	token.nextToken();
			String number2 = token.nextToken();
			String number3 = token.nextToken();
			String mask = "";
			for(int i = 0 ; i < number2.length() ; i++){
				mask += "*";
			}
			returnValue = number1+"-"+mask+"-"+number3;
		}

		return returnValue;
	}

    public static String setPhoneNumber(String str) {
    	String returnValue = "";
    	if(str  == null) {
    		returnValue = "";
    	}
    	if(str.length() == 8) {
    		returnValue = str.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
    	} else if (str.length() == 12) {
    		returnValue = str.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
    	} else {
    		returnValue = str.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    	}
    	return returnValue;
    }
    /**
     * �뒪�듃留� �옄瑜닿린
     * 吏��젙�븳 �젙�닔�쓽 媛쒖닔 留뚰겮 鍮덉뭏(" ")�쓣 �뒪�듃留곸쓣 援ы븳�떎.
     * �젅�떒�맂 String�쓽 諛붿씠�듃 �닔媛� �옄瑜� 諛붿씠�듃 媛쒖닔瑜� �꽆吏� �븡�룄濡� �븳�떎.
     *
     * @param str �썝蹂� String
     * @param int �옄瑜� 諛붿씠�듃 媛쒖닔
     * @param char + or -
     * @return String �젅�떒�맂 String
     */
     public static String cutStr(String str, int length ,char type) {
    	 byte[] bytes = str.getBytes();
    	 int len = bytes.length;
    	 int counter = 0;

    	 if (length >= len) {
		 	StringBuffer sb = new StringBuffer();
		 	sb.append(str);
		 	for(int i=0;i<length-len;i++){
	 			sb.append(' ');
		 	}
		 	return sb.toString();
    	 }

    	 for (int i = length - 1; i >= 0; i--) {
    		 if (((int)bytes[i] & 0x80) != 0)
    			 counter++;
    	 }

    	 String f_str = null;

    	 if(type == '+'){
    		 f_str = new String(bytes, 0, length + (counter % 3));
    	 }else if(type == '-'){
    		 f_str = new String(bytes, 0, length - (counter % 3));
    	 }else{
    		 f_str = new String(bytes, 0, length - (counter % 3));
    	 }

    	 return f_str;
     }

     @SuppressWarnings("unused")
	public static String encodeReturnUrl(String s) throws IOException {

 		byte byte0 = 10;

 		byte[] bString = s.getBytes();

 		StringBuffer stringbuffer = new StringBuffer(bString.length);

 		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(byte0);

 		OutputStreamWriter outputstreamwriter = new OutputStreamWriter(bytearrayoutputstream);

 		int mValue = 0;
 		try {
     		for (int i = 0; i < bString.length; i++)
    
     		{
    
     			char c = (char) bString[i];
    
     			mValue = (bString[i] < 0) ? bString[i] + 256 : bString[i];
    
     			// if(mValue > 127){ //2byte
    
     			// stringbuffer.append(Integer.toHexString(c));
    
     			// }
    
     			// else{
    
     			if (dontNeedEncoding.get(c))
    
     			{
    
     				stringbuffer.append(Integer.toHexString(c));
    
     				continue;
    
     			}
    
     			try
    
     			{
    
     				outputstreamwriter.write(c);
    
     				outputstreamwriter.flush();
    
     			}
    
     			catch (IOException _ex)
    
     			{
    
     				bytearrayoutputstream.reset();
     				    
     				continue;
    
     			}
    
     			byte abyte0[] = bytearrayoutputstream.toByteArray();
    
     			for (int j = 0; j < abyte0.length; j++)
    
     			{
    
     				char c1 = Character.forDigit(abyte0[j] >> 4 & 0xf, 16);
    
     				if (Character.isLetter(c1))
    
     					c1 -= ' ';
    
     				stringbuffer.append(c1);
    
     				c1 = Character.forDigit(abyte0[j] & 0xf, 16);
    
     				if (Character.isLetter(c1))
    
     					c1 -= ' ';
    
     				stringbuffer.append(c1);
    
     			}
    
     			bytearrayoutputstream.reset();
    
     			// }
    
     		}
 		}catch (Exception e) {
 		    logger.error(e.toString());
 		}finally {
 		   outputstreamwriter.close();
 		   bytearrayoutputstream.close();
        }
 		return stringbuffer.toString();
 		
 	}

 	static BitSet dontNeedEncoding;

 	static final int caseDiff = 32;

 	static

 	{

 		dontNeedEncoding = new BitSet(256);

 		for (int i = 97; i <= 122; i++)

 			dontNeedEncoding.set(i);

 		for (int j = 65; j <= 90; j++)

 			dontNeedEncoding.set(j);

 		for (int k = 48; k <= 57; k++)

 			dontNeedEncoding.set(k);

 		dontNeedEncoding.set(32);

 		dontNeedEncoding.set(45);

 		dontNeedEncoding.set(95);

 		dontNeedEncoding.set(46);

 		dontNeedEncoding.set(42);

 	}

 	public static String decodeReturnUrl(String cEncodeString) {

 		StringBuffer stringbuffer = new StringBuffer(cEncodeString.length() / 2);

 		for (int i = 0; i < cEncodeString.length(); i = i + 2) {

 			stringbuffer.append((char) Integer.parseInt(cEncodeString
 					.substring(i, i + 2), 16));

 		}

 		return stringbuffer.toString();

 	}

 	
 	/**
 	 *
 	 * @Method Name : shNumLngth
 	 * @�옉�꽦�씪 : 2019. 1. 20.
 	 * @�옉�꽦�옄 : s1212921
 	 * @蹂�寃쎌씠�젰 : 
 	 * @Method �꽕紐� : 二쇰�쇰쾲�샇 �씠�쇅�쓽 �궗�뾽�옄踰덊샇�벑�씠 �븵�옄由ъ뿉 000 臾몄옄�뿴�쓣 遺숈씠�뒗 �삎�깭�뿬�꽌 �븘�슂 
 	 * @param shNum
 	 * @return
 	 *
 	 */
 	public static String shNumLngth(String shNum) {
 		if(CommonUtil.isEmpty(shNum)){
 			return nvl(shNum, "");
 		}else{
 			int bb = 13 - shNum.length();
 			if(shNum.length() < 13) {
 				for(int i=0 ; i < bb ; i++) {
 					shNum = "0" + shNum;
 				}
 			}
 		}
 		return shNum;
 	}
 	
 	
    /**
     *
     * @Method Name : cleanXSS
     * @�옉�꽦�씪 : 2019. 1. 29.
     * @�옉�꽦�옄 : s1212921
     * @蹂�寃쎌씠�젰 : 
     * @Method �꽕紐� : XSS 蹂댁븞愿��젴 肄붾뱶 �궘�젣  
     * @param value
     * @return
     *
     */
    public static String cleanXSS(String value) {
        //You'll need to remove the spaces from the html entities below
    	value = nvl(value);
		value = value.replace("<", "&lt;").replace(">", "&gt;");
		value = value.replace("\\(", "&#40;").replace("\\)", "&#41;");
		value = value.replace("'", "&#39;");
		value = value.replace("eval\\((.*)\\)", "");
		value = value.replace("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replace("script", "");
	return value;
    }

    public static boolean juminCheck(String value){
    	
    	String regExp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])[1-4][0-9]{6}$";
    	
    	if(CommonUtil.isEmpty(value)){
    		return false;
    	}else{
    		// 二쇰�쇰쾲�샇�삎�떇�씠硫� true 由ы꽩
    		return value.matches(regExp);
    	}
    }
    
    /**
     * �뜲�씠�� �뒪�듃留� �룷留룹쑝濡� 蹂�寃�
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Object date, String pattern){
    	
    	try {
    		if(CommonUtil.isEmpty(date)){
    			return "";
    		}else{
    			if(CommonUtil.isEmpty(pattern)) {
    				pattern = "yyyy-MM-dd";
    			}
    			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    			String result = "";
    			result = formatter.format(date);
    			return result;
    		}
    	}catch (Exception e) {
    	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), e);
			return "";
		}
    		
    }
    
    /**
     * �뙣�뒪�썙�뱶瑜� �젙洹쒖떇�쑝濡� 泥댄겕�븳�떎.
     * @param password
     * @return
     */
    public static Boolean passwordChk(String password) {
        String reg = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-\\`\\~=\\[\\]{};':\\\"\\\\|,.<>\\/?]).*$";
        
        if(Pattern.matches(reg, password)) {
            return true;
        }else{
            return false;
        }
    }
    
    public static String versionDate() {
        String versionDate = null;
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        String month = String.valueOf(date.get(Calendar.MONTH)+1);
        if(month.length() == 1) {
            month  = "0"+month;
        }
        
        String day = String.valueOf(date.get(Calendar.DATE));
        if(day.length() == 1) {
            day  = "0"+day;
        }
        String hour = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
        if(hour.length() == 1) {
            hour  = "0"+hour;
        }
        
        String minute = String.valueOf(date.get(Calendar.MINUTE));
        if(minute.length() == 1) {
            minute  = "0"+minute;
        }
        String second = String.valueOf(date.get(Calendar.SECOND));
        if(second.length() == 1) {
            second  = "0"+second;
        }
        versionDate = year+month+day+hour+minute+second;
        
        return versionDate;
    }
    
    public static boolean isEmpty( String value )
    {
        if( value == null || value.trim().length() <= 0 )
        {
            return true ;
        }

        return false ;
    }

    public static boolean in( String value, String... args )
    {
        for( int i = 0 ; i < args.length ; i ++ )
        {
            if( compareTo( value, args[i] ) == 0 )
            {
                return true ;
            }
        }

        return false ;
    }
    
    public static boolean notOIn( String value, String... args )
    {
        for( int i = 0 ; i < args.length ; i ++ )
        {
            if( compareTo( value, args[i] ) == 0 )
            {
                return false ;
            }
        }

        return true ;
    }

    public static int compareTo( String val1, String val2 )
    {
        if( val1 == null && val2 == null )
        {
            return 0 ;
        }
        
        if( val1 == null && val2 != null )
        {
            return 1 ;
        }
        
        if( val1 != null && val2 == null )
        {
            return -1 ;
        }

        return val1.compareTo( val2 ) ;

    }

    public static int compareToIgnoreCase( String val1, String val2 )
    {
        if( val1 == null && val2 == null )
        {
            return 0 ;
        }
        
        if( val1 == null && val2 != null )
        {
            return 1 ;
        }
        
        if( val1 != null && val2 == null )
        {
            return -1 ;
        }

        return val1.compareToIgnoreCase( val2 ) ;

    }

    
    public static String format( String format, Object... args )
    {
        String rtn = String.format( format, args ) ;
        return rtn ;
    }

    public static String toHexs( byte[] bytes )
    {
        if( bytes == null ) return "" ;
        return toHexs( bytes, 0, bytes.length ) ;
    }
    
    public static String toHexs( byte[] bytes, int offset, int len )
    {
        int onebyte;
        int i = offset ;

        if( bytes == null || bytes.length <= 0 || offset < 0 || offset + len > bytes.length  )
            return "" ;

        StringBuffer out = new StringBuffer( len * 2 );

        while( i < offset + len )
        {
            onebyte = ( ( 0x000000ff & bytes[i] ) | 0xffffff00 );
            out.append( Integer.toHexString( onebyte ).substring( 6 ) );
            i++;
        }
        
        return out.toString();
    }

    public static byte[] toBytes( String hex )
    {
        if( hex.startsWith( "0x" ))
        {
            hex = hex.substring( 2 ) ;
        }

        if( hex.length() <= 0 ) return new byte[0] ;

        int len = hex.length() / 2;

        byte[] ret = new byte[len];

        try
        {
            for( int i = 0; i < len; i++ )
            {
                ret[i] = (byte) Integer.valueOf( hex.substring( i * 2, ( i + 1 ) * 2 ), 16 ).intValue();
            }
        }

        catch ( Exception e )
        {
            return null;
        }

        return ret;
    }
    
    /**
     * �듅�닔湲고샇 �씪愿� �젣嫄�
     * @param paramValue
     * @param gubun
     * @return
     */
    public static String requestReplace (String paramValue, String gubun) {

        String result = "";
        
        if (paramValue != null) {
            
            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");
            
            if(gubun != "encodeData"){
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
            }
            
            result = paramValue;
            
        }
        return result;
    }
    
    /*
     * String 援щ텇�옄濡� 援щ텇�맂 臾몄옄�뿴�쓣 String [] �삎�깭�쓽 �닚�닔 臾몄옄�뿴濡� 蹂��솚 (DB ���옣�슜)
     * */
    public static String StringArrayTextChange(String text,String separator) {
        
        String returnText = "";
        
        if(text.indexOf(separator) > -1) {
            String array[] = text.split(separator);
            
            for(int i=0;i<array.length;i++) {
                if(i == 0) {                   //�떆�옉
                    returnText += "[\""+array[i]+"\"";
                }else if(i == array.length-1) {//醫낅즺
                    returnText += ",\""+array[i]+"\"]";
                }else {                        //以묎컙
                    returnText += ",\""+array[i]+"\"";
                }
            }
        }else {
            if(text == null || 
               "".equalsIgnoreCase(text)
            ) {
                returnText = "[]";//理쒖쥌 �뀓�뒪�듃
            }else {
                returnText = "[\""+text+"\"]";//理쒖쥌 �뀓�뒪�듃
            }
            
        }
        
        return returnText;
    }
    
    /**
     * db 肄붾뱶 諛곗뿴 臾몄옄�뿴�쓣 �븳湲� 諛곗뿴 �삎�깭�쓽  臾몄옄�뿴濡� 蹂��솚 (db 肄붾뱶 �궡�슜怨� , �빐�떦 db�뿉 �엯�젰�맂 而щ읆媛믪쓣 �씠�슜)
     * @param codeList
     * @param text
     * @return
     */
    public static String codeArrayTextChange(List <CamelMap> codeList,String text) {
        
        String preFix                    = "";  // , 援щ텇�옄 �궗�슜�뿬遺�
        String chgYn                     = "N"; // 蹂�寃쎌뿬遺� �떒嫄� 泥댄겕
        List<Object> array               = null;
        String returnText                = "";
        
        if(CommonUtil.isEmpty(text)) {
            //誘몄엯�젰 �떆
            return "";
        }
        
        if(text.indexOf(",") > -1) {
            array = new ArrayList<> (Arrays.asList(text.split(",")));// []�쓣 List濡� 蹂�寃�

            //肄붾뱶 �븳湲� 移섑솚
            for(int codeI=0;codeI<codeList.size();codeI++) {
                //肄붾뱶 roof
                for(int splitI=0;splitI<array.size();splitI++) {
                    //肄붾뱶 List �� text �궡�슜怨� �씪移섑븷 寃쎌슦 �븳湲� text add
                    if(codeList.get(codeI).get("value").toString().equalsIgnoreCase((String) array.get(splitI))) {
                        returnText += preFix+codeList.get(codeI).get("code");
                        preFix     = ",";//泥ル쾲吏몃쭔 , 誘명룷�븿 泥섎━
                    }
                }
            }
        }else {
            //�떒嫄�
            
            chgYn = "N";//珥덇린�솕
            
            for(int codeI=0;codeI<codeList.size();codeI++) {
                if(codeList.get(codeI).get("value").toString().equalsIgnoreCase(text)) {
                    returnText  = (String)codeList.get(codeI).get("code");
                    chgYn = "Y";
                    break;
                }
            }

            //�씪移섑븳 �뀓�뒪�듃媛� �븘�땶 �뿁�슧�븳 �궡�슜�씪 寃쎌슦 怨듬갚�쑝濡� 移섑솚
            if("N".equalsIgnoreCase(chgYn)) {
                returnText = "";
            }
        }
        return returnText;
    }
    
    /**
     * �븳湲� �엯�젰媛믪쓣 code Array �삎�깭�쓽 String�쑝濡� 諛섑솚 (db 肄붾뱶 �궡�슜怨� , �엯�젰媛믪쓣 �씠�슜)
     * @param codeList
     * @param value
     * @return
     */
    public static String textArrayCodeChange(List <CamelMap> codeList,String value) {
        
        String preFix    = "";  // , 援щ텇�옄 �궗�슜�뿬遺�
        String chgYn     = "N"; // 蹂�寃쎌뿬遺� �떒嫄� 泥댄겕
        String saveData  = "";
        
        if(CommonUtil.isEmpty(value)) {
            //誘몄엯�젰 �떆
            return "";
        }
        
        if(value.indexOf(",") > -1) {
            //援щ텇�옄 議댁옱�떆�뿉留�
            String chk[] = value.split(",");
            for(int i=0;i<codeList.size();i++) {
            //肄붾뱶 泥댄겕 roof
                for(int c=0;c<chk.length;c++) {
                //�엯�젰媛� 肄붾뱶 泥댄겕 roof
                    if(codeList.get(i).get("code").toString().equalsIgnoreCase(chk[c])) {
                    //肄붾뱶�뿉 �엳�뒗 �궡�슜�씠 �엯�젰媛믪뿉 議댁옱�떆
                        saveData += preFix+(String)codeList.get(i).get("value");
                        preFix    = ",";
                    }
                }
            }
        }else {
            if(value != null &&
               !"".equalsIgnoreCase(value.trim())
            ){
                //�븳嫄� �엯�젰 �떆
                for(int i=0;i<codeList.size();i++) {
                    //肄붾뱶 泥댄겕 roof
                    if(codeList.get(i).get("code").toString().equalsIgnoreCase(value)) {
                    //肄붾뱶�뿉 �엳�뒗 �궡�슜�씠 �엯�젰媛믪뿉 議댁옱�떆
                        saveData = (String)codeList.get(i).get("value");
                        chgYn    = "Y";
                    }
                }
                if("N".equals(chgYn)) {
                    //肄붾뱶 移섑솚 �떎�뙣 �떆 珥덇린�솕
                    saveData = "";
                }
            }else{
                //誘몄엯�젰 �떆
                saveData = "";
            }
        }
        return saveData;
    }
    
    
    
    
    public static void main(String[] args)
    {
        String test = StringArrayTextChange("DEW2,DE35,DEST6",",");
        //system.out.println("test="+test);
    }
}

