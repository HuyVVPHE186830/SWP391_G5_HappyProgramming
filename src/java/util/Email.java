package util;

import java.util.Date;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.User;
//import javax.activation.DataHandler;
//import javax.activation.DataSource;

/**
 *
 * @author yeudar
 */
public class Email {

    public boolean sendMail(User user) {
        boolean test = false;

        String sendFrom = "yeudangyeunuoc2424@gmail.com";
        String password = "roos rltr pymd ytjs";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP host
        props.put("mail.smtp.port", "587"); // TLS 587 , SSL 465 port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendFrom, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        String sendTo = user.getMail();

        MimeMessage msg = new MimeMessage(session);

        try {

//            message.setFrom(new InternetAddress(from));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject("Test Email");
//            message.setText("Hello, this is a test email!");
//content type
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            // who send
            msg.setFrom(new InternetAddress(sendFrom));
            //recip
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo, false));
            //subtitle
            msg.setSubject("Happy Programing: Your confirm code!!!" + System.currentTimeMillis());
            //set send day
            msg.setSentDate(new Date());
            //quy ddinhj emal nhan phan hoi(email khacs)
            //--  msg.setReplyTo(InternetAddress.parse(from,false));
            String resetLink = "http://localhost:8080/HappyProgramming/verify.jsp"; // Thay đổi đường dẫn cho phù hợp
            msg.setContent("Verification code: " + user.getVerificationCode() + ". Reset your password <a href=\"" + resetLink + "\">HERE</a>", "text/html");
            Transport.send(msg);
            test = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return test;
    }
}
