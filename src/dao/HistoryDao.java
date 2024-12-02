package dao;

import jdk.internal.net.http.frame.SettingsFrame;
import model.History;
import model.Page;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {
    public List<History> selectAll() throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        // SQL 查询语句，按 datatime 列降序排列
        String sql = "SELECT * FROM history ORDER BY datatime DESC";
        // 使用 BeanListHandler 将结果集映射为 List<History>
        return r.query(sql, new BeanListHandler<>(History.class));
    }

    // 分页查询历史记录列表
    public List<History> selectHistoryList(int userId, int pageNo, int pageSize) throws SQLException {
        System.out.println("管理员搜索用户记录"+userId);
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM history WHERE user_id = ? ORDER BY datatime DESC LIMIT ?, ?";
        return r.query(sql, new BeanListHandler<>(History.class), userId, (pageNo - 1) * pageSize, pageSize);
    }

    // 查询指定用户的历史记录总数
    public int selectHistoryCount(int userId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        System.out.println("获取数量Dao "+userId);
        String sql = "SELECT COUNT(*) FROM history WHERE user_id = ?";
        try {
            System.out.println("准备查询用户历史记录总数，用户ID: " + userId);
            Long count = r.query(sql, new ScalarHandler<>(), userId);
            return count != null ? count.intValue() : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // 查询失败时返回 0
        }

    }

    // 插入一条浏览记录
    public void insertHistory(int userId,String username, String goodsname) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "INSERT INTO history (user_id,username, goodsname) VALUES (?, ?,?)";
        r.update(sql,userId,username,goodsname);
        System.out.println("调用insert插入历史记录 "+userId+" " + username + " " + goodsname);
    }
    // 删除指定 ID 的历史记录
    public void deleteHistoryById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "DELETE FROM history WHERE id = ?";
        r.update(sql, id);
        System.out.println("调用 deleteHistoryById 删除记录，ID: " + id);
    }
}
