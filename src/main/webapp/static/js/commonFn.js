/**
 * f_vaildation_check 유효성 체크
 * 해당 form name의 validation check
 * 해당 속성의 title 미입력시 alert에 undefined 발생
 */
function f_txt_vaildation_check(formName){
    var isValid = true;
    var text    = "";
    var fristFocusCnt = 1;
    $("form[name="+formName+"]").find("*[class*=required]").each(function(){
        var element = $(this);
        //fn_alert("["+element.val()+"]");
        if(element.val() == "" || element.val() == null){
            isValid = false;
            //if(fristFocusCnt == 1){
                element.focus();//첫번째 위치를 가리킨다.
            //}
            text = $(this).attr('title');
            return false;
            //fristFocusCnt++;//첫번째만 체크하기 위함 증감
        }
    });
    if(!isValid){
        fn_alert(text+"의 필수입력 정보를 입력하세요");
//        fn_alert("필수 입력사항을 확인해 주세요.");
    }
    return isValid;//true, false
}
function f_txt_vaildation_check2(formName){
    var isValid = true;
    var text    = "";
    var fristFocusCnt = 1;
    $("form[name="+formName+"]").find("*[class*=required]").each(function(){
        var element = $(this);
        //fn_alert("["+element.val()+"]");
        if(element.val() == "" || element.val() == null){
            isValid = false;
            if(fristFocusCnt == 1){
                element.focus();//첫번째 위치를 가리킨다.
            }
            text += " ["+$(this).attr('title')+"]";
            fristFocusCnt++;//첫번째만 체크하기 위함 증감
        }
    });
//    if(!isValid){
//        fn_alert(text+"의 필수입력 정보를 입력하세요");
//    }
    return isValid;//true, false
}


$(document).ready(function(){
    //class명not-kor 대상 한글입력 차단 
    $(".not-kor").keyup(function(e){
        if(!(e.keyCode >= 37 && e.keyCode <= 40)){
            var v = $(this).val();
            $(this).val(v.replace(/[^a-z0-9]/gi,''));
        }
    });
    
    //class명 onlyNumber 대상 오직 숫자
    $(".onlyNumber").on("focus", function(){
        var x = $(this).val();
//        x = removeCommas(x);
        $(this).val(x);
    }).on("focusout", function(){
        var x = $(this).val();
        if(x && x.length > 0){
            if(!$.isNumeric(x)){
                x = x.replace(/[^0-9]/g,"");
            }
//            x = addCommas(x);
            $(this).val(x);
        }
    }).on("keyup", function(){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
    });
    
    
    $(".commaOnlyNumber").on("focus", function(){
        var x = $(this).val();
        $(this).val(x);
    }).on("focusout", function(){
        var x = $(this).val();
        if(x && x.length > 0){
            if(!$.isNumeric(x)){
                x = x.replace(/[^0-9\,]/g,"");
            }
//            x = addCommas(x);
            $(this).val(x);
        }
    }).on("keyup", function(){
        $(this).val($(this).val().replace(/[^0-9\,]/g,""));
    }).on("change", function(){
        $(this).val($(this).val().replace(/[^0-9\,]/g,""));
    });
});

//3자리 단위마다 콤마 생성
function addCommas(num){
    var len, point, str;

    num = num+"";
    if(num.indexOf(',') != -1){ 
        num = num.replace(/,/g, '');
    }
    point = num.length % 3;
    len = num.length;
    
    str = num.substring(0, point);
    while(point < len) {
        if(str != "") str+= ",";
        str += num.substring(point, point+3);
        point += 3;
    }
    
    return str;
}

// 콤마 붙여서 넣어줌
function addCommasObj(obj){
    var len, point, str;

    var num = obj.val();
    
    num = num+"";
    if(num.indexOf(',') != -1){ 
        num = num.replace(/,/g, '');
    }
    point = num.length % 3;
    len = num.length;
    
    str = num.substring(0, point);
    while(point < len) {
        if(str != "") str+= ",";
        str += num.substring(point, point+3);
        point += 3;
    }
    
    obj.val(str);
    
}


//모든 콤마 제거
function removeCommas(x){
    if(!x || x.length == 0) return "";
    else return x.split(",").join("");
}
// 콤마 잘라서 넣어줌
function removeCommasObj(obj){
    if(!obj.val() || obj.val().length == 0){
        obj.val("");
    }else{
        obj.val(obj.val().split(",").join(""));
    }
}


//이메일 검증 스크립트 작성
function f_email_check(obj){
    var emailVal = obj.val();
    var returnVal = true;
    //fn_alert("emailVal="+emailVal);
    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    
    //검증에 사용할 정규식 변수 regExp에 저장
    if(emailVal.match(regExp) != null){
        //이상없음
    }else{
        //규칙오류
        fn_alert("이메일 양식에 맞게 입력해 주세요");
        obj.focus();
        returnVal= false;
    }
    
    return returnVal;
}


//휴대폰 유효성 체크
function f_hpno_chk(obj){
    
    if(obj == null){
        fn_alert("휴대폰 번호가 입력되지 않았습니다.");
        return false;
    }
    
    var regExp = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
    //$("input[name="+name+"]").val())
    if(!regExp.test(obj.val())){
        fn_alert("잘못된 휴대폰 번호입니다. 숫자만 입력하세요");
        obj.focus();
        return false;
    }else{
        return true;
    }
}

//일반 전화번호 유효성 체크
function f_normal_telno_chk(obj){
    
    var regExp = /^\d{2,3}-?\d{3,4}-?\d{4}$/;
    
    if(!regExp.test(obj.val())){
        fn_alert("잘못된 전화번호 입니다. 숫자만 입력하세요");
        obj.focus();
        return false;
    }else{
        return true;
    }
}

/**
 * 
 * @param pInterval yyyy:년, m:월, d:일
 * @param pAddVal    연산할 값
 * @param pYyyymmdd    날짜스트링
 * @param pDelimiter    구분자
 * @returns {String}
 */
function addDateCal(pInterval, pAddVal, pYyyymmdd, pDelimiter){
    
     var yyyy;
     var mm;
     var dd;
     var cDate;
     var cYear, cMonth, cDay;

     if( pDelimiter != "" ){
          pYyyymmdd = pYyyymmdd.replace(eval("/\\" + pDelimiter + "/g"), "");
     }//end if

     yyyy = pYyyymmdd.substr(0, 4);
     mm  = pYyyymmdd.substr(4, 2);
     dd  = pYyyymmdd.substr(6, 2);

     if( pInterval == "yyyy" ){
          yyyy = (yyyy * 1) + (pAddVal * 1);
     }else if( pInterval == "m" ) {
          mm  = (mm * 1) + (pAddVal * 1);
     }else if( pInterval == "d" ){
          dd  = (dd * 1) + (pAddVal * 1);
     }//end if
     cDate = new Date(yyyy, mm - 1, dd);
     cYear = cDate.getFullYear();
     cMonth = cDate.getMonth() + 1;
     cDay = cDate.getDate();

    if( (cMonth + "").length < 2 ){
        cMonth = "0" + cMonth;
    }//end if

    if( (cDay + "").length < 2 ){
        cDay = "0" + cDay;
    }//end if

     if( pDelimiter != "" ){
          return cYear + pDelimiter + cMonth + pDelimiter + cDay;
     }else{
          return cYear + "" + cMonth + "" + cDay;
     }//end if

}

/**
 * 셀렉트박스 자동선택
 * @param Obj_id : selectbox id
 * @param Obj_val: 선택원하는 value
 */
function selectbox_selected(Obj_id, Obj_val){
//    fn_alert($("#"+Obj_id + " option[value="+Obj_val+"]").index());
    var $myselect = $("#"+Obj_id);
    $myselect[0].selectedIndex = $("#"+Obj_id + " option[value='"+Obj_val+"']").index();
    $myselect.selectmenu('refresh');
    $myselect.selectmenu();
}

/**
 * 숫자 콤마제거
 * @param str
 * @returns
 */
function fn_removeComma(str){
    if(str == null || str == ""){
        return 0;
    }else{
        return str.replace(/\,/gi, '');
    }
}


//쿠키 처리
var saveclass = null;

function saveListCntSetting(cookieValue)
{
    var sel = $(".ListChkSelection")

    saveclass = saveclass ? saveclass : document.body.className;
    document.body.className = saveclass + ' ' + sel.value;

    setCookie('ListChkSelection', cookieValue, 31);
}

function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function setCookie(cookieName, cookieValue, nDays) {
    var today = new Date();
    var expire = new Date();

    if (nDays==null || nDays==0)
        nDays=1;

    expire.setTime(today.getTime() + 3600000*24*nDays);
    document.cookie = cookieName+"="+escape(cookieValue)+";path=/" + ";expires="+expire.toGMTString();
}
var getCookie = function(name) {
    var x, y;
    var val = document.cookie.split(';');

    if(val.length > 0){
        for (var i = 0; i < val.length; i++) {
            x = val[i].substr(0, val[i].indexOf('='));
            y = val[i].substr(val[i].indexOf('=') + 1);
            x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
            if (x == name) {
                return unescape(y); // unescape로 디코딩 후 값 리턴
            }
        }
    }
};

/**
쿠키값 확인용
 */
var getAlertCookie = function(name) {
    var x, y;
    var val = document.cookie.split(';');

    if(val.length > 0){
        for (var i = 0; i < val.length; i++) {
            x = val[i].substr(0, val[i].indexOf('='));
            y = val[i].substr(val[i].indexOf('=') + 1);
            x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
            if (x == name) {
                alert(unescape(  y));
                return unescape(y); // unescape로 디코딩 후 값 리턴
            }
        }
    }
};

/*ListChkSelection가 class로 부여된 객체의 selectbox 선택에 따른 쿠키 기억처리*/
$(function(){
    $(".ListChkSelection [value='"+getCookie('ListChkSelection')+"']").prop("selected", true);
});

/*ajax loading setting*/

var loadingbarYN = "Y";

$.ajaxSetup({
     beforeSend: function() {
    var loadingHtml  = '<div id="loading" style="z-index: 99999;position: fixed;width:100%;height:100%;top:0;left:0;text-align:center;"> ';
        loadingHtml += '	<div class="loading_box" style="position:absolute;top:50%;left:50%;transform: translate(-50%, -50%);"><img src="/static/img/loading/loading.gif"  /></div></div>';
        if(loadingbarYN == "Y"){
        	$('body').fadeTo( "fast", 1 ).append(loadingHtml);
        }
     },
     complete: function() {
    	 if(loadingbarYN == "Y"){
    		 $('body').fadeTo( "slow", 1 ).find('#loading').remove();
    	 }
     }
     ,
     success: function() {
    	 if(loadingbarYN == "Y"){
    		 $('body').fadeTo( "slow", 1 ).find('#loading').remove();
    	 }
     }
});


/**
 * date 스트링으로 변경
 * @param date
 * @param format
 * @returns
 */
function getStringDate(date, format){
	
	var today = "";
	var gbn = "";
	
	if(date == null || date == ""){
		return today;
	}
	if(format != null && format != ""){
		gbn =  format;
	}
	
    var year = date.getFullYear();
    var month = date.getMonth()+1
    var day = date.getDate();
    if(month < 10){
        month = "0"+month;
    }
    if(day < 10){
        day = "0"+day;
    }
 
    today = year+gbn+month+gbn+day;
    
	return today;
}

function parse_Date(str) {
	var y = str.substr(0, 4);
	var m = str.substr(4, 2);
	var d = "01";
	if(str.length == 8) {
		d = str.substr(6, 2);
	}
	return new Date(parseInt(y), parseInt(m)-1, parseInt(d));
}

// 필수항목체크
function formNullCheck(obj, str){
	var returnVal = "";
	if ( typeof obj == "undefined" || obj.val() == null || obj.val().trim() == "" ) {
		str = str || "필수입력항목";
		$(document).find('.messageOk').find('.lpHeader h2').text("확인");
		$(document).find('.messageOk').find('.lpTxt').text(str + "을(를) 입력해주세요");
        lpOpen('.messageOk');
		//alert(str + "을(를) 입력해주세요");
        $(document).on('click', '.messageOk .btnLpClose', function(){        	
        	obj.focus();
        })
		return true;
	}
}

// select box 연도 , 월 표시
function setYearDateBox(startYear){
    var dt = new Date();
    var year = "";
    var com_year = dt.getFullYear();
    // 발행 뿌려주기
    // 기준년 부터 올해까지
    startYear = startYear || 2018;
    for(var y = (startYear); y <= (com_year); y++){
        $("#searchYear").append("<option value='"+ y +"'>"+ y + " 년" +"</option>");
    }
}
function setMonthDateBox(){
	var dt = new Date();
	var year = "";
	var com_year = dt.getFullYear();
	// 월 뿌려주기(1월부터 12월)
	var month;
	for(var i = 1; i <= 12; i++){
	    if(i < 10){
	        i = "0"+i;
	    }
		$("#searchMonth").append("<option value='"+ i +"'>"+ i + " 월" +"</option>");
	}
}

//월별날짜 계산 (모바일)
function dateMonthCall(){
	var sdd = document.getElementById("startSearchMonth").value;
    var edd = document.getElementById("endSearchMonth").value;
    var ar1 = sdd.split('-');
    var ar2 = edd.split('-');
    var da1 = new Date(ar1[0], ar1[1]);
    var da2 = new Date(ar2[0], ar2[1]);
    var dif = da2 - da1;
    var cDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
    var cMonth = cDay * 30;// 월
    
    if(parseInt(dif/cMonth)+1 > 12){
		alert("기간은 최대 12개월까지 선택가능합니다");
        return true;
    }else{
    	return false;
    }
}

// 매출통계 기간계산 함수
function dateCall(){
	var ck = false;
	
	if($("form[name=frm] select[name=dayMonthGbn]").val() == "month"){
		// 월별날짜 계산
		var sdd = document.getElementById("startSearchMonth").value;
	    var edd = document.getElementById("endSearchMonth").value;
	    var ar1 = sdd.split('-');
	    var ar2 = edd.split('-');
	    var da1 = new Date(ar1[0], ar1[1]);
	    var da2 = new Date(ar2[0], ar2[1]);
	    var dif = da2 - da1;
	    var cDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
	    var cMonth = cDay * 30;// 월
	    
	    if(parseInt(dif/cMonth)+1 > 12){
	    	$(document).find('.messageOk').find('.lpHeader h2').text("기간 확인");
			$(document).find('.messageOk').find('.lpTxt').text("기간은 최대 12개월까지 선택가능합니다");
	        lpOpen('.messageOk');
	        
	        ck = true;
	        return ck;
	    }
	}else{
		// 일별날짜 계산
		var sdd = document.getElementById("startSearchDate").value;
	    var edd = document.getElementById("endSearchDate").value;
	    var ar1 = sdd.split('-');
	    var ar2 = edd.split('-');
	    var da1 = new Date(ar1[0], ar1[1], ar1[2]);
	    var da2 = new Date(ar2[0], ar2[1], ar2[2]);
	    var dif = da2 - da1;
	    var cDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
	    
	    if(parseInt(dif/cDay) > 31){
	    	$(document).find('.messageOk').find('.lpHeader h2').text("기간 확인");
			$(document).find('.messageOk').find('.lpTxt').text("시작일과 종료일은 최대 한달까지 선택가능합니다");
	        lpOpen('.messageOk');
	        
	        ck = true;
	        return ck;
	    }
	}
	
	return ck;
}

function headerHeadOfficeIdChk(){
    $('.lpChoice').click(function(){
        lpClose('.lpChoice');
    });
  if(sessionStorage.headerHeadOfficeId == null) {
      $('#lpChoice').find('.lpContent .lpTxt').text('본사를 선택해주세요.');
      $('#lpChoice').find('.lpFooter p').text('본사 선택시 자동으로 화면 전환됩니다.');
      lpOpen('.lpChoice');
      return false;
  }
  return true;
}

function headerFranchiseChk(){
    $('.lpChoice').click(function(){
        lpClose('.lpChoice');
    });
	  if(sessionStorage.headerFranchiseId == null) {
	      $('#lpChoice').find('.lpContent .lpTxt').text('가맹점을 선택해주세요.');
	      $('#lpChoice').find('.lpFooter p').text('가맹점 선택시 자동으로 화면 전환됩니다.');
	      lpOpen('.lpChoice');
	      return false;
	  }
	return true;
}


(function($){
	$.fn.throwspan = function(colIdx){
		
		return this.each(function(){
			
			var that;
			
			$('tr', this).each(function(row){
				$('th:eq('+colIdx+')',this).each(function(col){
					if($(this).html() == $(that).html()){
						rowspan = $(that).attr("rowSpan")
						
						if(rowspan == undefined){
							$(that).attr("rowSpan",1);
							rowspan = $(that).attr("rowSpan");
						}
						
						rowspan = Number(rowspan)+1;
						
						$(that).attr("rowSpan",rowspan);
						$(this).hide();
					}else{
						that = this;
					}
					
					that = (that == null) ? this : that;
				});
			});
		});
	};
})(jQuery);

(function($){
	$.fn.tdrowspan = function(colIdx){
		
		return this.each(function(){
			
			var that;
			
			$('tr', this).each(function(row){
				$('td:eq('+colIdx+')',this).each(function(col){
					if($(this).html() == $(that).html()){
						rowspan = $(that).attr("rowSpan")
						
						if(rowspan == undefined){
							$(that).attr("rowSpan",1);
							rowspan = $(that).attr("rowSpan");
						}
						
						rowspan = Number(rowspan)+1;
						
						$(that).attr("rowSpan",rowspan);
						$(this).hide();
					}else{
						that = this;
					}
					
					that = (that == null) ? this : that;
				});
			});
		});
	};
})(jQuery);

(function($){
	$.fn.tdrowspan_two = function(colIdx){
		
		return this.each(function(){
			
			var that;
			
			$('tr', this).each(function(row){
				$('td:eq('+colIdx+')',this).each(function(col){
					if($(this).html() == $(that).html() &&
					   $(this).prev().html() == $(that).prev().html()
					){
						rowspan = $(that).attr("rowSpan")
						
						if(rowspan == undefined){
							$(that).attr("rowSpan",1);
							rowspan = $(that).attr("rowSpan");
						}
						
						rowspan = Number(rowspan)+1;
						
						$(that).attr("rowSpan",rowspan);
						$(this).hide();
					}else{
						that = this;
					}
					
					that = (that == null) ? this : that;
				});
			});
		});
	};
})(jQuery);

(function($){
	$.fn.tdrowspan_three = function(colIdx){
		
		return this.each(function(){
			
			var that;
			
			$('tr', this).each(function(row){
				$('td:eq('+colIdx+')',this).each(function(col){
					if($(this).html()               == $(that).html()               &&
					   $(this).prev().html()        == $(that).prev().html()        &&
					   $(this).prev().prev().html() == $(that).prev().prev().html()
					){
						rowspan = $(that).attr("rowSpan")
						
						if(rowspan == undefined){
							$(that).attr("rowSpan",1);
							rowspan = $(that).attr("rowSpan");
						}
						
						rowspan = Number(rowspan)+1;
						
						$(that).attr("rowSpan",rowspan);
						$(this).hide();
					}else{
						that = this;
					}
					
					that = (that == null) ? this : that;
				});
			});
		});
	};
})(jQuery);

(function($){
	$.fn.tdrowspan_four = function(colIdx){
		
		return this.each(function(){
			
			var that;
			
			$('tr', this).each(function(row){
				$('td:eq('+colIdx+')',this).each(function(col){
					if($(this).html()               == $(that).html()               &&
					   $(this).prev().html()        == $(that).prev().html()        &&
					   $(this).prev().prev().html() == $(that).prev().prev().html() &&
					   $(this).prev().prev().prev().html() == $(that).prev().prev().prev().html()
					){
						rowspan = $(that).attr("rowSpan")
						
						if(rowspan == undefined){
							$(that).attr("rowSpan",1);
							rowspan = $(that).attr("rowSpan");
						}
						
						rowspan = Number(rowspan)+1;
						
						$(that).attr("rowSpan",rowspan);
						$(this).hide();
					}else{
						that = this;
					}
					
					that = (that == null) ? this : that;
				});
			});
		});
	};
})(jQuery);