/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.json.simple.JSONObject;

/**
 *
 * @author Parinda
 */
public class EmailSender {
    
    static String username = "parinda.urapi@gmail.com";
    static String password = "parinda1996";
    
    public JSONObject sendEmail(String mail){
       
        JSONObject json = new JSONObject();
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("parinda.urapi@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mail));
            message.setSubject("Spring API Testing Demo By Parinda");
            int code = (int) Math.floor(Math.random() * 999999);
            message.setText("Hi, We are from Spring API Testing Demo By Parinda\nThis is your Forget password Code"
                    + "\n\nCODE : "+code+"\n\nCopy and past this code to your Application");
            Transport.send(message);
            json.put("name", "Done");
            json.put("code",code);
        } catch (MessagingException e) {
            json.put("name", e.getMessage());
            json.put("code",-1);
        }
        return json;
   }
}
