package kjh.common.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FileDto extends Model{
	
	private String file_id;
	private String upload_group;
	private String upload_group_sub;
	private String master_table;
	private String master_id;
	private String org_file_name;
	private String sys_file_name;
	private String file_path;
	private String file_size;
	private String file_ext;
	private String element_nm;
	
	// 로그
	public String toStringVo(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).concat("\nVO======================END");
	}
}
