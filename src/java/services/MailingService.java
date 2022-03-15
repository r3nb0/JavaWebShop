/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author r3nb0
 */
public class MailingService {

    private String to = "";
    private String from = "boren.dujnic@gmail.com";
    private String host = "smtp.gmail.com";
    private Properties properties;

    public MailingService() {

        properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
    }

    public void sendPasswordOnEmail(String password, String email) {
        this.to = email;
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("boren.dujnic@gmail.com", "258fgh1hjf");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Little Shop - Password Request");
            message.setText("Seems like you've forgotten password for Little Shop! This is your password: " + password);

            System.out.println("Sending email...");
            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Email was not sent successfully!");
        }
    }

    public void sendOrderSuccess(String invoiceNumber, String email) {

        this.to = email;
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("boren.dujnic@gmail.com", "zyygrnujrbihvplt");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Little Shop - Order placed successfully");
            message.setText("Congratz on your order from Little Shop! This is your order number: " + invoiceNumber + ". If you have any questions, please contact us via email!");

            System.out.println("Sending email...");
            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Email was not sent successfully!");
        }
    }

    public void sendAdminOrderSuccess(String invoiceNumber, String email) {
        this.to = email;
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("boren.dujnic@gmail.com", "zyygrnujrbihvplt");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Little Shop - NEW ORDER(" + invoiceNumber + ")");
            message.setText("NEW ORDER! " + invoiceNumber + "! !");

            System.out.println("Sending email...");
            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Email was not sent successfully!");
        }
    }
}
