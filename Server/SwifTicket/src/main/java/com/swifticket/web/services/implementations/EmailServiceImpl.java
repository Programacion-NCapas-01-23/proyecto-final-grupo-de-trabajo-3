package com.swifticket.web.services.implementations;

import com.swifticket.web.services.EmailServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailServices {
    // Local SMTP server configuration
    @Value("${email-service-smtp-host}")
    private String SMTP_HOST;

    @Value("${email-service-smtp-port}")
    private String SMTP_PORT;

    @Value("${email-service-smtp-username}")
    private String SMTP_USERNAME;

    @Value("${email-service-smtp-password}")
    private String SMTP_PASSWORD;

    @Value("${project-base-url}")
    private String BASE;
    
    @Override
    public void sendVerificationAccountCode(String email, String confirmationCode) {
        String subject = "Swifticket - Verifica tu cuenta";
        String body = "Para verificar tu cuenta, haz clic en el siguiente enlace:  " +  BASE + "/auth/validate-account/" + confirmationCode;

        sendEmail(email, subject, body);
    }

    @Override
    public void sendVerificationTransactionCode(String email, String confirmationCode) {
        String subject = "Swifticket - Transferencia de tickets";
        String body = "Para confirmar la transacción, haz clic en el siguiente enlace:  " +  BASE + "/tickets/transfer/validate-transfer/" + confirmationCode;

        sendEmail(email, subject, body);
    }

    private void sendEmail(String email, String subject, String body) {
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
            message.setSubject(subject);
            message.setText(body);

            // Send the message
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

