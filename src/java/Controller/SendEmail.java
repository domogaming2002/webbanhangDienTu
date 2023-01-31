/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ADMIN
 */
public class SendEmail {
    
    public static String RandomToken(){
        Random rd = new Random();
        String token = String.format("%6d", rd.nextInt(999999));
        return token;
    }
    
    public static void sendEmail(String toAddress, String token) throws AddressException, MessagingException, javax.mail.MessagingException {

        String message = "Chào bạn " + toAddress + ",\n"
                + "Đây là email từ DomoShop gửi cho bạn.\n"
                + "\n"
                + "Xác thực email của bạn bằng code dưới đây: \n"
                + token + "\n"
                + "Cảm ơn đã sử dụng dịch vụ của chúng tôi!";

        String subject = "Xác thực tài khoản từ DomoShop";

        final String userName = "minhtdhe161389@fpt.edu.vn";
        final String password = "ifozfvbgkznzpdpr";

        String host = "smtp.gmail.com", port = "587";

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {

            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        // sends the e-mail
        Transport.send(msg);
    }

    
}
