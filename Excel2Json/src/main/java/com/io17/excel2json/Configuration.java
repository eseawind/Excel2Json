package com.io17.excel2json;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class Configuration {

	public static final Configuration DEFAULT;
	static {
		DEFAULT = new Configuration();
		// TODO: define defaults;
	}

	private ExcelReaderConfiguration excel;
	private JSONAggregatorConfiguration aggregate;

	public Configuration(String configFile) throws IOException {
		String source = readFile(configFile, Charset.defaultCharset());
		JSONObject json = new JSONObject(source);
		JSONObject excelJSON = json.getJSONObject("excel");
		JSONObject aggregateJSON = json.getJSONObject("aggregate");
		excel = new ExcelReaderConfiguration(excelJSON);
		aggregate = new JSONAggregatorConfiguration(aggregateJSON);
		
	}

	public Configuration() {
		// TODO Auto-generated constructor stub
	}

	public ExcelReaderConfiguration getExcelConfiguration() {
		return excel;
	}

	public JSONAggregatorConfiguration getAggregationConfig() {
		return aggregate;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
