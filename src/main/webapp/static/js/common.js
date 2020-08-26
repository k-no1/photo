function fileUpLoad(){
	// 파일 리스트 번호
    var fileIndex = 0;
    // 등록할 전체 파일 사이즈
    var totalFileSize = 0;
    // 파일 리스트
    var fileList = new Array();
    // 파일 사이즈 리스트
    var fileSizeList = new Array();
    // 등록 가능한 파일 사이즈 MB
    var uploadSize = 50;
    // 등록 가능한 총 파일 사이즈 MB
    var maxUploadSize = 500;
    
    var dropIdx = 0;
 
    $(function (){
        // 파일 드롭 다운
        fileDropDown();
    });
 
    // 파일 드롭 다운
    function fileDropDown(){
    	
        var dropZone = $(".dropWrap .tbl .dropZone");
        
        //Drag기능 
        dropZone.on('dragenter',function(e){
            e.stopPropagation();
            e.preventDefault();
            dropZone.css('background-color','#E3F2FC');
        });
        dropZone.on('dragleave',function(e){
            e.stopPropagation();
            e.preventDefault();
            dropZone.css('background-color','#FFFFFF');
        });
        dropZone.on('dragover',function(e){
            e.stopPropagation();
            e.preventDefault();
            dropZone.css('background-color','#E3F2FC');
        });
        dropZone.on('drop',function(e){
            e.preventDefault();
            dropZone.css('background-color','#FFFFFF');
            dropIdx = $(this).closest(".tbl").index();
            console.log(dropIdx);
            
            var files = e.originalEvent.dataTransfer.files;
            if(files != null){
                if(files.length < 1){
                    alert("폴더 업로드 불가");
                    return;
                }
                selectFile(files)
            }else{
                alert("ERROR");
            }
        });
    }
 
    // 파일 선택시
    function selectFile(fileObject){
        var files = null;
 
        if(fileObject != null){
            // 파일 Drag 이용하여 등록시
            files = fileObject;
        }else{
            // 직접 파일 등록시
            files = $('#multipaartFileList_' + fileIndex)[0].files;
        }
        
        // 다중파일 등록
        if(files != null){
            for(var i = 0; i < files.length; i++){
                // 파일 이름
                var fileName = files[i].name;
                var fileNameArr = fileName.split("\.");
                // 확장자
                var ext = fileNameArr[fileNameArr.length - 1];
                // 파일 사이즈(단위 :MB)
                var fileSize = files[i].size / 1024 / 1024;
                
                if($.inArray(ext, ['exe', 'bat', 'sh', 'java', 'jsp', 'html', 'js', 'css', 'xml']) >= 0){
                    // 확장자 체크
                    alert("등록 불가 확장자");
                    break;
                }else if(fileSize > uploadSize){
                    // 파일 사이즈 체크
                    alert("용량 초과\n업로드 가능 용량 : " + uploadSize + " MB");
                    break;
                }else{
                    // 전체 파일 사이즈
                    totalFileSize += fileSize;
                    
                    // 파일 배열에 넣기
                    fileList[fileIndex] = files[i];
                    
                    // 파일 사이즈 배열에 넣기
                    fileSizeList[fileIndex] = fileSize;
 
                    // 업로드 파일 목록 생성
                    addFileList(fileIndex, fileName, fileList);
 
                    // 파일 번호 증가
                    fileIndex++;
                }
            }
        }else{
            alert("ERROR");
        }
    }
 
    // 업로드 파일 목록 생성
    function addFileList(fIndex, fileName, fileList){
        var html = "";
        html += "<li id='file_" + fIndex + "'>";
        html +=         "<span class='fileName'><img src='" + fileName + "'></span><button type='button' onclick='deleteFile(" + fIndex + "); return false;' class='btn mid btnGray'>삭제</button>"
        html +=			 "<span class='checkWrap'>"
        html +=					"<label for=''>"
        html +=						"<input type='checkbox' name=''>"
        html +=						"<span>선택</span>"
        html +=					"</label>"
        html +=			"</span>"
        html += "</li>"
        
        $(".dropWrap .tbl:eq("+dropIdx+") .dropZone .fileBox").append(html);
        $( ".fileBox" ).sortable({
	  	      revert: true
	  	});        
    }
 
    // 업로드 파일 삭제
    function deleteFile(fIndex){
        // 전체 파일 사이즈 수정
        totalFileSize -= fileSizeList[fIndex];
        
        // 파일 배열에서 삭제
        delete fileList[fIndex];
        
        // 파일 사이즈 배열 삭제
        delete fileSizeList[fIndex];
        
        // 업로드 파일 테이블 목록에서 삭제
        $("#file_" + fIndex).remove();
    }
 
    // 파일 등록
    function uploadFile(){
        // 등록할 파일 리스트
        var uploadFileList = Object.keys(fileList);
 
        // 파일이 있는지 체크
        if(uploadFileList.length == 0){
            // 파일등록 경고창
            alert("파일이 없습니다.");
            return;
        }
        
        // 용량을 500MB를 넘을 경우 업로드 불가
        if(totalFileSize > maxUploadSize){
            // 파일 사이즈 초과 경고창
            alert("총 용량 초과\n총 업로드 가능 용량 : " + maxUploadSize + " MB");
            return;
        }
            
        if(confirm("등록 하시겠습니까?")){
            // 등록할 파일 리스트를 formData로 데이터 입력
            var form = $('#uploadForm');
            var formData = new FormData(form);
            for(var i = 0; i < uploadFileList.length; i++){
                formData.append('files', fileList[uploadFileList[i]]);
            }
            
            $.ajax({
                url:"업로드 경로",
                data:formData,
                type:'POST',
                enctype:'multipart/form-data',
                processData:false,
                contentType:false,
                dataType:'json',
                cache:false,
                success:function(result){
                    if(result.data.length > 0){
                        alert("성공");
                        location.reload();
                    }else{
                        alert("실패");
                        location.reload();
                    }
                }
            });
        }
    }
}

//lnb setup
function setLnb(mn,cm) {	
	$(".depth01").eq(mn).addClass("on active");
	$(".depth01.on .depth02 li").eq(cm).addClass("current");
}

//Layer Popup Open
function lpOpen(el) {
	var $el = $(el);
	$el.addClass("on");
	$el.stop().fadeIn("fast");
	var win_h = $(window).height() - 80;
	var lp_h = $el.find(".lpHeader").height() + $el.find(".lpContainer").height() + 100;
	$("html").css("overflow","hidden");
	if($el.hasClass("lpModalWrap")){
		$("body").addClass("lpActive");
	};
	/*console.log($el.find(".lpHeader").height(),$el.find(".lpFooter").height());*/
	if(lp_h > win_h){
		$el.find(".lpContainer").height(win_h - $el.find(".lpHeader").height() - 200 + "px");
	}
	
	// 스크롤 높이값 설정
	$(".categoryCopy .scroll").css("max-height",$el.find(".lpContainer").height() -  144 + "px"); // 카테고리복사
}
// Layer Popup Close
function lpClose(el) {
	var $el = $(el);
	$el.stop().fadeOut("fast",function(){
		$el.removeClass("on");
		$(".lpFocus").focus();
		$(".lpFocus").removeClass(".lpFocus");
		$("html").css("overflow","");
		$("body").removeClass("lpActive");
	});
}

// tableDnd
function tableDnd(){
	$(".tblDrag").tableDnD({
		dragHandle: ".dragHandle",
	});
}

// color
function colorPicker(){
	var inputs = document.querySelectorAll('.colorInput');

 	for (var i = 0, len = inputs.length; i < len; ++i) {
		var pickerInput = new CP(inputs[i]);

		pickerInput.on("change", function (color) {
			this.source.value = '#' + color;
			this.source.style.backgroundColor = '#' + color;
		});
		
		// == input text 변경시 업데이트 부분 ==
		
		function update() {
			this.style.backgroundColor = this.value;
			pickerInput.set(this.value).enter();
		}

		pickerInput.source.oncut = update;
		pickerInput.source.onpaste = update;
		pickerInput.source.onkeyup = update;
		pickerInput.source.oninput = update;
		
		// == input text 변경시 업데이트 부분 ==
	}
	
}
//function colorPicker(){
//	var source = document.querySelectorAll('.colorInput');
//    
//	for (var i = 0, len = source.length; i < len; ++i) {
//		var code = document.createElement('input');
//		
//		code.className = 'color-code';
//		code.type = 'text';
//		
//		var tpicker = (new CP(source[i])); 
//		tpicker.on("change", function(color) {
//			console.log(color);
//	        this.source.value = '#' + color;
//	        code.value = '#' + color;
//	        this.source.style.backgroundColor = '#' + color;
//	    });
//		
//		tpicker.on("enter", function() {
//	        code.value = '#' + CP._HSV2HEX(this.get());
//	    });
//		
//		tpicker.self.appendChild(code);
//		
//		function update() {
//	        if (this.value.length) {
//	        	tpicker.set(this.value);
//	        	var colorVal = [this.value.slice(1)];
//	        	tpicker.fire("change", colorVal);
//	        }
//	    }
//
//	    code.oncut = update;
//	    code.onpaste = update;
//	    code.onkeyup = update;
//	    code.oninput = update;
//	}
//		
//	var x = document.createElement('a');
//	x.href = 'javascript:;';
//	//x.innerHTML = 'Close Me!';
//	x.addEventListener("click", function() {
//		picker.exit();
//	}, false);
//	//picker.self.appendChild(x);
//}

function colorPicker1(){
	var picker = new CP(document.querySelector('.colorInput'));
	
	picker.on("change", function(color) {
		this.source.value = '#' + color;
		$(".colorInput").css("background-color",'#' + color);
	});
	
	var x = document.createElement('a');
	x.href = 'javascript:;';
	//x.innerHTML = 'Close Me!';
	x.addEventListener("click", function() {
	picker.exit();
	}, false);
	
	picker.self.appendChild(x);
}

function colorPicker2(){
	var picker2 = new CP(document.querySelector('.colorInput2'));
	
	picker2.on("change", function(color) {
		this.source.value = '#' + color;
		$(".colorInput2").css("background-color",'#' + color);
	});
	
	var x = document.createElement('a');
	x.href = 'javascript:;';
	//x.innerHTML = 'Close Me!';
	x.addEventListener("click", function() {
	picker2.exit();
	}, false);
	
	picker2.self.appendChild(x);
}

//datepicker
/*function openCal(el, choice) {
	var $initFld = $(el).prev("input[type=text]");	
	
	if(choice == "month"){
		var $choice = "yy-mm";
		$initFld.datepicker({
			formatDate:"ATOM",
			dateFormat: $choice,
			defaultDate: $initFld.val(),
			changeMonth: true,
			changeYear: true,
			showMonthAfterYear:false,
			altField: $initFld,
			showButtonPanel: true,
			closeText:"X",
			onClose: function() {
		        var iMonth = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
		        var iYear = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		        $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
		     },
		}).datepicker("show");
		$(".ui-datepicker").addClass("off");
	}else{
		var $choice = "yy-mm-dd";
		$initFld.datepicker({
			formatDate:"ATOM",
			dateFormat: $choice,
			defaultDate: $initFld.val(),
			changeMonth: true,
			changeYear: true,
			showMonthAfterYear:false,
			altField: $initFld,
			showButtonPanel: true,
			closeText:"X",
		}).datepicker("show");
	}
}*/

// air datepicker
function datepicker() {
	$(".datepicker-here").datepicker({
		autoClose:true,
	});

	/*var dp1 = $("#dateWork1").datepicker({
		autoClose:true,
	}).data('datepicker');
	
	var dp2 = $("#dateWork2").datepicker({
		autoClose:true,
	}).data('datepicker');
	
	dp1.selectDate(new Date());
	dp2.selectDate(new Date());*/
}

// air datepicker content
function datepickerContent() {
	var eventDates = [10,20],
    $picker = $('#custom-cells'),
    $content = $('#custom-cells'),
    sentences = ['88,342,123 (총)<br>12.231.231 (카)<br>42.123.122 (현)<br>123,423 (쿠)<br>98,522 (포)','88,342,123 (총)<br>12.231.231 (카)<br>42.123.122 (현)<br>123,423 (쿠)<br>98,522 (포)']
	$picker.datepicker({
		onRenderCell: function (date, cellType) {
		    var currentDate = date.getDate();
		    var content = ''
	    	if (date && eventDates.indexOf(date.getDate()) != -1) {
	    		content = sentences[Math.floor(Math.random() * eventDates.length)];
	    	}
		    if (cellType == 'day' && eventDates.indexOf(currentDate) != -1) {
		        return {
		            html: currentDate + '<span class="dp-note">' + content + '</span>'
		        }
		    }
		    $('.dp-note', $content).html(content);
		},
	})
	var currentDate = new Date();
}


//scroll
function lnbScroll(){
	$(".lnbWrap").mCustomScrollbar({
	    theme:"dark"
	});
}
function selectScroll(){
	$(".selectWrap").mCustomScrollbar({
	    theme:"minimal-dark"
	});
}
function yScroll(){
	$(".scroll , .yscroll , .ascroll").mCustomScrollbar({
	    theme:"dark-thick"
	});
}

function selectTab(){
	$(document).on("change",".selectTab",function(e){
		var index = $(this).children("option:selected").index();
		console.log(index);
		if(index == 3){
			$(this).next().addClass("on");
		}else{
			$(this).next().removeClass("on");
		}
	});
}

function rewardTab(){
	$(document).on("change",".rewardTab",function(e){
		var index = $(this).children("option:selected").index()+1;
		//console.log(index);
		$(".reward").removeClass("on");
		$(".reward"+index).addClass("on");
	});
}

function pTab(){
	$(document).on("click",".pTab label",function(e){
		var index = $(this).index();
		$(".productTabWrap > .productCon").removeClass("on");
		if(index < 0){
			$(".productTabWrap > .productCon").eq(index).addClass("on");
		}else{
			$(".productTabWrap > .productCon").eq(index).addClass("on");
		}
	});
}

function fileBox(){
	$( ".fileBox" ).sortable({
	      revert: true
	});
}

jQuery(document).ready(function(e) {
	
	tableDnd();
	lnbScroll();
	selectScroll();
	yScroll();
	
	// tbody scroll
	$(".tblScroll .tbl:nth-child(2)").on('scroll', function(e){
		$(this).prev(".tbl").css('left', -$(this).scrollLeft() +'px');
		$(this).next(".tbl").css('left', -$(this).scrollLeft() +'px');
	});
	
	$(window).scroll(function() {
		if ($(document).scrollTop() > 1200) {
			$(".fixedSet").css("padding-bottom","60px");
			$(".btnFixed").addClass("on");
			$(".btnFixed").css("width",$(".btnFixed").parent().width());
			$(".btnSetUpDown").addClass("on");
		} else {
			$(".fixedSet").css("padding-bottom","0");
			$(".btnFixed").removeClass("on");
			$(".btnSetUpDown").removeClass("on");
		}
	});
	
	
	// lnb
	$(document).on("click",".lnbToggle",function(e){
		var canvas = $(".canvasWrap");
		if($(canvas).hasClass('adhesion')){
			$(".canvasWrap").removeClass("adhesion");
			
			if($(this).parent().hasClass("on")){
				
			}else{
				$(this).parent().find("ul").stop().slideDown();
				$(this).parent().addClass("on");
			}
		}else{
			if($(this).parent().hasClass("on")){
				$(this).parent().find("ul").stop().slideUp();
				$(this).parent().removeClass("on");
			}else{
				$(this).parent().find("ul").stop().slideDown();
				$(this).parent().addClass("on");
			}
		}
	});
	
	// lnb size
	$(document).on("click",".menuToggle",function(e){
		$(".depth01.active").siblings("li").find("ul").css("display","none");
		if($(".canvasWrap").hasClass('adhesion')){
			$(".canvasWrap").removeClass("adhesion");
		}else{
			$(".canvasWrap").addClass("adhesion");
			$(".depth01.active").addClass("on").siblings("li").removeClass("on");
			$(".depth01.active").find("ul").stop().slideDown();
		}
	});
	
	// tab
	$(document).on("click",".tabWrap .item",function(e){
		e.preventDefault();
		$(this).addClass("on").siblings("li").removeClass("on");
	});
	
	// tab print
	$(document).on("click",".printerWrite .tab li",function(e){
		e.preventDefault();
		$(this).addClass("current").siblings("li").removeClass("current");
		var index = $(this).index();
		$(".printConWrap .printCon").eq(index).addClass("on").siblings(".printCon").removeClass("on");
	});
	
	// inputFile
	$(document).on("click",".fileLabel span",function(e){
		$(this).prev().click();
	});
	$(document).on("change",".inputFile", function() {
	  $(this).parent().prev().val($(this).val());
	});

	// inputFile del
	$(document).on("click",".fileDel",function(e){
		$(this).parent().find("img").remove();
		$(this).prev().find("input:not('[name=fileMappingId],[name=contentsListId]')").val("");
	});
	
	// table checkbox
	$(document).on("change", ".inputChoice", function() {
		if($(this).is(":checked")){
			$(this).parent().parent().addClass("on");
		}else{
			$(this).parent().parent().removeClass("on");
		}
	});
	
	// table drag add
	$(document).on("click", ".tblDragAdd", function() {
		var tblDragAdd = "<tr class='partition'><td colspan='5' class='dragHandle'><button type='button' class='del'></button></td></tr>"; 
		$(this).closest('.exchangeSet').find(".tblDrag").append(tblDragAdd);
		$(".partition + .partition").remove();
		tableDnd();
	});
	
	// table del
	$(document).on("click", ".tblDelSet .btn", function() {
		$(this).closest(".tbl").remove();
	});
	
	// toggle
	$(document).on("click",".toggleBox dt strong",function(e){
		e.preventDefault();
		$(this).parent().parent().toggleClass("active");
	});
	$(document).on("click",".toggleBox dt button",function(e){
		e.preventDefault();
		if($(this).parent().parent().hasClass("on")){
			$(this).parent().parent().removeClass("on");
			$(this).parent().next().slideUp();
		}else{
			$(this).parent().parent().addClass("on");
			$(this).parent().next().slideDown();
		}
	});
	
	// boxToggle
	$(document).on("click",".boxToggle",function(e){
		if($(this).closest(".h2Tit").hasClass("tblOff")){
			$(this).closest(".h2Tit").next(".tbl").stop().slideDown();
			$(this).closest(".h2Tit").removeClass("tblOff");
			$(this).find("span").text("닫기");
		}else{
			$(this).closest(".h2Tit").next().stop().slideUp();
			$(this).closest(".h2Tit").addClass("tblOff");
			$(this).find("span").text("열기");
		}
	});
	
	$(document).on("click","th .sort",function(e){
		$(this).toggleClass("on");
	});
	
	// productTab
	$(document).on("click",".productTab label",function(e){
		var index = $(this).index()-1;
		$(".productTabWrap > .productCon").removeClass("on");
		if(index < 0){
			index = 0;
			$(".productTabWrap > .productCon").eq(index).addClass("on");
		}else{
			$(".productTabWrap > .productCon").eq(index).addClass("on");
		}
		colorPicker();
	});
	
	// workTab
	$(document).on("click",".workTab label",function(e){
		var index = $(this).index();
		$(".workTabWrap .workCon").removeClass("on");
		$(".workTabWrap .workCon").eq(index).addClass("on");
	});
	
	// dnd del
	$(document).on("click",".partition .del",function(e){
		$(this).parent().parent().remove();
	});
	
	// Popup Event
	$(document).on("click",".lpOpen", function(e){
		e.preventDefault();
		$(this).addClass("lpFocus");
	});	
	$(document).on("click",".btnLpClose",function(e){
		if($(this).closest(".lpFooter").length > 0 && $(this).closest(".lpModalWrap2").length > 0){
			$(".lpPopup").stop().fadeOut("fast",function(){
				$(".lpPopup").removeClass("on");
				$(".lpFocus").focus();
				$(".lpFocus").removeClass(".lpFocus");
				$("html").css("overflow","");
				$("body").removeClass("lpActive");
			});	
		}else{
			$(this).closest(".lpPopup").stop().fadeOut("fast",function(){
				if($(this).closest(".lpActive").length > 0 && $(this).closest(".lpModalWrap2").length > 0){
					$(this).closest(".lpPopup").removeClass("on");
				}else{
					$(this).closest(".lpPopup").removeClass("on");
					$(".lpFocus").focus();
					$(".lpFocus").removeClass(".lpFocus");
					$("html").css("overflow","");
					$("body").removeClass("lpActive");
				}	
			});
		}
	});
	
	// 기기별 배포 현황 체크시
	$(document).on("click",".checkOff label",function(e){
		if($(this).find("input").is(":checked")){
			$(".checkCon").removeClass("off");
		}else{
			$(".checkCon").addClass("off");
		};
	});
	
	// 검색, 본사, 가맹점 선택
	$(document).on("click , focus",".selectable",function(e){
		$(".selectWrap").removeClass("on");
		$(this).closest(".office").find(".selectWrap").addClass("on");
	});
	
	$(document).on("click , focus",".branchSearch .office:eq(0) input",function(e){
		$(".selectWrap").removeClass("on");
	});
	
	$(document).on("click",".office .searchBtn",function(e){
		$(".selectWrap").removeClass("on");
		$(this).closest(".office").find(".selectWrap").addClass("on");
	});
	
	$(document).on("click",".ulSelect li a",function(e){
		$(this).addClass("on").parent().siblings("li").find("a").removeClass("on");
		//$(this).closest(".office").find("input").val($(this).text());
	});
	
	$(document).on("click","body",function(e){
	    if (!$(e.target).parent().hasClass("office")) {
	    	$(".selectWrap").removeClass("on");
	    }
	});
	
	
	// colorPicker close
	$(document).on("click",".color-picker-sv",function(e){
	    $(".color-picker").remove();
	});
});
