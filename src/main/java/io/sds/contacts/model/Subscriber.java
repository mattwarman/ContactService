package io.sds.contacts.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class Subscriber {
	
	@Schema(
			description = "users contact code",
			name = "contactCode",
			example = "AUSER99999")
	@NotNull
	private String contactCode;
	@Schema(
			description = "subscribers contact type",
			name = "contactType",
			example = "PUBLIC")
	@NotNull
	private String contactType;
	@Schema(
			description = "subscribers contact code",
			name = "subContactCode",
			example = "JODOE12345")
	@NotNull
	private String subContactCode;
	@Schema(
			description = "subscribers contact type",
			name = "subContactType",
			example = "PRIVATE")
	@NotNull
	private String subContactType;
	@Schema(
			description = "contact category for subscription",
			name = "category",
			example = "VENDOR")
	@NotNull
	private String category;
	@Schema(
			description = "Has the subscriber verified the subscription?",
			name = "isContactVerified",
			example = "true")
	@NotNull
	private boolean isContactVerified;
	
	public Subscriber(String contactCode, String contactType, String subContactCode, String subContactType, String category) {
		this.contactCode = contactCode;
		this.contactType = contactType;
		this.subContactCode = subContactCode;
		this.subContactType = subContactType;
		this.category = category;
	}
	
	public Subscriber() {}
	
	public String getContactCode() {
		return contactCode;
	}
	
	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}
	
	public String getContactType() {
		return contactType;
	}
	
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	
	public String getSubContactCode() {
		return subContactCode;
	}
	
	public void setSubContactCode(String subContactCode) {
		this.subContactCode = subContactCode;
	}
	
	public String getSubContactType() {
		return subContactType;
	}
	
	public void setSubContactType(String subContactType) {
		this.subContactType = subContactType;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String categoryType) {
		this.category = category;
	}
	
	public boolean isContactVerified() {
		return isContactVerified;
	}
	
	public void setContactVerified(boolean contactVerified) {
		isContactVerified = contactVerified;
	}
	
}
