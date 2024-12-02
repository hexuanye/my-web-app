package servlet;

import model.User;
import service.UserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.sql.DataSource;
@WebServlet(name = "UserLoginServlet", urlPatterns = "/user_login")
public class UserLoginServlet extends HttpServlet {
    private UserService uService;
    private DataSource dataSource;
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
        String ue = request.getParameter("ue");
        String password=request.getParameter("password");
                User user=uService.login(ue,password);
        if(user==null) {
            request.setAttribute("failMsg", "用户名，邮箱或密码错误，请重新登录！");
            request.getRequestDispatcher("/user_login.jsp").forward(request, response);
        }else{
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/header.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
