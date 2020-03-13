package com.example.landidi;

import org.json.JSONObject;

public class Order {
    String dh_id;
    String driver_id;
    String xq_id;
    String hz_id;
    String driver_name;
    String hz_name;
    String hz_phone;
    String driver_phone;
    String zfb;

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getZfb() {
        return zfb;
    }

    boolean paid = false;

    public Order(String zfb,String dh_id, String driver_id, String xq_id, String hz_id, String driver_name, String hz_name, String hz_phone, String driver_phone) {
        this.dh_id = dh_id;
        this.zfb = zfb;
        this.driver_id = driver_id;
        this.xq_id = xq_id;
        this.hz_id = hz_id;
        this.driver_name = driver_name;
        this.hz_name = hz_name;
        this.hz_phone = hz_phone;
        this.driver_phone = driver_phone;
    }

    public String getDh_id() {
        return dh_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getXq_id() {
        return xq_id;
    }

    public String getHz_id() {
        return hz_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public String getHz_name() {
        return hz_name;
    }

    public String getHz_phone() {
        return hz_phone;
    }

    public String getDriver_phone() {
        return driver_phone;
    }
    public static Order getInstance(JSONObject response){
        return null;
    }
}
