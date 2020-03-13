package com.example.landidi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class RegisterActivity extends SimpleActivity {

    RadioGroup mRegisterUserType;
    EditText mRegisterName;
    EditText mRegisterTel;
    EditText mRegisterPassword;
    EditText mRegisterPassword2;
    EditText mRegisterCity;
    EditText mRegisterIdCard;
    EditText mRegisterAlipay;
    EditText mRegisterCarId;
    EditText mRegisterCarWeight;
    Button mRegister;

    String xmlName = "";
    int mUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterUserType = (RadioGroup) findViewById(R.id.register_user_type);
        mRegisterName = (EditText) findViewById(R.id.register_name);
        mRegisterTel = (EditText) findViewById(R.id.register_tel);
        mRegisterPassword = (EditText) findViewById(R.id.register_password);
        mRegisterPassword2 = (EditText) findViewById(R.id.register_password2);
        mRegisterCity = (EditText) findViewById(R.id.register_city);
        mRegisterIdCard = (EditText) findViewById(R.id.register_id_card);
        mRegisterAlipay = (EditText) findViewById(R.id.register_alipay);
        mRegisterCarId = (EditText) findViewById(R.id.register_car_id);
        mRegisterCarWeight = (EditText) findViewById(R.id.register_car_weight);
        mRegister = (Button) findViewById(R.id.register);

        mRegisterAlipay.setVisibility(View.GONE);
        mRegisterCarId.setVisibility(View.GONE);
        mRegisterCarWeight.setVisibility(View.GONE);

        mRegisterUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.register_shipper: {
                        mUser = 0;
                        setUserType(R.id.shipper);
                        mRegisterAlipay.setVisibility(View.GONE);
                        mRegisterCarId.setVisibility(View.GONE);
                        mRegisterCarWeight.setVisibility(View.GONE);
                        break;
                    }
                    case R.id.register_driver: {
                        mUser = 1;
                        setUserType(R.id.driver);
                        mRegisterAlipay.setVisibility(View.VISIBLE);
                        mRegisterCarId.setVisibility(View.VISIBLE);
                        mRegisterCarWeight.setVisibility(View.VISIBLE);
                        break;
                    }
                    default: break;
                }
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p1 = mRegisterPassword.getText().toString();
                String p2 = mRegisterPassword2.getText().toString();
                if(!p1.equals(p2)){
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(register()){
                            if (mUser == 0) {
                                startActivity(new Intent(RegisterActivity.this, ShipperActivity.class));
                            } else {
                                startActivity(new Intent(RegisterActivity.this, DriverActivity.class));
                            }
                        }else{
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"注册失败，唯一性数据被占用。若已注册，请直接登录！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });
    }
    public boolean register(){
        boolean ok = false;
        String name = mRegisterName.getText().toString();
        String phone = mRegisterTel.getText().toString();
        String password = mRegisterPassword.getText().toString();
        String city = mRegisterCity.getText().toString();
        String sid = mRegisterIdCard.getText().toString();
        String zfb = mRegisterAlipay.getText().toString();
        String car_id = mRegisterCarId.getText().toString();
        int limit = -1;
        if(mUser == 1){
            limit = Integer.parseInt(mRegisterCarWeight.getText().toString());
        }
        JSONObject response = APIFetcher.register(mUser,name,phone,sid,password,city,zfb,limit,car_id);
        try {
            if (response.getString("status").equals("ok")) {
                ok=true;
                System.out.println(response.toString());
                String userid = response.getString("userid");
                CurrentUser.setType(mUser);
                if(mUser == 0){
                    xmlName = APIFetcher.sShipper;
                    CurrentUser.setShipper(new Shipper(userid,password,phone,name,sid,city));
                }else if(mUser == 1){
                    xmlName = APIFetcher.sDriver;
                    CurrentUser.setDriver(new Driver(userid,password,phone,name,sid,city,zfb,limit,0,car_id));
                }
                setAccount(phone,xmlName);
                setPassword(password,xmlName);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return ok;
    }
}
