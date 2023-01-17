package com.fgc.email.service;

import com.fgc.email.controller.model.EmailRequestDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

@Service
public class EmailService {

  @Value("app.sender.from")
  private String username;

  @Value("app.sender.password")
  private String password;

  public void sendEmail(final EmailRequestDTO request) {
    final Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.socketFactory.port", 587);
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.put("mail.smtp.ssl.checkserveridentity", true);
    properties.put("mail.smtp.host", "smtp.ionos.es");
    properties.put("mail.smtp.port", 587);

    // Create session object passing properties and authenticator instance
    final Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    try {
      // Create MimeMessage object
      final MimeMessage message = new MimeMessage(session);

      // Set the Senders mail to From
      message.setFrom(new InternetAddress(username));

      // Set the recipients email address
      message.addRecipient(TO, new InternetAddress(request.getTo()));

      if (Strings.isNotEmpty(request.getCc())) {
        message.addRecipient(CC, new InternetAddress(request.getCc()));
      }

      if (Strings.isNotEmpty(request.getBcc())) {
        message.addRecipient(BCC, new InternetAddress(request.getBcc()));
      }

      // Subject of the email
      message.setSubject(request.getSubject());

      // Body of the email
      message.setContent(request.getText(), "text/html");

      // Send email.
      Transport.send(message);

    } catch (MessagingException me) {
      me.getMessage();
    } catch (Throwable e) {
      e.getMessage();
    }
  }

}
