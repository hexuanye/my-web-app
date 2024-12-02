package servlet;

import model.Goods;
import model.Order;
import service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "goods_buy", urlPatterns = "/goods_buy")
public class GoodsBuyServlet extends HttpServlet {
    private GoodsService gService = new GoodsService();

    // 捕获SQLException异常
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order o = null;

        // 若购物车不为空，则获取购物车对象；若为空，则创建一个购物车对象
        if (request.getSession().getAttribute("order") != null) {
            o = (Order) request.getSession().getAttribute("order");
        } else {
            o = new Order();
            request.getSession().setAttribute("order", o);
        }

        try {
            // 获取商品ID并查询商品信息
            int goodsid = Integer.parseInt(request.getParameter("goodsid"));
            Goods goods = gService.getGoodsById(goodsid);

            // 如果商品库存大于0，则添加到购物车
            if (goods != null && goods.getStock() > 0) {
                o.addGoods(goods);
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("fail");
            }
        } catch (SQLException e) {
            response.getWriter().print("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            response.getWriter().print("Invalid goodsid format");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 可以在这里处理 GET 请求，如果需要的话
    }
}
