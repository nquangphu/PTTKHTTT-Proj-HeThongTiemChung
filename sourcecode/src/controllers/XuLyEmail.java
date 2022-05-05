package controllers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * controllers
 * Created by NhatLinh - 19127652
 * Date 5/1/2022 - 5:46 PM
 * Description: ...
 */

class SendEmailAsyns extends Thread {
    private final String email;
    private final String tieuDe;
    private final String noiDung;

    public SendEmailAsyns(String email, String tieuDe, String noiDung)
    {
        this.email = email;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        start();
    }

    public void run()
    {
        String from = "bookbooksoftwareengineering@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bookbooksoftwareengineering@gmail.com", "bookbook1!");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(tieuDe);
            message.setText(noiDung);
            Transport.send(message);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
        catch (Error error)
        {
            error.printStackTrace();
        }
    }
}

public class XuLyEmail {

    public static void guiMail(String email, String tieuDe, String noiDung)
    {
        new SendEmailAsyns(email, tieuDe, noiDung);
    }
}
