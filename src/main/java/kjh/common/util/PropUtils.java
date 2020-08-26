/***************************************************************
 * 1. �떆  �뒪 �뀥       	: 
 * 2. �꽌釉뚯떆�뒪�뀥 	: ���쇅梨꾨꼸怨� �떆�뒪�뀥
 * 3. 媛�     �슂       	: �봽濡쒗띁�떚 �쑀�떥由ы떚
 * 4. 理쒖큹�옉�꽦      	: 2014. 06. 20 
 * 5. �옉  �꽦  �옄		: 
 ***************************************************************/
package kjh.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * PropUtil �겢�옒�뒪�뒗 �봽濡쒗띁�떚瑜� 泥섎━�븯�뒗 �쑀�떥由ы떚�씠�떎.
 *
 * @author  
 */
public class PropUtils 
{
    private static final Logger logger = LoggerFactory.getLogger(PropUtils.class);
    
	private String propertiesFileName ;

	public static PropUtils getInstance( String propertiesFileName )
	{
		PropUtils propUtil = new PropUtils( propertiesFileName ) ;
		return propUtil ;
	}

	public static PropUtils getInstance()
	{
		PropUtils propUtil = new PropUtils( "application" ) ;
		return propUtil ;
	}

	private PropUtils( String propertiesFileName)
	{
		this.propertiesFileName = propertiesFileName ;
	}

	/**
	 * �봽濡쒗띁�떚�뿉�꽌 String 媛믪쓣 媛��졇�삩�떎.
	 * 
	 * @param propertiesFileName
	 * @param keyName
	 * @return
	 */
	private String getValue(String keyName ) 
	{
		return getValue( keyName, null ) ;
	}

	private String getValue(String keyName, String defaultValue ) 
	{
		ResourceBundle bundle = ResourceBundle.getBundle(propertiesFileName);

		if (! bundle.containsKey(keyName)) 
		{
			return defaultValue ;
		} 

		return bundle.getString(keyName);
	}

	public String getString( String keyName) 
	{
		return getValue( keyName, null ) ;
	}

	public String getString(String keyName, String defaultValue ) 
	{
		return getValue( keyName, defaultValue ) ;
	}

	/**
	 * �봽濡쒗띁�떚�뿉�꽌 Int 媛믪쓣 媛��졇�삩�떎.
	 * 
	 * @param propertiesFileName
	 * @param keyName
	 * @return
	 */
	public int getInt(String keyName) 
	{
		return getInt( keyName, 0 ) ;
	}

	public int getInt(String keyName, int defaultValue ) 
	{
		try
		{
			String value = getValue(keyName, String.valueOf( defaultValue )) ;
			return Integer.parseInt( value );
		}
		catch( Exception e )
		{
			return defaultValue ;
		}
	}
	
	/**
	 * �봽濡쒗띁�떚�뿉�꽌 Int 媛믪쓣 媛��졇�삩�떎.
	 * 
	 * @param propertiesFileName
	 * @param keyName
	 * @return
	 */
	public long getLong(String keyName) 
	{
		return getLong( keyName, 0 ) ;
	}

	public long getLong(String keyName, long defaultValue ) 
	{
		try
		{
			String value = getValue(keyName, String.valueOf( defaultValue )) ;
			return Long.parseLong( value );
		}
		catch( Exception e )
		{
			return defaultValue ;
		}
	}

	/**
	 * �봽濡쒗띁�떚�뿉�꽌 boolean 媛믪쓣 媛��졇�삩�떎.
	 * 
	 * @param propertiesFileName
	 * @param keyName
	 * @return
	 */
	public boolean getBoolean(String keyName)
	{
		return getBoolean( keyName, false ) ;
	}
	
	public boolean getBoolean(String keyName, boolean defaultValue ) 
	{
		try
		{
			String value = getValue(keyName, "false" ) ;
			return Boolean.parseBoolean( value );
		}
		catch( Exception e )
		{
			return defaultValue ;
		}
	}
	
	/**
	 * null 寃��궗瑜� �븳�떎.
	 * 
	 * @param in
	 * @return
	 */
	public static String nullCheck(String in) 
	{
		return (in == null ? "" : in);
	}
	

	/**
	 * �봽濡쒗띁�떚 �뙆�씪 �씠由꾩쑝濡� 李얠븘�꽌 �쟻�옱�븯怨� Properties 媛앹껜瑜� 諛섑솚�븳�떎.
	 * 
	 * @param propsName
	 * @return
	 */
	public static Properties load(String propsName) throws Exception
	{
		Properties props = new Properties();
		URL url = ClassLoader.getSystemResource(propsName);
		try 
		{
			props.load(url.openStream());
		} 
		catch (IOException e) 
		{
		    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), e) ;
		}
		return props;
	}
	
	/**
	 * �봽濡쒗띁�떚 �뙆�씪�쓣 �쟻�옱�븯怨� Properties 媛앹껜瑜� 諛섑솚�븳�떎.
	 * 
	 * @param propsFiles
	 * @return
	 * @throws IOException 
	 */
	public static Properties load(File propsFiles) throws Exception, IOException
	{
		Properties props = new Properties();
		FileInputStream fis = null;
		try 
		{
			fis = new FileInputStream(propsFiles);
			props.load(fis);
		} 
		catch (Exception e) 
		{
		    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.error( "{}", LogUtils.toHeader(request), e) ;
		} finally {
            fis.close();
        }
		return props;	
	}
	
}
