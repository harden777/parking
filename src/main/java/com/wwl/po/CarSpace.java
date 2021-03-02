package com.wwl.po;

/**
 * @Author wwl
 * @Date 2020/12/18 23:03
 * @Version 1.0
 */
public class CarSpace {
    private int s_id;
    private String s_name;
    private int s_type;
    private int s_state;
    private int c_id;
    private String s_code;
    private CarStation carStation;


    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_code() {
        return s_code;
    }

    public void setS_code(String s_code) {
        this.s_code = s_code;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }


    public int getS_type() {
        return s_type;
    }

    public void setS_type(int s_type) {
        this.s_type = s_type;
    }

    public int getS_state() {
        return s_state;
    }

    public void setS_state(int s_state) {
        this.s_state = s_state;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public CarStation getCarStation() {
        return carStation;
    }

    public void setCarStation(CarStation carStation) {
        this.carStation = carStation;
    }

    @Override
    public String toString() {
        return "CarSpace{" +
                "s_id=" + s_id +
                ", s_name='" + s_name + '\'' +
                ", s_type=" + s_type +
                ", s_state=" + s_state +
                ", c_id=" + c_id +
                ", s_code='" + s_code + '\'' +
                ", carStation=" + carStation +
                '}';
    }
}
