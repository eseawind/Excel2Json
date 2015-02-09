package com.io17.excel2json;

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

	public JSONArray process(JSONArray json) {
		List<JSONObject> list = JSONUtils.toList(json);
		
		Map<String, List<JSONObject>> groups = list.stream().collect(Collectors.groupingBy(s -> s.getString(configuration.getPivotField())));
		return toJSON(groups);
	}

	private JSONArray toJSON(Map<String, List<JSONObject>> groups) {
		JSONArray ja = new JSONArray();
		for(String pivot : groups.keySet()){
			JSONObject jo = new JSONObject();			
			List<JSONObject> group = groups.get(pivot);
			List<String> extractFields = configuration.getExtractFields();
			for(String field : extractFields){
				jo.put(field, group.get(0).opt(field));
			}
			fixGroup(group);
			jo.put(configuration.getGroupField(), new JSONArray(group.toArray()));
			ja.put(jo);
		}
		return ja;
	}

	private JSONArray fixGroup(List<JSONObject> group) {
		JSONArray ja = new JSONArray();
		for(JSONObject jo : group){
			fixFields(group, jo);
			ja.put(jo);
		}
		return ja;
	}

	private void fixFields(List<JSONObject> group, JSONObject jo) {
		if(!configuration.shouldRetainFields()){
			for(String field : configuration.getExtractFields()){
				jo.remove(field);
			}
		}
	}

}
