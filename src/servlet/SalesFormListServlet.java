package servlet;

import model.SalesForm;
import service.SalesFormService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "salesform", urlPatterns = "/admin/salesform_list")
public class SalesFormListServlet extends HttpServlet {
    private SalesFormService salesFormService = new SalesFormService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // 根据请求参数执行操作
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                salesFormService.deleteSalesForm(id); // 删除记录
                request.setAttribute("msg", "删除成功");
            } catch (NumberFormatException e) {
                request.setAttribute("failMsg", "无效的ID");
            }
        }

        // 获取所有销售记录
        List<SalesForm> salesForms = salesFormService.getAllSalesForms();
        request.setAttribute("salesForms", salesForms);

        // 转发到 JSP 页面
        request.getRequestDispatcher("/admin/salesform.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
