package servlet;

import model.Goods;
import model.Page;
import model.Type;
import model.User;
import service.GoodsService;
import service.HistoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "goods_detail",urlPatterns = "/goods_detail")
public class GoodsDetailServlet extends HttpServlet {

    private GoodsService gService = new GoodsService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int id = -1;
        try {
            // 获取商品 ID
            id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Received ID: " + id); // 调试用
        } catch (NumberFormatException e) {
            // 如果获取 id 出现异常，返回错误信息或重定向
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的商品 ID");
            return;
        }

        Goods g = null;
        try {
            // 获取商品详细信息
            g = gService.getGoodsById(id);
            if (g == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "商品未找到");
                return;
            }
        } catch (Exception e) {
            // 如果获取商品信息时出错，返回错误信息
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "获取商品信息失败");
            return;
        }

        // 检查商品类型是否为 null
        if (g.getType() == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "商品类型为空");
            return;
        }

        // 获取商品类型 ID
        int type = g.getType().getId();  // 获取商品类型的 ID

        // 获取分页信息
        int pageNumber = 1;  // 默认页码为1
        try {
            if (request.getParameter("pageNumber") != null) {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            }
        } catch (Exception e) {
            // 如果页码出现异常，保持默认页码
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        Page p = gService.getGoodsRecommendPage(type, pageNumber);

        // 如果查询出的分页信息为空，则设为第一页
        if (p.getTotalPage() == 0) {
            p.setTotalPage(1);
            p.setPageNumber(1);
        } else {
            if (pageNumber >= p.getTotalPage() + 1) {
                p = gService.getGoodsRecommendPage(type, p.getTotalPage());
            }
        }

        // 获取 Session 并尝试获取 user 信息
        HttpSession session = request.getSession(false); // false 表示不创建新 Session
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        // 判断 user 是否为空
        if (user != null) {
            String username=user.getUsername();
            String goodsname="";
            try {
                Goods good=gService.getGoodsById(id);
                goodsname=good.getName();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("用户"+user.getId()+" "+user.getUsername()+"浏览了商品"+id+" "+goodsname);
            // 调用 HistoryService 插入记录
            HistoryService historyService = new HistoryService();
            try {
                historyService.addHistory(user.getId(),username, goodsname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        // 设置属性用于 JSP 页面展示
        request.setAttribute("g", g);
        request.setAttribute("p", p);
        request.setAttribute("t", type);
        request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
    }
}
