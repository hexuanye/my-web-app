package servlet;

import model.Goods;
import model.User;
import service.GoodsService;
import service.OrderService;
import service.UserService;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet(name = "admin_order_status",urlPatterns = "/admin/order_status")
public class AdminOrderStatusServlet extends HttpServlet {
    private OrderService oService = new OrderService();
    private UserService uService=new UserService();
    private GoodsService gService=new GoodsService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));
        oService.updateStatus(id, status);
        // 如果状态为“完成”，发送邮件通知
        if (status == 4) { // 假设状态4表示“完成”
            try {
                System.out.println("AdminOrderStatus的id："+id);//验证order的id

                int UserId=oService.getUserId(id);//先获取用户的id
                System.out.println("AdminOrderStatus获取的用户id"+id);//验证UserId
                User user=uService.selectById(UserId);//根据id获取用户所有信息
                String email=user.getEmail();
                String username=user.getName();
                String goodsName=gService.getGoodsNameByOrderId(id);//根据id获取商品的信息
                String subject="web开发测试：订单通知";
                String content=username+"web开发测试：您好，你的订单"+goodsName+"已发货，请注意签收";//名字，订单+“已完成”
                System.out.println(content);//验证最终的邮件内容
                sendEmail(email, subject, content);
            } catch (MessagingException | SQLException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("/admin/order_list?status="+status);
    }

    private void sendEmail(String recipient, String subject, String content) throws MessagingException {
        //recipient收件人    subject主题   content内容
        // 邮箱配置
        final String SMTP_HOST = "smtp.qq.com"; // SMTP服务器
        final String SMTP_PORT = "465"; // 端口号
        final String USERNAME = "3073495176@qq.com"; // 发件人邮箱
        final String PASSWORD = "oadqaksumzredfjf"; // 授权码

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        // 创建会话
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        // 创建邮件消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setText(content);

        // 发送邮件
        Transport.send(message);
    }
}
