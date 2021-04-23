package io.sds.contacts.util.model;

import java.util.Locale;

public enum ContactTypes {
	PUBLIC (1L),  
	WORK (2L), 
	PRIVATE (3L),
	CLIENT (4L),
	SUPPLIER (5L); 
	
	
	private Long contactType = 1L;
	
	private ContactTypes(Long contactType) {
		this.contactType = contactType;
	}
	
	public static Long getValue(String name) {
		return ContactTypes.valueOf(name.toUpperCase(Locale.ROOT)).contactType;
	}
}
