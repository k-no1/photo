
	
function ConvertFormToJSON(form)
{


	
    //var frmObj = jQuery(form) ;


	var jsonStr = JSON.stringify( form );

	/*
    var json = {};

    jQuery.each(array, function() 
    {
        json[this.name] = this.value || '';
    });
    */
    return jsonStr;
}



function apiGet( uri )
{
	try
	{
		var obj = null ;
		obj = _callApi( "GET", uri, null ) ;
		
		return obj ;
	}
	catch( excp )
	{
		throw excp
	}
}

function apiPost( uri, data )
{
	try
	{
		return _callApi( "POST", uri, data ) ;
	}
	catch( excp )
	{
		throw excp
	}
}

function apiPatch( uri, data )
{
	try
	{
		return _callApi( "PATCH", uri, data ) ;
	}
	catch( excp )
	{
		throw excp
	}
}

function apiDelete( uri, data )
{
	try
	{
		return _callApi( "DELETE", uri, data ) ;
	}
	catch( excp )
	{
		throw excp
	}
}


function _callApi( method, uri, formJsonData )
{
	try
	{
		var json = null ;
		var isErr = false ;
		var httpCd ;
		
	     $.ajax(
	    		 {
		             type: method,
		             url: uri, 
		             contentType: "application/json",
		             crossDomain: true,
		             data: formJsonData,
		             dataType: "json",
		             async: false,
		             success: function ( data ) 
		             {
		            	 json = data ;
		             },
		             error: function (jqXHR, status, errorThrown) 
		             {
		            	 httpCd = jqXHR.status ;
		            	 isErr = true ;

		             }
	    		 }) ;

	     if( isErr )
	     {
	    	 throw Error( httpCd ) ; 
	     }

	     return json ;
	}
	catch( excp )
	{
		throw excp
	}
	
}


