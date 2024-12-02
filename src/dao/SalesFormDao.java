package dao;

import model.SalesForm;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class SalesFormDao {

    // 查询所有销售记录
    public List<SalesForm> selectAll() throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT id, goods_id AS goodsId, number, money FROM salesform ORDER BY id DESC";


        // 执行查询并返回结果
        List<SalesForm> salesForms = r.query(sql, new BeanListHandler<>(SalesForm.class));

        // 输出获取的所有销售记录的信息
        System.out.println("当前查询到的销售记录数量: " + salesForms.size());
        for (SalesForm salesForm : salesForms) {
            // 输出每条记录的详细信息
            System.out.println("销售记录 ID: " + salesForm.getId());
            System.out.println("商品ID: " + salesForm.getGoodsId());
            System.out.println("数量: " + salesForm.getNumber());
            System.out.println("金额: ¥" + salesForm.getMoney());
            System.out.println("---------------");
        }

        return salesForms;
    }


    // 根据 ID 删除销售记录
    public void deleteById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "DELETE FROM salesform WHERE id = ?";
        r.update(sql, id);
        System.out.println("删除销售记录，ID: " + id);
    }

    // 根据商品 ID 查询记录是否存在
    public Integer findByGoodsId(int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT id FROM salesform WHERE goods_id = ?";
        return r.query(sql, new ScalarHandler<>(), goodsId);
    }

    // 根据商品 ID 获取当前数量和金额
    public SalesForm getByGoodsId(int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT number, money FROM salesform WHERE goods_id = ?";
        return r.query(sql, new BeanHandler<>(SalesForm.class), goodsId);
    }

    // 插入一条新的销售记录
    public void insert(SalesForm salesForm) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "INSERT INTO salesform (goods_id, number, money) VALUES (?, ?, ?)";
        r.update(sql, salesForm.getGoodsId(), salesForm.getNumber(), salesForm.getMoney());
        System.out.println("新增销售记录，商品ID: " + salesForm.getGoodsId());
    }

    // 更新现有销售记录的数量和金额
    public void update(SalesForm salesForm) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "UPDATE salesform SET number = ?, money = ? WHERE id = ?";
        r.update(sql, salesForm.getNumber(), salesForm.getMoney(), salesForm.getId());
        System.out.println("更新销售记录，ID: " + salesForm.getId());
    }

    // 插入或更新销售记录
    public void addOrUpdate(SalesForm salesForm) throws SQLException {
        Integer existingId = findByGoodsId(salesForm.getGoodsId());
        if (existingId != null) {
            System.out.println("SalesDao更新销售表");
            // 如果记录存在，更新数量和金额
            SalesForm existingRecord = getByGoodsId(salesForm.getGoodsId());
            int newNumber = existingRecord.getNumber() + salesForm.getNumber();
            int newMoney = existingRecord.getMoney() + salesForm.getMoney();
            salesForm.setId(existingId);
            salesForm.setNumber(newNumber);
            salesForm.setMoney(newMoney);
            update(salesForm);
        } else {
            System.out.println("SalesDao插入销售表");
            // 如果记录不存在，插入新记录
            insert(salesForm);
        }
    }
}
