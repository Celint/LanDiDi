package com.example.landidi;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser {
    private static int type = -1;
    private static Shipper sShipper = null;
    private static Driver sDriver = null;
    private static List<Demand> acceptedByDriver = new ArrayList<>();//司机已经接单的列表

    public static List<Demand> getAcceptedByDriver() {
        return acceptedByDriver;
    }

    public static void setAcceptedByDriver(List<Demand> acceptedByDriver) {
        CurrentUser.acceptedByDriver = acceptedByDriver;
    }

    //如果是司机用户那么 sDemands有效
    //如果是货主，那么两个列表都有效
    private static List<Driver> sDrivers = new ArrayList<>();
    private static List<Demand> sDemands = new ArrayList<>();

    public static List<Driver> getDrivers() {
        return sDrivers;
    }
    public static List<Demand> getDemandsForShipperWhichIdAcceptedOrPaid(){
        List<Demand> demands = new ArrayList<>();
        for(int i = 0;i<sDemands.size();++i){
            Demand demand = sDemands.get(i);
            if(demand.getStatus()!=0){
                demands.add(demand);
            }
        }
        return demands;
    }
    public static void setDrivers(List<Driver> drivers) {
        sDrivers = drivers;
    }

    public static List<Demand> getDemands() {
        return sDemands;
    }

    public static void setDemands(List<Demand> demands) {
        sDemands = demands;
    }

    public static int getType() {
        return type;
    }
    public static void logout(){
        setType(-1);
        setShipper(null);
        setDriver(null);
    }
    public static void setType(int type) {
        CurrentUser.type = type;
    }

    public static Shipper getShipper() {
        return sShipper;
    }

    public static void setShipper(Shipper shipper) {
        sShipper = shipper;
    }

    public static Driver getDriver() {
        return sDriver;
    }

    public static void setDriver(Driver driver) {
        sDriver = driver;
    }
}
