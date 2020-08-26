package kjh.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonDto {

	int limit;
	int offset;
	String pagination;
	int totalPageCount;
	int viewLastPage;
	
	String orderColumn;
	String orderMethod;
}
