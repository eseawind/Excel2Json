package com.io17.excel2json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExcelReaderConfiguration {

	Map<Integer, String> fields = new HashMap<Integer, String>();
	Map<String, Integer> invFields = new HashMap<String, Integer>();

	private boolean skipFirstRow = false;

	private List<JSONObject> multiRecordFields;

	private String recordSeparator;
	private JSONObject types = new JSONObject(); 
	
	public static final ExcelReaderConfiguration DEFAULT;
	static {
		DEFAULT = new ExcelReaderConfiguration();
		//TODO:
		
	}
	public ExcelReaderConfiguration(JSONObject excelJSON) {
		for(String key : excelJSON.keySet()){
			if(key.startsWith("_"))continue;
			Integer i = Integer.parseInt(key);
			String name = excelJSON.getString(key);
			fields.put(i, name);
			invFields.put(name, i);
		}
		skipFirstRow = excelJSON.optBoolean("_skipFirstRow", false);
		multiRecordFields = JSONUtils.toList(excelJSON.optJSONArray("_multiRecordFields"));
		recordSeparator = excelJSON.optString("_recordSeparator", null);
		types = excelJSON.optJSONObject("_types");
		if(types==null)types = new JSONObject();
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

	public boolean isMultirecordField(String key) {
		return multiRecordFields.contains(key);
	}

	public String getRecordsSeparator() {
		return recordSeparator;
	}

	public Integer getIndexForKey(String recordsSeparator) {
		return invFields.get(recordsSeparator);
	}

	public String getFieldType(String key) {
		return types.optString(key, null);
	}

}
