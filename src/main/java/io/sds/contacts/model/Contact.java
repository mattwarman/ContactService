package io.sds.contacts.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Contact {
	
	@Schema(
			description = "unique contact code",
			name = "contactCode",
			required = true,
			example = "JODOE12345")
	@NotNull
	private String contactCode;
	@Schema(
			description = "first name of the contact",
			name = "firstName",
			required = true,
			example = "John")
	@NotNull
	private String firstName;
	@Schema(
			description = "last name of the contact",
			name = "lastName",
			example = "Doe")
	@NotNull
	private String lastName;
	@Schema(
			description = "city of the contact",
			name = "city",
			required = true,
			example = "Cincinnati")
	@NotNull
	private String city;
	@Schema(
			description = "state abbreviation of the contact",
			name = "state",
			required = true,
			example = "OH")
	@NotNull
	private String state;
	@Schema(
			description = "A list of contact details",
			name = "details",
			required = true
			)
	@NotNull
	private List<Details> details;
		
	public Contact(String contactCode, String firstName, String lastName, String city, String state, List<Details> details) {
		this.contactCode = contactCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.details = details;
	}
	
	public Contact() {}
	
	public String getContactCode() {
		return contactCode;
	}
	
	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
		
	public List<Details> getDetails() {
		return details;
	}
	
	public void setDetails(List<Details> details) {
		this.details = details;
	}
}
