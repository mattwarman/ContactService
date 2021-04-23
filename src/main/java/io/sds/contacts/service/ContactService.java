package io.sds.contacts.service;

import io.sds.contacts.model.Contact;
import io.sds.contacts.model.Subscriber;
import io.sds.contacts.model.Subscription;
import io.sds.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

	@Service
	@Transactional
	public class ContactService {
		@Autowired
		private ContactRepository contactRepository;
		
		private static final String CONTACT_SUCCESS = "contact successfully added";
		private static final String CONTACT_FAIL = "contact not added";
		private static final String SUBSCRIBE_SUCCESS = " successfully subscribed to ";
		private static final String SUBSCRIBE_FAIL = " did not subscribe to ";
		private static final String UNSUBSCRIBE_SUCCESS = " successfully unsubscribed to ";
		private static final String UNSUBSCRIBE_FAIL = " did not unsubscribe to ";
	
	
	
		public ContactService() { }
	
	public Contact getContactByCode(String contactCode) {
			return contactRepository.findByContactCode(contactCode);
	}
	
	public List<Contact> getContactsByRegion(String city, String state, String type) {
		return contactRepository.findByRegion(city, state, type);
	}
	
	public String newContact(Contact contact) throws DuplicateKeyException {
			int ret =  contactRepository.newContact(contact);
			if(1 != ret) {
				return CONTACT_FAIL + " Code of " + ret;
			}
			return CONTACT_SUCCESS;
	}
	
	public String subscribe(Subscriber subscriber) {
		int ret = contactRepository.subscribe(subscriber);
		if(1 != ret) {
			return subscriber.getContactCode() + SUBSCRIBE_FAIL + subscriber.getSubContactType();
		}
		return subscriber.getContactCode() + SUBSCRIBE_SUCCESS + subscriber.getSubContactCode();
	}	
		
	public String unsubscribe(Subscriber subscriber) {
			int ret = contactRepository.unsubscribe(subscriber);
			if(1 != ret) {
				return subscriber.getContactCode() + UNSUBSCRIBE_FAIL + subscriber.getSubContactType();
			}
			return subscriber.getContactCode() + UNSUBSCRIBE_SUCCESS + subscriber.getSubContactCode();
	}
				
	public List<Subscription> getSubscriptions(Subscriber subscriber) {
		return contactRepository.getSubscriptions(subscriber);
	}	

}