package model;

public class SalesForm {
    private int id;          // 唯一标识符，主键
    private int goodsId;     // 商品 ID
    private int number;      // 销售数量
    private int money;       // 销售金额

    // 无参构造函数
    public SalesForm() {
    }

    // 带参构造函数
    public SalesForm(int id, int goodsId, int number, int money) {
        this.id = id;
        this.goodsId = goodsId;
        this.number = number;
        this.money = money;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // 重写 toString 方法，便于调试输出对象信息
    @Override
    public String toString() {
        return "SalesForm{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", number=" + number +
                ", money=" + money +
                '}';
    }
}
