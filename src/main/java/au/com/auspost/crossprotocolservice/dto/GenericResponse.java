package au.com.auspost.crossprotocolservice.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GenericResponse {
	
	Column[] columns;
	String[][] rows;
	@JsonProperty
	public Column[] getColumns() {
		return columns;
	}
	public void setColumns(Column[] columns) {
		this.columns = columns;
	}
	@JsonProperty
	public String[][] getRows() {
		return rows;
	}
	public void setRows(String[][] rows) {
		this.rows = rows;
	}
	
	private String getCommaSeparatedColumnNames() {
		String commaSeparatedColumnNames="";
		for(int index=0; index<columns.length; index++) {
			commaSeparatedColumnNames += columns[index].getName()+",";
		}
		return StringUtils.removeEnd(commaSeparatedColumnNames, ",");
		
	}
	private String getCommaSeparatedFields() {
		String commaSeparatedFields = "";
		for(int row=0; row< rows.length; row++) {
			for(int column=0; column< rows[row].length; column++) {
				commaSeparatedFields += rows[row][column]+",";
			}
			commaSeparatedFields = StringUtils.removeEnd(commaSeparatedFields, ",")+"\n";
		}
		return commaSeparatedFields;
	}
	
	public String toString() {
		return getCommaSeparatedColumnNames() + getCommaSeparatedFields();
	}
	public List<Map<String,String>> getJsonObject() throws Exception {
		List<String> fieldNames = null;
		if(columns.length > 0) {
			fieldNames = Arrays.asList(getCommaSeparatedColumnNames().split(","));
			
		}else {
			return null;
		}
		List<Map<String,String>> list = new ArrayList<>();
		for(int i=0; i< rows.length; i++) {
			Map<String,String> jsonObject = new LinkedHashMap<>();
			for (int j = 0 ; j < fieldNames.size() ; j++) {
				jsonObject.put(fieldNames.get(j), rows[i][j]);
	            
	        }
			list.add(jsonObject);
		}
		
		return list;
	}
}
