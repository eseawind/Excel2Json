package com.io17.excel2json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ExcelReaderConfiguration {

	Map<Integer, String> fields = new HashMap<Integer, String>();

	private boolean skipFirstRow = false; 
	
	public static final ExcelReaderConfiguration DEFAULT;
	static {
		DEFAULT = new ExcelReaderConfiguration();
		//TODO:
		
	}
	public ExcelReaderConfiguration(JSONObject excelJSON) {
		for(String key : excelJSON.keySet()){
			if(key.startsWith("_"))continue;
			Integer i = Integer.parseInt(key);
			fields.put(i, excelJSON.getString(key));
		}
		skipFirstRow = excelJSON.optBoolean("_skipFirstRow", false);
	}
	
	public ExcelReaderConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public boolean skipFirstRow() {
		return skipFirstRow ;
	}
	public String getKey(int index) {
		return fields.get(index);
	}
	public boolean isIncluded(int index) {
		return fields.containsKey(index);
	}
	public void addField(int columnIndex, String columnName) {
		fields.put(columnIndex, columnName);
	}

}
