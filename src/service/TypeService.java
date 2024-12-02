package service;

import dao.TypeDao;
import model.Type;

import java.sql.SQLException;
import java.util.List;

public class TypeService {
    private TypeDao typeDao = new TypeDao();

    /**
     * 根据类型ID查询类型名称
     * @return Type 对象，如果未找到则返回 null
     */
    public List<Type> GetAllType()
    {
        List<Type> list=null;
        try {
            list=typeDao.GetAllType();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Type selectTypeNameByID(int typeid)
    {
        Type type=null;
        try {
            type=typeDao.selectTypeNameByID(typeid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }
    public Type select(int id) {
        Type t=null;
        try {
            t = typeDao.select(id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }
    public void insert(Type t) {
        try {
            typeDao.insert(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void update(Type t) {
        try {
            typeDao.update(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean delete(int id) {
        try {
            typeDao.delete(id);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
