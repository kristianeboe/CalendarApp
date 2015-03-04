package no.ntnu.stud.controller;

import no.ntnu.stud.model.User;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by adrianh on 04.03.15.
 */
public class MailController {

    private final String username = "sagacalendar365@gmail.com";
    private final String password = "banan1234";
    private Session session;

    public MailController() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private String buildMessage (User recipient) {
        //TODO: Create getUserAppoinment function in GetData and use it here.
        String message;
        message = "Dear " + recipient.getGivenName() +
                ".\n\nYou have received a notification from Ultimate Saga Calendar Pro 365 Cloud Edition";
        return message;
    }

    public void createAndSendMail(User user) {
        String recipient;
        try {
            recipient = user.getEmail().toLowerCase();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sagacalendar365@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject("Notification from Ultimate Saga Calendar Pro 365 Cloud Edition");
            message.setText(buildMessage(user));
            System.out.println("Email sent to: " + recipient);
        } catch (AddressException e) {
            System.err.print("Failed to send email");
            e.printStackTrace();
        } catch (MessagingException e) {
            System.err.print("Failed to send email");
            e.printStackTrace();
        }
    }
}
