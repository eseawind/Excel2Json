package com.io17.excel2json;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExcelReader {

	private ExcelReaderConfiguration configuration;

	private Map<String, String> shortMem = new HashMap<String, String>();
	
	public ExcelReader(ExcelReaderConfiguration config) {
		this.configuration = config;
	}

	public JSONArray getJSON(String fileName) throws InvalidFormatException,
			IOException {
		return getJSON(new File(fileName));
	}

	public JSONArray getJSON(File file) throws InvalidFormatException,
			IOException {
		return getJSON(WorkbookFactory.create(file));
	}

	public JSONArray getJSON(Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0);
		JSONArray ja = getJSON(sheet);
		return ja;
	}

	private JSONArray getJSON(Sheet sheet) {
		JSONArray ja = new JSONArray();
		Iterator<Row> rowIterator = sheet.rowIterator();
		int index = -1;
		while (rowIterator.hasNext()) {
			index++;
			Row row = rowIterator.next();
			if ((index == 0) && (configuration.skipFirstRow()))
				continue;

			JSONObject jo = getJSON(row);
			ja.put(jo);
		}

		return ja;
	}

	private JSONObject getJSON(Row row) {
		processSeparator(row);
		
		Iterator<Cell> cellIterator = row.cellIterator();
		JSONObject jo = new JSONObject();
		int index = -1;
		while (cellIterator.hasNext()) {
			index++;
			Cell cell = cellIterator.next();

			if (configuration.isIncluded(index)) {
				String key = configuration.getKey(index);
				String value = getValue(key, cell);
				if ((value != null) && (!"".equals(value))) {
					Object o=value;
					String fieldType = configuration.getFieldType(key);
					if (fieldType!=null && "array".equals(fieldType)){
						String[] v = value.split(",");
						JSONArray ja = new JSONArray(v);
						o=ja;
					}
					
					jo.put(key, o);
				}
			}
		}
		return jo;
	}

	private void processSeparator(Row row) {
		String recordsSeparator = configuration.getRecordsSeparator();
		if (recordsSeparator == null)
			return;
		Integer index = configuration.getIndexForKey(configuration.getRecordsSeparator());
		Cell cell = row.getCell(index);
		if (!"".equals(cell.toString())
				&& (!cell.toString().equals(shortMem.get(recordsSeparator)))) {
			shortMem.clear();
		}

	}
	
	private String getValue(String key, Cell cell) {
		String value = cell.toString();
		
		boolean multirecordField = configuration.isMultirecordField(key);
		
		if (value == null || "".equals(value)) {
			if (multirecordField) {
				value = shortMem.get(key);
			}
		}
		if (multirecordField) {
			shortMem.put(key, value);
		}
		
		return value;
	}

}
