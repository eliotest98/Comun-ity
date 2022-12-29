package controller.utility;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailSender {
	
	private Properties properties = System.getProperties();
	
	private Session session = Session.getDefaultInstance(properties);
	
	private MimeMessage message = new MimeMessage(session);
	
	public MailSender (List<String> list, String from, String host, String text, String subject) throws AddressException, MessagingException {
		
		properties.setProperty("mail.smtp.host", host);
		
		list.forEach((to) -> {
			try {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});		
		message.setSubject(subject);
		message.setFrom(new InternetAddress(from));
		message.setText(text);
		
		Transport.send(message);
	}
}
