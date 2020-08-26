// JavaScript Document
var _g_event = "click"; // mouseover , click

//키보드 열기
/*
jQuery(function ($) {
  $("input[type='text']").on(_g_event,function () {
    $(".keyboard").toggleClass("open");
    var yn =  $("#keyboard")[0].style.display;
    if (yn == "block") $("#mySlide")[0].style.display = "";
    else  $("#keyboard")[0].style.display = "block";
  })
});
*/

//팝업열기

jQuery(function ($) {
	$(".pop_open").on(_g_event, function () {
		$("#popup")[0].style.display = "block";
	})
});


//서비스 이용약관 팝업

jQuery(function ($) {
	$(".pop_terms").on(_g_event, function () {
		$("#pop_terms").toggleClass("open");
	})
});

//개인정보 수집 및 이용 동의 팝업
jQuery(function ($) {
	$(".pop_personal").on(_g_event, function () {
		$("#pop_personal").toggleClass("open");
	})
});

//비밀번호 재확인
jQuery(function ($) {
	$(".pop_pw").on(_g_event, function () {
		$("#pop_pw").toggleClass("open");
	})
});

//회원가입 완료
jQuery(function ($) {
	$(".pop_join_ok").on(_g_event, function () {
		$("#pop_join_ok").toggleClass("open");
	})
});

//종료예정 좌석
jQuery(function ($) {
	$(".pop_exit_seat").on(_g_event, function () {
		$("#pop_exit_seat").toggleClass("open");
	})
});

//시간패키지 선택  
jQuery(function ($) {
	$(".pop_time_pk").on(_g_event, function () {
		$("#pop_time_pk").toggleClass("open");
	})
});

//현금 선택  
jQuery(function ($) {
	$(".pop_cash_receipt").on(_g_event, function () {
		$("#pop_cash_receipt").toggleClass("open");
	})
});

//이용정보 없음
jQuery(function ($) {
	$(".pop_use").on(_g_event, function () {
		$("#pop_use").toggleClass("open");
	})
});


//이용정보 있음
jQuery(function ($) {
	$(".pop_use2").on(_g_event, function () {
		$("#pop_use2").toggleClass("open");
	})
});

//좌석이동
jQuery(function ($) {
	$(".pop_seat_movement").on(_g_event, function () {
		$("#pop_seat_movement").toggleClass("open");
	})
});

//좌석대기
jQuery(function ($) {
	$(".pop_seat_waiting").on(_g_event, function () {
		$("#pop_seat_waiting").toggleClass("open");
	})
});
//스마트키 선택
jQuery(function ($) {
	$(".pop_smart_key_01").on(_g_event, function () {
		$("#pop_smart_key_01").toggleClass("open");
	})
});

//스마트키 발송방법선택
jQuery(function ($) {
	$(".pop_smart_key_02").on(_g_event, function () {
		$("#pop_smart_key_02").toggleClass("open");
	})
});
//로그아웃
jQuery(function ($) {
	$(".pop_logout").on(_g_event, function () {
		$("#pop_logout").toggleClass("open");
	})
});


//팝업닫기
jQuery(function ($) {
	$(".btn_pop_closed").on(_g_event, function () {
		$(".pop_box").removeClass("open");
		$("#popup")[0].style.display = "none";

	})
});


//현금영수증
jQuery(function ($) {
	$(".no").on(_g_event, function () {
		$(".click_num").toggleClass("click_num_off");
		$("#cash_receip_txt")[0].disabled = true;
		$(".num_wrap button").each(function () {
			$(this)[0].disabled = true
		})

	})
});
jQuery(function ($) {
	$(".yes").on(_g_event, function () {
		$(".click_num").removeClass("click_num_off");
		$("#cash_receip_txt")[0].disabled = false;
		$(".num_wrap button").each(function () {
			$(this)[0].disabled = false;
		})
	})
});


/*
jQuery(function ($) {
	$(".pop_box").on(_g_event, function () {
		$(".pop_box").removeClass("open");
		$("#popup")[0].style.display = "none";

	})
});
*/

//공통 ALERT팝업
(function ($) {

	$.fn.popup = function () {

		$('#popup').show();
		this.addClass("open");
		this.find('.btn_pop_closed').unbind('click').bind('click', function () {
			$(this).parents('.pop_box').removeClass('open');
			$('#popup').hide();
		});
	};
	$.fn.close = function () {
		this.removeClass('open');
		$('#popup').hide();
	};

	$.alert = function (str, cb) {
		$('#popup').show();
		$('#pop_alert').find('.pop_contents').text(str);
		$('#pop_alert').addClass("open");
		$('#pop_alert').find('.btn_pop_closed').unbind('click').bind('click', function () {
			$(this).parents('.pop_box').removeClass('open');
			$('#popup').hide();
			if (cb) {
				cb();
			}
		});
	};
	$.noti = function (str) {
		$('#popup').show();
		$('#pop_noti').find('.pop_contents').text(str);
		$('#pop_noti').addClass("open");
		$('#pop_noti').find('.btn_pop_closed').unbind('click').bind('click', function () {
			$(this).parents('.pop_box').removeClass('open');
			$('#popup').hide();
			if (cb) {
				cb();
			}
		});
	};
	$.error = function (str) {
		$('#popup').show();
		$('#pop_error').find('.pop_contents').text(str);
		$('#pop_error').addClass("open");
		$('#pop_error').find('.btn_pop_closed').unbind('click').bind('click', function () {
			$(this).parents('.pop_box').removeClass('open');
			$('#popup').hide();
			if (cb) {
				cb();
			}
		});
	};
	$.confirm = function (str, cb) {
		$('#popup').show();
		$('#pop_confirm').find('.pop_contents').text(str);
		$('#pop_confirm').addClass("open");
		$('#pop_confirm').find('.btn_pop_closed').unbind('click').bind('click', function () {
			$(this).parents('.pop_box').removeClass('open');
			$('#popup').hide();
		});
		$('#pop_confirm .pop_bottom').find('a:eq(0)').unbind('click').bind('click', function () {
			$(this).parents('.pop_box').removeClass('open');
			$('#popup').hide();
		});
		$('#pop_confirm .pop_bottom').find('a:eq(1)').unbind('click').bind('click', function () {
			$(this).parents('.pop_box').removeClass('open');
			$('#popup').hide();
			if (cb) {
				cb();
			}
		});
	};

	$.commonErrorHandler = function (result) {
		if (result.code == 'E401') {
			$.alert(result.message, function () {
				location.href = 'login.html';
			});
		} else {
			$.alert(result.message);
		}

	};




})(jQuery);

$(document).ready(function () {

	$('body').attr('ondragstart', 'return false;');
	$('body').attr('ondrop', 'return false;');
	$('body').attr('onselectstart', 'return (arguments[0].target.tagName == "INPUT" ? true : false);');


	// HEADER/FOOTER 정보 셋팅 
	$('header .ci span').text("");
	
	var version = "Ver."+db.masterInfo.KIOSK_VERSION+" Kiosk."+db.masterInfo.KIOSK_ID+" Store."+ db.masterInfo.STORE_CODE+" Pos."+ db.masterInfo.POS_ID;
	$('.test_footer .ver').text(version);
	
	var $today_time = $('header .today_time');
	var $footer_time = $('.test_footer .time');
	var m = moment();
	$today_time.html(m.format('YYYY년 M월 D일') + '<span>' + m.format('A h:mm:ss') + '</span>');
	$footer_time.html(m.format('A h:mm'));
	setInterval(function () {
		var m = moment();
		$today_time.html(m.format('YYYY년 M월 D일') + '<span>' + m.format('A h:mm:ss') + '</span>');
		$footer_time.html(m.format('A h:mm'));
	}, 1000);



});


/**
 * UI 공통 (관리자 인증)
 */
(function ($) {

	var str = '';
	str += '<div id="admin_pw" class="admin_pw_wrap" style="display:none">';
	str += '	<div class="admin_pw fade_up">';
	str += '		<p class="admin_title">test KIOSK</p>';
	str += '		<div class="click_num  ">';
	str += '			<input type="password" id="admin_pw_num" class="admin_pw_num" placeholder="관리자 비밀번호 입력" disabled="disabled">';
	str += '			<div class="num_wrap">';
	str += '				<button class="btn_click numpad" type="button" >1</button>';
	str += '				<button class="btn_click numpad" type="button" >2</button>';
	str += '				<button class="btn_click numpad" type="button" >3</button>';
	str += '				<button class="btn_click numpad" type="button" >4</button>';
	str += '				<button class="btn_click numpad" type="button" >5</button>';
	str += '				<button class="btn_click numpad" type="button" >6</button>';
	str += '				<button class="btn_click numpad" type="button" >7</button>';
	str += '				<button class="btn_click numpad" type="button" >8</button>';
	str += '				<button class="btn_click numpad" type="button" >9</button>';
	str += '				<button class="btn_click num_del" type="button" >전체<br>지우기</button>';
	str += '				<button class="btn_click numpad" type="button">0</button>';
	str += '				<button class="btn_click num_back" type="button" ></button>';
	str += '			</div>';
	str += '		</div>';
	str += '		<div class="btn_wrap">';
	str += '			<a id="buttonAdminPwCancel" class="btn_click btn_red">취소</a>';
	str += '			<a id="buttonAdminPwConfirm"  class="btn_click btn_green">확인</a>';
	str += '		</div>';
	str += '	</div>';
	str += '</div>';
	$('body').prepend(str);

	$('#buttonAdminPwCancel').click(function () {
		$('#admin_pw_num').val('');
		$('#admin_pw').hide();
	});
	$('#buttonAdminPwConfirm').click(function () {
		$('#admin_pw').hide();
		if (db.masterInfo.KIOSK_ADMIN_PWD ==$('#admin_pw_num').val()) {
			biz.gotoAdminUI();
		} else {
			popupView.alert2('에러',"비밀번호를 확인해주세요.", '');
		}
		$('#admin_pw_num').val('');
	});
	$('#admin_pw .numpad').click(function () {
		var val = $('#admin_pw_num').val();
		$('#admin_pw_num').val(val + $(this).text());
	});
	$('#admin_pw .num_del').click(function () {
		$('#admin_pw_num').val('');
	});
	$('#admin_pw .num_back').click(function () {
		var val = $('#admin_pw_num').val();
		$('#admin_pw_num').val(val.substring(0, val.length - 1));
	});


	// 어드민 진입 이벤트 (좌상단 연속클릭 5회)
	var clickAdminStartTime = 0;
	var clickAdminCount = 0;

	var clickLogViewerStartTime = 0;
	var clickLogViewerCount = 0;
	var documentWidth = $(document).width();

	$(document).on('click', function (e) {
		if (e.originalEvent && e.originalEvent.timeStamp) {
			if (e.originalEvent.timeStamp - clickAdminStartTime < 2000 && e.originalEvent.pageX < 100 && e.originalEvent.pageY < 100) {
				clickAdminCount++;
			} else {
				clickAdminStartTime = e.originalEvent.timeStamp;
				clickAdminCount = 0;
			}
			if (clickAdminCount >= 4) {
				clickAdminCount = 0;
				$('#admin_pw').show();
			}

			if (e.originalEvent.timeStamp - clickLogViewerStartTime < 2000 && e.originalEvent.pageX > documentWidth - 100 && e.originalEvent.pageY < 100) {
				clickLogViewerCount++;
			} else {
				clickLogViewerStartTime = e.originalEvent.timeStamp;
				clickLogViewerCount = 0;
			}
			if (clickLogViewerCount >= 4) {
				clickLogViewerCount = 0;
				console.toggleUILog();
			}
		}
	});

})(jQuery);


/**
 * UI 공통 (로그인 연장 및 로딩바 처리)
 */
(function ($) {
	var str = '';
	str += '<div id="loader" class="loader_wrap" style="display:none">';
	str += '	<div class="loader">';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '		<span class="loader-block"></span>';
	str += '	</div>';
	str += '</div>';
	str += '<div id="logoutNotificator" class="time_logout_wrap" style="display:none">';
	str += '	<div class="time_logout">';
	str += '		<div id="timer" class="radialtimer"></div>';
	str += '		<div class="timer_text">';
	str += '			<span class="timer">10</span>초 후 자동 로그아웃됩니다.</div>';
	str += '		<div class="timer_btn_wrap">';
	str += '			<a id="buttonNotificatorLogoutCacnel" class="btn_click btn_green">연장하기</a>';
	str += '			<a id="buttonNotificatorLogout" class="btn_click btn_red">LOGOUT</a>';
	str += '		</div>';
	str += '	</div>';
	str += '</div>	';

	$('body').prepend(str);

	$loader = $('#loader');
	var countShowLoading = 0;
	$.showLoading = function () {
		if (countShowLoading == 0) {
			var html = $loader.html(); // IE 7에서 hide and show 시 작동하지 않으므로 ELEMENT를 RE APPEND 한다.
			$loader.empty();

			$loader.show();
			$loader.append(html);
		}
		countShowLoading++;
	};
	$.hideLoading = function () {
		if (countShowLoading > 0) {
			countShowLoading--;
		}
		if (countShowLoading == 0) {
			$loader.hide();
		}
	};


})(jQuery);




function radialTimer() {
	var self = this;
	this.seconds = 0;
	this.count = 0;
	this.degrees = 0;
	this.interval = null;
	this.timerHTML = "<div class='n'></div><div class='slice'><div class='q'></div><div class='pie r'></div><div class='pie l'></div></div>";
	this.timerContainer = null;
	this.number = null;
	this.slice = null;
	this.pie = null;
	this.pieRight = null;
	this.pieLeft = null;
	this.quarter = null;
	this.stoped = false;
	this.init = function (e, s, d) {
		self.timerContainer = $("#" + e);
		self.timerContainer.html(self.timerHTML);

		self.number = self.timerContainer.find(".n");
		self.slice = self.timerContainer.find(".slice");
		self.pie = self.timerContainer.find(".pie");
		self.pieRight = self.timerContainer.find(".pie.r");
		self.pieLeft = self.timerContainer.find(".pie.l");
		self.quarter = self.timerContainer.find(".q");
		self.stoped = false;
		self.seconds = 0;
		self.count = 0;
		self.degrees = 0;

		self.done = d;

		// start timer
		clearInterval(self.interval);
		self.start(s);
	};
	this.done = null;
	this.stop = function (s) {
		self.stoped = true;
		clearInterval(self.interval);
	};
	this.start = function (s) {
		self.seconds = s;
		self.number.html(self.seconds - self.count);
		$('.timer').text(self.seconds - self.count);

		var tick = function () {
			if (self.stoped) {
				clearInterval(self.interval);
				return;
			}

			self.number.html(self.seconds - self.count);
			$('.timer').text(self.seconds - self.count);
			self.count++;

			if (self.count > (self.seconds)) {
				clearInterval(self.interval);
				if (self.done) {
					self.done();
				}
				return;
			}

			self.degrees = self.degrees + (360 / self.seconds);
			if (self.count >= (self.seconds / 2)) {
				self.slice.addClass("nc");
				if (!self.slice.hasClass("mth")) self.pieRight.css({
					"transform": "rotate(180deg)"
				});
				self.pieLeft.css({
					"transform": "rotate(" + self.degrees + "deg)"
				});
				self.slice.addClass("mth");
				if (self.count >= (self.seconds * 0.75)) self.quarter.remove();
			} else {
				self.pie.css({
					"transform": "rotate(" + self.degrees + "deg)"
				});
			}
		};
		tick();
		self.interval = window.setInterval(tick, 1000);
	}
}

var disableTimeExpire = disableTimeExpire ? disableTimeExpire : false;
$(document).ready(function () {

	var timeExpireActive = 15 * 1000;
	var timeShowNotificator = 10;

	if (timeExpireActive && emart.isLogin() && disableTimeExpire == false) {

		var LogoutTimer = new radialTimer();
		var lastActiveTime = new Date().getTime();
		$logoutNotificator = $('#logoutNotificator');

		var timeoutInteval = setInterval(function () {
			if ($logoutNotificator.is(':visible')) {
				return;
			}

			var nowTime = new Date().getTime();
			if ((nowTime - lastActiveTime) > timeExpireActive) {
				$logoutNotificator.show();
				LogoutTimer.init("timer", timeShowNotificator, function () {
					location.href = 'login.html';
				});
			}

		}, 10 * 1000);

		$(document).on('click', function () {
			lastActiveTime = new Date().getTime();
		});


		$('#buttonNotificatorLogout').click(function () {
			location.href = 'login.html';
		});
		$('#buttonNotificatorLogoutCacnel').click(function () {
			LogoutTimer.stop();
			$('#logoutNotificator').hide();
		});
	}


});