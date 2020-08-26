 
var pagingfn = {
    type : "default", //  fix or default. 
    offset : "5",
    totalCnt : "0", 
    currPage : "1",
    splitCnt : "10",   
/*    
    class_btnfirst : "btnpage-first",   
    class_btnprev : "btnpage-prev",     
    class_btnnext : "btnpage-next",     
    class_btnlast : "btnpage-last",     
    class_btncurrent : "btnpage-list current",   
    class_btnlist : "btnpage-list",   
*/
    
    class_btnfirst : "pagination",   
    class_btnprev : "pagination",     
    class_btnnext : "pagination",     
    class_btnlast : "pagination",     
    class_btncurrent : "current",   
    class_btnlist : "pagination",   

    
    initPage : function(){   
    pagingfn.currPage = 1;
        if (pagingfn.offset%2 == "0")
            pagingfn.offset = parseInt(pagingfn.offset)+1;
        pagingfn.makePageNav(1);  
    } ,
    appendListStr : function(){},
    makePageNav : function(i) { 
        var str = "";
        var totalPage = Math.ceil((pagingfn.totalCnt / pagingfn.splitCnt)); 
        str = pagingfn.makePageNavStr(totalPage, i);
        $("#pageNav").empty();
        $("#pageNav").append(str);
        pagingfn.appendListStr(); 
    } ,
      makePageNavStr : function(totalPage, curr) 
      {
        pagingfn.currPage = curr;
        var i = 1; 
        var str = "";

        /*
        if (pagingfn.offset < totalPage && pagingfn.currPage > 2) 
        {   
            str += " <li><a href=\"javascript:pagingfn.makePageNav('1');\" class=\""+pagingfn.class_btnfirst+"\"><span>First</span></a></li>";
        }
        
        if (pagingfn.currPage > 1) 
        {  
            str += " <li><a href=\"javascript:pagingfn.makePageNav('" + parseInt(parseInt(pagingfn.currPage) - 1) + "');\" class=\""+pagingfn.class_btnfirst+"\"><span>Previous</span></a></li>";
        }
        */
        
        str += " <li><a href=\"javascript:goPage('1');\" > <span>First</span></a></li>";
        
        var prevPageNum = parseInt(pagingfn.currPage) - 1 ;
        if( prevPageNum <=  0 ) 
        {
        	prevPageNum = 1 ;
        }
        str += " <li><a href=\"javascript:goPage('" + prevPageNum + "');\" > <span>Previous</span></a></li>";

        
        var multiplyCnt = Math.ceil((pagingfn.currPage / pagingfn.offset)); 
        var temp=1;
        
        var endOffset = pagingfn.offset;
        endOffset = endOffset * multiplyCnt;  
        if (endOffset > totalPage)
            endOffset = totalPage; 
        if (pagingfn.type === "default")
        { 
            while (temp < multiplyCnt) 
            {  
                i = i + parseInt(pagingfn.offset);
                temp += 1;
            }
            
            while (i <= endOffset) 
            { 
                if (i == pagingfn.currPage) 
                {
                    str += "<li class=\"current\" ><a href=\"#\" >" + i + "</a></li>";
                }
                else 
                {
                    str += "<li><a href=\"javascript:goPage('"+i+"');\" > " + i + " </a></li>";
                }
                
                i += 1;
            }
        }
        else
        { 
            while (temp < multiplyCnt) 
            {  
                i = i + parseInt(pagingfn.offset);
                temp += 1;
            }   
            
            while (i <= endOffset) 
            { 
                if (i == pagingfn.currPage) 
                {
                    str += "<li><a href=\"#\" class=\"current\">" + i + "</a></li>";
                }
                else 
                {
                    str += "<li><a href=\"javascript:goPage('"+i+"');\" > " + i + " </a></li>";
                }
                
                i += 1;
            } 
        }
        
        /*
        if (pagingfn.currPage < totalPage) 
        {
            str += " <li><a href=\"javascript:pagingfn.makePageNav('" + parseInt(parseInt(pagingfn.currPage) + 1) + "');\" class=\""+pagingfn.class_btnnext+"\"><span>Next</span></a></li>";
        }
        
        if (pagingfn.offset < totalPage && pagingfn.currPage < totalPage) 
        {
            str += " <li><a href=\"javascript:pagingfn.makePageNav('" + totalPage + "');\" class=\""+pagingfn.class_btnlast+"\"><span>List</span></a></li>";
        }
        */
        
        var nextPageNum = parseInt(pagingfn.currPage) + 1 ;
        if( nextPageNum > totalPage )
        {
        	nextPageNum = totalPage
        }
        
        str += " <li><a href=\"javascript:goPage('" + nextPageNum + "');\" > <span>Next</span></a></li>";
        str += " <li><a href=\"javascript:goPage('" + totalPage + "');\" ><span>List</span></a></li>";
        
        return str;
    } 
}