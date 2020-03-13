package com.example.landidi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailDriverActivity extends AppCompatActivity {

    Driver mDriver;

    TextView mDetailDriverName;
    TextView mDetailDriverTel;
    TextView mDetailDriverCity;
    TextView mDetailDriverIdCard;
    TextView mDetailDriverAlipay;
    TextView mDetailDriverCarId;
    TextView mDetailDriverWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_driver);

        mDriver = getIntent().getParcelableExtra("Driver");

        mDetailDriverName = (TextView) findViewById(R.id.detail_driver_name);
        mDetailDriverTel = (TextView) findViewById(R.id.detail_driver_tel);
        mDetailDriverCity = (TextView) findViewById(R.id.detail_driver_city);
        mDetailDriverIdCard = (TextView) findViewById(R.id.detail_driver_id_card);
        mDetailDriverAlipay = (TextView) findViewById(R.id.detail_driver_alipay);
        mDetailDriverCarId = (TextView) findViewById(R.id.detail_driver_car_id);
        mDetailDriverWeight = (TextView) findViewById(R.id.detail_driver_car_weight);

        mDetailDriverName.setText("姓名: " + mDriver.getName());
        mDetailDriverTel.setText("电话号码: " + mDriver.getPhone());
        mDetailDriverCity.setText("地址: " + mDriver.getCity());
        mDetailDriverIdCard.setText("身份证号码: " + mDriver.getId_card());
        mDetailDriverAlipay.setText("支付宝账号: " + mDriver.getZfb());
        mDetailDriverCarId.setText("车牌号: " + mDriver.getCar_id());
        mDetailDriverWeight.setText("汽车最大承重: " + mDriver.getLimit() + "吨");
    }
}
