package service;

import dao.UserDao;
import model.Page;
import model.User;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
public class UserService {
    private UserDao uDao=new UserDao();
    public UserService(){}
    public UserService(DataSource dataSource) {
        this.uDao = new UserDao(dataSource);
    }
    public boolean register(User user) {
        System.out.println("调用注册函数：验证"+user.getName()+" "+user.getEmail());
       try {
           if (uDao.isUsernameExist(user.getUsername())) {
               System.out.println("名字重复");
               return false;
           }
           if (uDao.isEmailExist(user.getEmail())) {
               System.out.println("邮件重复");
               return false;
           }
           System.out.println("用户名和地址都没有重复，可以插入");
           uDao.addUser(user);
           return true;
       }catch (SQLException e){
           e.printStackTrace();
       }
       return false;
    }

    public User login(String ue,String password){
        User user=null;
        try{
            user=uDao.selectByUsernamePassword(ue,password);
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(user!=null){
            return user;
        }
        try{
            user=uDao.selectByEmailPassword(ue,password);
        }catch(SQLException e){
            e.printStackTrace();
        }
        if(user!=null){return user;}
        return null;
    }


    public User selectById(int id) {
        System.out.println("UserService调用了函数selectById搜索"+id);
        User u = null;
        try {
            u = uDao.selectById(id);
            if (u == null) {
                System.out.println("No user found for id: " + id);
            }
        } catch (SQLException e) {
            System.out.println("没有找到用户"+id);
            e.printStackTrace();
        }
        System.out.println("找到用户"+id);
        return u;
    }

    public void updateUserAddress(User user) {
        try {
            uDao.updateUserAddress(user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void updatePwd(User user) {
        try {
            uDao.updatePwd(user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Page getUserPage(int pageNumber) {
        Page p = new Page();
        p.setPageNumber(pageNumber);
        int pageSize = 7;
        int totalCount = 0;
        try {
            totalCount = uDao.selectUserCount();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.SetPageSizeAndTotalCount(pageSize, totalCount);
        List list=null;
        try {
            list = uDao.selectUserList( pageNumber, pageSize);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.setList(list);
        return p;
    }
    public boolean delete(int id ) {
        try {
            uDao.delete(id);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
