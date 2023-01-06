package controller.utility;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

  private final Properties properties = System.getProperties();

  private final Session session = Session.getDefaultInstance(properties);

  private final MimeMessage message = new MimeMessage(session);

  public MailSender(List<String> list, String from, String host, String text, String subject)
      throws MessagingException {

    properties.setProperty("mail.smtp.host", "smtp.comun-ity.com");

    list.forEach((to) -> {
      try {
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      } catch (MessagingException e) {
        e.printStackTrace();
      }
    });

    message.addHeader("Content-type", "text/HTML; charset=UTF-8");
    message.addHeader("format", "flowed");
    message.addHeader("Content-Transfer-Encoding", "8bit");

    message.setSubject(subject);
    message.setFrom(new InternetAddress(from));
    message.setText(text);
  }

  public void sendMail() throws MessagingException {
    Transport.send(this.message);
  }
}
