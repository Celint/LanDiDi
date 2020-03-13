package com.example.landidi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddDemandActivity extends AppCompatActivity {

    EditText mDemandDescribe;
    EditText mDemandStartCity;
    EditText mDemandDestination;
    EditText mDemandDeadline;
    EditText mDemandConsigneeName;
    EditText mDemandConsigneePhone;
    EditText mDemandTotalWeight;
    EditText mDemandPrice;
    Button mDemandPublish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demand);

        mDemandDescribe = (EditText) findViewById(R.id.demand_describe);
        mDemandStartCity = (EditText) findViewById(R.id.demand_start_city);
        mDemandDestination = (EditText) findViewById(R.id.demand_destination);
        mDemandDeadline = (EditText) findViewById(R.id.demand_deadline);
        mDemandConsigneeName = (EditText) findViewById(R.id.demand_consignee_name);
        mDemandConsigneePhone = (EditText) findViewById(R.id.demand_consignee_phone);
        mDemandTotalWeight = (EditText) findViewById(R.id.demand_total_weight);
        mDemandPrice = (EditText) findViewById(R.id.demand_price);
        mDemandPublish = (Button) findViewById(R.id.demand_publish);

        mDemandPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 完成发布需求，发布完成之后弹窗并退出
                        if (sendDemand()) {
                            Looper.prepare();
                            Toast.makeText(AddDemandActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                            finish();
                            Looper.loop();
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(AddDemandActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                            finish();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });
    }
    protected boolean sendDemand(){
        boolean ok = false;
        String userid = CurrentUser.getShipper().getUserid();
        String grocery = mDemandDescribe.getText().toString();
        String ddl = mDemandDeadline.getText().toString();
        String start = mDemandStartCity.getText().toString();
        String end = mDemandDestination.getText().toString();
        String rephone = mDemandConsigneePhone.getText().toString();
        String rename  = mDemandConsigneeName.getText().toString();
        int weight = Integer.parseInt(mDemandTotalWeight.getText().toString());
        int price = Integer.parseInt(mDemandPrice.getText().toString());
        JSONObject response = APIFetcher.newDemand(userid,grocery,weight,ddl,start,end,rephone,rename,price);
        try {
            if (response.getString("status").equals("ok")) {
                String xq_id = response.getString("xq_id");
                ok = true;
                Demand demand = new Demand(CurrentUser.getShipper().getName(),xq_id,userid,grocery,ddl,start,end,rephone,rename,weight,price,0);
                System.out.println(demand.getGrocery());
                CurrentUser.getDemands().add(demand);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return ok;
    }
}
