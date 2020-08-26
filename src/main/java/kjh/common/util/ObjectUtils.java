package kjh.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kjh.common.dto.CamelMap;
import kjh.common.dto.ObjectMap;

public class ObjectUtils 
{
	public static void main( String[] args )
	{
/*	    
		try
		{
			ObjectMap map = new ObjectMap() ;
			
			map.put( "ISSUE_ID", "11111" ) ;
			
			//system.out.println( map ) ;
			
			
			//IssueDto dto = ObjectUtils.from( map, IssueDto.class, ObjectUtils.UPPER_UNDERLINE_TO_CAMEL ) ;
			IssueDto dto = ObjectUtils.from( map, IssueDto.class ) ;

			//system.out.println( dto.getIssueId() ) ;
			
			
			String str = JsonUtils.toJsonString( dto, true ) ;
			//system.out.println( str ) ;
			
			ObjectMap m = ObjectUtils.to( dto ) ;
			
			
			//system.out.println( m ) ;
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
*/
	    
	}

	public static <T> CamelMap to(T object ) throws Exception 
	{
		try 
		{
		    CamelMap map = new CamelMap();
			for (Field field : object.getClass().getDeclaredFields()) 
			{
				field.setAccessible(true);

				String key = field.getName() ;
				map.put( key, field.get(object));
			}

			return map;
		}
		catch (Exception e) 
		{
			throw e ;
		}
	}

	public static <T> List<CamelMap> to(List<T> objects) throws Exception
	{
		try 
		{
			List<CamelMap> list = new ArrayList<CamelMap>();
			
			for (T object : objects) 
			{
			    CamelMap map = to( object ) ;
				
				list.add( map ) ;
			}

			return list;
		} 
		catch (Exception e) 
		{
			throw e ;
		}
	}
	
	public static <T> List<T> from(List<CamelMap> maps, Class<T> clazz ) throws Exception 
	{
		try 
		{
			List<T> ret = new ArrayList<T>();
		
			for (CamelMap map : maps) 
			{
				T object = from( map, clazz ) ;
				
				ret.add( object ) ;
			}
			
			return ret;
		} 
		catch (Exception e) 
		{
			throw e ;
		}
	}

	public static <T> T from( CamelMap map, Class<T> clazz ) throws Exception
	{
		/*
		if (model == null || model.size() == 0)
			return clazz.newInstance();
		*/
		try 
		{
			T ret = clazz.newInstance();
			
			if (map == null || map.size() == 0)
			{
				return ret;
			}
			
			for (Field field : clazz.getDeclaredFields()) 
			{
				if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL)
				{
					continue;
				}
				
				field.setAccessible(true);

				String key = field.getName() ;

				Object value = map.get(key);

				if (value instanceof Timestamp)
				{
					value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
				}
				else if (value instanceof Boolean && "String".equals(field.getType().getSimpleName()))
				{
					value = (Boolean)value ? "Y" : "N";
				}
				else if (value instanceof Long )
				{
					if( field.getType() == Integer.class )
					{
						int val = ((Long) value ).intValue() ;
						value = new Integer( val ) ;
					}
				}

				field.set(ret, value);
			}

			return ret;
		} 
		catch (Exception e) 
		{
			throw e ;
		}
	}
}