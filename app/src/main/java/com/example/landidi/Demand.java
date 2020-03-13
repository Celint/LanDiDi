package com.example.landidi;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class Demand implements Parcelable {
    String userid;
    String grocery;
    String ddl ;
    String start;
    String end;
    String rephone;
    String rename;
    int weight;
    int price;
    int status;
    String xq_id;
    String username;

    public String getUsername() {
        return username;
    }

    public Demand(String username, String xq_id, String userid, String grocery, String ddl, String start, String end, String rephone, String rename, int weight, int price, int status) {
        this.userid = userid;
        this.username = username;
        this.xq_id = xq_id;
        this.grocery = grocery;
        this.ddl = ddl;
        this.start = start;
        this.end = end;
        this.rephone = rephone;
        this.rename = rename;
        this.weight = weight;
        this.price = price;
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public String getXq_id() {
        return xq_id;
    }

    public void setXq_id(String xq_id) {
        this.xq_id = xq_id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGrocery() {
        return grocery;
    }

    public void setGrocery(String grocery) {
        this.grocery = grocery;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRephone() {
        return rephone;
    }

    public void setRephone(String rephone) {
        this.rephone = rephone;
    }

    public String getRename() {
        return rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static Demand newDemand(JSONObject response,int type){
        Demand demand = null;
        try{
            String xq_id = response.getString("demand_id");
            String grocery = response.getString("demand_name");
            String userid = response.getString("hz_id");
            int weight = response.getInt("weight");
            int price = response.getInt("price");
            String start = response.getString("start");
            String end = response.getString("end");
            String rephone = response.getString("receiver_tel");
            String rename = response.getString("receiver_name");
            String ddl = response.getString("deadline");
            int status = response.getInt("status");
            if(type==1){
                String username = response.getString("username");
                demand = new Demand(username,xq_id,userid,grocery,ddl,start,end,rephone,rename,weight,price,status);
            }else{
                demand = new Demand(CurrentUser.getShipper().getName(),xq_id,userid,grocery,ddl,start,end,rephone,rename,weight,price,status);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return demand;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userid);
        parcel.writeString(this.grocery);
        parcel.writeString(this.ddl);
        parcel.writeString(this.start);
        parcel.writeString(this.end);
        parcel.writeString(this.rephone);
        parcel.writeString(this.rename);
        parcel.writeInt(weight);
        parcel.writeInt(price);
        parcel.writeInt(status);
        parcel.writeString(xq_id);
    }

    protected Demand(Parcel parcel) {
        this.userid = parcel.readString();
        this.grocery = parcel.readString();
        this.ddl = parcel.readString();
        this.start = parcel.readString();
        this.end = parcel.readString();
        this.rephone = parcel.readString();
        this.rename = parcel.readString();
        this.weight = parcel.readInt();
        this.price = parcel.readInt();
        this.status = parcel.readInt();
        this.xq_id = parcel.readString();
    }

    public static final Parcelable.Creator<Demand> CREATOR = new Parcelable.Creator<Demand>() {

        @Override
        public Demand createFromParcel(Parcel parcel) {
            return new Demand(parcel);
        }

        @Override
        public Demand[] newArray(int i) {
            return new Demand[i];
        }
    };
}
