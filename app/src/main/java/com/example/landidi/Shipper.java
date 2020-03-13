package com.example.landidi;

public class Shipper {
    private String userid ;
    private String password;
    private String phone;
    private String name;
    private String id_card;
    private String city;

    public Shipper(String userid, String password, String phone, String name, String id_card, String city) {
        this.userid = userid;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.id_card = id_card;
        this.city = city;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
