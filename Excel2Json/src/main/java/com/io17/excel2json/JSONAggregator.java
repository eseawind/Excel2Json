package com.io17.excel2json;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONAggregator {


	private JSONAggregatorConfiguration configuration;

	public JSONAggregator(JSONAggregatorConfiguration config) {
		this.configuration = config;
	}

	public JSONObject process(JSONArray json) {
		List<JSONObject> list = toList(json);
		
		Map<String, List<JSONObject>> groups = list.stream().collect(Collectors.groupingBy(s -> s.getString(configuration.getPivotField())));
		return toJSON(groups);
	}

	private JSONObject toJSON(Map<String, List<JSONObject>> groups) {
		JSONObject jo = new JSONObject();
		for(String pivot : groups.keySet()){
			jo.put(pivot, new JSONArray(groups.get(pivot).toArray()));
		}
		return jo;
	}

	private List<JSONObject> toList(JSONArray json) {
		List<JSONObject> list = new LinkedList<JSONObject>();
		for(int i=0;i<json.length();i++){
			list.add(json.getJSONObject(i));
		}
		return list;
	}
	
	

}
