package kjh.common.util;

import kjh.common.dto.CommonDto;
import kjh.common.dto.CommonForm;

/**
 * ������ �׺���̼� ���� ������ ���� Ŭ����
 */
public class PagingUtil {
    
	public static CommonDto setPageUtil(CommonForm commonForm) {

		CommonDto commonDto = new CommonDto();

		String pagination = ""; // ����¡ ��� ��
		String functionName = commonForm.getFunctionName(); // ����¡ ����� ��û�ϴ� �ڹٽ�ũ��Ʈ �Լ���
		
		int currentPage       = commonForm.getCurrentPageNo(); // ���� ������ ��ȣ
		int countPerList      = commonForm.getCountPerList(); // �� ȭ�鿡 ��µ� �Խù� ��
		int countPerPage      = commonForm.getCountPerPage(); // �� ȭ�鿡 ��µ� ������ ��
		int totalListCount    = commonForm.getTotalListCount(); // �� �Խù� ��
		
		//������ �ʱⰪ ����
    	if(CommonUtil.isEmpty(currentPage)) {
    		currentPage = 1;
    	}
    	
    	//ȭ�� ����Ʈ ����
    	if(CommonUtil.isEmpty(countPerPage)) {
    		commonForm.setCountPerPage(10);
    	}
    	
    	//�۹�ȣ ǥ�� ����
    	if(CommonUtil.isEmpty(countPerList)) {
    		commonForm.setCountPerList(10);
    	}

		
		int totalPageCount    = totalListCount / countPerList; // �� ������ ��

		if (totalListCount % countPerList > 0) { // �� ���̼��� ���� �� int������ ����ϸ� �������� �ִ� ��� �Խù��� �����ϱ� ������ �� �������� ���� ����
			totalPageCount  = totalPageCount + 1;
		}

		int viewFirstPage = (((currentPage - 1) / countPerPage) * countPerPage) + 1; // �� ȭ�鿡 ù ������ ��ȣ
		int ViewLastPage  = viewFirstPage + countPerPage - 1; // �� ȭ�鿡 ������ ������ ��ȣ
		if (ViewLastPage > totalPageCount) { // ������ �������� ���� �� �������� ������ ū ���� �Խù��� �������� �ʱ� ������ ������ �������� ���� ����
			ViewLastPage  = totalPageCount;
		}

		int totalFirstPage = 1; // ��ü ������ �߿� ó�� ������
		int totalLastPage = totalPageCount; // ��ü ������ �߿� ������ ������
		int prePerPage = 0; // ���� ȭ�鿡 ù��° ��ȣ
		if (viewFirstPage - countPerPage > 0) {
			prePerPage = viewFirstPage - countPerPage;
		} else {
			prePerPage = totalFirstPage;
		}
		int nextPerPage = 0; // ���� ȭ�鿡 ù��° ��ȣ
		if (viewFirstPage + countPerPage < totalPageCount) {
			nextPerPage = viewFirstPage + countPerPage;
		} else {
			nextPerPage = totalPageCount;
		}

		// ������ ���̰��̼� ����
        pagination += "<div class='pagination'>";
        pagination += "<a href='javascript:" + functionName + "(\"" + totalFirstPage + "\");' class=\"btn btnPage first\"><i class=\"icoFirst\">ó��</i></a>";
        pagination += "<a href='javascript:" + functionName + "(" + prePerPage + ");' class=\"btn btnPage prev\"><i class=\"icoPrev\">����</i></a>";
        for (int pNum = viewFirstPage; pNum <= ViewLastPage; pNum++) {
            if (pNum == currentPage) {
                pagination += "<a href='javascript:" + functionName + "(\"" + pNum + "\");' class='btn btnNum on'>" + pNum + "</a>";
            } else {
                pagination += "<a href='javascript:" + functionName + "(\"" + pNum + "\");' class='btn btnNum'>" + pNum + "</a>";
            }
        }
        pagination += "<a href='javascript:" + functionName + "(" + nextPerPage + ");' class=\"btn btnPage next\"><i class=\"icoNext\">����</i></a>";
        pagination += "<a href='javascript:" + functionName + "(" + totalLastPage + ");' class=\"btn btnPage last\"><i class=\"icoLast\">������</i></a>";
        pagination += "</div>";
		////system.out.println("pagination="+pagination);
		int offset = ((currentPage - 1) * countPerList); // �� ȭ���� ǥ��Ǵ� �Խù��� ���� ��ȣ�� -1 (���� ������)
		// LIMIT�� ������ row�� ��, OFFSET�� �� ��° row���� ���������� ����
		commonDto.setLimit(countPerList);
		commonDto.setOffset(offset);
		commonDto.setPagination(pagination);
        commonDto.setTotalPageCount(totalPageCount);
		return commonDto;
	}
}
