package nl.hu.sie.bep.friendspammer;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EmailSender {

  private EmailSender() {
    throw new IllegalStateException("Utility class");
  }

  private static Session getSession() {
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.mailtrap.io");
    props.put("mail.smtp.port", "2525");
    props.put("mail.smtp.auth", "true");


    //PW staat er nu hardcoded in, maar dat zal je niet in productie willen. Beter uitlezen van een lokaal bestand zoals te zien op:
    //https://wiki.sei.cmu.edu/confluence/display/java/MSC03-J.+Never+hard+code+sensitive+information
    String us = "42c2290a3b08cb";
    String code = "3befb4c6f8a5a1";

    return Session.getInstance(props,
      new javax.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(us, code);
        }
      });
  }

  public static void sendEmail(String subject, String to, String messageBody, boolean asHtml) {
    final Logger logger = LoggerFactory.getLogger(EmailSender.class);

		try {

			Message message = new MimeMessage(getSession());
			message.setFrom(new InternetAddress("spammer@spammer.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			
			if (asHtml) {
					message.setContent(messageBody, "text/html; charset=utf-8");
			} else {
				message.setText(messageBody);	
			}
			Transport.send(message);
      logger.info("Done");

			MongoSaver.saveEmail(to, "spammer@spamer.com", subject, messageBody, asHtml);

    } catch (MessagingException e) {
      throw new IllegalArgumentException("Destination address not known");
    }
	}

	public static void sendEmail(String subject, String[] toList, String messageBody, boolean asHtml) {
    final Logger logger = LoggerFactory.getLogger(EmailSender.class);
		try {

			for (int index = 0; index < toList.length; index++) {
			
				Message message = new MimeMessage(getSession());
				message.setFrom(new InternetAddress("spammer@spammer.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toList[index]));
				message.setSubject(subject);
				
				if (asHtml) {
						message.setContent(messageBody, "text/html; charset=utf-8");
				} else {
					message.setText(messageBody);	
				}
				Transport.send(message);
				logger.info("Done");
			}

    } catch (MessagingException e) {
      throw new IllegalArgumentException("Destination address not known");
    }
  }
}
