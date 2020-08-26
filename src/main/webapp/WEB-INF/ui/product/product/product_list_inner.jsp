<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="kjh.common.dto.Token,kjh.common.dto.Constant,kjh.common.util.StringUtil "
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%


%>

<div class="btnSet">
    <div class="rightSet">
        <button type="button" class="btn mid btnGray" id="resetBtn"><i class="icoReset"></i><span>검색 초기화</span></button>
    </div>
</div>

<div class="textBtnSet">
    <div class="leftSet">
        <p>총 ${data.cnt }개</p>
    </div>
    <div class="rightSet">
        <button type="button" class="btn mid btnGray" id="productTemplete" ><span>엑셀 템플릿</span></button>
        <button type="button" id="excelUploadBtn" class="btn mid btnGray"><span>상품일괄 엑셀업로드</span></button>
        <button type="button" id="excelDownBtn" name="excelDownBtn" class="btn mid btnGray"><span>엑셀다운로드</span></button>
             
        <select title="select" class="ListChkSelection selectBasic" id="listPerCnt" onChange="saveListCntSetting(this.value);">
            <option value="10">10개씩 보기</option>
            <option value="20">20개씩 보기</option>    
            <option value="30">30개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
        </select>
    </div>
</div>



<div class="tbl rowType pdtDiv xscroll">
    <c:choose>
        <c:when test="${auth ne 'FRN' && auth ne 'DLR'  && empty franchiseId }">
            <table>
                <colgroup>
                    <col style="width:50px">
                    <col style="width:250px">
                    <col style="width:250px">
                    <col style="width:80px">
                    <col style="width:80px">
                    <col style="width:80px">
                    <col style="width:80px">
                    <col style="width:80px">
                    <col style="width:180px">
                    <col style="width:80px">
                    <col style="width:120px">
                    <col style="width:130px">
                    <col style="width:120px">
                    <col style="width:130px">
                    <col style="width:100px">
                    <col style="width:100px">
                    <col style="width:100px">
                    <col style="width:80px">
                    <col style="width:180px">
                    <col style="width:180px">
                    <col style="width:180px">
                    <col style="width:150px">
                    <col style="width:150px">
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">상품 관리명</th>
                        <th scope="col">상품 노출명</th>
                        <th scope="col">가맹점 수</th>
                        <th scope="col">구분</th>
                        <th scope="col">상태</th>
                        <th scope="col">품절</th>
                        <th scope="col">화면</th>    
                        <th scope="col">상품ID</th>
                        <th scope="col">재고</th>
                        <th scope="col">원가</th>
                        <th scope="col">매장가 사용여부</th>
                        <th scope="col">매장가</th>
                        <th scope="col">포장가 사용여부</th>
                        <th scope="col">포장가</th>
                        <th scope="col">기본</th>
                        <th scope="col">최대수량</th>
                        <th scope="col">세금</th>
                        <th scope="col">상품코드</th>
                        <th scope="col">첨가물코드</th>
                        <th scope="col">기타외부코드</th>
                        <th scope="col">최종 수정일</th>
                        <th scope="col">최종 수정자</th>
                    </tr>
                </thead>
                <tbody id="productTableBody">
                    <c:choose>
                        <c:when test="${not empty data.dataList }">
                            <c:forEach var="list" items="${data.dataList }">
                                <tr>
                                    <td class="align-c">
                                        <input type="checkbox" name="" id="" class="inputChoice productChoice">
                                        <input type="hidden" name="productId" class="productId" value="${list.productId }"/>
                                    </td>
                                    <td class="align-l" title="${list.productMgrName }"><span class="ellipsis">${list.productMgrName }</span></td>
                                    <td title="${list.productKr }">
                                        <a class="link targetUpdate" style="cursor:pointer;">
                                        <c:choose>
                                            <c:when test="${list.fileId ne null && list.fileId ne ''}">
                                                <img src="${list.realFilePath}" alt="img" class="tblImg" onerror="this.src='/static/img/common/img.jpg'">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="/static/img/common/img.jpg" alt="img" class="tblImg" onerror="this.style.display='none'">
                                            </c:otherwise>
                                        </c:choose>
                                            <span class="imgTxt"><span class="ellipsis">${list.productKr }</span></span>
                                        </a>
                                    </td>
                                    <td class="align-c">${list.cnt }</td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.productType eq 'P' }">상품</c:when>
                                            <c:when test="${list.productType eq 'O' }">옵션</c:when>
                                            <c:when test="${list.productType eq 'I' }">이미지</c:when>
                                            <c:when test="${list.productType eq 'T' }">텍스트</c:when>
                                        </c:choose>
                                    </td>
                                    
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.saleStatus eq 'INACTIVE' }">중지</c:when>
                                            <c:otherwise>정상</c:otherwise>                                            
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.soldoutYn eq 'TRUE' }">Y</c:when>
                                            <c:otherwise>N</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.visibility eq 'VISIBLE' }">노출</c:when>
                                            <c:when test="${list.visibility eq 'HIDDEN' }">미노출</c:when>
                                        </c:choose>
                                    </td>
                                    
                                    <td class="align-c">
                                        ${list.productId }
                                    </td>
                                    
                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.limitQty }" pattern="#,###"/>
                                    </td>
                                    
                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.primeCost }" pattern="#,###"/>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.isStore eq 'TRUE' }">Y</c:when>
                                            <c:otherwise>N</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.storePrice }" pattern="#,###"/>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.isPacking eq 'TRUE' }">Y</c:when>
                                            <c:otherwise>N</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-r"> 
                                        <fmt:formatNumber value="${list.packingPrice }" pattern="#,###"/>
                                    </td>
                                    <td class="align-c">
                                        <fmt:formatNumber value="${list.baseSaleQty }" pattern="#,###"/>
                                        
                                    </td>
                                    <td class="align-c">
                                        <fmt:formatNumber value="${list.maxSaleQty }" pattern="#,###"/>
                                        
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.useTax eq 'TRUE' }">과세</c:when>
                                            <c:when test="${list.useTax eq 'FALSE' }">면세</c:when>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${not empty list.extr2Cd }">${list.extr2Cd }</c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${not empty list.extr3Cd }">${list.extr3Cd }</c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${not empty list.extrCd }">${list.extrCd }</c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">${list.updatedAt }</td>
                                    <td class="align-c" title="${list.updatedBy }"><span class="ellipsis">${list.updatedBy}</span></td>
                                </tr>
                            </c:forEach>    
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="23" class="noData">등록된 데이터가 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </c:when>
        <c:when test="${auth eq 'FRN' || auth eq 'DLR' || not empty franchiseId }">
            <table>
                <colgroup>
                    <col style="width:50px">
                    <col style="width:250px">
                    <col style="width:300px">
                    <col style="width:250px"> <!-- 상품 노출명 -->
                    <col style="width:60px"> <!-- 20 -->
                    <col style="width:60px"> <!-- 20 -->
                    <col style="width:60px"> <!-- 20 -->
                    <col style="width:60px"> <!-- 20 -->
                    <col style="width:180px"> <!-- 상품ID -->
                    <col style="width:80px">  
                    <col style="width:100px">
                    <col style="width:130px">
                    <col style="width:100px">
                    <col style="width:130px">
                    <col style="width:100px">
                    <col style="width:60px">
                    <col style="width:60px">
                    <col style="width:60px">
                    <col style="width:180px">  <!-- 상품코드 -->
                    <col style="width:180px"> <!-- 첨가물코드 -->
                    <col style="width:180px"> <!-- 기타외부코드 -->
                    <col style="width:180px">
                    <col style="width:180px">
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">카테고리</th>
                        <th scope="col">상품 관리명</th>
                        <th scope="col">상품 노출명</th>
                        <th scope="col">구분</th>
                        <th scope="col">상태</th>
                        <th scope="col">품절</th>
                        <th scope="col">화면</th>
                        <th scope="col">상품ID</th>
                        <th scope="col">재고</th>
                        <th scope="col">원가 </th>
                        <th scope="col">매장가 사용여부</th>
                        <th scope="col">매장가</th>
                        <th scope="col">포장가 사용여부</th>
                        <th scope="col">포장가</th>
                        <th scope="col">기본</th>
                        <th scope="col">최대수량</th>
                        <th scope="col">세금</th>
                        <th scope="col">상품코드</th>
                        <th scope="col">첨가물코드</th>
                        <th scope="col">기타외부코드</th>
                        <th scope="col">최종 수정일</th>
                        <th scope="col">최종 수정자</th>
                    </tr>
                </thead>
                <tbody id="productTableBody">
                  <c:choose>
                        <c:when test="${not empty data.dataList }">
                            <c:forEach var="list" items="${data.dataList }">
                                <tr>
                                    <td class="align-c">
                                        <input type="checkbox" name="" id="" class="inputChoice productChoice">
                                        <input type="hidden" name="productId" class="productId" value="${list.productId }"/>
                                    </td>
                                    <td>
                                        <c:if test="${not empty list.categoryMgrName && list.categoryMgrName ne '' }">
                                        <ul class="optionList">
                                            <li>${fn:replace(list.categoryMgrName, ',', '</li><li>') }</li>
                                        </ul>
                                        </c:if>
                                    </td>
                                    <td title="${list.productMgrName }">
                                        <a class="link targetUpdate" style="cursor:pointer;">
                                        <c:choose>
                                            <c:when test="${list.realFilePath ne null}">
                                                <img src="${list.realFilePath}" alt="img" class="tblImg" onerror="this.style.display='none'">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="/static/img/common/img.jpg" alt="img" class="tblImg" onerror="this.style.display='none'">
                                            </c:otherwise>
                                        </c:choose>
                                            <span class="imgTxt"><span class="ellipsis">${list.productMgrName }</span></span>
                                        </a>
                                    </td>
                                    <td title="${list.productKr }"><span class="ellipsis">${list.productKr }</span></td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.productType eq 'P' }">상품</c:when>
                                            <c:when test="${list.productType eq 'O' }">옵션</c:when>
                                            <c:when test="${list.productType eq 'I' }">이미지</c:when>
                                            <c:when test="${list.productType eq 'T' }">텍스트</c:when>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.saleStatus eq 'INACTIVE' }">중지</c:when>
                                            <c:otherwise>정상</c:otherwise>                                            
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.soldoutYn eq 'TRUE' }">Y</c:when>
                                            <c:otherwise>N</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.visibility eq 'VISIBLE' }">노출</c:when>
                                            <c:when test="${list.visibility eq 'HIDDEN' }">미노출</c:when>
                                        </c:choose>
                                    </td>
                                    <td class="align-l">
                                        ${list.productId } 
                                    </td>

                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.limitQty }" pattern="#,###"/>
                                    </td>
                                    
                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.primeCost }" pattern="#,###"/>
                                    </td>
                                    
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.isStore eq 'TRUE' }">Y</c:when>
                                            <c:otherwise>N</c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.storePrice }" pattern="#,###"/>
                                    </td>
                                    
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.isPacking eq 'TRUE' }">Y</c:when>
                                            <c:otherwise>N</c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                                    <td class="align-r"> 
                                        <fmt:formatNumber value="${list.packingPrice }" pattern="#,###"/>
                                    </td>
                                    
                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.baseSaleQty }" pattern="#,###"/>
                                        
                                    </td>
                                    
                                    <td class="align-r">
                                        <fmt:formatNumber value="${list.maxSaleQty }" pattern="#,###"/>
                                    </td>
                                    
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${list.useTax eq 'TRUE' }">과세</c:when>
                                            <c:when test="${list.useTax eq 'FALSE'}">면세</c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>   
                                    </td>
                                    
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${not empty list.extr2Cd }">${list.extr2Cd }</c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${not empty list.extr3Cd }">${list.extr3Cd }</c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                                    <td class="align-c">
                                        <c:choose>
                                            <c:when test="${not empty list.extrCd }">${list.extrCd }</c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="align-c">${list.updatedAt}</td>
                                    <td class="align-c">${list.updatedBy}</td>
                                </tr>
                            </c:forEach>    
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="23" class="noData">등록된 데이터가 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </c:when>
    </c:choose>
</div>

<div class="btnSet">

</div>

${data.paging}