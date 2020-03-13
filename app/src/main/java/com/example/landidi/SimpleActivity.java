package com.example.landidi;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class SimpleActivity extends AppCompatActivity {

    protected void setUserType(int userType) {
        SharedPreferences.Editor editor = getSharedPreferences("userType", MODE_PRIVATE).edit();
        editor.putInt("userType", userType);
        editor.commit();
    }

    protected void setAccount(String account,String xmlName) {
        SharedPreferences.Editor editor = getSharedPreferences(xmlName, MODE_PRIVATE).edit();
        editor.putString("username", account);
        editor.commit();
    }

    protected void setPassword(String password,String xmlName) {
        SharedPreferences.Editor editor = getSharedPreferences(xmlName, MODE_PRIVATE).edit();
        editor.putString("password", password);
        editor.commit();
    }

    protected void setSavePassword(boolean savePassword,String xmlName) {
        SharedPreferences.Editor editor = getSharedPreferences(xmlName, MODE_PRIVATE).edit();
        editor.putBoolean("savePassword", savePassword);
        editor.commit();
    }

    protected int getUerTye() {
        SharedPreferences pre = getSharedPreferences("userType", MODE_PRIVATE);
        return pre.getInt("userType", 0);
    }

    protected String getAccount(String xmlName) {
        SharedPreferences pre = getSharedPreferences(xmlName, MODE_PRIVATE);
        return pre.getString("username", "");
    }

    protected String getPassword(String xmlName) {
        SharedPreferences pre = getSharedPreferences(xmlName, MODE_PRIVATE);
        return pre.getString("password", "");
    }

    protected boolean getSavePassword(String xmlName) {
        SharedPreferences pre = getSharedPreferences(xmlName, MODE_PRIVATE);
        return pre.getBoolean("savePassword", false);
    }
}
