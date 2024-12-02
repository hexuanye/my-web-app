package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import model.User;
import service.UserService;

@WebServlet(name = "UserRegisterServlet", urlPatterns = "/user_register")
public class UserRegisterServlet extends HttpServlet {
    private DataSource dataSource;
    private UserService uService;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDataSource"); // 替换为实际的数据源名称
            uService = new UserService(dataSource);
        } catch (NamingException e) {
            throw new ServletException("DataSource lookup failed", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        try {
            BeanUtils.copyProperties(user, request.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            request.setAttribute("msg", "数据处理错误，请重试！");
            request.getRequestDispatcher("user_register.jsp").forward(request, response);
            return;
        }

        if (uService.register(user)) {
            request.setAttribute("msg", "注册成功，请登录！");
            request.getRequestDispatcher("user_login.jsp").forward(request, response);
        } else {
            request.setAttribute("msg", "用户名或邮箱重复，请重新填写！");
            request.getRequestDispatcher("user_register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 可选择实现 doGet 方法
    }
}
