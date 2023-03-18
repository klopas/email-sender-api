package com.fgc.email.service;

import com.fgc.email.controller.model.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

  @Value("${app.sender.from}")
  private String username;

  @Value("${app.sender.password}")
  private String password;

  @Async
  public void sendEmail(final EmailRequest request) {
    final JavaMailSender mailSender = createMailSender();
    final MimeMessage mimeMessage = mailSender.createMimeMessage();
    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

    try {
      helper.setFrom(username);
      helper.setTo(request.getTo());

      if (StringUtils.hasText(request.getCc())) {
        helper.setCc(request.getCc());
      }

      if (StringUtils.hasText(request.getBcc())) {
        helper.setBcc(request.getBcc());
      }

      helper.setSubject(request.getSubject());
      helper.setText(request.getText(), true);

      mailSender.send(mimeMessage);
    } catch (MessagingException me) {
      me.getMessage();
    } catch (Throwable e) {
      e.getMessage();
    }
  }

  private JavaMailSender createMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.1and1.es");
    mailSender.setPort(25);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    final Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.ssl.checkserveridentity", true);
    mailSender.setJavaMailProperties(properties);

    return mailSender;
  }

}
