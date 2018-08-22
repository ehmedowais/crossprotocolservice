package au.com.auspost.crossprotocolservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Column {
	private String name;
	private String type;
	
	@JsonProperty
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
