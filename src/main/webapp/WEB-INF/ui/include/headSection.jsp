<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="headSection">
	<div class="flaxArticle">
		<div class="logo" onclick="location.href='/asp/dash/main'">
			<h1><a href="/asp/dash/main"><span>한국전자금융</span><strong>키오스크 시스템</strong></a></h1>
			<p>test Total Order Platform Solution</p>
		</div>
	</div>
	<div class="branchArticle">
		<p class="affiliation">본사 또는 가맹점을 선택해주세요.</p>
		<div class="branchSearch">
			<div class="office">
				<c:if test="${auth eq 'ADM' || auth eq 'DEV' || auth eq 'OPR' }">
				<input type="text" name="headerKeyword" id="" placeholder="검색어를 입력하세요" autocomplete="off">
				<button type="button" name="" id="headerSearchBtn" class="searchBtn" >검색</button>
				<div class="selectWrap">
					<ul class="ulSelect searchSelect">
						<li><a>선택</a></li>
					</ul>
				</div>
				</c:if>
			</div>
			
			<div class="office">
				<input type="text" name="" id="headOfficeInput" placeholder="선택" class="selectable" autocomplete="off" >
				<div class="selectWrap scrollControl">
					<ul class="ulSelect headOfficeSelect">
						<li><a>선택</a></li>
					</ul>
				</div>
			</div>
			
			<div class="office">
				<input type="text" name="" id="franchiseInput" placeholder="본사" class="selectable" autocomplete="off" > 
				<div class="selectWrap scrollControl2">
					<ul class="ulSelect franchiseSelect">
						<li><a>본사를 선택해주세요.</a></li>
					</ul>
				</div>
			</div>
			
			<button type="button" name="" id="" class="myReport"><span>0</span></button>
			<button type="button" name="" id="" class="logout" onclick="javascript:f_logout()">로그아웃</button>
		</div>
	</div>
	<div class="headline"><span></span></div>
</div><!-- //headSection  -->
<div id="logoutOK" class="lpPopup lpModalWrap2 logoutOK">
	<!-- lpWrap -->
	<div class="lpWrap">
		<div class="lpCon">
			<!-- lpHeader -->
			<div class="lpHeader">
				<h2>로그아웃</h2>
			</div>
			<!--// lpHeader -->
			<!-- lpContainer -->
			<div class="lpContainer">
				<!-- lpContent -->
				<div class="lpContent">
					<p class="lpTxt">로그아웃 되었습니다.</p>
				</div>
				<!--// lpContent -->
			</div>
			<!--// lpContainer -->
			<div class="lpFooter">
				<div class="btnSet align-c">
					<a href="/"><button type="button" class="btn big btnOrange"><span>확인</span></button></a>
				</div>
			</div>
		</div>
	</div>
	<!--// lpWrap -->
</div>
<script>
	$(function(){
		myReportCnt();
		headerHeadOfficeList();
		
		var readyAffiTxt = '본사 또는 가맹점을 선택해주세요.';	
		
		//세션 스토리지에 본사 정보가 있을경우 셋팅해주기
		if(sessionStorage.headerHeadOfficeName != null && sessionStorage.headerHeadOfficeName != '') { 	
			
			$(document).find('#headOfficeInput').attr("placeholder", sessionStorage.headerHeadOfficeName);
			headerFranchiseList(sessionStorage.headerHeadOfficeId);
			
			//세션 스토리지에 가맹점 정보가 있을경우 셋팅해주기 
			if(sessionStorage.headerFranchiseName != "undefined"){
				
				if(sessionStorage.headerFranchiseName != null && sessionStorage.headerFranchiseName != ''
						&& sessionStorage.headerFranchiseName != '등록된 가맹점이 없습니다.' && sessionStorage.headerFranchiseName != '본사를 선택해주세요.') {
					$(document).find('#franchiseInput').attr("placeholder", sessionStorage.headerFranchiseName);
					
					readyAffiTxt = sessionStorage.headerHeadOfficeName+' &gt; <span class="current">'+sessionStorage.headerFranchiseName+'</span>';
				}else {
					readyAffiTxt = '<span class="current">'+sessionStorage.headerHeadOfficeName+'</span>'
				}
			
			}
			
			$(document).find('.headSection .branchArticle .affiliation').html(readyAffiTxt);
		}
		
	});
	
	$(document).on('keyup', '#headOfficeInput', function(){
		headerHeadOfficeList();
	});
	
	$(document).on('keyup', '#franchiseInput', function(){
		headerFranchiseList(sessionStorage.headerHeadOfficeId);
	});
	
	$(document).on('keyup', "input[name='headerKeyword']", function(){
		$('#headerSearchBtn').click();
	});
	
	//본사를 선택했을 경우
	$(document).find('.headOfficeSelect').on('click', 'li', function(){
		
		var headerHeadOfficeName = $(this).children('a').text();
		var headerHeadOfficeId = $(this).find('.headerHeadOfficeId').val();
		
		sessionStorage.removeItem("headerHeadOfficeName");
		sessionStorage.removeItem("headerHeadOfficeId");
		sessionStorage.removeItem("headerFranchiseName");
		sessionStorage.removeItem("headerFranchiseId");
		
		$(document).find('#headOfficeInput').attr("placeholder", "선택");
		$(document).find('#headOfficeInput').val('');
		
		if(headerHeadOfficeName != '선택') {
			headerHeadOfficeName = headerHeadOfficeName.split('] ')[1]		
			
			$(document).find('#headOfficeInput').attr("placeholder", headerHeadOfficeName);
			$(document).find('#headOfficeInput').val('');
			
			sessionStorage.headerHeadOfficeName = headerHeadOfficeName;
			sessionStorage.headerHeadOfficeId   = headerHeadOfficeId;
			
			//2019.12.11 상위 권한 로그인,본사 선택으로 화면 전환시 가맹점 값 오류 발생 보완
	        sessionStorage.headerFranchiseName  = null;
	        sessionStorage.headerFranchiseId    = null;
			
//			sessionStorage.removeItem("headerFranchiseName");
//			sessionStorage.removeItem("headerFranchiseId");
			
			var affiTxt = '<span class="current">'+headerHeadOfficeName+'</span>';
			
			$(document).find('.headSection .branchArticle .affiliation').html(affiTxt);
			
			headerFranchiseList(headerHeadOfficeId);
			
			$(document).find('#franchiseInput').attr("placeholder", "본사");
			$(document).find('.franchiseSelect li:first').click();
			$(document).find('.franchiseSelect li:first a').addClass("on");
			
			headerHeadOfficeList();
			if($('#lpChoice').hasClass('on')) {
				var id = $('#lnbFrm').attr("action");
				
				$(document).find('.depth02 li').each(function(){
					if($(this).find('a').attr("id") == id) {
						$(this).click();
					}
				});
			}
		}else {
			$(document).find('.headOfficeSelect li').css("background-color","").css("color", "").removeClass('selectyongyong');
			$(document).find('#franchiseInput').attr("placeholder", "본사");
			$(document).find('.franchiseSelect li:first').click();
			$(document).find('.franchiseSelect li:first a').addClass("on");
			$(document).find('.headSection .branchArticle .affiliation').html('본사 또는 가맹점을 선택해주세요.');
			$(document).find('.headSection').find('.franchiseSelect').html('<li><a>본사를 선택해주세요.</a></li>');
			
			var id = window.location.pathname;
			
			$(document).find('.depth02 li').each(function(){
				if($(this).find('a').attr("id") == id) {
					if($(this).hasClass("chkHof")) {
						$('#lnbFrm').attr("action", "/asp/dash/main");
						lpClose('.lpChoice');
						$('#lnbFrm').submit();
					}
				}
			});
		}
		
		myReportCnt();
	});
	
	
	
	//가맹점을 선택했을 경우
	$(document).find('.franchiseSelect').on('click', 'li', function(){
		var headerFranchiseName = $(this).children('a').text();
		var headerFranchiseId = $(this).find('.headerFranchiseId').val();	
		
		sessionStorage.removeItem("headerFranchiseName");
		sessionStorage.removeItem("headerFranchiseId");
		
		sessionStorage.headerFranchiseName = headerFranchiseName;
		if(headerFranchiseName != '등록된 가맹점이 없습니다.' && headerFranchiseName != '본사를 선택해주세요.') {
			if(headerFranchiseName.indexOf(' ') != -1) {
				headerFranchiseName = headerFranchiseName.split('] ')[1]
			}
			
			$(document).find('#franchiseInput').attr("placeholder", headerFranchiseName);
			$(document).find('#franchiseInput').val('');
			
			sessionStorage.headerFranchiseName = headerFranchiseName;
			sessionStorage.headerFranchiseId = headerFranchiseId;
			
			if(headerFranchiseName == '본사') {
				sessionStorage.removeItem("headerFranchiseName");
				sessionStorage.removeItem("headerFranchiseId");
			}
			
			var affiTxt = '';
			if(headerFranchiseName != '본사') {
				affiTxt = sessionStorage.headerHeadOfficeName+' &gt; <span class="current">'+headerFranchiseName+'</span>';
			}else {
				affiTxt = '<span class="current">'+sessionStorage.headerHeadOfficeName+'</span>';
			}
			$(document).find('.headSection .branchArticle .affiliation').html(affiTxt);
			
		}
		
		if(sessionStorage.headerHeadOfficeId) {
			headerFranchiseList(sessionStorage.headerHeadOfficeId);
			myReportCnt();
		}
		if($('#lpChoice').hasClass('on')) {
			var id = $('#lnbFrm').attr("action");
			
			$(document).find('.depth02 li').each(function(){
				if($(this).find('a').attr("id") == id) {
					$(this).click();
				}
			});
		}		
		
	});
	
	//본사 정보 가져오기
	function headerHeadOfficeList(){
		var obj = new Object();
		
		if(sessionStorage.headerHeadOfficeId != null) {
			obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;	
		}
		
		if($('#headOfficeInput').val() != '') {
			obj["headOfficeName"] = $('#headOfficeInput').val();
		}
		
		obj = JSON.stringify(obj);
		
		$.ajax({
			url : "/common/hdhois010",
			type : "POST",
			contentType : "application/json",
			async:true,
			dataType: "json",
			data : obj,
			success : function(data) {
				var txt = '';
				
				
				if(data.data.dataList === undefined){
                    txt += ' <li>';
                    txt += '        <a>등록된 본사가 없습니다.</a>';
                    txt += ' </li>';
				}else{
					if(data.data.dataList.length > 0) {  
						if("${auth}" != 'FRN' && "${auth}" != 'HDO') txt += '<li><a style="cursor:pointer;">선택</a></li>';
						$.each(data.data.dataList, function(index, items){
							if(items.userId == null ) items.userId = '';
							txt += ' <li title="['+items.headOfficeId+'] '+items.headOfficeName+'" style="cursor:pointer;">';
							txt += '		<a>['+items.headOfficeId+'] '+items.headOfficeName+'</a>';
							txt += '		<input type="hidden" class="headerHeadOfficeId" value="'+items.headOfficeId+'">';
							txt += ' </li>';
						});
					}else {
						txt += ' <li>';
						txt += '		<a>등록된 본사가 없습니다.</a>';
						txt += ' </li>';
					}
				}
				
				$(document).find('.headSection').find('.headOfficeSelect').html(txt);
				
				if(sessionStorage.headerHeadOfficeId != null) {
					$(document).find('.headSection').find('.headOfficeSelect li').each(function(){
						if($(this).find('.headerHeadOfficeId').val() == sessionStorage.headerHeadOfficeId) {
							$(this).css("background-color","#ffd6b2").css("color", "#333").addClass('selectyongyong');
						}
					});
					
					$(document).on('click', 'input#headOfficeInput', function() {
						$('.scrollControl').mCustomScrollbar('scrollTo',$(".selectyongyong").position().top);
					});
				}
				myReportCnt();
			},
			error : function(e) {
				console.log(e);
			}
			
		});
	}
	
	//가맹점 정보 가져오기
	function headerFranchiseList(headOfficeId) {
		var obj = new Object();
		obj["headOfficeId"] = headOfficeId;

		if(sessionStorage.headerFranchiseId != null) {
			obj["franchiseId"] = sessionStorage.headerFranchiseId;	
		}
		
		if($('#franchiseInput').val() != '') {
			obj["franchiseName"] = $('#franchiseInput').val(); 
		}
		
		obj = JSON.stringify(obj);
		
		$.ajax({
			url : "/common/hdfhis020",
			type : "POST",
			contentType : "application/json",
			async:true,
			dataType: "json",
			data : obj,
			success : function(data) {
				var txt = '';

                if(data.data.dataList === undefined){
                    txt += ' <li>';
                    txt += '        <a>등록된 가맹점이 없습니다.</a>';
                    txt += ' </li>';
                }else{     
				
					if(data.data.dataList.length > 0) {
						if("${auth}" != 'FRN') txt += '<li><a style="cursor:pointer;">본사</a></li>';
						
						$.each(data.data.dataList, function(index, items){
							if(items.userId == null ) items.userId = '';
							txt += ' <li title="['+items.franchiseId+'] '+items.franchiseName+'" style="cursor:pointer;">';
							txt += '		<a>['+items.franchiseId+'] '+items.franchiseName+'</a>';
							txt += '		<input type="hidden" class="headerFranchiseId" value="'+items.franchiseId+'">';
							txt += ' </li>';
						});
					}else {
						txt += ' <li>';
						txt += '		<a>등록된 가맹점이 없습니다.</a>';
						txt += ' </li>';
					}
                }
				
				$(document).find('.headSection').find('.franchiseSelect').html(txt);
				if(sessionStorage.headerFranchiseId != null) {
					$(document).find('.headSection').find('.franchiseSelect li').each(function(){
						if($(this).find('.headerFranchiseId').val() == sessionStorage.headerFranchiseId) {
							$(this).css("background-color","#ffd6b2").css("color", "#333").addClass('selectyongyong2');
						}
					});
					
					$(document).on('click', 'input#franchiseInput', function() {
						$('.scrollControl2').mCustomScrollbar('scrollTo',$(".selectyongyong2").position().top);
					});
				}
				myReportCnt();
			},
			error : function(e) {
				console.log(e);
			}
			
		});
	}
	
	$(document).on('click', '#headerSearchBtn', function(){
		var obj = new Object();
		
		if(sessionStorage.headerHeadOfficeId != null) {
			obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;
		}
		if(sessionStorage.headerFranchiseId != null) {
			obj["franchiseId"] = sessionStorage.headerFranchiseId;
		}
		var keyword = $("input[name='headerKeyword']").val();
		var keywordList = new Array();
		if(keyword.indexOf(' ') != -1) {
			keywordList = keyword.split(' ');
		}else {			
			keywordList.push(keyword);
		}
		obj["keyword"] = keywordList;
		
		obj = JSON.stringify(obj);
		
		$.ajax({
			url : "/common/hdseis020",
			type : "POST",
			contentType : "application/json",
			async:true,
			dataType: "json",
			data : obj,
			success : function(data) {
				
				var txt = '<li><a>선택</a></li>';
				
				if(data.data.dataList === undefined){
                    txt += ' <li>';
                    txt += '        <a>등록된 가맹점이 없습니다.</a>';
                    txt += ' </li>';
				}else{
					if(data.data.dataList != null && data.data.dataList.length > 0) {
						$.each(data.data.dataList, function(index, items){
							if(items.huId == null ) items.huId = '';
							if(items.fuId == null ) items.fuId = '';
							txt += ' <li title="'+ items.headOfficeName + ' | ' + items.headOfficeId + ' | ' + items.franchiseName + ' | ' + items.franchiseId +'" style="cursor:pointer;">';
							txt += '		<a>'+ items.headOfficeName + ' | ' + items.headOfficeId + ' | ' + items.franchiseName + ' | ' + items.franchiseId + '</a>';
							txt += '		<input type="hidden" class="headerHeadOfficeId" value="'+items.headOfficeId+'">';
							txt += '		<input type="hidden" class="headerFranchiseId" value="'+items.franchiseId+'">';
							txt += ' </li>';
						});
					}else {
						txt += ' <li>';
						txt += '		<a>등록된 가맹점이 없습니다.</a>';
						txt += ' </li>';
					}
				}
				
				$(document).find('.headSection').find('.searchSelect').html(txt);
				
			},
			error : function(e) {
				console.log(e);
			}
			
		});
	});
	
	$(document).on('keyup', 'input[name="headerKeyword"]', function(e){
		if(e.keyCode == 13) {
			$(document).find('#headerSearchBtn').click();
		}
	});
	
	$(document).on('click', '.searchSelect li', function(){
		var clickTxt = $(this).children('a').text();
		clickTxt = clickTxt.split(' | ');
		
		sessionStorage.headerHeadOfficeName = clickTxt[0];
		sessionStorage.headerHeadOfficeId = $(this).children('.headerHeadOfficeId').val();
		sessionStorage.headerFranchiseName = clickTxt[2];
		sessionStorage.headerFranchiseId = $(this).children('.headerFranchiseId').val();
		
		$(document).find('input[name="headerKeyword"]').val('');
		
		$(document).find('#headOfficeInput').attr("placeholder",clickTxt[0]);
		$(document).find('#franchiseInput').attr("placeholder",clickTxt[2]);
		
		var affiTxt = clickTxt[0]+' &gt; <span class="current">'+clickTxt[2]+'</span>';
		$(document).find('.headSection .branchArticle .affiliation').html(affiTxt);
		
		
		myReportCnt();
		
		var id = window.location.pathname;

		if(id == '/dash/main') {
			w_goPage(1);
			fn_orderTopList("");
		}else {
			$(document).find('.depth02 li').each(function(){
				if($(this).find('a').attr("id") == id) {
					$(this).click();
				}
			});
		}
				
	});
	
	function myReportCnt() {
		var obj = new Object();
		
		if(sessionStorage.headerHeadOfficeId != null) {
			obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;
		}
		if(sessionStorage.headerFranchiseId != null) {
			obj["franchiseId"] = sessionStorage.headerFranchiseId;
		}
		
		obj = JSON.stringify(obj);
		$.ajax({
			url : "/common/hdwois010",
			type : "POST",
			contentType : "application/json",
			async:true,
			dataType: "json",
			data : obj,
			success : function(data) {
				var headerCnt = data.data.cnt;
				if(parseInt(data.data.cnt) > 9999) {
					headerCnt = "+9999";
				}
				$(document).find('.headSection .branchSearch .myReport span').text(headerCnt);
			},
			error : function(e) {
				console.log(e);
			}
			
		});
	}
	
	$(document).on('click', '.myReport', function(){
		var pageMoveTxt = '<form id="workfrm" method="post" action="/asp/of/wo/ofwoms010">';
		if(sessionStorage.headerFranchiseId != null) {
		pageMoveTxt += '        <input type="hidden" name="franchiseId" value="'+sessionStorage.headerFranchiseId+'" />';
		}
		if(sessionStorage.headerHeadOfficeId != null) {
		pageMoveTxt += '        <input type="hidden" name="headOfficeId" value="'+sessionStorage.headerHeadOfficeId+'" />';
		}
		pageMoveTxt += '</form>';
		
		$('body').append(pageMoveTxt);
		$(document).find('#workfrm').submit();
	});
	
	function f_logout(){

		$.ajax({	
	        method: "post",
	        type: "json",    
	        contentType: 'application/json; charset=utf-8',
		    url		:"/common/logout",
		    data    : JSON.stringify({loginId:$("input[name=loginId]").val(),loginPw:$("input[name=loginPw]").val()}),
	        success : function(msg) {
	        	sessionStorage.clear();
	        	
	        	//alert("로그아웃 되었습니다.");
	        	lpOpen('.logoutOK');
	        	//location.href="/"; 
	        },	              
	        error 	: function(xhr, status, error,e) {lpOpen('.logoutOK');}
	    });
	}
</script>