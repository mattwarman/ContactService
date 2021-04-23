package io.sds.contacts.controller;

import io.sds.contacts.model.Contact;
import io.sds.contacts.model.Subscriber;
import io.sds.contacts.model.Subscription;
import io.sds.contacts.service.ContactService;
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
				description = "Contact Service for SDS Contact application",
				version = "V1.0",
				title = "SDS Contact API",
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
@RequestMapping("/contacts")
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	private static final String OK = "200";
	private static final String ERROR = "400";
	private static final String NOT_FOUND = "404";
	private static final String DUPLICATE_CODE = "Duplicate contact code, please change and try again";
	
	@RequestMapping(value="/test", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	public String test() {
		return "Contact Service successfully connected";
	}
	
	@Operation(summary = "Retrieves a contact by city, state, and contact type.",
			responses = { @ApiResponse(responseCode = OK, description = "Contacts Returned",
					content = @Content(schema = @Schema(implementation = Contact.class))),
					@ApiResponse(responseCode = NOT_FOUND, description = "Contacts not found") })
	@RequestMapping(value = "/region/{city}/{state}/{type}", method = RequestMethod.GET)
	public List<Contact> getContactsByRegion(@PathVariable String city, @PathVariable String state, @PathVariable String type) {
		return contactService.getContactsByRegion(city, state, type);
	}
	
	@Operation(summary = "Retrieves a contact by contact code.")  
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "Contact found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Contact.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Invalid contact code supplied",
					content = @Content),
			@ApiResponse(responseCode = NOT_FOUND, description = "Contact not found",
					content = @Content) })
	@RequestMapping(value = "/contact/{contactCode}", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	public Contact contact(@Parameter(description = "Contact code to lookup", example ="{'contactCode': 'JODOE123453'}" ) @PathVariable String contactCode) {
		return contactService.getContactByCode(contactCode);
	}
	
	@Operation(summary = "Creates a new contact")
	@Parameter( description = "Contact to add", example = "{\"contactCode\": \"JODOE12345\", \"firstName\": \"John\"," +
			"\"lastName\": \"Doe\",\"city\": \"Cincinnati\",\"state\": \"OH\",\"details\": " +
			"[{\"type\": \"public\",\"security\": \"none\", \"detailName\": \"phone\"," +
			"\"detailValue\": \"5133471111\"},{ \"type\": \"public\",\"security\": \"none\"," +
			"\"detailName\": \"email\",\"detailValue\": \"john.doe@example.io\" }]}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "Contact created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Invalid contact code supplied",
					content = @Content) })	
	@RequestMapping(value = "/contact", method = RequestMethod.POST,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String newContact(@RequestBody Contact contact) {
		try {
			return contactService.newContact(contact);
		} catch(DuplicateKeyException dke) {
			return DUPLICATE_CODE;
		}
	}
	
	@Operation(summary = "Subscribes your contact to the selected contact. If not public, the contact will " +
			"not be available until verified by the subscribed contact.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "Subscription successful",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Subscription error",
					content = @Content),
			@ApiResponse(responseCode = NOT_FOUND, description = "subscription not found",
					content = @Content) })	
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String subscribe(@RequestBody Subscriber subscriber) {
		return contactService.subscribe(subscriber);
	}
	
	@Operation(summary = "Unsubscribes your contact from the selected contact.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "Contact unsubscribed",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Invalid subscriber supplied",
					content = @Content),
			@ApiResponse(responseCode = NOT_FOUND, description = "subscription not found",
					content = @Content) })	
	@RequestMapping(value = "/unsubscribe", method = RequestMethod.POST,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String unsubscribe(@RequestBody Subscriber subscriber) {
		return contactService.unsubscribe(subscriber);
	}
	
	@Operation(summary = "Retrieves a subscription list for your contact.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "Contact found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Subscription.class)) }),
			@ApiResponse(responseCode = ERROR, description = "Invalid subscriber code supplied",
					content = @Content),
			@ApiResponse(responseCode = NOT_FOUND, description = "Subscription not found",
					content = @Content) })	
	@RequestMapping(value = "/subscriptions", method = RequestMethod.POST,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<Subscription> subscriptions(@RequestBody Subscriber subscriber) {
		return contactService.getSubscriptions(subscriber);
	}
	
}