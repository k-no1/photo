<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
<title>test Total Order Platform Solution</title>
<%@ include file="/WEB-INF/ui/include/taglibs.jsp" %>
<jsp:include page="../include/commonMeta.jsp"></jsp:include>
<jsp:include page="../include/commonResource.jsp"></jsp:include>
<link rel="stylesheet" href="/static/css/login.css" type="text/css">
</head>
<body>
<div class="loginWrap">
    <!-- loginLeft -->
    <div class="loginLeft">
        <div class="loginLogo"><img src="/static/img/login/logo.png" alt="logo"></div>
        <div class="loginBox">
            <p class="loginTit1">한국전자금융</p>
            <p class="loginTit2">키오스크 시스템</p>
            <p class="loginTit3">test Total Order Platform Solution</p>
            <div class="loginForm">
            <form id="loginForm2" name="loginForm2" onsubmit="return false;">
                <input type="text" placeholder="아이디" class="loginID" name="loginId" id="loginId" autocomplete="off" style="ime-mode:disable;-webkit-ime-mode:disable;-moz-ime-mode:disable;-ms-ime-mode:disable">
                <input type="text" placeholder="비밀번호" class="loginPW" name="fakeloginPw" autocomplete="off" style="display:none">
                <input type="text" placeholder="비밀번호" class="loginPW" name="loginPw" autocomplete="off" style="-webkit-text-security:disc;text-security:disc;-moz-text-security:disc;ime-mode:disable;-webkit-ime-mode:disable;-moz-ime-mode:disable;-ms-ime-mode:disable">
                <input type="hidden" name="twoFactorKey">
                <span class="loginCheck">
                    <label for="saveName">
                        <input type="checkbox" name="saveName" id="saveName" >
                        <span>아이디 저장</span>
                    </label>
                </span>
                <button type="submit" class="loginBtn" id="loginBtn"><span>로그인</span></button>
                <button type="button" name="changeBtn" id="changeBtn" class="loginBtn"><span>비밀번호 변경</span></button>
            </form>
            </div>
        </div>
    </div>
    <!-- //loginLeft -->
    
    <!-- loginRight -->
    <div class="loginRight">
        <div class="loginRT"></div>
        <div class="loginVisual"></div>
        <div class="loginRB"></div>
    </div>
	<div id="twoFactor" class="lpPopup lpModalWrap2 twoFactor">
	    <!-- lpWrap -->
	    <div class="lpWrap">
	        <div class="lpCon">
	            <!-- lpHeader -->
	            <div class="lpHeader">
	                <h2>2차 인증</h2>
	            </div>
	            <!--// lpHeader -->
	            <!-- lpContainer -->
	            <div class="lpContainer">
	                <!-- lpContent -->
	                <div class="lpContent">
	                    <p class="lpTxt"><font color="red">※ 관리자는 외부 접속시 2차 인증번호가 필요합니다.</font></p>
	                    <p class="lpTxt"> 문자로 발송된 인증번호를 입력하세요</p>
	                    <p class="lpTxt">
	                        <input type="text" placeholder="인증번호" name="outConnPw" maxlength="6" autocomplete="off" style="-webkit-text-security:disc;text-security:disc;-moz-text-security:disc;ime-mode:disable;-webkit-ime-mode:disable;-moz-ime-mode:disable;-ms-ime-mode:disable">
	                        <input type="hidden" name="twoFactorId" id="twoFactorId">
	                        <button type="button" class="btn btnGray" id="twoFactorSmsBtn" style="height: 35px;font-size:17px;" onClick="javascript:f_twoFactorSms()"><span>재요청</span></button>
	                        <input type="text" readOnly id="time" size="4">초
	                    </p>
	                </div>
	                <!--// lpContent -->
	            </div>
	            <!--// lpContainer -->
	            <div class="lpFooter">
	                <div class="btnSet col01 align-c">
	                    <button type="button" class="btn big btnOrange" id="twoFactorLoginBtn"><span>확인</span></button>
	                </div>
	            </div>
	            <button type="button" class="btn btnLpClose"><span>레이어팝업 닫기</span></button>
	        </div>
	    </div>
	    <!--// lpWrap -->
	</div>
    <!-- //loginRight -->
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
                    <div class="btnSet col01 align-c">
                        <a href="/"><button type="button" class="btn big btnOrange"><span>확인</span></button></a>
                    </div>
                </div>
            </div>
        </div>
        <!--// lpWrap -->
    </div>
</div><!-- //loginWrap  -->

<div id="messageOk" class="lpPopup lpModalWrap2 messageOk">
    <!-- lpWrap -->
    <div class="lpWrap">
        <div class="lpCon">
            <!-- lpHeader -->
            <div class="lpHeader">
                <h2>로그인 실패</h2>
            </div>
            <!--// lpHeader -->
            <!-- lpContainer -->
            <div class="lpContainer">
                <!-- lpContent -->
                <div class="lpContent">
                    <p class="lpTxt">아이디나 패스워드가 일치하지 않습니다.</p>
                </div>
                <!--// lpContent -->
            </div>
            <!--// lpContainer -->
            <div class="lpFooter">
                <div class="btnSet col01 align-c">
                    <button type="button" class="btn big btnOrange btnLpClose" id="passwordFailBtn"><span>확인</span></button>
                </div>
            </div>
            <button type="button" class="btn btnLpClose"><span>레이어팝업 닫기</span></button>
        </div>
    </div>
    <!--// lpWrap -->
</div>

<div id="messagePopOk" class="lpPopup lpModalWrap2 messagePopOk">
    <!-- lpWrap -->
    <div class="lpWrap">
        <div class="lpCon">
            <!-- lpHeader -->
            <div class="lpHeader">
                <h2>로그인 실패</h2>
            </div>
            <!--// lpHeader -->
            <!-- lpContainer -->
            <div class="lpContainer">
                <!-- lpContent -->
                <div class="lpContent">
                    <p class="lpTxt">아이디나 패스워드가 일치하지 않습니다.</p>
                </div>
                <!--// lpContent -->
            </div>
            <!--// lpContainer -->
            <div class="lpFooter">
                <div class="btnSet col01 align-c">
                    <button type="button" class="btn big btnOrange" id="passwordFailBtn" onClick="javascript:lpClose('.messagePopOk');"><span>확인</span></button>
                </div>
            </div>
            <button type="button" class="btn btnLpClose"><span>레이어팝업 닫기</span></button>
        </div>
    </div>
    <!--// lpWrap -->
</div>

<script>
<%
if(request.getParameter("message") != null){
%>
    sessionStorage.clear();
    $(document).find('#logoutOK').addClass("on");
    location.href="/";
<%
}
%>

$(function(){
    if(sessionStorage != null) {
        sessionStorage.clear();
    }
});

$(document).ready(function(){
    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var userInputId = getCookie("loginId");
    $("input[name='loginId']").val(userInputId); 
     
    if($("input[name='loginId']").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
        $("#saveName").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }
     
    $("#saveName").change(function(){ // 체크박스에 변화가 있다면,
        if($("#saveName").is(":checked")){ // ID 저장하기 체크했을 때,
            var userInputId = $("input[name='loginId']").val();
            setCookie("loginId", userInputId, 7); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
            deleteCookie("loginId");
        }
    });
     
    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("input[name='loginId']").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#saveName").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            var userInputId = $("input[name='loginId']").val();
            setCookie("loginId", userInputId, 7); // 7일 동안 쿠키 보관
        }
    });
    

    var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    if(!isChrome){
        $("input[name=loginPw]").attr("type","password");
    }
});

var timer    = null;
var time     = 0;

function f_login(){

	var check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	
	if(check.test($("input[name=loginPw]").val()) ||
	   check.test($("input[name=loginId]").val())		
	){
        $(document).find('.messageOk').find('.lpTxt').text("아이디와 패스워드에 한글입력은 허용하지 않습니다.");
        $('#passwordFailBtn').focus();
        lpOpen('.messageOk');
        return false;
	}
	
	$('#passwordFailBtn').focus();
    var listChkCookie = getCookie("ListChkSelection");
    
    if(listChkCookie == null){
        setCookie('ListChkSelection', 10, 365);
    }
    
    var data = null;
    
    //parameter setting
    if($("input[name=outConnPw]").length == 0){
    	data = {loginId:$("input[name=loginId]").val(),loginPw:$("input[name=loginPw]").val()};
    }else{
    	data = {loginId:$("input[name=loginId]").val(),loginPw:$("input[name=loginPw]").val(),outConnPw:$("input[name=outConnPw]").val(),twoFactorId:$("input[name=twoFactorId]").val()};
    }
    
    $.ajax({    
        method: "post",
        type: "json",    
        contentType: 'application/json; charset=utf-8',
        url     :"/asp/front/login",
        data    : JSON.stringify(data),
        success : function(obj) {
            var messageData = obj.resultJson;

            if(obj.headOfficeId != null && obj.headOfficeId != '') {
                sessionStorage.headerHeadOfficeId = obj.headOfficeId;
                sessionStorage.headerHeadOfficeName = obj.headOfficeName;
            }
            
            if(obj.franchiseId != null) {               
                sessionStorage.headerFranchiseId = obj.franchiseId;
                sessionStorage.headerFranchiseName = obj.franchiseName;
            }

            if(messageData.rCode == "0000"){//성공
                if(obj.pwdChk.pwdChk.changedDay == 'true'){
                    $(document).find('.messageOk').find('.lpHeader h2').text('로그인');
                    $(document).find('.messageOk').find('.lpTxt').text('비밀번호를 변경하신 지 3개월이 지났으니, 변경하시기 바랍니다.');
                    lpOpen('.messageOk');
                    $(document).find('.messageOk').find('.btnOrange').click(function(){
                        movePage('/asp/au/pa/aupams010', obj.pwdChk.pwdChk.userId);
                    });
                }else{
                      //정상 로그인 처리
                    location.href="/asp/dash/main";
                }
            }else{//실패

                //2차 인증 대상
                if(messageData.secondPwdYn == "Y"){
                	lpOpen("#twoFactor");
                	if(messageData.messageYn != "N"){
	                    $(document).find('.messagePopOk').find('.lpHeader h2').text('2차 인증');
	                    $(document).find('.messagePopOk').find('.lpTxt').text(messageData.rMsg);
	                	lpOpen(".messagePopOk");
                	}else{
                		time  = 60*5;
                		
                		$("#time").val(time);
                		
                		if(timer != null){
                			clearInterval(timer);
                		}

                		timer = setInterval(function(){
                                                          time --;
                                                          $("#time").val(time);
                                                          //console.log(time);
                                                          if(time == 0){
                                                        	$("#twoFactorId").val("");
                                                            clearInterval(timer);
                                                            $(document).find('.messageOk').find('.lpHeader h2').text('2차 인증');
                                                            $(document).find('.messageOk').find('.lpTxt').text("인증 시간이 만료되었습니다.");
                                                            $('input[name=outConnPw]').val("");
                                                            lpOpen(".messageOk");
                                                            lpClose(".messagePopOk");
                		                                  }
                                                          
                                                          //타이머 창을 닫은 경우
                                                          if($(".lpPopup.lpModalWrap2.twoFactor.on").text() == ""
                                                          ){
                                                        	  $("#twoFactorId").val("");
                                                              time = 0;
                                                              clearInterval(timer);
                                                              $('input[name=outConnPw]').val("");
                                                          }
                                                          
                		                              },1000);
                		$("#twoFactorId").val(messageData.twoFactorId);
                	}
                }else{  
                	$('input[name=loginPw]').val("");//로그인 실패 패스워드 초기화
                    $(document).find('.messageOk').find('.lpHeader h2').text('로그인 실패');
                    $(document).find('.messageOk').find('.lpTxt').text(messageData.rMsg);
                    lpOpen('.messageOk');
                    $('#passwordFailBtn').focus();
                }
            }
        },                
        error   : function(xhr, status, error,e) {
        	$('input[name=loginPw]').val("");
            $(document).find('.lpPopup').each(function(){
                var id = $(this).attr("id");
                if($(this).hasClass('on')){
                    lpClose('.'+id);
                }
            });
            $(document).find('.messageOk').find('.lpTxt').text("실행도중 오류가 발생했습니다.");
            lpOpen('.messageOk');
            $('#passwordFailBtn').focus();
        }
    });
}


function movePage(url, target) {
    var pageMoveTxt = '<form id="frm" method="post" action="'+url+'">';
    if(target != null) {
    pageMoveTxt += '        <input type="hidden" name="userId" value="'+target+'" />';
    }
    if(sessionStorage.headerFranchiseId != null) {
    pageMoveTxt += '        <input type="hidden" name="franchiseId" value="'+sessionStorage.headerFranchiseId+'" />';
    }
    if(sessionStorage.headerHeadOfficeId != null) {
    pageMoveTxt += '        <input type="hidden" name="headOfficeId" value="'+sessionStorage.headerHeadOfficeId+'" />';
    }
    pageMoveTxt += '</form>';
    
    $('body').append(pageMoveTxt);
    $(document).find('#frm').submit();
}

/**
 * 인증번호 재요청 SMS 발송
 */
function f_twoFactorSms(){
	//타이머 체크 필요
	
	data = {loginId:$("input[name=loginId]").val(),loginPw:$("input[name=loginPw]").val()};
	
    $.ajax({    
        method: "post",
        type: "json",    
        contentType: 'application/json; charset=utf-8',
        url     :"/asp/front/twoFactorSms",
        data    : JSON.stringify(data),
        success : function(obj) {
            var messageData = obj.resultJson;
            $("input[name=twoFactorId]").val(messageData.twoFactorId);
            $(document).find('.messagePopOk').find('.lpHeader h2').text('인증번호 문자발송');
            $(document).find('.messagePopOk').find('.lpTxt').text("인증번호가 발송되었습니다.");
            lpOpen('.messagePopOk');
            
            if(timer != null){
                clearInterval(timer);
            }
            
            time  = 60*5;
            timer = setInterval(function(){
                                              time --;
                                              $("#time").val(time);
                                              //console.log(time);
                                              if(time == 0){
                                                clearInterval(timer);
                                                $(document).find('.messageOk').find('.lpHeader h2').text('2차 인증');
                                                $(document).find('.messageOk').find('.lpTxt').text("인증 시간이 만료되었습니다.");
                                                $('input[name=outConnPw]').val("");
                                                lpOpen(".messageOk");
                                                lpClose(".messagePopOk");
                                              }
                                              
                                              //타이머 창을 닫은 경우
                                              if($(".lpPopup.lpModalWrap2.twoFactor.on").text() == ""){
	                                              $("#twoFactorId").val("");
	                                              time = 0;
	                                              clearInterval(timer);
	                                              $('input[name=outConnPw]').val("");
                                              }
                                          },1000);
        },                
        error   : function(xhr, status, error,e) {
            $('input[name=loginPw]').val("");
            $(document).find('.lpPopup').each(function(){
                var id = $(this).attr("id");
                if($(this).hasClass('on')){
                    lpClose('.'+id);
                }
            });
            $(document).find('.messageOk').find('.lpTxt').text("실행도중 오류가 발생했습니다.");
            lpOpen('.messageOk');
            $('#passwordFailBtn').focus();
        }
    });
}

/**
 * 1차 인증 btn
 */
$(document).on('click', '#loginBtn', function(){
    if($('input[name=loginId]').val() == '' || $('input[name=loginPw]').val() == ''){
        lpOpen('.messageOk');
    }else{
      f_login();
    } 
});

/**
 * 2차 인증 btn
 */
$(document).on('click', '#twoFactorLoginBtn', function(){
    if($('input[name=loginId]').val() == '' || $('input[name=loginPw]').val() == ''){
    	$(document).find('.messageOk').find('.lpTxt').text("ID 와 PASSWORD를 입력하세요");
        lpOpen('.messageOk');
    }else{
      if($('input[name=outConnPw]').val() == ''){
        $(document).find('.messagePopOk').find('.lpHeader h2').text('2차 인증');
        $(document).find('.messagePopOk').find('.lpTxt').text("인증번호를 입력하세요");
        lpOpen('.messagePopOk');
      }else{
    	  f_login();
      }
    }
});

/**
 * 
 */
$(document).on('click', '#passwordFailBtn', function(){
	if($('input[name=outConnPw]').length == 0 ||
	   $('input[name=loginPw]').val() == ""
	){
		$('input[name=loginPw]').focus();//1차 패스워드
	}else{
		$('input[name=outConnPw]').focus();//2차 패스워드
	}
});



window.name ="Parent_window";
function changePop(){
    window.open('', 'popupChk', 'width=550px, height=620px, top=30, left=20%');
    document.loginForm2.action = "/asp/front/change";
    document.loginForm2.target = "popupChk";
    document.loginForm2.submit();
}

$(document).on('click', '#changeBtn', function(){
    changePop();
});

</script>
</body>
</html>