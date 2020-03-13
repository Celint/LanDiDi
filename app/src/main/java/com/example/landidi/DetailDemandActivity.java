package com.example.landidi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailDemandActivity extends AppCompatActivity {

    TextView mDetailDemandDescribe;
    TextView mDetailDemandStartCity;
    TextView mDetailDemandDestination;
    TextView mDetailDemandDeadline;
    TextView mDetailDemandReceiver;
    TextView mDetailDemandReceiverPhone;
    TextView mDetailDemandThingWeight;
    TextView mDetailDemandPrice;
    TextView mDetailDemandShipper;
    TextView mDetailDemandShipperPhone;
    Button mDetailDemandReceipt;

    AlertDialog.Builder builder;

    Demand sDemand;
    Shipper shipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_demand);

        sDemand = getIntent().getParcelableExtra("Demands");
        //  通过demand获取货主，因为还没有接单
        new fetchOrderTask().execute(sDemand.getXq_id());

        mDetailDemandDescribe = (TextView) findViewById(R.id.detail_demand_describe);
        mDetailDemandStartCity = (TextView) findViewById(R.id.detail_demand_start_city);
        mDetailDemandDestination = (TextView) findViewById(R.id.detail_demand_destination);
        mDetailDemandDeadline = (TextView) findViewById(R.id.detail_demand_deadline);
        mDetailDemandReceiver = (TextView) findViewById(R.id.detail_demand_receiver);
        mDetailDemandReceiverPhone = (TextView) findViewById(R.id.detail_demand_receiver_phone);
        mDetailDemandThingWeight = (TextView) findViewById(R.id.detail_demand_thing_weight);
        mDetailDemandPrice = (TextView) findViewById(R.id.detail_demand_price);
        mDetailDemandShipper = (TextView) findViewById(R.id.detail_demand_shipper);
        mDetailDemandShipperPhone = (TextView) findViewById(R.id.detail_demand_shipper_phone);
        mDetailDemandReceipt = (Button) findViewById(R.id.detail_demand_receipt);

        mDetailDemandDescribe.setText(sDemand.getGrocery());
        mDetailDemandStartCity.setText(sDemand.getStart());
        mDetailDemandDestination.setText(sDemand.getEnd());
        mDetailDemandDeadline.setText(sDemand.getDdl());
        mDetailDemandReceiver.setText(sDemand.getRename());
        mDetailDemandReceiverPhone.setText(sDemand.getRephone());
        mDetailDemandThingWeight.setText(sDemand.getWeight()+"吨");
        mDetailDemandPrice.setText(sDemand.getPrice()+"RMB");

        mDetailDemandReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiptDemand();
            }
        });
    }

    private void receiptDemand() {
        builder = new AlertDialog.Builder(this)
                .setTitle("确定接单？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String driver_id = CurrentUser.getDriver().getUserid();
                        String xq_id = sDemand.getXq_id();
                        new AcceptTask().execute(driver_id,xq_id);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }
    public void deal(JSONObject object) {
        System.out.println(object.toString());
        try {
            if (object.getString("status").equals("ok")) {
                //表示接单成功，
                String dh_id = object.getString("dh");
                String d_id = object.getString("d_id");
                String d_name = object.getString("d_name");
                String hz_id = object.getString("hz_id");
                String hz_name = object.getString("hz_name");
                String d_zfb = object.getString("d_zfb");
                String d_phone = object.getString("d_phone");
                String hz_phone = object.getString("hz_phone");
                Order order = new Order(d_zfb,dh_id,d_id,sDemand.getXq_id(),hz_id,d_name,hz_name,hz_phone,d_phone);
                order.setPaid(false);//刚下单设置为false;
                //李天红最帅了，这里接单之后创建了一个Order，你看看要存在哪里吧,
                Toast.makeText(this,"接单成功",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,"接单失败，已经被别人抢走了哦",Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    protected class AcceptTask extends AsyncTask<String,Void,JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject response = APIFetcher.accept(strings[0],strings[1]);
            return response;
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            deal(object);
        }
    }
    public void setShipper(){
        mDetailDemandShipper.setText(shipper.getName());
        mDetailDemandShipperPhone.setText(shipper.getPhone());
    }
    public class fetchOrderTask extends AsyncTask<String,Void,Shipper> {
        @Override
        protected Shipper doInBackground(String... strings) {
            return  APIFetcher.getShipperBaseOnDemand(strings[0]);
        }

        @Override
        protected void onPostExecute(Shipper s) {
            shipper=s;
            setShipper();
        }
    }
}
