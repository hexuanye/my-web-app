package model;

import java.util.List;

public class Page<T> {
    private int totalCount;  // 总记录数
    private int totalPage;   // 总页数
    private int pageSize;    // 每页显示的记录数
    private int pageNumber;  // 当前页数
    private List<T> data;    // 当前页的数据
    private List<T> goodsList;  // 当前页的商品列表

    // 默认构造函数
    public Page() {}

    // 构造函数，支持传入分页信息和数据
    public Page(int totalCount, int totalPage, int pageSize, int pageNumber, List<T> data) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.data = data;
    }

    // Getter 和 Setter 方法
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setGoodsList(List<T> goodsList) {
        this.goodsList = goodsList;
    }

    private List<Object> list;

    public void SetPageSizeAndTotalCount(int pageSize,int totalCount)
    {
        this.pageSize=pageSize;
        this.totalCount=totalCount;
        totalPage= (int)Math.ceil((double)totalCount/pageSize);
    }
    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

}
