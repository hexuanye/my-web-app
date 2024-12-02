package dao;

import model.Type;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class TypeDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    /**
     * 根据类型ID查询类型信息
     * @param id 类型ID
     * @return Type 对象，如果未找到则返回 null
     */
    public Type selectByID(int id) {
        String sql = "SELECT * FROM type WHERE id = ?";
        try {
            return qr.query(sql, new BeanHandler<>(Type.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Type> GetAllType() throws SQLException {
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from type";
        return r.query(sql,new BeanListHandler<Type>(Type.class));
    }
    public Type selectTypeNameByID(int id) throws SQLException {
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from type where id=?";
        return r.query(sql,new BeanHandler<Type>(Type.class),id);
    }
    public Type select(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from type where id = ?";
        return r.query(sql, new BeanHandler<Type>(Type.class),id);
    }
    public void insert(Type t) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into type(name) values(?)";
        r.update(sql,t.getName());
    }
    public void update(Type t) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update type set name=? where id = ?";
        r.update(sql,t.getName(),t.getId());
    }
    public void delete(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from type where id = ?";
        r.update(sql,id);
    }
}
