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
				<button type="button" id="btnForm" style="border:solid 2px; padding:5px;">매출전송</button>
				<button type="button" id="btnEnd" style="border:solid 2px; padding:5px;">마감전송</button>
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
			url:"/api/sales/uploadDataV001",
	        data		:  JSON.stringify($("#jsonStr").val()), 
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
	
	// 마감전송
	$("#btnEnd").on("click", function(){
		$.ajax({
			type:"post",
			url:"/api/sales/uploadEndData",
	        data:  JSON.stringify($("#jsonStr").val()), 
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
