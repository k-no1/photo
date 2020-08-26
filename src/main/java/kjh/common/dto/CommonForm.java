package kjh.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonForm {
	String functionName;
	int currentPageNo;
	int countPerPage;
	int countPerList;
	int totalPageCount;
	int totalListCount;
	int viewLastPage;
	int limit;
	int offset;
}
