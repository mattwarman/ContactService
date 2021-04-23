package io.sds.contacts.model;

public class ContactDetails {
	
	private String contactcode;
	private Long typeid;
	private Long securityid;
	private Long attributeid; 
	private String attributevalue;
	
	public ContactDetails(String contactcode, Long typeid, Long securityid, Long attributeid, String attributevalue) {
		this.contactcode = contactcode;
		this.typeid = typeid;
		this.securityid = securityid;
		this.attributeid = attributeid;
		this.attributevalue = attributevalue;
	}
	
	public String getContactcode() {
		return contactcode;
	}
	
	public void setContactcode(String contactcode) {
		this.contactcode = contactcode;
	}
	
	public Long getTypeid() {
		return typeid;
	}
	
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	
	public Long getSecurityid() {
		return securityid;
	}
	
	public void setSecurityid(Long securityid) {
		this.securityid = securityid;
	}
	
	public Long getAttributeid() {
		return attributeid;
	}
	
	public void setAttributeid(Long attributeid) {
		this.attributeid = attributeid;
	}
	
	public String getAttributevalue() {
		return attributevalue;
	}
	
	public void setAttributevalue(String attributevalue) {
		this.attributevalue = attributevalue;
	}
}
