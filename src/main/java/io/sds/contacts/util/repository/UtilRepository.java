package io.sds.contacts.util.repository;

import io.sds.contacts.util.model.Categories;
import io.sds.contacts.util.model.ContactTypes;
import io.sds.contacts.util.model.SecurityTypes;
import io.sds.contacts.util.model.State;
import io.sds.contacts.util.model.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class UtilRepository {
		
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
		
	private String CHECK_DUPLICATE = " SELECT COUNT(contactcode) FROM contact where contactcode = :contactCode";
	private String GET_STATE_ID = "SELECT idstate from state WHERE abbreviation = :state";
	private String GET_SECURITY_ID = "SELECT idsecurity from security WHERE value= :security";
	private String GET_TYPE_ID = "SELECT idtype from type WHERE value= :type";
	private String GET_ATTRIBUTE_ID = "SELECT idattribute from attribute WHERE name= :attribute";
		
	public List<State> getStates() {
		List<State> states = new ArrayList<>();
		State st;
		for(States state: States.values()) {
			st = new State(state.name(), state.getName());
			states.add(st);
		}
		return states;
	}
	
	public List<String> getContactTypes() {
		return Stream.of(ContactTypes.values())
				.map(ContactTypes::name)
				.collect(Collectors.toList());
	}
	
	public List<String> getSecurityTypes() {
		return Stream.of(SecurityTypes.values())
				.map(SecurityTypes::name)
				.collect(Collectors.toList());
	}
	
	public List<String> getCategories() {
		return Stream.of(Categories.values())
				.map(Categories::name)
				.collect(Collectors.toList());
	}	
	
	public int checkDuplicate(@Param("contactCode") String contactCode) {
		String result = "Contact code is unique";
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("contactCode", contactCode);
		return jdbcTemplate.queryForObject(CHECK_DUPLICATE, parameters, Integer.class);
	}
	
	public Long getStateId(@Param("state")String state) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("state", state);
		return jdbcTemplate.queryForObject(GET_STATE_ID, parameters, Long.class);		
	}
	
	public Long getTypeId(@Param("type")String type) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("type", type);
		return jdbcTemplate.queryForObject(GET_TYPE_ID, parameters, Long.class);
	}
	
	public Long getSecurityId(@Param("security")String security) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("security", security);
		return jdbcTemplate.queryForObject(GET_SECURITY_ID, parameters, Long.class);
	}
	
	public Long getAttributeId(@Param("attribute")String attribute) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("attribute", attribute);
		return jdbcTemplate.queryForObject(GET_ATTRIBUTE_ID, parameters, Long.class);
	}
		
}