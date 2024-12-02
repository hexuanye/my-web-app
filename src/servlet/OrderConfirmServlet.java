package servlet;

import model.*;
import org.apache.commons.beanutils.BeanUtils;
import service.OrderService;
import service.SalesFormService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "order_confirm",urlPatterns = "/order_confirm")
public class OrderConfirmServlet extends HttpServlet {
    private OrderService oService = new OrderService();
    private SalesFormService sService=new SalesFormService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order o = (Order) request.getSession().getAttribute("order");
        try {
            BeanUtils.copyProperties(o, request.getParameterMap());
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        o.setDatetime(new Timestamp(new Date().getTime()));
        o.setStatus(2);
        o.setUser((User) request.getSession().getAttribute("user"));
        oService.addOrder(o);
        System.out.println("在confirmservlet调用了oservice.addoder()//");
        request.getSession().removeAttribute("order");

        // 打印购买的商品信息到控制台
        System.out.println("支付成功！购买的商品信息如下：");
        if (o.getItemMap() != null) {
            System.out.println("购物车中记录条数: " + o.getItemMap().size());
            o.getItemMap().forEach((goodsId, orderItem) -> {
                System.out.println("商品ID: " + goodsId);
                System.out.println("商品名称: " + orderItem.getGoodsName());
                System.out.println("单价: " + orderItem.getPrice());
                System.out.println("数量: " + orderItem.getAmount());
                System.out.println("-----------------------------");

                // 插入或更新销售记录
                SalesForm salesForm = new SalesForm();
                salesForm.setGoodsId(goodsId);
                salesForm.setNumber(orderItem.getAmount());
                salesForm.setMoney((int) (orderItem.getPrice() * orderItem.getAmount()));
                sService.addOrUpdateSalesForm(salesForm);
            });
        } else {
            System.out.println("购物车为空！");
        }

        request.setAttribute("msg", "订单支付成功！");
        request.getRequestDispatcher("/order_success.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
