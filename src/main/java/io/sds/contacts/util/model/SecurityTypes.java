package io.sds.contacts.util.model;

import java.util.Locale;

public enum SecurityTypes {
	NONE (1L),  
	EMAIL (2L), 
	KEY (3L); 
	
	
	private Long securityType = 1L;
	
	private SecurityTypes(Long securityType) {
		this.securityType = securityType;
	}
	
	public static Long getValue(String name) {
		return SecurityTypes.valueOf(name.toUpperCase(Locale.ROOT)).securityType;
	}
}
