package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    private static final String SMTP_HOST = "smtp.example.com"; // 替换为实际的SMTP服务器
    private static final String SMTP_PORT = "465"; // 替换为实际的端口号
    private static final String USERNAME = "3073495176@qq.com"; // 替换为实际的邮箱
    private static final String PASSWORD = "oadqaksumzredfjf"; // 替换为实际的邮箱密码

    public static void sendEmail(String recipient, String subject, String content) throws MessagingException {
        // 配置邮件服务器属性
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密

        // 创建会话
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        // 创建邮件消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(content);

        // 发送邮件
        Transport.send(message);
    }
}
