package com.example.landidi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends SimpleActivity {

    RadioGroup mUserType;
    EditText mLoginAccount;
    EditText mLoginPassword;
    CheckBox mLoginSavePassword;
    Button mLoginButton;
    TextView mGoRegister;
    String xmlName = "";
    int mUser = 0;      // 用户的类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserType = (RadioGroup) findViewById(R.id.user_type);
        mLoginAccount = (EditText) findViewById(R.id.login_account);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginSavePassword = (CheckBox) findViewById(R.id.remember_pass);
        mLoginButton = (Button) findViewById(R.id.login);
        mGoRegister = (TextView) findViewById(R.id.go_register);
        xmlName = APIFetcher.sShipper;
        readFromFile();
        mUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.shipper: {
                        mUser = 0;
                        xmlName = APIFetcher.sShipper;
                        setUserType(R.id.shipper);
                        break;
                    }
                    case R.id.driver: {
                        mUser = 1;
                        xmlName = APIFetcher.sDriver;
                        setUserType(R.id.driver);
                        break;
                    }
                    default: break;
                }
                readFromFile();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 完成登录的操作，登录之后根据是否保存密码的的checkbox进行保存密码，
                        //  登录成功跳转到MainActivity即可
                        if (login(mUser, mLoginAccount.getText().toString(), mLoginPassword.getText().toString())) {
                            if (mUser == 0) {
                                startActivity(new Intent(LoginActivity.this, ShipperActivity.class));
                            } else {
                                startActivity(new Intent(LoginActivity.this, DriverActivity.class));
                            }
                            setAccount(mLoginAccount.getText().toString(),xmlName);
                            if (mLoginSavePassword.isChecked()) {
                                setPassword(mLoginPassword.getText().toString(),xmlName);
                            }
                            else{
                                setPassword("",xmlName);
                            }
                            setSavePassword(mLoginSavePassword.isChecked(),xmlName);
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });
        mGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserType.check(getUerTye());
    }
    protected void readFromFile(){
        mLoginAccount.setText(getAccount(xmlName));
        mLoginPassword.setText(getPassword(xmlName));
        mLoginSavePassword.setChecked(getSavePassword(xmlName));
    }

    // 登录测试，如果登录成功，把网络请求封装到SimpleActivity里面的sendHttpRequest函数里面。
    //  封装的部分包括HttpURLConnection以下，只需保留urlStr、requestMethod，params即可，可以用
    //  responseText接收返回的请求数据
    private boolean login(int user, String account, String password) {
        JSONObject response = APIFetcher.login(user,account,password);
        System.out.println(response.toString());
        boolean result = false;
        try {
            if (response.getString("status").equals("ok")) {
                //登录成功之后
                result = true;
                //获取用户信息
                String userid = response.getString("userid");
                String phone = response.getString("phone");
                String city = response.getString("city");
                String id_card = response.getString("id_card");
                String name = response.getString("name");
                if(user == 1){
                    String zfb = response.getString("zfb");
                    int limit = response.getInt("limit");
                    int status = response.getInt("driver_status");
                    String car_id = response.getString("car_id");
                    CurrentUser.setDriver(new Driver(userid,password,phone,name,id_card,city,zfb,limit,status,car_id));
                }
                else{
                    CurrentUser.setShipper(new Shipper(userid,password,phone,name,id_card,city));
                }
                CurrentUser.setType(user);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }
}
