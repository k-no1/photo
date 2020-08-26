package kjh.common.dto;

import kjh.common.util.PropUtils;

import org.springframework.util.StringUtils;

public class UniqId 
{
	private static final String WAS_APP_NAME ;

	static
	{
		String wasNo = System.getProperty( "was.instance.no", "11" ) ;

		String appNm = PropUtils.getInstance().getString( "app.name", "TP" ) ;

		WAS_APP_NAME = appNm.trim() + wasNo.trim() ;
	}

	private static long _SEQ = 0;

	private static Object _SEQ_LOCK = new Object() ;

	private static long _getSeq()
	{
		synchronized( _SEQ_LOCK )
		{
			if ( _SEQ >= 60466175 )
			{
				_SEQ = 0;
			}

			_SEQ++;

			return _SEQ ;
		}
	}

	public static String getAppName()
	{
	    return WAS_APP_NAME ;
	}
	
	public static String getID()
	{
		long seq = _getSeq() ;

		long timestamp = System.currentTimeMillis() ;

		String uid = String.format( "%8s%5s%s", Long.toString( timestamp, 36 ).toUpperCase(), Long.toString( seq, 36 ).toUpperCase(), WAS_APP_NAME ).replaceAll( " ", "0" ) ;	

		return uid ;
	}

	public static String getID( String surffix )
	{
		String uid = getID() ;

		if( ! StringUtils.isEmpty( surffix ))
		{
			uid = String.format( "%s%s", uid, surffix ) ;
		}

		return uid ;
	}

	public static void main( String[] args )
	{
		
		
		long st = System.currentTimeMillis() ;
		
		for( int i = 0 ; i < 10000 ; i ++ )
		{
			String uid = UniqId.getID() ;
			System.out.println( uid ) ;
		}

		long ed = System.currentTimeMillis() ;

		System.out.println( "elapsed=" + ( ed - st ) + " ms" ) ;
	}

}
