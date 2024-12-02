package service;

import dao.HistoryDao;
import model.History;
import model.Page;

import java.sql.SQLException;
import java.util.List;

public class HistoryService {
    private HistoryDao hDao = new HistoryDao(); // 初始化 HistoryDAO 实例

    /**
     * 获取所有 history 记录，并按时间从近到远排序
     * @return List<History> 包含所有记录的列表
     */
    public Page<History> getHistoryPage(int userId, int pageNumber) {
        int pageSize = 10; // 每页默认 10 条记录
        Page<History> page = new Page<>();
        try {
            // 查询总记录数
            int totalCount = hDao.selectHistoryCount(userId);
            System.out.println("HistoryService搜索到用户记录数量"+totalCount);
            page.setTotalCount(totalCount);

            // 设置每页大小和总记录数，计算总页数
            page.SetPageSizeAndTotalCount(pageSize, totalCount);

            // 确保当前页码合法
            if (pageNumber <= 0) {
                pageNumber = 1;
            } else if (pageNumber > page.getTotalPage()) {
                pageNumber = page.getTotalPage();
            }
            page.setPageNumber(pageNumber);

            // 查询当前页数据
            List<History> data = hDao.selectHistoryList(userId, pageNumber, pageSize);
            page.setData(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return page;
    }


    // 添加浏览记录
    public void  addHistory(int userId,String username, String goodsname) throws SQLException {
        // 调用 HistoryDao 的方法插入记录
        hDao.insertHistory(userId,username, goodsname);
    }
    // 调用 DAO 删除记录
    public void deleteHistoryById(int id) {
        try {
            hDao.deleteHistoryById(id);
        } catch (SQLException e) {
            System.err.println("删除记录失败，ID: " + id);
            e.printStackTrace();
        }
    }
}
