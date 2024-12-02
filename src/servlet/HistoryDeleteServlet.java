package servlet;

import service.HistoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HistoryDeleteServlet",urlPatterns = "/admin/history_delete")
public class HistoryDeleteServlet extends HttpServlet {
    private HistoryService hService=new HistoryService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                hService.deleteHistoryById(id);

                // 删除成功，设置提示信息
                request.getSession().setAttribute("msg", "删除记录成功！");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.getSession().setAttribute("failMsg", "无效的记录 ID！");
            }
        } else {
            request.getSession().setAttribute("failMsg", "记录 ID 不能为空！");
        }

        // 重定向到记录列表页面
        response.sendRedirect(request.getContextPath() + "/admin/history");
    }
}
