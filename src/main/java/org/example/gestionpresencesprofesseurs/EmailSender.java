package org.example.gestionpresencesprofesseurs;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private static final String USERNAME = "be1fbca03b0799";
    private static final String PASSWORD = "a3919026c0a320";

    public static void sendEmail(String recipient, String subject, String content) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port", "2525");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email envoyé avec succès !");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
