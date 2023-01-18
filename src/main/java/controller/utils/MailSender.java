package controller.utils;

import controller.gestione.utenza.service.GestioneUtenzaService;
import controller.gestione.utenza.service.GestioneUtenzaServiceImpl;
import java.util.List;
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
  
  private static final String mail = "comunity.unisa@gmail.com";
  private static final String password = "axtrqkxjxfwnarth";
  
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
        return new javax.mail.PasswordAuthentication(mail,
            password);
      }
    });
    
    System.out.println(session);

    try {
      String to = annuncio.getAutore();
      String subject = "Annuncio " + annuncio.getId() + " preso in carico";

      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(mail));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      message.setText(createBody(utente, annuncio));
      Transport.send(message);

      System.out.println("Email to: " + annuncio.getAutore() + " SENT.");
    } catch (Exception e) {
      System.out.println("Email to: " + annuncio.getAutore() + " NOT SENT.");
      e.printStackTrace();
    }
  }
  
  
  /**
   * Sends a notification email to admins to let them know
   * there is a new accreditation request.
   *
   * @param accreditamento is the ad object to extract ad datas from.
   */
  public static void notifyAccreditationReq(Accreditamento accreditamento) {
    
    String host = "smtp.gmail.com";

    Properties properties = System.getProperties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
      protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(mail,
            password);
      }
    });
    
    try {
      GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
      List<String> admins = service.getAllAdminsEmails();
    
      String subject = "Nuova richiesta di accreditamento sottomessa!";
    
      MimeMessage message = new MimeMessage(session);

      message.setFrom(new InternetAddress(mail));
      for (String e : admins) {
        message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(e));
      }
      message.setSubject(subject);
      message.setText(createBody(accreditamento));
      Transport.send(message);
    
      for (String e : admins) {
        System.out.println("Email to: " + e + " SENT.");
      }
    
    } catch (Exception e) {
      System.out.println("Error, emails not sent.");
      e.printStackTrace();
    }
    
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

    text = "Il tuo annuncio: " + annuncio.getTitolo() + ", è stato preso in carico da " 
        + utente.getNome() + " " + utente.getCognome() 
        + "[Reputazione: " + utente.getReputazione() + "]."
        + "\n\n"
        + "Ecco i suoi recapiti: "
        + "\nNumero telefonico: " + utente.getNumeroTelefono()
        + "\nEmail: " + utente.getMail()
        + "\nContattalo subito!"
        ;
    return text;
  }
  
  
  /**
   * Creates the email body.
   *
   * @param accreditamento is the ad object to extract ad datas from.
   * 
   * @return a String containing the email body text;
   */
  private static String createBody(Accreditamento accreditamento) {
    String text;

    text =
        "L'utente " + accreditamento.getRichiedente() + ", \n" 
        + " ha sottomesso una richiesta di"
        + "accreditamento al fine di essere riconosciuto dal sistema come " 
        + accreditamento.getAbilitazione() + ".";
        
    return text;
  }

}
