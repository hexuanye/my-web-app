package service;

import dao.SalesFormDao;
import model.SalesForm;

import java.sql.SQLException;
import java.util.List;

public class SalesFormService {
    private SalesFormDao salesFormDao = new SalesFormDao();

    // 获取所有销售记录
    public List<SalesForm> getAllSalesForms() {
        try {
            return salesFormDao.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // 查询失败时返回 null
        }
    }

    // 删除销售记录
    public void deleteSalesForm(int id) {
        try {
            salesFormDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateSalesForm(SalesForm salesForm) {
        try {
            salesFormDao.addOrUpdate(salesForm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新或插入销售记录时出错！");
        }
    }
}
