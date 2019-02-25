package services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {

    // returns :true if message was successfully send to recipient, otherwise :false
    public boolean sendMail(String sendTo, String subject, String message) {

        // sender's email props needs to be mentioned
        final String from = "future.navigator@ro.ru";
        final String username = "future.navigator@ro.ru";
        final String password = "Qwerty123";

        Authenticator authenticate= new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username, password);
            }
        };

        Properties props= System.getProperties();
        props.put("mail.smtp.host", "smtp.rambler.ru");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        @SuppressWarnings("deprecation")
        Session mailSession= Session.getInstance(props, authenticate);
        // for debugging opportunities
        //mailSession.setDebug(true);

        Message mailMessage= new MimeMessage(mailSession);

        try {
            mailMessage.setFrom(new InternetAddress(from));
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            Transport trans= mailSession.getTransport("smtp");
            trans.connect("smtp.rambler.ru", username, password);
            Transport.send(mailMessage);

            System.out.println("eMail was successfully send to " + sendTo);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();

            System.out.println("ERROR: eMail wasn't send to " + sendTo);

            return false;
        }
    }
}