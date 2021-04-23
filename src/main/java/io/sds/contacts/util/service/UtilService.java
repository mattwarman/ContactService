package io.sds.contacts.util.service;

import io.sds.contacts.util.email.EmailUtil;
import io.sds.contacts.util.model.Email;
import io.sds.contacts.util.model.State;
import io.sds.contacts.util.repository.UtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.List;
import java.util.Properties;

@Service
public class UtilService {
	@Autowired
	private UtilRepository utilRepository;
	
	public UtilService() {
	}
	
	public String checkDuplicateCode(String contactCode) throws DuplicateKeyException {
		int result = utilRepository.checkDuplicate(contactCode);
		if (result > 0) {
			throw new DuplicateKeyException("Duplicate Contact Code");
		}
		return "Contact Code is unique";
	}
	
	public List<State> getStates() {
		return utilRepository.getStates();
	}
	
	public List<String> getContactTypes() {
		return utilRepository.getContactTypes();
	}
	
	public List<String> getSecurityTypes() {
		return utilRepository.getSecurityTypes();
	}
	
	public List<String> getCategories() {
		return utilRepository.getCategories();
	}
	
	public String sendEmail(Email email) {
		String result = "Email successfully sent";
		try {
			final String fromEmail = "mattwrock@gmail.com"; //requires valid gmail id
			final String password = "Terrazane4"; // correct password for gmail id
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
			props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
			props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
			props.put("mail.smtp.port", "465"); //SMTP Port			
			//create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};			
			Session session = Session.getInstance(props, auth);
			EmailUtil.sendEmail(session, email.getToEmail(), email.getSubject(), email.getBody());
		} catch (Exception e) {
			result =  "Email failed with " + e.getMessage();
		}
		return result;
	}
}