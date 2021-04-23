package io.sds.contacts.util.controller;

import io.sds.contacts.util.model.Email;
import io.sds.contacts.util.model.State;
import io.sds.contacts.util.service.UtilService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@OpenAPIDefinition(
		info = @Info(
				description = "Utility Service for SDS Contact application",
				version = "V1.0",
				title = "SDS Utility API",
				contact = @io.swagger.v3.oas.annotations.info.Contact(
						name = "Matt Warman",
						email = "matt.warman@sds.io",
						url = "http://sds.io"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0"
				)
		))
@RestController
@RequestMapping("/util")
public class UtilController {
	
	@Autowired
	UtilService utilService;
	
	private static final String OK = "200";
	private static final String ERROR = "400";
	private static final String DUPLICATE_CODE = "Duplicate contact code, please change and try again";
			
	@Operation(summary = "This method checks contact code for duplicate key")
	@Parameter( description = "Contact contact to check for duplicate", example = "{\"contactCode\": \"JODOE12345\"}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "Contact code unique",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Contact code is a duplicate",
					content = @Content) })
	@RequestMapping(value = "/duplicate/{contactCode}", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String checkDuplicateContactCode(@PathVariable String contactCode) {
		try {
			return utilService.checkDuplicateCode(contactCode);
		} catch(DuplicateKeyException dke) {
			return DUPLICATE_CODE;
		}
	}
	
	@Operation(summary = "This method retrieves a list of states for the Contact service")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "List of State objects",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = State.class)) }),
			@ApiResponse(responseCode = ERROR, description = "States not found",
					content = @Content) })
	@RequestMapping(value = "/states", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<State> getStates() {
			return utilService.getStates();
	}
		
	@Operation(summary = "This method retrieves a list of Contact types for the Contact service")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "List of Contact Type objects",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = ERROR, description = "States not found",
					content = @Content) })
	@RequestMapping(value = "/contactTypes", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<String> getContactTypes() {
		return utilService.getContactTypes();
	}
	
	@Operation(summary = "Retrieve a list of security types for the Contact service")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "List of SecurityType objects",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Security Type not found",
					content = @Content) })
	@RequestMapping(value = "/securityTypes", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<String> getSSecurityTypes() {
		return utilService.getSecurityTypes();
	}
	
	@Operation(summary = "Retrieve a list of categories for the Contact service")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "List of Categories",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = ERROR, description = "States not found",
					content = @Content) })
	@RequestMapping(value = "/categories", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<String> getCategories() {
		return utilService.getCategories();
	}
	
	
	@Operation(summary = "Send email for the Contact service")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "Email success",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Email.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Email problem. Contact SDS admin",
					content = @Content) })
	@RequestMapping(value = "/email", method = RequestMethod.POST,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String sendEmail(@RequestBody Email email) {
		 return utilService.sendEmail(email);
	}
}
