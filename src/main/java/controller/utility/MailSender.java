package controller.utility;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {		
	
	private static String user = "comunity.unisa@gmail.com";
	private static String password = "Comun-ity_2023";
	
	public static void sendMail (String text, String subject) throws AddressException, MessagingException {
		
		System.out.println("ao");
		
		Properties props = new Properties();

	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    
	    System.out.println("create props");

	    Session session = Session.getInstance(props, new Authenticator() {
	      protected PasswordAuthentication getPasswordAuthentication() {
	    	  System.out.println(new PasswordAuthentication(user, password));
	        return new PasswordAuthentication(user, password);
	      }
	    });

	    try {
	    	
	      MimeMessage message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(user));
	      message.addRecipient(Message.RecipientType.TO, new InternetAddress("gabrielesantoro46@gmail.com"));
	      message.setSubject(subject);
	      message.setText(text);
	      message.setContent(text, "text/html; charset=utf-8");
	      
	      System.out.println("invio della mail");
	      
	      Transport.send(message);

	    } catch (Exception e) {
	      e.printStackTrace();
	      System.out.println("mail non inviata");
	    }
	}
	
	
}
