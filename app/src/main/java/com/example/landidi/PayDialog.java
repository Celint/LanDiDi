package com.example.landidi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class PayDialog extends Dialog {

    TextView mOrderDescribe;
    TextView mAlipayAccount;
    TextView mPayAmount;
    Button mAlipayBtn;

    String userid;
    String describe;
    String account;
    int money;

    public PayDialog(@NonNull Context context,String userid, String describe, String account, int monney) {
        super(context);
        setContentView(R.layout.dialog_pay);
        this.userid = userid;
        this.describe = describe;
        this.account = account;
        this.money = monney;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        mOrderDescribe = (TextView) findViewById(R.id.order_describe);
        mAlipayAccount = (TextView) findViewById(R.id.alipay_account);
        mPayAmount = (TextView) findViewById(R.id.pay_amount);

        mOrderDescribe.setText(describe);
        mAlipayAccount.setText(account);
        mPayAmount.setText(money + ".00");

        mAlipayBtn = (Button) findViewById(R.id.alipay_pay);
        mAlipayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();

                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(APIFetcher.paid(userid).toString());
                            }
                        }
                ).start();
                dismiss();
            }
        });
    }
}
