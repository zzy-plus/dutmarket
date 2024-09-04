package com.dut.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

public class EmailUtil {

    private static final String sender = "15226603631@163.com";
    private static final String authCode = "ZZXXTLKYULGSGKVC";

    public static void sendEmail(Map<String, String> mp){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com"); // SMTP Host
        props.put("mail.smtp.port", "25"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        // 使用授权码进行认证
                        return new PasswordAuthentication(mp.get("user"), mp.get("authCode"));
                    }
                });
        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mp.get("user")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mp.get("to")));
            message.setSubject("大工菜市场");
            message.setText("你的验证码是：" + mp.get("code") + "，请勿泄露于他人，5分钟内有效。如果不是您的操作，请忽略此邮件");
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


