package io.sds.contacts.util.model;

public enum States {
	
		AK("Alaska"),
		AL("Alabama"),
		AR("Arkansas"),
		AZ("Aizona"),
		CA("California"),
		CO("Colorada"),
		CT("Connecticut"),
		DE("Delaware"),
		FL("Florida"),
		GA("Georgia"),
		HI("Hawaii"),
		IA("Iowa"),
		ID("Idaho"),
		IL("Illinois"),
		IN("Indiana"),
		KS("Kansas"),
		KY("Kentucky"),
		LA("Loiusiana"),
		MA("Massachusetts"),
		MD("Maryland"),
		ME("Maine"),
		Mi("Michigan"),
		MN("Minnesota"),
		MO("Missouri"),
		MS("Mississippi"),
		MT("Montana"),
		NC("North Carolina"),
		ND("North Dakota"),
		NE("Nebraska"),
		NH("New Hampshire"),
		NJ("New Jersey"),
		NM("New Mexico"),
		NV("Nevada"),
		NY("New York"),
		OH("Ohio"),
		OK("Oklahoma"),
		OR("Oregon"),
		PA("Pennsylvania"),
		RI("Rhode Island"),
		SC("South Carolina"),
		SD("South Dakota"),
		TN("Tennessee"),
		TX("Texas"),
		UT("Utah"),
		VA("Virginia"),
		VT("Vermont"),
		WA("Washington"),
		WI("Wisconsin"),
		WV("West Virginia"),
		WY("Wyoming");
	
	private final String name;
	
	States(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
