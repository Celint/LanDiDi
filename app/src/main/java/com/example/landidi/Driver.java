package com.example.landidi;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Driver implements Parcelable {

    private String userid ;
    private String password;
    private String phone;
    private String name;
    private String id_card;
    private String city;
    private String zfb;
    private String car_id;
    private int limit;
    private int driver_status;

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getId_card() {
        return id_card;
    }

    public String getCity() {
        return city;
    }

    public String getZfb() {
        return zfb;
    }

    public int getLimit() {
        return limit;
    }

    public int getStatus() {
        return driver_status;
    }

    public String getCar_id() {
        return car_id;
    }

    public Driver(String userid, String password, String phone, String name, String id_card, String city, String zfb, int limit, int status, String car_id) {
        this.userid = userid;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.id_card = id_card;
        this.city = city;
        this.zfb = zfb;
        this.limit = limit;
        this.driver_status = status;
        this.car_id = car_id;
    }
    public static Driver createDriver(JSONObject driver){
        //从JSON中获取司机信息，返回一个司机信息
        try {
            String driver_id = driver.getString("driver_id");
            String driver_name = driver.getString("driver_name");
            String phone = driver.getString("driver_phone");
            String zfb = driver.getString("driver_zfb");
            String id_card = driver.getString("driver_id_card");
            String car_id = driver.getString("car_id");
            int limit = driver.getInt("car_limit");
            String city = driver.getString("city");
            int status = driver.getInt("driver_status");
            String password = "";
            return new Driver(driver_id,password,phone,driver_name,id_card,city,zfb,limit,status,car_id);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userid);
        parcel.writeString(this.password);
        parcel.writeString(this.phone);
        parcel.writeString(this.name);
        parcel.writeString(this.id_card);
        parcel.writeString(this.city);
        parcel.writeString(this.zfb);
        parcel.writeString(this.car_id);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.driver_status);
    }

    protected Driver(Parcel parcel) {
        this.userid = parcel.readString();
        this.password = parcel.readString();
        this.phone = parcel.readString();
        this.name = parcel.readString();
        this.id_card = parcel.readString();
        this.city = parcel.readString();
        this.zfb = parcel.readString();
        this.car_id = parcel.readString();
        this.limit = parcel.readInt();
        this.driver_status = parcel.readInt();
    }

    public static final Parcelable.Creator<Driver> CREATOR = new Parcelable.Creator<Driver>() {

        @Override
        public Driver createFromParcel(Parcel parcel) {
            return new Driver(parcel);
        }

        @Override
        public Driver[] newArray(int i) {
            return new Driver[i];
        }
    };
}
