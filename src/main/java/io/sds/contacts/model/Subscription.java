package io.sds.contacts.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class Subscription {
	
	@Schema(
			description = "contact detail value",
			name = "contactName",
			required = true, 
			example = "5133471111")
	private String contactName;
	@Schema(
			description = "contact detail value",
			name = "category",
			required = true, 
			example = "5133471111")
	private String category;
	@Schema(
			description = "subscribed contact",
			name = "subscriberContact",
			required = true)
	private Contact subscriberContact;	
	
	public Subscription(String contactName, String category, Contact subscriberContact) {
		this.contactName = contactName;
		this.category = category;
		this.subscriberContact = subscriberContact;
	}
	
	public Subscription() {}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Contact getSubscriberContact() {
		return subscriberContact;
	}
	
	public void setSubscriberContact(Contact subscriberContact) {
		this.subscriberContact = subscriberContact;
	}
}
