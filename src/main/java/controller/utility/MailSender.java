package controller.utility;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import model.Annuncio;
import model.Utente;

public class MailSender {

	/**
	 * Empty Constructor.
	 */
	public MailSender() {}

	public boolean sendMail(Utente utente, Annuncio annuncio, String email) {

		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(email, "uslzhowdfvoknjum");
			}
		});

		try {
			String to = annuncio.getAutore();
			String subject = "Annuncio "+annuncio.getId()+" preso in carico";

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(to));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(CreaText(utente,annuncio));
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String CreaText(Utente utente, Annuncio annuncio) {
		String text;

		text="L'annuncio"+annuncio.getTitolo()+"\n"+"è stato preso in carico da"+utente.getNome()+" "+utente.getCognome();
		return text;
	}

}
