package kjh.common.dto;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.CaseFormat;

public class CamelMap extends ObjectMap
{
    private static final long serialVersionUID = 1L;
    
    public static String convert2CamelCase(String value) 
    {
        String conv ;
        
        if( value == null || value.length() <= 0 )
        {
            return value ;
        }

        if (value.indexOf('_') < 0 )  
        {
            if( Character.isLowerCase(value.charAt(0)))
            {
                conv = value ;
            }
            else
            {
                conv = value.toLowerCase() ;
            }
        }
        else
        {
            conv = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value) ;
        }

        return conv ;
    }

    @Override
    public void putAll(Map map) 
    {
        Iterator itr = map.keySet().iterator() ;
        for( ; itr.hasNext() ; )
        {
            Object key = itr.next() ;
            if( key == null )
            {
                continue ;
            }

            Object val = map.get( key ) ;

            this.put( key.toString(), val ) ;
        }
    }

    @Override
    public Object put(String key, Object value) 
    {
        if( key == null )
        {
            return null ;
        }

        String camelKey = convert2CamelCase( key ) ;

        return super.put(camelKey, value );
    }

    @Override
    public Object get(Object key ) 
    {
        if( key == null )
        {
            return null ;
        }

        String camelKey = convert2CamelCase( key.toString() ) ;

        return super.get(camelKey );
    }    
}