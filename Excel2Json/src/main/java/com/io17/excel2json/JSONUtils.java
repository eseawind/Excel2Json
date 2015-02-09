package com.io17.excel2json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {
	public static List toList(JSONArray json) {
		List list = new LinkedList();
		for (int i = 0; i < json.length(); i++) {
			list.add(json.get(i));
		}
		return list;
	}

}
