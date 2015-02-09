package com.io17.excel2json;

import java.util.List;

import org.json.JSONObject;

public class JSONAggregatorConfiguration {

	private String pivotField;
	private List<String> extractFields;
	private String groupField;
	private boolean retainFields;

	public JSONAggregatorConfiguration(String pivotField) {
		this.pivotField = pivotField;
	}

	public JSONAggregatorConfiguration(JSONObject aggregateJSON) {
		pivotField = aggregateJSON.optString("_pivotField");
		groupField = aggregateJSON.optString("_groupField");
		retainFields = aggregateJSON.optBoolean("_retainFields");
		extractFields = JSONUtils.toList(aggregateJSON.optJSONArray("_extractFields"));
	}

	public String getPivotField() {
		return pivotField;
	}

	public void setPivotField(String pivotField) {
		this.pivotField = pivotField;
	}

	public List<String> getExtractFields() {
		return extractFields;
	}

	public String getGroupField() {
		return groupField;
	}

	public boolean shouldRetainFields() {
		return retainFields;
	} 
	

	
}
