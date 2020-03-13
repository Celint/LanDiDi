package com.example.landidi.driver;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.landidi.CurrentUser;
import com.example.landidi.Driver;
import com.example.landidi.R;

public class DriverInfoFragment extends Fragment {

    Driver mDriver;
    TextView mDriverInfoName;
    TextView mDriverInfoTel;
    TextView mDriverInfoCity;
    TextView mDriverInfoIdCard;
    TextView mDriverInfoAlipay;
    TextView mDriverInfoCarId;
    TextView mDriverInfoCarWeight;
    Button mDriverLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_driver_info, container, false);

        mDriver = CurrentUser.getDriver();
        mDriverInfoName = (TextView) root.findViewById(R.id.driver_info_name);
        mDriverInfoTel = (TextView) root.findViewById(R.id.driver_info_tel);
        mDriverInfoCity = (TextView) root.findViewById(R.id.driver_info_city);
        mDriverInfoIdCard = (TextView) root.findViewById(R.id.driver_info_id_card);
        mDriverInfoAlipay = (TextView) root.findViewById(R.id.driver_info_alipay);
        mDriverInfoCarId = (TextView) root.findViewById(R.id.driver_info_car_id);
        mDriverInfoCarWeight = (TextView) root.findViewById(R.id.driver_info_car_weight);
        mDriverLogout = (Button) root.findViewById(R.id.driver_logout);
        mDriverInfoName.setText("姓名: " + mDriver.getName());
        mDriverInfoTel.setText("电话号码: " + mDriver.getPhone());
        mDriverInfoCity.setText("地址: " + mDriver.getCity());
        mDriverInfoIdCard.setText("身份证号码: " + mDriver.getId_card());
        mDriverInfoAlipay.setText("支付宝账号: " + mDriver.getZfb());
        mDriverInfoCarId.setText("车牌号: " + mDriver.getCar_id());
        mDriverInfoCarWeight.setText("汽车最大承重: " + mDriver.getLimit() + "吨");

        mDriverLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 清空用户信息再退出
                CurrentUser.logout();
                getActivity().finish();
            }
        });

        return root;
    }
}