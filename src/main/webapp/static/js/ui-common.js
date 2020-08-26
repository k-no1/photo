var clkChk = 0;
$(function(){
	 //전체선택 및 개별 선택 control(checkbox)
    $(document).on('click','.inputChoiceAll', function(){
    	if($(this).prop('checked')) {
	    	$(this).closest('table').find('.inputChoice').each(function(){
	    		if(!$(this).prop("disabled")) {
	    			$(this).prop('checked', true);
	    		}
	    	});
    	}else {
    		$(this).closest('table').find('.inputChoice').each(function(){
    			if(!$(this).prop("disabled")) {
    				$(this).prop('checked', false);
    			}
	    	});
    	}
    });    
    $(document).on('click', '.inputChoice', function(){ //단일 체크박스 control
    	var $tableOneChk = $(this).closest('table').find('.inputChoice');
    	var chkBoxCnt = $tableOneChk.size();
		var chkBoxClickCnt = 0;
		
		$tableOneChk.each(function(){
			if($(this).prop("checked")) {
				chkBoxClickCnt++;
			}else {
				chkBoxClickCnt--;
			}
		});
		
		if(chkBoxCnt == chkBoxClickCnt) {
			$(this).closest('table').find('.inputChoiceAll').prop("checked", true);	
		}else {
			$(this).closest('table').find('.inputChoiceAll').prop("checked", false);
		}
	});
    
    //콤마 자동으로 붙이기
    $(document).on('keyup','.priceRig', function(){    	
    	var price = $(this).val();
		var minus = '';
		if(price.indexOf('-') != -1) {
			minus = '-';
		}
		
		price = price.replace(/[^0-9]/g, '');
		price = price.replace(/,/gi, '');
		
		$(this).val(minus+addCommas(price));
    });
  //콤마 자동으로 붙이기
    $(document).on('blur','.priceRig', function(){    	
    	var price = $(this).val();
		var minus = '';
		if(price.indexOf('-') != -1) {
			minus = '-';
		}
		
		price = price.replace(/[^0-9]/g, '');
		price = price.replace(/,/gi, '');
		
		$(this).val(minus+addCommas(price));
    });
    
    //체크박스 하나만 클릭할 수 있도록 하기
	$(document).on('click', '#tableBody tr td .inputChoice', function(){
		if($(this).prop("checked")) {
			clkChk++;
		} else {
			clkChk--;
		}
		
		if(clkChk >= 2) {
			$("#messageOk").find('.lpTxt').text('1개만 선택해 주세요.');
			lpOpen('.messageOk');

			$(this).prop("checked", false);
			
			clkChk = 1;
		}
	});
	
	//숫자만 입력
	$(document).on('keyup', '.inputNum', function(){
		var txt = $(this).val().replace(/[^0-9]/g,'');
		if(txt.length > 1 &&
		   txt.charAt(0) == "0"		
		){
			txt = txt.substring(1);
		}
		$(this).val(txt);
	});
	$(document).on('blur', '.inputNum', function(){
		var txt = $(this).val().replace(/[^0-9]/g,'');
		if(txt.length > 1 &&
		   txt.charAt(0) == "0"		
		){
			txt = txt.substring(1);
		}
		$(this).val(txt);
	});
});