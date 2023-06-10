package com.swifticket.web.services.implementations;

import com.swifticket.web.services.EmailServices;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailServices {
    // Local SMTP server configuration
    private static final String SMTP_HOST = "smtp-host";
    private static final int SMTP_PORT = 587;
    private static final String SMTP_USERNAME = "username";
    private static final String SMTP_PASSWORD = "password";

    // Default message configuration (subject and text)
    private static final String MESSAGE_SUBJECT = "Swifticket - Código de confirmación";
    private static final String MESSAGE_TEXT = "Tu código de confirmación es: ";
    
    @Override
    public void sendConfirmationCode(String email, String confirmationCode) {
        // Configure the SMTP connection
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        // Create a session with the SMTP server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            // Create a message to be sent by the SMTP server
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(MESSAGE_SUBJECT);
            message.setText(MESSAGE_TEXT + confirmationCode);

            // Send the message
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

