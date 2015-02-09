package com.io17.excel2json;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Excel2Json {

	private static final String SAMPLE_FILE = "static/sample.xlsx";
	private ExcelReader xReader;
	private Configuration config;
	
	public Excel2Json(String configFile) throws IOException, InvalidFormatException {
		config = new Configuration(configFile);
		
		xReader = new ExcelReader(config.getExcelConfiguration());
	}

	private JSONArray transform(String inputFile) throws InvalidFormatException, IOException {
		JSONArray ja = xReader.getJSON(inputFile);
		
		JSONAggregator aggregator = new JSONAggregator(config.getAggregationConfig());
		JSONArray jo = aggregator.process(ja);
		return jo;
	}
	
	public static void main(String[] args) throws IOException,
			InvalidFormatException {

		String configFile = args[0];
		String inputFile = args[1];
		
		Excel2Json x = new Excel2Json(configFile);
		JSONArray ja = x.transform(inputFile);
		
		System.out.println(ja.toString(3));

	}

}
