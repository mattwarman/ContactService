package io.sds.contacts.util.model;

import java.util.Locale;

public enum Categories {
	CONTRACTOR(1L),  
	VENDOR (2L),
	CLIENT (3L),
	EMPLOYEE (4L),
	SUPPLIER (5L),
	WORK (6L),
	FRIEND (7L);
	
	
	private Long category = 1L;
	
	private Categories(Long category) {
		this.category = category;
	}
	
	public static Long getValue(String name) {
		return Categories.valueOf(name.toUpperCase(Locale.ROOT)).category;
	}
}
