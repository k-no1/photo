package kjh.common.dao;

import java.util.List;

import kjh.common.dto.CamelMap;


public interface FileDao{

	public List<CamelMap> getFileList(CamelMap model) throws Exception;
	public CamelMap getFile(CamelMap model) throws Exception;
	public int insertFile(CamelMap model) throws Exception;
	public int deleteFile(CamelMap model) throws Exception;
	public int fileMatchingUpdate(CamelMap model) throws Exception;

}
