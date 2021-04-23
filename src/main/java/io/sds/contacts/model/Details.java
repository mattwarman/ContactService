package io.sds.contacts.model;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;


public class Details {
	
	@Schema(
			description = "contact type detail",
			name = "type",
			example = "[\"public\",\"private\",\"work\",\"custom\"]")
	@NotNull
	private String type;
	@Schema(
			description = "contact type security",
			name = "security",
			allowableValues = "none,email,key",
			example = "none")
	@NotNull
	private String security;
	@Schema(
			description = "contact detail name",
			name = "detailName",
			allowableValues = "title,phone,text, email, address,building, floor,group",
			example = "phone")
	@NotNull
	private String detailName;
	@Schema(
			description = "contact detail value",
			name = "detailValue",
			example = "5133471111")
	@NotNull
	private String detailValue;
	
	public Details(String type, String security, String detailName, String detailValue) {
		this.type = type;
		this.security = security;
		this.detailName = detailName;
		this.detailValue = detailValue;
	}
	
	public Details() {
	}
		
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSecurity() {
		return security;
	}
	
	public void setSecurity(String security) {
		this.security = security;
	}
	
	public String getDetailName() {
		return detailName;
	}
	
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	
	public String getDetailValue() {
		return detailValue;
	}
	
	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}
}
