package io.sds.contacts.repository;

import io.sds.contacts.model.Contact;
import io.sds.contacts.util.model.ContactTypes;
import io.sds.contacts.model.Details;
import io.sds.contacts.model.Subscriber;
import io.sds.contacts.model.Subscription;
import io.sds.contacts.util.repository.UtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepository  {
		
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private UtilRepository utilRepository;
	
	private String CONTACT_BASE ="SELECT  contactcode, firstname, lastname, city, st.abbreviation AS state \n"
			+ " FROM contact c \n"
			+ "LEFT JOIN state st ON st.idstate = stateId \n";
	
	private String CONTACT ="SELECT * FROM contact c \n";
	private String DETAILS ="SELECT contactcode, typeid, securityid,  a.name AS name, attributevalue AS value FROM details d \n"
			+ "LEFT JOIN attribute a ON a.idattribute = attributeid \n";
	
	private String CONTACT_DETAILS = "SELECT t.value AS type, s.value AS security, a.name AS name, attributevalue AS value FROM details \n"
			+ "LEFT JOIN type t ON t.idtype = typeid \n"
			+ "LEFT JOIN security s ON s.idsecurity = securityid \n"
			+ "LEFT JOIN attribute a ON a.idattribute = attributeid \n"
			+ " WHERE contactcode =:contactCode AND typeid =:typeId";
	
	private String SUBSCRIPTION = "SELECT y.firstname, y.lastname, c.contactcode AS contact, c.firstname, c.lastname, ct.value AS category \n" 
			+ "FROM subscriber s\n" 
			+ "LEFT JOIN contact y ON y.contactcode = s.contactcode\n" 
			+ "LEFT JOIN contact c ON c.contactcode = s.subcontactcode\n" 
			+ "LEFT JOIN category ct ON ct.idcategory = s.categoryid\n" 
			+ "WHERE s.contactcode = :contactCode";
	private final String CHECK_DUPLICATE = " SELECT COUNT(contactcode) FROM contact where contactcode = :contactCode";
	private final String INSERT_CONTACT = "INSERT INTO contact(contactcode, firstname, lastname, city, stateid) VALUES(:contactCode, :firstName, :lastName, :city, :state)";
	private final String INSERT_DETAILS = "INSERT INTO details(contactcode, typeid, securityid, attributeid, attributevalue) VALUES(:contactCode, :typeId, :securityId, :attributeId, :attributeValue)";
	private final String INSERT_SUBSCRIBER = "INSERT INTO subscriber (contactcode, contacttypeid, subcontactcode, subcontacttypeid, categoryid) " +
			"VALUES(:contactCode, :contactType, :subContactCode, :subContactType, :category)";
	private final String DELETE_SUBSCRIBER = "DELETE FROM subscriber WHERE contactcode = :contactCode AND subcontactcode = :subContactCode";
	private final String BY_CODE = " WHERE contactcode=:contactCode";
	private final String BY_REGION = " WHERE city=:city AND st.abbreviation = :state";
	
	public Contact findByContactCode(@Param("contactCode") String contactCode) {
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("contactCode", contactCode);
		Contact contact  = jdbcTemplate.queryForObject(CONTACT_BASE + BY_CODE, parameters, (rs, rowNum) ->
				new Contact(
						rs.getString("contactcode"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("city"),
						rs.getString("state"),
						new ArrayList<Details>())
						
		);
		//set to public for now
		contact.setDetails(getDetails(contactCode, ContactTypes.getValue(ContactTypes.PUBLIC.name())));
		return contact;
	}
	
	
	public List<Contact> findByRegion(@Param("city")String city, @Param("state") String state, @Param("type") String type) {
		SqlParameterSource parameters = new MapSqlParameterSource()				
				.addValue("city", city)				
				.addValue("state", state)				
				.addValue("type", type);
		return jdbcTemplate.query(CONTACT_BASE + BY_REGION, parameters, (rs, rowNum) ->
			new Contact(
				rs.getString("contactcode"),
				rs.getString("firstname"),
				rs.getString("lastname"),
				rs.getString("city"),
				rs.getString("state"),
				getDetails(rs.getString("contactcode"), utilRepository.getTypeId(type)))
		);
	}
	
	private List<Details> getDetails(@Param("contactCode")String contactCode, @Param("typeid") Long typeId) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("contactCode", contactCode)
				.addValue("typeId", typeId);
		return jdbcTemplate.query(CONTACT_DETAILS, parameters, (rs, rowNum) ->
				new Details(
						rs.getString("type"),
						rs.getString("security"),
						rs.getString("name"),
						rs.getString("value"))
		);
	}
	
	@Transactional(rollbackFor = { SQLException.class })
	public int newContact(@Param("contact")Contact contact) {			
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("contactCode", contact.getContactCode())
			.addValue("firstName", contact.getFirstName())
			.addValue("lastName", contact.getLastName())
			.addValue("city", contact.getCity())
			.addValue("state", utilRepository.getStateId(contact.getState()));
		int result =  jdbcTemplate.update(INSERT_CONTACT, parameters);
		for(Details details: contact.getDetails()) {
			parameters = new MapSqlParameterSource()
					.addValue("typeId", utilRepository.getTypeId(details.getType()))
					.addValue("securityId", utilRepository.getSecurityId(details.getSecurity()))
					.addValue("attributeId", utilRepository.getAttributeId(details.getDetailName()))
					.addValue("attributeValue", details.getDetailValue());
			//TODO if result not = 1, send error
			result = jdbcTemplate.update(INSERT_DETAILS, parameters);
		}
		return result;
	} 
	
	public int subscribe(@Param("subscriber") Subscriber subscriber) {
		//if contact is not public, set the isverified boolean value to false. send an email to contact
		subscriber.setContactVerified(true);
		if(!subscriber.getContactType().equals(ContactTypes.PUBLIC.name())) {
			subscriber.setContactVerified(false);
		}
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("contactCode", subscriber.getContactCode())
				.addValue("contactType", subscriber.getContactType())
				.addValue("subContactCode", subscriber.getSubContactCode())
				.addValue("subContactType", subscriber.getSubContactType())
				.addValue("category", subscriber.getCategory());
		int ret = jdbcTemplate.update(INSERT_SUBSCRIBER, parameters);		
		//TODO add email sending if necessary after successful insert
		return ret;
	}
	
	public int unsubscribe(@Param("subscriber") Subscriber subscriber) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("contactCode", subscriber.getContactCode())
				.addValue("subContactCode", subscriber.getSubContactCode());
		return jdbcTemplate.update(DELETE_SUBSCRIBER, parameters);		
	}
		
	public List<Subscription> getSubscriptions(@Param("subscriber") Subscriber subscriber) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("contactCode", subscriber.getContactCode());
		return jdbcTemplate.query(SUBSCRIPTION, parameters, (rs, rowNum) -> 
				new Subscription( 
						rs.getString("firstname") + " " + rs.getString("lastname"),
						rs.getString("category"),
						findByContactCode(rs.getString("contact"))
				));
	}	
}