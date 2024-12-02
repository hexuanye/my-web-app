package servlet;

import model.History;
import model.Page;
import service.HistoryService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "admin_history", urlPatterns = "/admin/history")
public class HistoryListServlet extends HttpServlet {
    private HistoryService historyService = new HistoryService();
    private  UserService uService=new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = 0; // 默认用户ID
        int pageNumber = 1; // 默认页码

        try {
            // 获取请求参数
            if (request.getParameter("id") != null) {
                userId = Integer.parseInt(request.getParameter("id"));
            }
            if (request.getParameter("pageNumber") != null) {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // 确保页码合法
        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        // 获取分页对象
        Page<History> historyPage = historyService.getHistoryPage(userId, pageNumber);

        //验证 Page 是否存储了正确的数据
        System.out.println("===== Page Debug Info =====");
        System.out.println("UserID: " + userId);
        System.out.println("PageNumber: " + pageNumber);
        System.out.println("TotalCount: " + historyPage.getTotalCount());
        System.out.println("TotalPage: " + historyPage.getTotalPage());
        System.out.println("PageSize: " + historyPage.getPageSize());
        System.out.println("CurrentPage Data: ");
        if (historyPage.getData() != null) {
            for (History history : historyPage.getData()) {
                System.out.println(history); // 确保 History 的 toString 方法已实现
            }
        } else {
            System.out.println("No data found for this page.");
        }

        // 如果没有记录，调整分页信息
        if (historyPage.getTotalPage() == 0) {
            historyPage.setTotalPage(1);
            historyPage.setPageNumber(1);
        }

        // 将分页数据设置到请求属性
        request.setAttribute("historyPage", historyPage);
        request.setAttribute("userId", userId);

        // 转发到 JSP 页面
        request.getRequestDispatcher("/admin/history.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
