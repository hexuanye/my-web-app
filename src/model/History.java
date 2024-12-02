package model;

import java.time.LocalDateTime;

public class History {
    private int id; // 主键
    private int user_id; // 用户ID
    private String username; // 用户名
    private String goodsname; // 商品名称
    private LocalDateTime datatime; // 浏览时间

    // 默认构造函数
    public History() {
    }

    // 全参构造函数（根据需要可以选择性添加）
    public History(int id, int user_id, String username, String goodsname, LocalDateTime datatime) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.goodsname = goodsname;
        this.datatime = datatime;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public LocalDateTime getDatatime() {
        return datatime;
    }

    public void setDatatime(LocalDateTime datatime) {
        this.datatime = datatime;
    }

    // 重写 toString 方法，方便调试
    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                ", goodsname='" + goodsname + '\'' +
                ", datatime=" + datatime +
                '}';
    }
}
