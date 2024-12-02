package service;

import dao.GoodsDao;
import model.Goods;
import model.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GoodsService {
    private GoodsDao goodsDao;

    public GoodsService() {
        this.goodsDao = new GoodsDao(); // 使用默认的 DataSource
    }

    public GoodsService(GoodsDao goodsDao) {
        this.goodsDao = goodsDao; // 使用注入的 GoodsDao
    }


    // 通过商品ID查询商品
    public Goods getGoodsById(int id) throws SQLException {
        System.out.println("调用了goodsservice中的搜索goods函数");
        return goodsDao.selectById(id);
    }

    // 根据商品名称查询商品
    public Goods getGoodsByName(String name) throws SQLException {
        return goodsDao.selectByName(name);
    }

    // 获取所有商品列表
    public List<Goods> getAllGoods() throws SQLException {
        return goodsDao.selectAllGoods();
    }

    // 更新商品信息
    public void updateGoods(Goods goods) throws SQLException {
        goodsDao.updateGoods(goods);
    }

    // 删除商品
    public void deleteGoods(int id) throws SQLException {
        goodsDao.delete(id);
    }

    // 根据 typeId 和分页参数查询商品列表
    public List<Goods> getGoodsListByTypeId(int typeId, int pageNo, int pageSize) throws SQLException {
        return goodsDao.selectGoodsListByTypeId(typeId, pageNo, pageSize);
    }

    // 获取商品分页信息（例如：总记录数、总页数等）
    public Page getPageInfo(int typeId, int pageNo, int pageSize) throws SQLException {
        // 获取总商品数量
        int totalCount = goodsDao.getGoodsCountByTypeId(typeId);
        // 创建 Page 对象并计算总页数
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        Page page = new Page();
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);
        page.setPageNumber(pageNo);
        page.setPageSize(pageSize);
        return page;
    }
    // 根据商品类型ID和页码查询商品，并返回分页信息
    public Page<Goods> selectPageByTypeID(int typeId, int pageNumber) throws SQLException {
        // 定义每页显示的商品数
        int pageSize = 10; // 你可以根据需要修改该值
        // 获取商品总数
        int totalCount = goodsDao.getGoodsCountByTypeId(typeId);

        // 计算总页数
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        // 计算当前页的商品数据起始位置
        int offset = (pageNumber - 1) * pageSize;

        // 获取当前页商品数据
        List<Goods> goodsList = goodsDao.selectPageByTypeId(typeId, offset, pageSize);

        // 创建 Page 对象并设置分页信息
        Page<Goods> page = new Page<>();
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        page.setData(goodsList);

        return page;
    }

    /**
     * 根据关键词和页码获取分页商品信息
     *
     * @param keyword    搜索关键词
     * @param pageNumber 当前页码
     * @return 分页结果对象 Page
     */
    public Page getSearchGoodsPage(String keyword, int pageNumber) throws SQLException {
        Page page = new Page();
        int pageSize = 10; // 每页显示的商品数量

        // 获取总记录数
        int totalCount = goodsDao.countGoodsByKeyword(keyword);
        page.setTotalCount(totalCount);

        // 计算总页数
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        page.setTotalPage(totalPage);

        // 确保当前页码在合理范围内
        if (pageNumber > totalPage && totalPage > 0) {
            pageNumber = totalPage;
        }
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        page.setPageNumber(pageNumber);

        // 计算当前页的起始位置
        int startIndex = (pageNumber - 1) * pageSize;

        // 获取当前页商品列表
        List<Goods> goodsList = goodsDao.findGoodsByKeyword(keyword, startIndex, pageSize);
        page.setGoodsList(goodsList);

        return page;
    }
    public List<Map<String,Object>> getGoodsList(int recommendType) {
        List<Map<String,Object>> list=null;
        try {
            list=goodsDao.getGoodsList(recommendType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //    public Map<String,Object> getScrollGood()
//    {
//        Map<String,Object> scroolGood=null;
//        try {
//            scroolGood=gDao.getScrollGood();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return scroolGood;
//    }
    public List<Map<String,Object>> getScrollGood() {
        List<Map<String,Object>> list=null;
        try {
            list=goodsDao.getScrollGood();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Goods> selectGoodsByTypeID(int typeID, int pageNumber, int pageSize)
    {
        List<Goods> list=null;
        try {
            list=goodsDao.selectGoodsByTypeID(typeID,pageNumber,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Page getGoodsRecommendPage(int type,int pageNumber) {
        Page p = new Page();
        p.setPageNumber(pageNumber);
        int totalCount = 0;
        try {
            totalCount = goodsDao.getRecommendCountOfGoodsByTypeID(type);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.SetPageSizeAndTotalCount(8, totalCount);
        List list=null;
        try {
            list = goodsDao.selectGoodsbyRecommend(type, pageNumber, 8);
            for(Goods g : (List<Goods>)list) {
                g.setScroll(goodsDao.isScroll(g));
                g.setHot(goodsDao.isHot(g));
                g.setNew(goodsDao.isNew(g));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.setList(list);
        return p;
    }
    public void addRecommend(int id,int type) {
        try {
            goodsDao.addRecommend(id, type);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void removeRecommend(int id,int type) {
        try {
            goodsDao.removeRecommend(id, type);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void insert(Goods goods) {
        try {
            goodsDao.insert(goods);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void update(Goods goods) {
        try {
            goodsDao.update(goods);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        try {
            goodsDao.delete(id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getGoodsNameByOrderId(int orderId) throws SQLException {
        return goodsDao.getGoodsNameByOrderId(orderId);
    }
}
