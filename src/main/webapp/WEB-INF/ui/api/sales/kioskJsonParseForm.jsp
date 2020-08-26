<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ko">
<head>
<title>TEST 매출API</title>
<jsp:include page="/WEB-INF/ui/include/commonMeta.jsp"></jsp:include>
<jsp:include page="/WEB-INF/ui/include/commonResource.jsp"></jsp:include>

</head>

<body>
    <div>
        <jsp:include page="/WEB-INF/ui/include/headSection.jsp"></jsp:include>
        <div class="contentsWrap">
            <%-- <jsp:include page="/WEB-INF/ui/include/lnbSection.jsp"></jsp:include> --%>
        </div>
        
        <div style="margin-left:300px;">
            <h3 class="cont_tit">JSP 에서 JSON 으로 던지고 JAVA에서 파싱함</h3>
            <!-- 게시판 영역 -->
            <form name="frm" method="post">
                <textarea rows="30" cols="200" name="jsonStr" id="jsonStr"></textarea>
            </form>
    
            <div>
                <button type="button" id="btnForm">전송</button>
            </div>
        </div>
        
    </div>
</body>

<script>

$(function(){
    
    $("#btnForm").on("click", function(){
        $.ajax({
            type:"post",
            url:"/api/channel/kiosk/sales/uploadDataV001",
            data:JSON.stringify($("#jsonStr").val()),
            contentType : "application/json",
            dataType:"json",
            success:function(responseData){
                if(responseData.rCode == "0000"){
                    alert(responseData.rMsg);
                }else{
                    alert("실ㄹㄹㄹㄹㄹ패");
                    alert(responseData.rMsg);
                }
            },
            error: function (jqXHR, exception) {
                alert("["+jqXHR.status+"]오류입니다.\n"+exception);
                return;
            }
        });
    }); 

});

</script>


</html>
