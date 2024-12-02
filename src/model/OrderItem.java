package model;

public class OrderItem {
    private int id;
    private float price;
    private int amount;
    private String goodsname;  // 商品名称
    private int goods_id;    // 对应数据库的goods_id列
    private int order_id;    // 对应数据库的order_id列

    public OrderItem() {
        // 无参构造函数
    }

    // 构造函数
    public OrderItem(int id,float price, int amount, String goodsname, int goods_id,int order_id) {
        this.id=id;
        this.price = price;
        this.amount = amount;
        this.goodsname = goodsname;
        this.goods_id = goods_id;
        this.order_id=order_id;
    }
    public OrderItem(float price, int amount, String goodsname, int goods_id,int order_id) {
        this.price = price;
        this.amount = amount;
        this.goodsname = goodsname;
        this.goods_id = goods_id;
        this.order_id=order_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGoodsName() {
        return goodsname;
    }

    public void setGoodsName(String goodsName) {
        this.goodsname = goodsName;
    }

    public int getOrderId() {
        return order_id;
    }

    public void setOrderId(int orderId) {
        this.order_id = orderId;
    }

    public int getGoodsId() {
        return goods_id;
    }

    public void setGoodsId(int goodsId) {
        this.goods_id = goodsId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "price=" + price +
                ", amount=" + amount +
                ", goodsName='" + goodsname + '\'' +
                ", orderId=" + order_id +
                ", goodsId=" + goods_id +
                '}';
    }
}
