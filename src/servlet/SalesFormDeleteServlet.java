package servlet;

import service.SalesFormService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "salesFormDelete", urlPatterns = "/admin/salesform_delete")
public class SalesFormDeleteServlet extends HttpServlet {
    private SalesFormService salesFormService = new SalesFormService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message;

        // 获取要删除的 ID
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            salesFormService.deleteSalesForm(id); // 执行删除操作
            message = "删除成功";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            message = "无效的 ID";
        }

        // 将结果返回给前端
        request.setAttribute("msg", message);
        request.getRequestDispatcher("/admin/salesForm").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
