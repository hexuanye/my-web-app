package service;

import dao.*;
import model.*;
import utils.*;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;

import static java.lang.System.*;

public class OrderService {
    private OrderDao oDao = new OrderDao();
    public void addOrder(Order order) throws IOException {
        Connection con = null;
        try {
            con = DataSourceUtils.getConnection();
            con.setAutoCommit(false);

            oDao.insertOrder(con, order);
            int id = oDao.getLastInsertId(con);
            order.setId(id);
            for(OrderItem item : order.getItemMap().values()) {
                oDao.insertOrderItem(con, item);
            }

            con.commit();
            out.println("实现了insert");
        } catch (SQLException | IOException e) {
            out.println("error,无法实现insert");
            //println("SQL 错误： " + e.getMessage());


            // TODO Auto-generated catch block
            e.printStackTrace();
            if(con!=null)
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        }
    }
    public List<Order> selectAll(int userid){
        System.out.println("OrderService调用selectAll搜索用户"+userid+"的order的list");
        List<Order> list=null;
        try {
            list = oDao.selectAll(userid);
            System.out.println("order的list查询到的订单数量：" + (list != null ? list.size() : 0));// 获取并打印订单列表的大小
            for(Order o :list) {
                List<OrderItem> l = oDao.selectAllItem(o.getId());

                if (l == null || l.isEmpty()) {
                    System.out.println("Order ID " + o.getId() + " 的 OrderItem 列表为空");
                } else {
                    System.out.println("Order ID " + o.getId() + " 的 OrderItem 列表：");
                    for (OrderItem item : l) {
                        System.out.println(item);
                    }
                }

                o.setItemList(l);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
    public Page getOrderPage(int status,int pageNumber) {
        Page p = new Page();
        p.setPageNumber(pageNumber);
        int pageSize = 10;
        int totalCount = 0;
        try {
            totalCount = oDao.getOrderCount(status);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.SetPageSizeAndTotalCount(pageSize, totalCount);
        List list=null;
        try {
            list = oDao.selectOrderList(status, pageNumber, pageSize);
            for(Order o :(List<Order>)list) {
                List<OrderItem> l = oDao.selectAllItem(o.getId());
                o.setItemList(l);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.setList(list);
        return p;
    }
    public void updateStatus(int id,int status) {
        try {
            oDao.updateStatus(id, status);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        Connection con = null;
        try {
            con = DataSourceUtils.getDataSource().getConnection();
            con.setAutoCommit(false);

            oDao.deleteOrderItem(con, id);
            oDao.deleteOrder(con, id);
            con.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if(con!=null)
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        }


    }
    public int getUserId(int id) throws SQLException {
        return oDao.getUserId(id);
    }
}
