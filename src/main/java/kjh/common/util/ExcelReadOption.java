package kjh.common.util;

import java.util.ArrayList;
import java.util.List;

public class ExcelReadOption {

    /**
     * �뿊���뙆�씪�쓽 寃쎈줈
     */
    private String filePath;
    
    /**
     * 異붿텧�븷 而щ읆 紐�
     */
    private List<String> outputColumns;
    
    /**
     * 異붿텧�쓣 �떆�옉�븷 �뻾 踰덊샇
     */
    private int startRow;
    private int colLength;
    
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public List<String> getOutputColumns() {
        
        List<String> temp = new ArrayList<String>();
        temp.addAll(outputColumns);
        
        return temp;
    }
    public void setOutputColumns(List<String> outputColumns) {
        
        List<String> temp = new ArrayList<String>();
        temp.addAll(outputColumns);
        
        this.outputColumns = temp;
    }
    
    public void setOutputColumns(String ... outputColumns) {
        
        if(this.outputColumns == null) {
            this.outputColumns = new ArrayList<String>();
        }
        
        for(String ouputColumn : outputColumns) {
            this.outputColumns.add(ouputColumn);
        }
    }
    
    public int getStartRow() {
        return startRow;
    }
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
	public int getColLength() {
		return colLength;
	}
	public void setColLength(int colLength) {
		this.colLength = colLength;
	}
	
}
