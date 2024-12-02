package model;

import utils.PriceUtils;

import java.sql.Timestamp;
import java.util.*;

public class Order {
    private Map<Integer, OrderItem> itemMap = new HashMap<>();
    private int amount = 0;     // 总商品数量
    private float total = 0.0f; // 总金额
    private int id;
    private int status;//1未付款/2已付款/3已发货/4已完成
    private int paytype;//1微信/2支付宝/3货到付款
    private String name;
    private String phone;
    private String address;
    private Timestamp datetime;
    private User user;
    private List<OrderItem> itemList = new ArrayList<OrderItem>();


    public void addGoods(Goods g) {
        if (itemMap.containsKey(g.getId())) {
            OrderItem item = itemMap.get(g.getId());
            item.setAmount(item.getAmount() + 1);
        } else {
            OrderItem item=new OrderItem(g.getPrice(),1,g.getName(),this.id,g.getId());
            itemMap.put(g.getId(),item);
            amount++;
        }
        total= PriceUtils.add(total,g.getPrice());
    }

    public void delete(int goodsid){
        if(itemMap.containsKey(goodsid)){
            OrderItem item=itemMap.get(goodsid);
            total=PriceUtils.subtract(total,item.getAmount()*item.getPrice());
            amount-=item.getAmount();
            if(amount<0){
                amount=0;
            }
            itemMap.remove(goodsid);
        }
    }
    // Getters and Setters(gpt生成的方法)
    public Map<Integer, OrderItem> getItemMap() {
        return itemMap;
    }

    public int getAmount() {
        return amount;
    }

    public float getTotal() {
        return total;
    }
    public void setUsername(String username) {
        user = new User();
        user.setUsername(username);
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public void lessen(int goodsid) {
        if(itemMap.containsKey(goodsid)) {
            OrderItem item = itemMap.get(goodsid);
            item.setAmount(item.getAmount()-1);
            amount--;
            total = PriceUtils.subtract(total, item.getPrice());
            if(item.getAmount()<=0) {
                itemMap.remove(goodsid);
            }
        }
    }

    public void setItemMap(Map<Integer, OrderItem> itemMap) {
        this.itemMap = itemMap;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getPaytype() {
        return paytype;
    }
    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Timestamp getDatetime() {
        return datetime;
    }
    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Order() {
        super();
    }

    public String toString() {
        return "Order{" +
                "total=" + total +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", paytype='" + paytype + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", datetime=" + datetime;
    }
}
