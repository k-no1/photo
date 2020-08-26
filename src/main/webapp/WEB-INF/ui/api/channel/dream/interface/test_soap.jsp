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
		
		<div style="margin:50px 300px;">
			<h3 class="cont_tit">from JSON to JSON REST API 전송</h3>
			<!-- 게시판 영역 -->
			<form name="frm" method="post">
				<textarea rows="30" cols="150" name="jsonStr" id="jsonStr"></textarea>
			</form>
	
			<div style="width:100%; margin-top:10px;">
				<button type="button" id="btnForm" style="border:solid 2px; padding:5px;">Soap TEST</button>
			</div>
		</div>
		
	</div>
</body>

<script>

$(function(){
	
	// 매출전송
	$("#btnForm").on("click", function(){
		$.ajax({
			type:"post",
			url:"/api/channel/interface/PlayerUpdaterService.asmx",
	        data		:  $("#jsonStr").val(), 
	        contentType : "text/xml",
			dataType:"xml",
			success:function(xml){
	            alert(xml.documentElement.innerHTML);
			},
			error: function (jqXHR, exception) {
				alert("["+jqXHR.status+"]오류입니다.\n"+exception);
				return;
			},
			beforeSend:function(x){
				if(x && x.overrideMimeType){
					x.overrideMimeType("application/xml;charset=UTF-8");
				}
			}	
		});
	});	

});

</script>
<script>
$(document).ready(function(){
	setTimeout(function(){
		   // 1초 후 작동해야할 코드
				$("li.current").eq(0).removeClass("current");
		   }, 8000);
});
	
</script>

</html>
