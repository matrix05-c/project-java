package project.bibliotheque.services;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import project.bibliotheque.models.*;


public class Mailer {
  public static void main(Membre member, Livre book, Date date) {
    final String username = "ratsinjomananatolotra@gmail.com";
    final String password = "ujqs ktrq ufzy omcn";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    try {
      Message mimeMessage = new MimeMessage(session);
      mimeMessage.setFrom(new InternetAddress(username));
      mimeMessage.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(member.getContact()));
      mimeMessage.setSubject("Notification de retard!");
      mimeMessage.setText("""
          Vous êtes en retard sur le retour du livre:
          Livre: %s
          Auteur: %s
          Prêter le: %s
          """.formatted(book.getTitre(), book.getAuteur(), date.toString()));


      Transport.send(mimeMessage);
      System.out.println("Email sent successfully");
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
