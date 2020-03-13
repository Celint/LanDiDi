package com.example.landidi;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIFetcher {
    public static String sShipper = "shipper";
    public static String sDriver = "driver";
    //用来封装后台请求
    private static String sUrl = "http://139.155.50.242:8080/DIDIAPI.jsp?operation=";
    private static JSONObject AskForData(String operation,JSONObject request){
        //对外的接口，根据operation找到相应的函数
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new String(postResponse(sUrl + operation, request.toString())));
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("后台请求出问题了");
        }
        return jsonObject;
    }
    //需要写一个发送post请求的函数
    private static byte[] postResponse(String urlSpec,String json){
        HttpURLConnection connection;
        connection = null;
        try{
            URL thisUrl = new URL(urlSpec);
            connection =  (HttpURLConnection)thisUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            System.out.println(json);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(json.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+": with "+urlSpec);
            }
            int byteread = 0;
            byte[] buffer = new byte[1024];
            while((byteread=in.read(buffer))>=0){
                out.write(buffer,0,byteread);
            }
            out.close();
            return out.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return null;
    }
    public static JSONObject login(int type,String account, String password){
        JSONObject request = new JSONObject();
        try {
            request.put("type", type);
            request.put("account",account);
            request.put("password",password);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return AskForData("login",request);
    }
    public static JSONObject register(int type,String name,String phone,String id_card,String password,String city,String zfb,int weight,String car_id){
        JSONObject request = new JSONObject();
        try {
            request.put("type", type);
            request.put("name",name);
            request.put("tel",phone);
            request.put("city",city);
            request.put("password",password);
            request.put("id_card",id_card);
            request.put("zfb",zfb);
            request.put("weight",weight);
            request.put("car_id",car_id);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return AskForData("register",request);
    }
    private static JSONObject fetchDrivers(String city_name){
        JSONObject request = new JSONObject();
        try{
            request.put("city",city_name);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return AskForData("getDrivers",request);
    }
    public static List<Driver> parseDrivers(String city){
        List<Driver> drivers = new ArrayList<>();
        JSONObject response = fetchDrivers(city);
        System.out.println(response.toString());
        try {
            JSONArray array = response.getJSONArray("drivers");
            for(int i=0;i<array.length();++i){
                JSONObject object = array.getJSONObject(i);
                drivers.add(Driver.createDriver(object));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return drivers;
    }
    public static JSONObject newDemand(String hz_id,String grocery,int weight,String ddl,String start,String end,String rephone,String rename,int price){
        JSONObject request = new JSONObject();
        try{
            request.put("userid",hz_id);
            request.put("name",grocery);
            request.put("start",start);
            request.put("end",end);
            request.put("deadline",ddl);
            request.put("re_phone",rephone);
            request.put("re_name",rename);
            request.put("weight",weight);
            request.put("price",price);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return AskForData("demand",request);
    }
    public static List<Demand> fetchDemandsForDriver(String city_name,String driverid){
        JSONObject request = new JSONObject();
        try{
            request.put("type",1);
            request.put("city",city_name);
            request.put("driver_id",driverid);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return parseDemands(AskForData("getDemands",request),1);
    }
    public static  List<Demand> fetchDemandsForShipper(String suerid){
        JSONObject request = new JSONObject();
        try{
            request.put("type",0);
            request.put("userid",suerid);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return parseDemands(AskForData("getDemands",request),0);
    }
    private static List<Demand> parseDemands(JSONObject response,int type){
        System.out.println(response.toString());
        CurrentUser.setAcceptedByDriver(new ArrayList<Demand>());
        List<Demand> demands = new ArrayList<>();
        try{
            JSONArray ds = response.getJSONArray("demands");
            for(int i = 0;i<ds.length();++i){
                JSONObject object = ds.getJSONObject(i);
                Demand dd = Demand.newDemand(object,type);
                if(type==1 && object.getBoolean("accepted")){
                    CurrentUser.getAcceptedByDriver().add(dd);
                }else{
                demands.add(dd);}
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return demands;
    }
    public static JSONObject paid(String xq_id){
        JSONObject request = new JSONObject();
        try{
            request.put("xq_id",xq_id);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return AskForData("paid",request);
    }
    public static JSONObject accept(String driver_id,String xq_id){
        JSONObject request = new JSONObject();
        try{
            request.put("driver_id",driver_id);
            request.put("xq_id",xq_id);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return AskForData("accept",request);
    }
    public static Shipper getShipperBaseOnDemand(String demandid){
        //通过需求id生成一个Shipper 的JSON
        JSONObject request = new JSONObject();
        try{
            request.put("demandid",demandid);
            request.put("request",true);

            JSONObject response = AskForData("baseOnDemand",request);
            String userid = response.getString("hz_id");
            String username = response.getString("hz_name");
            String password="";
            String hz_phone = response.getString("hz_phone");
            String idCard = response.getString("hz_idcard");
            String city = response.getString("hz_city");
            Shipper shipper = new Shipper(userid,password,hz_phone,username,idCard,city);
            return shipper;
        }catch (JSONException e){e.printStackTrace();}

        return null;
    }
    public static Order getOrderBaseOnDemand(String demandid){
        //通过需求id获得一个Order 的JSON
        JSONObject request = new JSONObject();
        try{
            request.put("demandid",demandid);
            request.put("request",false);
            JSONObject response = AskForData("baseOnDemand",request);
            String driverid = response.getString("d_id");
            String dh_id = response.getString("dh_id");
            String hz_phone = response.getString("hz_phone");
            String hz_name = response.getString("hz_name");
            String hz_id = response.getString("hz_id");
            String zfb = response.getString("d_zfb");
            String d_name = response.getString("d_name");
            String d_phone = response.getString("d_phone");
            Order order = new Order(zfb,dh_id,driverid,demandid,hz_id,d_name,hz_name,hz_phone,d_phone);
            order.setPaid(response.getBoolean("isPaid"));
            return order;
        }catch (JSONException e){e.printStackTrace();}

        return null;
    }
}
