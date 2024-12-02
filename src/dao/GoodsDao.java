package dao;

import model.Goods;
import model.Recommend;
import model.Type;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GoodsDao {
    private DataSource dataSource;

    public GoodsDao() {
    }

    public GoodsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 获取最大商品id并加1，用于生成下一个商品的id
    public int getNextId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM goods"; // 获取当前最大ID并加1
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
    // 通过id查询商品
    public Goods selectById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        System.out.println("select搜索id" + id);
        String sql = "SELECT * FROM goods WHERE id = ?";
        Goods g = null;

        // 手动执行查询并赋值
        try (Connection conn = DataSourceUtils.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                g = new Goods();
                g.setId(rs.getInt("id"));
                g.setName(rs.getString("name"));
                g.setCover(rs.getString("cover"));
                g.setImage1(rs.getString("image1"));
                g.setImage2(rs.getString("image2"));
                g.setPrice(rs.getFloat("price"));
                g.setIntro(rs.getString("intro"));
                g.setStock(rs.getInt("stock"));

                // 根据type_id查找对应的Type对象
                int typeId = rs.getInt("type_id");
                Type type = getTypeById(typeId); // 获取Type对象
                g.setType(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return g;
    }

    //根据order_id查询goods_name
    public String getGoodsNameByOrderId(int orderId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());

        // SQL 查询：仅获取 goods 的 name
        String sql = "SELECT goods.name FROM orderitem " +
                "JOIN goods ON orderitem.goods_id = goods.id " +
                "WHERE orderitem.order_id = ?";

        // 执行查询，返回 String 类型的商品名称
        return r.query(sql, new ScalarHandler<>(), orderId);
    }

    // 根据type_id查询Type对象
    private Type getTypeById(int typeId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM type WHERE id = ?";
        Type type = null;
        try {
            type = r.query(sql, new BeanHandler<>(Type.class), typeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    // 根据商品名称查询商品
    public Goods selectByName(String name) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM goods WHERE name = ?";
        return r.query(sql, new BeanHandler<>(Goods.class), name);
    }

    // 获取所有商品
    public List<Goods> selectAllGoods() throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM goods";
        return r.query(sql, new BeanListHandler<>(Goods.class));
    }

    // 更新商品信息
    public void updateGoods(Goods goods) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "UPDATE goods SET name = ?, cover = ?, image1 = ?, image2 = ?, price = ?, intro = ?, stock = ?, type_id = ? WHERE id = ?";
        r.update(sql, goods.getName(), goods.getCover(), goods.getImage1(), goods.getImage2(), goods.getPrice(),
                goods.getIntro(), goods.getStock(), goods.getType(), goods.getId());
    }

    // 删除商品
    public void delete(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "DELETE FROM goods WHERE id = ?";
        r.update(sql, id);
    }

    // 根据 typeId 和分页查询商品列表
    public List<Goods> selectGoodsListByTypeId(int typeId, int pageNo, int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM goods WHERE type_id = ? LIMIT ?, ?";
        return r.query(sql, new BeanListHandler<>(Goods.class), typeId, (pageNo - 1) * pageSize, pageSize);
    }

    // 获取指定 typeId 的商品总数量
    public int getGoodsCountByTypeId(int typeId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT COUNT(*) FROM goods WHERE type_id = ?";
        Long count = r.query(sql, new ScalarHandler<>(), typeId);
        return count.intValue();
    }

    // 分页查询所有商品列表（未按 typeId 分组）
    public List<Goods> selectGoodsList(int pageNo, int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM goods LIMIT ?, ?";
        return r.query(sql, new BeanListHandler<>(Goods.class), (pageNo - 1) * pageSize, pageSize);
    }

    // 根据商品类型ID分页查询商品
    public List<Goods> selectPageByTypeId(int typeId, int offset, int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM goods WHERE type_id = ? LIMIT ?, ?";
        return r.query(sql, new BeanListHandler<>(Goods.class), typeId, offset, pageSize);
    }

    // 获取符合关键字的商品总数
    public int countGoodsByKeyword(String keyword) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT COUNT(*) FROM goods WHERE name LIKE ?";
        Long count = r.query(sql, new ScalarHandler<>(), "%" + keyword + "%");
        return count.intValue();
    }

    // 根据关键字分页查询商品
    public List<Goods> findGoodsByKeyword(String keyword, int startIndex, int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM goods WHERE name LIKE ? LIMIT ?, ?";
        return r.query(sql, new BeanListHandler<>(Goods.class), "%" + keyword + "%", startIndex, pageSize);
    }

    public void insert(Goods g) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into goods(name,cover,image1,image2,price,intro,stock,type_id) values(?,?,?,?,?,?,?,?)";
        r.update(sql,g.getName(),g.getCover(),g.getImage1(),g.getImage2(),g.getPrice(),g.getIntro(),g.getStock(),g.getType().getId());
    }
    public List<Map<String,Object>> getGoodsList(int recommendType) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select g.id,g.name,g.cover,g.price,t.name typename from recommend r,goods g,type t where type=? and r.goods_id=g.id and g.type_id=t.id";
        return r.query(sql, new MapListHandler(),recommendType);
    }

    public List<Map<String,Object>> getScrollGood()throws SQLException{
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
//        String sql="select g.id,g.name,g.cover,g.price  from recommend r,goods g where type=1 and r.goods_id=g.id";
//        return r.query(sql, new MapHandler());
        String sql="select g.id,g.name,g.cover,g.price  from recommend r,goods g where r.goods_id=g.id";
        return r.query(sql, new MapListHandler());
    }
    public List<Goods> selectGoodsByTypeID(int typeID,int pageNumber,int pageSize) throws SQLException {
        if(typeID==0)
        {
            String sql="select * from goods limit ? , ?";
            QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
            return  r.query(sql,new BeanListHandler<Goods>(Goods.class),(pageNumber-1)*pageSize,pageSize);
        }
        else
        {
            String sql="select * from goods where type_id=? limit ? , ?";
            QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
            return  r.query(sql,new BeanListHandler<Goods>(Goods.class),typeID,(pageNumber-1)*pageSize,pageSize);
        }
    }
    public int getCountOfGoodsByTypeID(int typeID) throws SQLException {
        String sql="";
        QueryRunner r=new QueryRunner(DataSourceUtils.getDataSource());
        if(typeID==0)
        {
            sql="select count(*) from goods";
            return r.query(sql,new ScalarHandler<Long>()).intValue();
        }
        else
        {
            sql="select count(*) from goods where type_id=?";
            return r.query(sql,new ScalarHandler<Long>(),typeID).intValue();
        }
    }
    public List<Goods> selectGoodsbyRecommend(int type,int pageNumber,int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        if(type==0) {
            //当不添加推荐类型限制的时候
            String sql = " select g.id,g.name,g.cover,g.image1,g.image2,g.intro,g.price,g.stock,t.name typename from goods g,type t where g.type_id=t.id order by g.id limit ?,?";
            return r.query(sql, new BeanListHandler<Goods>(Goods.class),(pageNumber-1)*pageSize,pageSize);

        }

        String sql = " select g.id,g.name,g.cover,g.image1,g.image2,g.intro,g.price,g.stock,t.name typename from goods g,recommend r,type t where g.id=r.goods_id and g.type_id=t.id and r.type=? order by g.id limit ?,?";
        return r.query(sql, new BeanListHandler<Goods>(Goods.class),type,(pageNumber-1)*pageSize,pageSize);
    }
    public int getRecommendCountOfGoodsByTypeID(int type) throws SQLException {
        if(type==0)return getCountOfGoodsByTypeID(0);
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from recommend where type=?";
        return r.query(sql, new ScalarHandler<Long>(),type).intValue();
    }
    public Goods getGoodsById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select g.id,g.name,g.cover,g.image1,g.image2,g.price,g.intro,g.stock,t.id typeid,t.name typename from goods g,type t where g.id = ? and g.type_id=t.id";
        return r.query(sql, new BeanHandler<Goods>(Goods.class),id);
    }
    public int getSearchCount(String keyword) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from goods where name like ?";
        return r.query(sql, new ScalarHandler<Long>(),"%"+keyword+"%").intValue();
    }
    public List<Goods> selectSearchGoods(String keyword, int pageNumber, int pageSize) throws SQLException{
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from goods where name like ? limit ?,?";
        return r.query(sql, new BeanListHandler<Goods>(Goods.class),"%"+keyword+"%",(pageNumber-1)*pageSize,pageSize);
    }
    public boolean isScroll(Goods g) throws SQLException {
        return isRecommend(g, 1);
    }
    public boolean isHot(Goods g) throws SQLException {
        return isRecommend(g, 2);
    }
    public boolean isNew(Goods g) throws SQLException {
        return isRecommend(g, 3);
    }
    private boolean isRecommend(Goods g,int type) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from recommend where type=? and goods_id=?";
        Recommend recommend = r.query(sql, new BeanHandler<Recommend>(Recommend.class),type,g.getId());
        if(recommend==null) {
            return false;
        }else {
            return true;
        }
    }
    public void addRecommend(int id,int type) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into recommend(type,goods_id) values(?,?)";
        r.update(sql,type,id);
    }
    public void removeRecommend(int id,int type) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from recommend where type=? and goods_id=?";
        r.update(sql,type,id);
    }

    public void update(Goods g) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update goods set name=?,cover=?,image1=?,image2=?,price=?,intro=?,stock=?,type_id=? where id=?";
        r.update(sql,g.getName(),g.getCover(),g.getImage1(),g.getImage2(),g.getPrice(),g.getIntro(),g.getStock(),g.getType().getId(),g.getId());
    }

}
