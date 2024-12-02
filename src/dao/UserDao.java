package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.management.Query;
import javax.sql.DataSource;
import model.User;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.util.List;

public class UserDao {
    private DataSource dataSource;
    public UserDao(){}
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private boolean isFieldExist(String field, String value) {            //判断是否存在的函数
        String sql = "SELECT COUNT(*) FROM user WHERE " + field + " = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameExist(String username) {
        return isFieldExist("username", username);
    }

    public boolean isEmailExist(String email) {
        return isFieldExist("email", email);
    }
    public int getNextId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM user"; // 获取当前最大ID并加1
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1); // 返回下一个ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 如果发生异常，返回-1或其他适当值
    }

    //根据用户名和密码查询用户
    public User selectByUsernamePassword(String username,String password)throws SQLException{
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from user where username=? and password =?";
        return r.query(sql,new BeanHandler<User>(User.class),username,password);
    }

    //根据邮箱和密码查询用户
    public User selectByEmailPassword(String email,String password)throws SQLException{
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from user where email=? and password =?";
        return r.query(sql,new BeanHandler<User>(User.class),email,password);
    }

    //通过id查询用户
    public User selectById(int id)throws SQLException{
        System.out.println("在数据库中寻找用户"+id);
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from user where id=?";
        return r.query(sql,new BeanHandler<User>(User.class),id);
    }

    //更新用户信息
    public void updateUserAddress(User user)throws SQLException {
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="update user set name=?,phone=?,address=? where id=?";
        r.update(sql,user.getName(),user.getPhone(),user.getAddress(),user.getId());
    }
    //更新用户密码
    public void updatePwd(User user)throws SQLException{
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="update user set password =? where id=?";
        r.update(sql,user.getPassword(),user.getId());
    }

    //根据id删除用户
    public void delete(int id)throws SQLException{
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="delete from user where id=?";
        r.update(sql,id);
    }
    public void addUser(User user) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user(username,email,password,name,phone,address,isadmin,usercol,isvalidate) values(?,?,?,?,?,?,?,?,?)";
        r.update(sql,user.getUsername(),user.getEmail(),user.getPassword(),user.getName(),user.getPhone(),user.getAddress(),user.isIsadmin(),user.isUsercol(),user.isIsvalidate());
    }

    //分页查询用户列表
    public List selectUserList(int pageNo,int pageSize)throws SQLException{
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from user limit ?,?";
        return r.query(sql,new BeanListHandler<User>(User.class),(pageNo-1)*pageSize,pageSize);
    }
    public int selectUserCount() throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from user";
        return r.query(sql, new ScalarHandler<Long>()).intValue();
    }


}
