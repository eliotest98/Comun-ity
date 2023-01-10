package controller.utility;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Accreditamento;
import model.Annuncio;
import model.Utente;

/**
 * Java class that defines static methods for email notification.
 */
public class MailSender {

  /**
   * Sends a notification email to the given ad author to let him know
   * that the given user has taken on his ad.
   *
   * @param utente is the user object to extract user datas from.
   * @param annuncio is the ad object to extract ad datas from.
   */
  public static void notifyAdTakeOn(Utente utente, Annuncio annuncio) {

    String host = "smtp.gmail.com";

    Properties properties = System.getProperties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
      protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication("comunity.unisa@gmail.com",
            "uslzhowdfvoknjum");
      }
    });

    try {
      String to = annuncio.getAutore();
      String subject = "Annuncio " + annuncio.getId() + " preso in carico";

      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(to));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      message.setText(createBody(utente, annuncio));
      Transport.send(message);

      System.out.println("Email to: " + utente.getMail() + " SENT.");
    } catch (Exception e) {
      System.out.println("Email to: " + utente.getMail() + " NOT SENT.");
      e.printStackTrace();
    }
  }
  
  public static void notifyAccreditationReq(Utente utente, Accreditamento accreditamento) {
    //TODO
  }

  /**
   * Creates the email body.
   *
   * @param utente   is the user object to extract user datas from.
   * @param annuncio is the ad object to extract ad datas from.
   * 
   * @return a String containing the email body text;
   */
  private static String createBody(Utente utente, Annuncio annuncio) {
    String text;

    text = "L'annuncio" + annuncio.getTitolo() + "\n" + "è stato preso in carico da" + utente
        .getNome() + " " + utente.getCognome();
    return text;
  }

}
