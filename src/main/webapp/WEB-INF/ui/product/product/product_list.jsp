<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="kjh.common.dto.Constant,
        kjh.common.util.StringUtil "
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%

%>
<!doctype html>
<html lang="ko">
<head>
<title>KIM Management System</title>
<jsp:include page="/include/commonMeta.jsp"></jsp:include>
<jsp:include page="/include/commonResource.jsp"></jsp:include>
<script src="/static/js/jquery.form.js"></script>
</head>
<body>
<div class="canvasWrap">
    <p style="height:50px">
    <%-- <jsp:include page="/../include/headSection.jsp"></jsp:include> --%>
    <div class="contentsWrap">
        <%-- <jsp:include page="/../include/lnbSection.jsp"></jsp:include> --%>
        <p style="height:50px">
        <div class="contentsSection">
            <div class="contentsTitle">
                <h1>상품 관리</h1>
                <p class="pageNav">홈 &gt; 상품관리 &gt; 상품 관리</p>
            </div>
            <div class="contents">
                <h2 class="h2Tit">검색 설정</h2>
                <div class="tbl colType">
                    <form method="post" id="searchFrm" onsubmit="return false;">
                        <table>
                            <colgroup>
                                <col style="width:15%">
                                <col style="width:35%">
                                <col style="width:15%">
                                <col style="width:35%">         
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row">카테고리</th>
                                    <td>
                                        <select title="select" name="categoryId" class="selectBasic size-100p">
                                            <option value="">전체</option>
                                            <c:forEach var="list" items="${data.categoryList }">
                                                <option value="${list.categoryId }" <c:if test="${list.categoryId eq search.categoryId }">selected="selected"</c:if>>${list.categoryMgrName }</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <th scope="row">상품 구분</th>
                                    <td>
                                        <span class="radioWrap" onclick="javascript:f_goPage(1);">
                                            <label for="" class="first">
                                                <input type="radio" name="productType" value="" id="option1" <c:if test="${empty search.productType}">checked="checked"</c:if>>
                                                <span>전체</span>
                                            </label>
                                            <label for="">
                                                <input type="radio" name="productType" value="P" id="option2" <c:if test="${search.productType eq 'P' }">checked="checked"</c:if>>
                                                <span>상품</span>
                                            </label>
                                            <label for="">
                                                <input type="radio" name="productType" value="O" id="option3" <c:if test="${search.productType eq 'O' }">checked="checked"</c:if>>
                                                <span>옵션</span>
                                            </label>
                                            <label for="">
                                                <input type="radio" name="productType" value="I" id="option4" <c:if test="${search.productType eq 'I' }">checked="checked"</c:if>>
                                                <span>이미지</span>
                                            </label>
                                            <label for="" class="last">
                                                <input type="radio" name="productType" value="T" id="option5" <c:if test="${search.productType eq 'T' }">checked="checked"</c:if>>
                                                <span>텍스트</span>
                                            </label>
                                    </span>
                                 </td>
                                </tr>
                                <tr>
                                    <th scope="row">판매상태</th>
                                    <td> 
                                        <span class="radioWrap" onclick="javascript:f_goPage(1);">
                                            <label for="" class="first">
                                                <input type="radio" name="saleStatus" value="" id="condition1" <c:if test="${empty search.saleStatus}">checked="checked"</c:if>>
                                                <span>전체</span>
                                            </label>
                                            <label for="">
                                                <input type="radio" name="saleStatus" value="ACTIVE" id="condition2" <c:if test="${search.saleStatus eq 'ACTIVE' }">checked="checked"</c:if>>
                                                <span>정상</span>
                                            </label>
                                            <label for="">
                                                <input type="radio" name="saleStatus" value="INACTIVE" id="condition3" <c:if test="${search.saleStatus eq 'INACTIVE' }">checked="checked"</c:if>>
                                                <span>중지</span>
                                            </label>
                                            <label for="" class="last">
                                                <input type="radio" name="saleStatus" value="SOLDOUT" id="condition4" <c:if test="${search.saleStatus eq 'SOLDOUT' }">checked="checked"</c:if>>
                                                <span>품절</span>
                                            </label>
                                        </span>
                                    </td>
                                    <th scope="row">화면</th>
                                    <td>
                                        <span class="radioWrap" onclick="javascript:f_goPage(1);">
                                            <label for="" class="first">
                                                <input type="radio" name="visibility" value="" id="screen1" checked="checked" <c:if test="${empty search.visibility}">checked="checked"</c:if>>
                                                <span>전체</span>
                                            </label>
                                            <label for="">
                                                <input type="radio" name="visibility" value="VISIBLE" id="screen2" <c:if test="${search.visibility eq 'VISIBLE' }">checked="checked"</c:if>>
                                                <span>노출</span>
                                            </label>
                                            <label for="" class="last">
                                                <input type="radio" name="visibility" value="HIDDEN" id="screen3" <c:if test="${search.visibility eq 'HIDDEN' }">checked="checked"</c:if>>
                                                <span>미노출</span>
                                            </label>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">검색어</th>
                                    <td colspan="3">
                                        <select title="select" class="selectBasic" name="keywordType">
                                            <option value="productName"  <c:if test="${search.keywordType eq 'productName' }">selected="selected"</c:if>>상품명</option>
                                            <option value="productTags"  <c:if test="${search.keywordType eq 'productTags' }">selected="selected"</c:if>>상품태그</option>
                                        </select>
                                        <input type="text" name="keyword" id="" class="inputBasic size-60p" value="${search.keyword }">
                                        <button type="button" class="btn mid btnGray" id="searchBtn"><span>검색</span></button>
                                   </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <br>
                <div id="changeView">
                <jsp:include page="./product_list_inner.jsp"></jsp:include>
                </div>
                <form id="excelFrm" onsubmit="return false;" method="POST" enctype="multipart/form-data" style="display:none">
                        <input type="hidden" readonly="readonly" name="headOfficeId">
                        <input type="hidden" readonly="readonly" name="franchiseId">
                        <input type="hidden" readonly="readonly" name="fileId">
                        <input type="text"   readonly="readonly" class="inputBasic size-400 fileChk">
                        <label for="" class="fileLabel">
                            <input type="file" name="uploadFile" class="inputFile"  id="oneFile">
                            <span>파일첨부</span>
                        </label>                
                </form>
            </div>
        </div><!-- //contentsSection  -->
    </div><!-- //contentsWrap  -->
</div><!-- //canvasWrap  -->
<%-- <jsp:include page="../../include/choice_popup.jsp"></jsp:include> --%>
<div id="appendDivPopup">
    <%-- <jsp:include page="./product_popup.jsp"></jsp:include> --%>
</div>

<iframe id="hidden" id="iFrm">

</iframe>
<script>


    var mode = "test";

   $(document).on('click','#excelUploadBtn',function(){
      mode = "ori";
      if(headerHeadOfficeIdChk() == false){
          return false;
      }
      <c:if test="${auth eq 'DLR'}">
      if(headerFranchiseChk() == false){
          return false;       
      }
      </c:if>
      $("input[name=uploadFile]").click();
   });
      
   $(document).on('change',"input[name=uploadFile]",function(){
            

            $("input[name=headOfficeId]").val(sessionStorage.headerHeadOfficeId);
            $("input[name=franchiseId]").val(sessionStorage.headerFranchiseId);

            if($("input[name=uploadFile]").val() != null){
               if(mode == "test"){
                   fileUploadStep1();
                   return false;
               }else{
                    $("#excelFrm").ajaxSubmit({
                          type:"post",
                          url:"/asp/pd/pdmi090",
                          data:$("#excelFrm").serialize(),
                          dataType:"json",
                          success:function(responseData){
                              if(responseData.resultJson.errorMsg != ""){
                                  $("#messageOk").find('.lpContent').html("<p class='lpTxt' style='text-align: center;font-size:20px;'>"+responseData.resultJson.errorMsg+"</p>");    
                                  lpOpen('.messageOk');
                              }else{
                                  $("#messageOk").find('.lpContent').html("<p class='lpTxt' style='text-align: center;font-size:20px;'>저장되었습니다.</p>");    
                                  $("input[name=uploadFile]").val(null); 
                                  $("input[name=fileId]").val(responseData.resultJson.fileId);
                                  
                              }
                              $('.messageOk').find('.btn').on('click', function(){
                                  movePage('/asp/pd/pdms010');
                              });
                          },
                          error: function (jqXHR, exception) {
                              $("#messageOk").find('.lpContent').html("<p class='lpTxt' style='text-align: center;font-size:20px;'>실행 도중 오류가 발생했습니다.</p>");    
                              lpOpen('.messageOk');
                          }
                      });
               }
            }
   });
   
   $(document).on('click','#test',function(){
             mode = "test";
             
          if(headerHeadOfficeIdChk() == false){
              return false;
          }
          <c:if test="${auth eq 'DLR'}">
          if(headerFranchiseChk() == false){
              return false;       
          }
          </c:if>
          $("input[name=uploadFile]").click();
    });

   /****
    * 엑셀 파일 업로드
    **/
   function fileUploadStep1(){
           $("#excelFrm").ajaxSubmit({
            type:"post",
            url:"/asp/pd/pdmi40",
            data:$("#excelFrm").serialize(),
            dataType:"json",
            success:function(responseData){
                if(responseData.resultJson.errorMsg != ""){
                    $("#messageOk").find('.lpContent').html("<p class='lpTxt' style='text-align: center;font-size:20px;'>"+responseData.resultJson.errorMsg+"</p>");    
                    lpOpen('.messageOk');
                }else{
                    fileUploadStep3(responseData.resultJson.fileId,responseData.resultJson.partitionKey);
                }

            },
            error: function (jqXHR, exception) {
                $("#messageOk").find('.lpContent').html("<p class='lpTxt'>실행 도중 오류가 발생했습니다.</p>");    
                lpOpen('.messageOk');
            }
        });
   }

    var countViewYn = "Y";//count 표시 창을 닫았는지 체크를 위함
    
    $(document).on('click', '.messageOk .btn', function(){
    	countViewYn = "N";
    });
    
    $(document).on('click', '.btn .btnLpClose', function(){
    	countViewYn = "N";
    });

    /**
     * 상품 엑셀 진핸현황 조회
    **/
    function fileUploadStep3(fileId,partitionKey){
    	
        loadingbarYN = "N";
        $('body').fadeTo( "slow", 1 ).find('#loading').remove();
        var obj = new Object();
    	
       if(countViewYn == "N"){
           $("#messageOk").find('.lpContent').html("<p class='lpTxt'>결과 조회 창을 닫았습니다.</p> <p class='lpTxt'>이후 작업을 확인하시려면 [엑셀 등록 현황]를 이용해 주세요</p>");    
           lpOpen('.messageOk');
           $(document).on('click', '.messageOk .btn', function(){
               var id = window.location.pathname;
               movePage(id);
           });
            
            $(document).on('click', '.btn .btnLpClose', function(){
                   var id = window.location.pathname;
                   movePage(id);
            });
           return false;
       }
       
       var obj = new Object();
       
       loadingbarYN = "N";
        
        if(sessionStorage.headerHeadOfficeId != null) {
           obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;    
       }else{
           obj["headOfficeId"] = "";
       }
        
        if(sessionStorage.headerFranchiseId != null) {
           obj["franchiseId"] = sessionStorage.headerFranchiseId;//상단 네비 선택 복사 줄 (대상)
       }else{
           obj["franchiseId"] = "";
       }
       
        
       obj["partitionKey"] = partitionKey;
       obj["jobId"]        = fileId;

       obj = JSON.stringify(obj);
       
       var url = "/common/tr020";
       $.ajax({    
            url     : url,
            type : "POST",    
            contentType : "application/json",
            dataType : "json",
            data    : obj,
            async : true,
            success : function(data) {

                var results = data.resultJson.returnjson.result;

				if(results != null ){ 
                	  var rate = 0;
                	  if(results.progressRate == ""){
                		  rate = 0;
                	  }else{
                		  rate = results.progressRate;
                	  }
                	  
	                  // Timer codes
	                  if(results.progressRate != "100" &&
	                     results.runningStatus != "end" &&
	                     results.resultStatus  != "fail"
	                  ){

	                	  $("#messageOk").find('.lpContent').html("<p class='lpTxt' style='text-align: center;font-size:20px;'>"+rate+" %</p> <p class='lpTxt' style='text-align: center;'>"+results.progressMsg+" 진행중</p>");    

	                      setTimeout(function() {
	                    	  fileUploadStep3(fileId,partitionKey);//진행 건수 표시
	                      }, 3000);
	                  }else{
	                	  if(results.resultStatus == "succ"){
	                	      $("#messageOk").find('.lpContent').html("<p class='lpTxt' style='text-align: center;font-size:20px;'>"+rate+"%</p> <p class='lpTxt' style='text-align: center;'>"+results.progressMsg+" 완료되었습니다.</p>");
	                	  }else{
	                		  $("#messageOk").find('.lpContent').html("<p class='lpTxt' style='text-align: center;font-size:20px;'>"+rate+"%</p> <p class='lpTxt' style='text-align: center;'>"+results.progressMsg+" 실행 도중 오류가 발생했습니다.</p>");
	                	  }
	                	  
		                   $(document).on('click', '.messageOk .btn', function(){
		                      var id = window.location.pathname;
		                      movePage(id);
		                  });
		                   
		                   $(document).on('click', '.btn .btnLpClose', function(){
			                      var id = window.location.pathname;
			                      movePage(id);
		                   });
	                  }
	                  lpOpen('.messageOk');
	              }else{
	                  $("#messageOk").find('.lpContent').html("<p class='lpTxt'>결과 조회를 실패했습니다. [엑셀 등록 현황]에서 진행결과를 확인하세요</p>");    
	                  lpOpen('.messageOk');

	                   $(document).on('click', '.messageOk .btn', function(){
	                      var id = window.location.pathname;
	                      movePage(id);
	                  });
	                   
	                   $(document).on('click', '.btn .btnLpClose', function(){
		                      var id = window.location.pathname;
		                      movePage(id);
	                   });
	              }
                    
            },           
            error : function(xhr, status, error) {
                $("#messageOk").find('.lpContent').html("<p class='lpTxt'>실행도중 오류가 발생했습니다. [엑셀 등록 현황]에서 진행결과를 확인하세요</p>");    
                lpOpen('.messageOk');

                 $(document).on('click', '.messageOk .btn', function(){
                    var id = window.location.pathname;
                    movePage(id);
                });
                 
                 $(document).on('click', '.btn .btnLpClose', function(){
                    var id = window.location.pathname;
                    movePage(id);
                 });
            },
            complete : function() {
                loadingbarYN = "Y";
            }
       });    
    }

    $(function(){
       f_goPage("${search.currentPageNo}");
       
         //툴팁 처리         
       $(document).find('.optionList li').each(function(){
           $(this).attr("title", $(this).text());
       });
         
    })

    var cnt = 0;
    //검색 초기화
    $(document).on('click', '#resetBtn', function(){
        var $targetFrm = $(document).find('#searchFrm');
        
        $targetFrm.find('input[type="text"]').val('');
        $targetFrm.find('select').each(function(){
            $(this).find('option:eq(0)').prop("selected", true);
        });
        $targetFrm.find('.radioWrap').each(function(){
            $(this).find('input[type="radio"]:eq(0)').prop("checked", true);
        });
        $targetFrm.find('input[name=keyword]').val('');
        
        f_goPage(1);
    });
    
    $(document).on('click', '.targetUpdate', function(){
        var target = $(this).closest('tr').find('.productId').val();
        movePage('/asp/pd/pdms050', target);
    });
   
    //엑셀 템플릿 다운로드
    $(document).on('click', '#productTemplete', function(){
        var $form = $('<form></form>');
        $form.attr('action', '/asp/pd/pdms100');
        $form.attr('id', 'productTempleteFrm');
        $form.attr('method', 'post');
        $form.attr('target', 'iFrm');
        $form.appendTo('body');
        $form.submit();
        $(document).find('#productTempleteFrm').remove();
    });

    $(document).on('click', '#saveBtn', function(){
       if(headerHeadOfficeIdChk() == false){
           return false;
       }
       <c:if test="${auth eq 'DLR'}">
       if(headerFranchiseChk() == false){
           return false;       
       }
       </c:if>
        movePage('/asp/pd/pdmm020');
    });
    
    $(document).on('click', '#excelDownBtn', function(){
        if(headerHeadOfficeIdChk() == false){
            return false;
        }
        <c:if test="${auth eq 'DLR'}">
        if(headerFranchiseChk() == false){
            return false;       
        }
        </c:if>
        f_excelDown();
    });
    
    function movePage(url, target) {
        if(headerHeadOfficeIdChk() == false){
            return false;
        }
        <c:if test="${auth eq 'DLR'}">
        if(headerFranchiseChk() == false){
            return false;       
        }
        </c:if>
        var pageMoveTxt = '<form id="frm" method="post" action="'+url+'">';
        
        pageMoveTxt += '        <input type="hidden" name="categoryId" value="'   +$('#searchFrm select[name="categoryId"]').val()+'"/>';
        pageMoveTxt += '        <input type="hidden" name="productType" value="'  +$('#searchFrm input[name="productType"]:checked').val()+'"/>';
        pageMoveTxt += '        <input type="hidden" name="saleStatus" value="'   +$('#searchFrm input[name="saleStatus"]:checked').val()+'"/>';
        pageMoveTxt += '        <input type="hidden" name="visibility" value="'   +$('#searchFrm input[name="visibility"]:checked').val()+'"/>';
        pageMoveTxt += '        <input type="hidden" name="keywordType" value="'  +$('#searchFrm select[name="keywordType"]').val()+'"/>';
        pageMoveTxt += '        <input type="hidden" name="keyword" value="'      +$('#searchFrm input[name="keyword"]').val()+'"/>';
        pageMoveTxt += '        <input type="hidden" name="currentPageNo" value="'+$('.pagination a.on').text()+'"/>';
        
        if(target != null) {
            pageMoveTxt += '        <input type="hidden" name="productId" value="'+target+'"/>';
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

    
    
    //페이징 및 검색
    function f_goPage(currentPageNo){
        if(headerHeadOfficeIdChk() == false){
            return false;
        }
        <c:if test="${auth eq 'DLR'}">
        if(headerFranchiseChk() == false){
            return false;       
        }
        </c:if>
        var obj = new Object();
        
        if(sessionStorage.headerHeadOfficeId != null) {
            obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;    
        }

        if(sessionStorage.headerFranchiseId != null) {
            obj["franchiseId"] = sessionStorage.headerFranchiseId;    
        }
        
        if(currentPageNo === undefined){
            currentPageNo = "1";
        }
        
        obj["currentPageNo"] = currentPageNo+""; //현재페이지
        obj["countPerList"] = $("#listPerCnt").val(); //화면에 뿌려질 리스트 갯수
        obj["countPerPage"] = "10";//페이지 건수
        
        //검색 조건 start
        if($('select[name="categoryId"]').val() != '') {
            obj["categoryId"] = $('select[name="categoryId"]').val();
        }
        
        if($('input[name="productType"]:checked').val() != '') {
            obj["productType"] = $('input[name="productType"]:checked').val();
        }
        
        if($('input[name="saleStatus"]:checked').val() != '') {
            obj["saleStatus"] = $('input[name="saleStatus"]:checked').val();
        }
        
        if($('input[name="visibility"]:checked').val() != '') {
            obj["visibility"] = $('input[name="visibility"]:checked').val();
        }
        
        if($('input[name="keyword"]').val() != '') {
            obj["keywordType"] = $('select[name="keywordType"]').val();
            obj["keyword"] = $('input[name="keyword"]').val();
        }
        
        
        obj = JSON.stringify(obj);

        $.ajax({    
            url     : "/asp/pd/pdis040",
            type : "POST",    
            contentType : "application/json",
            dataType : "html",
            data    : obj,
            async : true,
            success : function(data) {
                obj = JSON.parse(obj);
                
                $("#changeView").html(data);
                $(document).find('#listPerCnt option[value="'+getCookie('ListChkSelection')+'"]').prop("selected", true);
                cnt = 0;
            },           
            error : function(xhr, status, error) {
                $(document).find('.lpPopup').each(function(){
                    var id = $(this).attr("id");
                    if($(this).hasClass('on')){
                        lpClose('.'+id);
                    }
                });
                $(document).find('.messageOk').find('.lpTxt').text("실행도중 오류가 발생했습니다.");
                lpOpen('.messageOk');
                $(document).on('click', '.messageOk .btn', function(){
                    var id = window.location.pathname;
                    movePage(id);
                });
                return false;
            }
         });
    }
    
    //n개씩 보기
    $(document).on('change', '#listPerCnt', function(){
        f_goPage(1);
    });
    
    $(document).on('click', '#searchBtn', function(){
        f_goPage(1);
    });
    
    //카테고리 선택시 검색
    $(document).on('change', 'select[name=categoryId]', function(){
        f_goPage(1);
    });
    
    $(document).on('keyup', 'input[name="keyword"]', function(e){
        if(e.keyCode == 13){
            f_goPage(1);    
        }
    });
    
    $(document).find('.headOfficeSelect').on('click', 'li', function(){
        f_goPage(1);
    });

    $(document).find('.franchiseSelect').on('click', 'li', function(){
        f_goPage(1);    
    });
    
    function f_excelDown(){
        
        var obj = new Object();
        
        if(sessionStorage.headerHeadOfficeId != null) {
            obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;    
        }

        if(sessionStorage.headerFranchiseId != null) {
            obj["franchiseId"] = sessionStorage.headerFranchiseId;  
        }
        
        //검색 조건 start
        if($('select[name="categoryId"]').val() != '') {
            obj["categoryId"] = $('select[name="categoryId"]').val();
        }
        
        if($('input[name="productType"]:checked').val() != '') {
            obj["productType"] = $('input[name="productType"]:checked').val();
        }
        
        if($('input[name="saleStatus"]:checked').val() != '') {
            obj["saleStatus"] = $('input[name="saleStatus"]:checked').val();
        }
        
        if($('input[name="visibility"]:checked').val() != '') {
            obj["visibility"] = $('input[name="visibility"]:checked').val();
        }
        
        if($('input[name="keyword"]').val() != '') {
            obj["keywordType"] = $('select[name="keywordType"]').val();
            obj["keyword"] = $('input[name="keyword"]').val();
        }
        
        var $form = $('<form></form>');
        $form.attr('action', '/asp/pd/pdms020');
        $form.attr('id', 'f_excelDownFrm');
        $form.attr('method', 'post');
        $form.attr('target', 'iFrm');
        $form.appendTo('body');
        
        $form.append($('<input type="hidden" value="'+sessionStorage.headerHeadOfficeId+'" name="headOfficeId">'));
        $form.append($('<input type="hidden" value="'+sessionStorage.headerFranchiseId+'" name="franchiseId">'));
        $form.append($('<input type="hidden" value="'+$('select[name="categoryId"]').val()+'" name="categoryId">'));
        $form.append($('<input type="hidden" value="'+$('select[name="productType"]').val()+'" name="productType">'));
        $form.append($('<input type="hidden" value="'+$('input[name="saleStatus"]:checked').val()+'" name="saleStatus">'));
        $form.append($('<input type="hidden" value="'+$('input[name="visibility"]:checked').val()+'" name="visibility">'));
        $form.append($('<input type="hidden" value="'+$('select[name="keywordType"]').val()+'" name="keywordType">'));
        $form.append($('<input type="hidden" value="'+$('input[name="keyword"]').val()+'" name="keyword">'));
        $form.submit();
        $(document).find('#f_excelDownFrm').remove();
    }
    
    /**********************************************************************************************************************
    * 상품 복사 관련 스크립트 [s]
    **********************************************************************************************************************/
    //상품은 단건만 가능하도록
    var copyTargetproductId = '';
    var copyTargetproductName = '';
    $(document).on('click', '.productChoice', function(){
        copyTargetproductId = '';
        copyTargetproductName = '';
        if($(this).prop("checked")) {
            cnt++;
            copyTargetproductId = $(this).siblings('.productId').val();
            copyTargetproductName = $(this).closest('tr').find('td:nth-child(3) .imgTxt span').text();
        }else {
            cnt--;
        }
        if(cnt > 1) {
            $("#messageOk").find('h2').text('상품 복사');
            $("#messageOk").find('.lpTxt').text('1개만 선택해주세요.');
            lpOpen('.messageOk');
            
            $(this).prop("checked", false);
            
            cnt = 1;
        }
        
    });
    
    var type = '';
   //상품 전체 복사 버튼을 눌렀을 경우 copyAllBtn
   $(document).on('click', '#copyAllBtn', function(){
       if(sessionStorage.headerFranchiseId == null) {
           $("#messageOk").find('.lpTxt').text('상단에서 가맹점을 먼저 선택해주세요.');
            $("#messageOk").find('h2').text('상품 복사');
            lpOpen('.messageOk');
       }else {
           type = 'copyAll';
           var obj = new Object();
           
           if(sessionStorage.headerHeadOfficeId != null) {
               obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;    
           }
    
           if(sessionStorage.headerFranchiseId != null) {
               obj["franchiseId"] = sessionStorage.headerFranchiseId;  
           }
           
           obj = JSON.stringify(obj);
    
           $.ajax({    
               url  : "/asp/pd/pdms060",
               type : "POST",  
               contentType : "application/json",
               dataType : "html",
               data    : obj,
               async : true,
               success : function(data) {
                   
                   $("#appendDivPopup").html(data);
                   
                   var allCopyInnerTxt = '<tr>'
                                                    + '    <td class="align-c">'
                                                    + '        <input type="radio" name="sendfranchiseId" value="'+sessionStorage.headerFranchiseId+'">'
                                                    + '    </td>'
                                                    + '    <td>'
                                                   + '        <label for="sendfranchiseId">'+sessionStorage.headerFranchiseName+'</label>';
                                                    + '    </td>'
                                                    + '</tr>';
                   $(document).find('.productAllCopy #allCopyChiFrn tbody').html(allCopyInnerTxt);
                   lpOpen('.productAllCopy');
                   
               },         
               error : function(xhr, status, error) {
                   $(document).find('.lpPopup').each(function(){
                       var id = $(this).attr("id");
                       if($(this).hasClass('on')){
                           lpClose('.'+id);
                       }
                   });
                   $(document).find('.messageOk').find('.lpTxt').text("실행도중 오류가 발생했습니다.");
                   lpOpen('.messageOk');
                   $(document).on('click', '.messageOk .btn', function(){
                       var id = window.location.pathname;
                       movePage(id);
                   });
               }
            });
       }
    });
    
    //상품 복사 버튼을 눌렀을 경우
    $(document).on('click', '#copyBtn', function(){
        type = 'copy';
        var obj = new Object();
        
        obj["productId"] = copyTargetproductId;
        
        if(sessionStorage.headerHeadOfficeId != null) {
            obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;    
        }

        if(sessionStorage.headerFranchiseId != null) {
            obj["franchiseId"] = sessionStorage.headerFranchiseId;  
        }
        
        obj = JSON.stringify(obj);
        
        if(copyTargetproductName == null ||
           copyTargetproductName == ""
        ){
            $(document).find('.messageOk').find('.lpTxt').text("상품을 선택후에 클릭하세요");
            lpOpen('.messageOk');
            return false;
        }

        $.ajax({    
            url     : "/asp/pd/pdms060",
            type : "POST",    
            contentType : "application/json",
            dataType : "html",
            data    : obj,
            async : true,
            success : function(data) {
                $("#appendDivPopup").html(data);
                lpOpen('.productCopy');
                $('.productCopy').find('.lpContent .lpTit1').text("상품 : "+copyTargetproductName);
                $(document).find('#productCopy').find('input[name="productId"]').val(copyTargetproductId);
                
            },           
            error : function(xhr, status, error) {
                $(document).find('.lpPopup').each(function(){
                    var id = $(this).attr("id");
                    if($(this).hasClass('on')){
                        lpClose('.'+id);
                    }
                });
                $(document).find('.messageOk').find('.lpTxt').text("실행도중 오류가 발생했습니다.");
                lpOpen('.messageOk');
                $(document).on('click', '.messageOk .btn', function(){
                    var id = window.location.pathname;
                    movePage(id);
                });
            }
         });
    });
    
    //(전체복사)가맹점 선택 없이 저장버튼을 눌렀을 경우
    function checkSelect2(){
        var ch = true;
        var txt = '';
        $(document).find('#allCopyChiFrn').find('input[type="radio"]').each(function(){
            if($(this).prop("checked")){
                ch = false;
                txt = '가맹점을 선택해주세요.';
            }
        });
        
        ch = true;
        $(document).find('#ptargetChoiceFrnList').find('input[type="radio"]').each(function(){
            if($(this).prop("checked")){
                ch = false;
                txt = '적용할 가맹점을 선택해주세요.';
            }
        });
        
        if(ch){
            $("#messageOk").find('.lpTxt').text(txt);
            $("#messageOk").find('h2').text('상품 복사');
            lpOpen('.messageOk');
        }else{
            lpOpen('.productCopyOk');
        }
    }
    
    //가맹점 선택 없이 저장버튼을 눌렀을 경우
    function checkSelect(){
        var ch = true;
        
        $(document).find('#ptargetFrnList').find('.targetFrn').each(function(){
            if($(this).prop("checked")){
                ch = false;
            }
        });
        
        if(ch){
            $("#messageOk").find('.lpTxt').text('가맹점을 선택해주세요.');
            $("#messageOk").find('h2').text('상품 복사');
            lpOpen('.messageOk');
        }else{
            lpOpen('.productCopyOk');
        }
    }
    
    //상품 복사 버튼 안에서 가맹점을 선택했을 경우 해당 가맹점의 카테고리 목록 가져오기
    $(document).on('click' , '.targetFrn', function(){
        var obj = new Object();
        
        obj["franchiseId"] = $(this).val();
        obj["productId"]   = $(document).find('#productCopy').find('input[name="productId"]').val();
        if(sessionStorage.headerHeadOfficeId != null) {
            obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;    
        }
        
        if(sessionStorage.headerFranchiseId != null) {
            obj["franchiseId2"] = sessionStorage.headerFranchiseId;  
        }
        
        obj = JSON.stringify(obj);

        $.ajax({    
            url     : "/asp/pd/pdms070",
            type : "POST",    
            contentType : "application/json",
            dataType : "html",
            data    : obj,
            async : true,
            success : function(data) {
                
                $(".popupCaDiv").html(data);
                
                
            },           
            error : function(xhr, status, error) {
                $(document).find('.lpPopup').each(function(){
                    var id = $(this).attr("id");
                    if($(this).hasClass('on')){
                        lpClose('.'+id);
                    }
                });
                $(document).find('.messageOk').find('.lpTxt').text("실행도중 오류가 발생했습니다.");
                lpOpen('.messageOk');
                $(document).on('click', '.messageOk .btn', function(){
                    var id = window.location.pathname;
                    movePage(id);
                });
            }
         });
    });
    
    $(document).on('click', '#copyOkBtn', function(){
        var obj = new Object();
        
        if(sessionStorage.headerHeadOfficeId != null) {
            obj["headOfficeId"] = sessionStorage.headerHeadOfficeId;    
        }
        
        if(sessionStorage.headerFranchiseId != null) {
            obj["franchiseId2"] = sessionStorage.headerFranchiseId;//상단 네비 선택 복사 줄 (대상)
        }
        
        if(type == 'copy') {
            obj["productId"] = $(document).find('#productCopy').find('input[name="productId"]').val();
            obj["franchiseId"] = $(document).find('.targetFrn:checked').val();//복사 받을 (대상)
            var categoryIdList = new Array();
            
            $(document).find('#targetCopyCategoryList').find('input[name="categoryId"]').each(function(){
                if($(this).prop("checked")) {
                    categoryIdList.push($(this).val());
                }
            });
            obj["categoryIdList"] = categoryIdList;
        }else {
            obj["franchiseId"] = $(document).find('#ptargetChoiceFrnList input[type="radio"]:checked').val();//복사 받을 (대상)
        }
        
        console.log(obj);
        
        obj = JSON.stringify(obj);

        var url = "";
        
        if(type == "copyAll"){
            url ="/asp/pd/pdmi070";
        }else{
            url ="/asp/pd/pdmi060";
        }
        //console.log("url="+url);
        $.ajax({    
            url     : url,
            type : "POST",    
            contentType : "application/json",
            dataType : "json",
            data    : obj,
            async : true,
            success : function(data) {
                lpClose('.productCopyOk');
                lpClose('.productCopy');
                lpClose('.productAllCopy');
                f_goPage(1);
            },           
            error : function(xhr, status, error) {
                $(document).find('.lpPopup').each(function(){
                    var id = $(this).attr("id");
                    if($(this).hasClass('on')){
                        lpClose('.'+id);
                    }
                });
                $(document).find('.messageOk').find('.lpTxt').text("실행도중 오류가 발생했습니다.");
                lpOpen('.messageOk');
                $(document).on('click', '.messageOk .btn', function(){
                    var id = window.location.pathname;
                    movePage(id);
                });
            }
         });
    });
    /**********************************************************************************************************************
    * 상품 복사 관련 스크립트 [e]
    **********************************************************************************************************************/
</script>
</body>
</html>