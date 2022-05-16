package com.mycompany.careers.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailSenderUtil {
    private static final Logger logger = LogManager.getLogger(EmailSenderUtil.class);

    public void sendConfirmationEmail(String email, String fullName, int applicationID) {

        logger.debug("inside sendEmail()");

        String username = "xxxx@gmail.com";
        String password = "xxxxx";

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            // message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Thank you for applying!");
            message.setText("Dear " + fullName
                    + ",\n\n We have received your application on: " + strDate + ".\n\n Your application number is "
                    + applicationID);

            Transport.send(message);

            logger.debug("inside sendEmail() - Done");

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public void sendJobAlertEmail(List<String> emailList, String jobs) {

        logger.debug("inside sendJobAlertEmail()");
        String emails = "", delim = ",";

        String username = "xxxx@gmail.com"; // set the email
        String password = "xxxxxxxx"; // set the password

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            // message.setFrom(new InternetAddress("from@gmail.com"));
            if(emailList != null){
                    emails = String.join(delim, emailList);
            }
            logger.debug("inside sendEmail() - list of emails = " +emails);

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emails)
            );
            message.setSubject("New Job Alert!");
            message.setText("Dear Applicant! You are receiving this email because you subscribed to new job alerts."
                    + ",\n\n Here's the list of new jobs - "
                    + jobs);

            Transport.send(message);

            logger.debug("inside sendEmail() - Done");

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }
}
