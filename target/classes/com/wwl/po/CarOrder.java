package com.wwl.po;

import java.util.Date;

/**
 * @Author wwl
 * @Date 2020/12/18 23:02
 * @Version 1.0
 */
public class CarOrder {
    private int id;
    private String province;
    private String carNumber;
    private String customerName;
    private String customerPhone;
    private Date startTime;
    private Date endTime;
    private double time;
    private double cost;
    private int state;
    private int cid;
    private int sid;
    private int uid;
    private int is;
    private CarSpace carSpace;
    private User user;

    public CarSpace getCarSpace() {
        return carSpace;
    }

    public void setCarSpace(CarSpace carSpace) {
        this.carSpace = carSpace;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIs() {
        return is;
    }

    public void setIs(int is) {
        this.is = is;
    }

    @Override
    public String toString() {
        return "CarOrder{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", time=" + time +
                ", cost=" + cost +
                ", state=" + state +
                ", cid=" + cid +
                ", sid=" + sid +
                ", uid=" + uid +
                ", is=" + is +
                ", carSpace=" + carSpace +
                ", user=" + user +
                '}';
    }
}
