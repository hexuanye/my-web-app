package dao;

import model.*;
import org.apache.commons.dbutils.*;
import utils.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.dbutils.handlers.*;

import javax.servlet.ServletResponse;

public class OrderDao {
    public void insertOrder(Connection con, Order order) throws SQLException, IOException {
        System.out.println("调用了dao.insert");
        QueryRunner r = new QueryRunner();
        String sql = "insert into `order`(total,amount,status,paytype,name,phone,address,datetime,user_id) values(?,?,?,?,?,?,?,?,?)";

        System.out.println("SQL: " + sql);
        System.out.println("参数:");
        System.out.println("total: " + order.getTotal());
        System.out.println("amount: " + order.getAmount());
        System.out.println("status: " + order.getStatus());
        System.out.println("paytype: " + order.getPaytype());
        System.out.println("name: " + order.getName());
        System.out.println("phone: " + order.getPhone());
        System.out.println("address: " + order.getAddress());
        System.out.println("datetime: " + order.getDatetime());
        System.out.println("user_id: " + order.getUser().getId());

        r.update(con, sql,
                order.getTotal(), order.getAmount(), order.getStatus(),
                order.getPaytype(), order.getName(), order.getPhone(),
                order.getAddress(), order.getDatetime(), order.getUser().getId());
    }

    public int getLastInsertId(Connection con) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql = "select last_insert_id()";
        BigInteger bi = r.query(con, sql, new ScalarHandler<BigInteger>());
        return Integer.parseInt(bi.toString());
    }

    public void insertOrderItem(Connection con, OrderItem item) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql = "insert into `orderitem`(price,amount,goods_id,order_id) values(?,?,?,?)";
        r.update(con, sql, item.getPrice(), item.getAmount(), item.getGoodsId(), item.getOrderId());
    }

    public List<Order> selectAll(int userid) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from `order` where user_id=? order by datetime desc";
        return r.query(sql, new BeanListHandler<Order>(Order.class), userid);
    }

    public List<OrderItem> selectAllItem(int orderid) throws SQLException {
        System.out.println("Dao查询orderid=" + orderid + "的所有item");

        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="SELECT * FROM orderitem WHERE order_id=?";

        // 使用 BeanListHandler，自动将查询结果映射到 OrderItem 对象
        List<OrderItem> itemList = r.query(sql, new BeanListHandler<>(OrderItem.class), orderid);

        // 调试输出，检查查询结果是否为空
        if (itemList == null || itemList.isEmpty()) {
            System.out.println("没有找到与 orderid=" + orderid + " 对应的 OrderItem 记录。");
        } else {
            System.out.println("查询结果：");
            for (OrderItem item : itemList) {
                System.out.println(item);  // 输出每个 OrderItem 的 toString() 方法结果
            }
        }

        return itemList;
    }



    public int getOrderCount(int status) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "";
        if (status == 0) {
            sql = "select count(*) from `order`";
            return r.query(sql, new ScalarHandler<Long>()).intValue();
        } else {
            sql = "select count(*) from `order` where status=?";
            return r.query(sql, new ScalarHandler<Long>(), status).intValue();
        }
    }

    public List<Order> selectOrderList(int status, int pageNumber, int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        if (status == 0) {
            String sql = "select o.id,o.total,o.amount,o.status,o.paytype,o.name,o.phone,o.address,o.datetime,u.username from `order` o,user u where o.user_id=u.id order by o.datetime desc limit ?,?";
            return r.query(sql, new BeanListHandler<Order>(Order.class), (pageNumber - 1) * pageSize, pageSize);
        } else {
            String sql = "select o.id,o.total,o.amount,o.status,o.paytype,o.name,o.phone,o.address,o.datetime,u.username from `order` o,user u where o.user_id=u.id and o.status=? order by o.datetime desc limit ?,?";
            return r.query(sql, new BeanListHandler<Order>(Order.class), status, (pageNumber - 1) * pageSize, pageSize);
        }
    }

    public void updateStatus(int id, int status) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update `order` set status=? where id = ?";
        r.update(sql, status, id);
    }

    public void deleteOrder(Connection con, int id) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql = "delete from `order` where id = ?";
        r.update(con, sql, id);
    }

    public void deleteOrderItem(Connection con, int id) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql = "delete from orderitem where order_id=?";
        r.update(con, sql, id);
    }

    public int getUserId(int order_id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT user_id FROM `order` WHERE id = ?";

        // 使用 ScalarHandler<Integer> 查询 user_id
        Integer userId = r.query(sql, new ScalarHandler<Integer>(), order_id);

        // 如果查询结果为空，返回 -1，否则返回查询到的 user_id
        return userId != null ? userId : -1;
    }
}
