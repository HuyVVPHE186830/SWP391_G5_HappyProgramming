package util;

import constant.IConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
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

/**
 *
 * @author yeudar
 */
public class Email {

    public boolean sendNewPassToMail(User user, String newPass) {
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
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(sendFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo, false));
            msg.setSubject("HAPPY PROGRAMING WARNING: AUTHENTIC EMAIL, DO NOT SHARE!" );
            msg.setSentDate(new Date());
            String resetLink = IConstant.GOOGLE_REDIRECT_RESET;
            msg.setContent("Your new password is: " + newPass + ". You can change your password <a href=\"" + resetLink + "\">HERE</a>", "text/html");
            Transport.send(msg);
            test = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return test;

    }

    public String generateVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }

    public boolean sendVerifyEmail(User user, HttpServletRequest request, HttpServletResponse response) {
        boolean test = false;

        String verificationCode = generateVerificationCode();

        user.setVerificationCode(verificationCode);

        String sendFrom = "yeudangyeunuoc2424@gmail.com";
        String password = "roos rltr pymd ytjs";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
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
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(sendFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo, false));
            msg.setSubject("Please verify your email");
            msg.setSentDate(new Date());

            msg.setContent("Your verification code is: " + verificationCode, "text/html");

            Transport.send(msg);
            test = true;
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("verificationCode", verificationCode);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return test;
    }

}
