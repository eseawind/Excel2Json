package com.io17.excel2json;

import org.json.JSONObject;

public class JSONAggregatorConfiguration {

	private String pivotField;

	public JSONAggregatorConfiguration(String pivotField) {
		this.pivotField = pivotField;
	}

	public JSONAggregatorConfiguration(JSONObject aggregateJSON) {
		pivotField = aggregateJSON.optString("_pivotField");
	}

	public String getPivotField() {
		return pivotField;
	}

	public void setPivotField(String pivotField) {
		this.pivotField = pivotField;
	} 
	

	
}
