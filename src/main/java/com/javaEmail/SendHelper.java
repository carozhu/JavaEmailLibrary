package com.javaEmail;

import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
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
 * Created by caro
 */
public class SendHelper {
    /**
     * @param theme
     * @param content
     * @param sendEmail
     * @param sendEmailPassword 密码为且需要邮箱独立密码，并需要开通smtp功能
     * @param recvEmail
     * @throws AddressException
     * @throws MessagingException
     */
    public static void sendEmail(String theme, String content, String sendEmail, String sendEmailPassword, String recvEmail) throws AddressException, MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");// Send mail	// protocol
        properties.setProperty("mail.smtp.auth", "true");// Need to verify
        properties.setProperty("mail.debug", "true");//Background debug mode
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", "220");
        MyAuthenticator smyauth = new MyAuthenticator(sendEmail, sendEmailPassword);
        //setup process output messages sent
        Session session = Session.getInstance(properties, smyauth);
        session.setDebug(true);// debug mode
        //Email message
        Message messgae = new MimeMessage(session);
        messgae.setFrom(new InternetAddress(sendEmail));// set sender
        messgae.setText(content);// set the messgae content
        //messgae.setText(getErrorInfoFromException(e));// set the messgae content
        //System.out.println(getErrorInfoFromException(e));
        System.out.println(content);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(now);
        messgae.setSubject(theme + ":" + time);// set the message subject
        //Send e-mail
        Transport tran = session.getTransport();

        // tran.connect("smtp.sohu.com", 25, "xxx@sohu.com", "xxxx");//sohu-mail
        // server to connect to
        // tran.connect("smtp.sina.com", 25, "xxx@sina.cn",
        // "xxxxxxx");//Sina-mail server to connect to
        // tran.connect("smtp.qq.com", 25, "xxx@qq.com", "xxxx");//qq-mail
        // server to connect to

        tran.connect("smtp.qq.com", 25, sendEmail, sendEmailPassword);
        tran.sendMessage(messgae, new Address[]{new InternetAddress(recvEmail)});
        tran.close();
    }


    public static String getErrorInfoFromException(Throwable e) {
        try {
            StringBuilder sb = new StringBuilder();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return (sb.append("Exception from:").append(getDeviceMsg())
                    .append("\r\n").append(sw.toString()).append("\r\n"))
                    .toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "bad get ErrorInfo From Exception";
        }
    }

    /**
     * Get phone version information
     *
     * @return String
     */
    private static String getDeviceMsg() {

        StringBuilder sb = new StringBuilder();
        Field[] fields = Build.class.getFields();

        for (Field field : fields) {
            field.setAccessible(true); // Reflection violence
            String key = field.getName();// eg:BOARD BOOTLOADER unknown
            String value;
            try {
                value = field.get(null).toString();// eg:MSM8610 unknown Coolpad
                sb.append(key).append("=").append(value).append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    private static class MyAuthenticator extends Authenticator {
        public String strUser;
        public String strPwd;

        public MyAuthenticator(String user, String password) {
            this.strUser = user;
            this.strPwd = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(strUser, strPwd);
        }
    }

}
