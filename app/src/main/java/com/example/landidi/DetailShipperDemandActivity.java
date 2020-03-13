package com.example.landidi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailShipperDemandActivity extends AppCompatActivity {

    TextView mShipperDemandDescribe;
    TextView mShipperDemandStartCity;
    TextView mShipperDemandDestination;
    TextView mShipperDemandDeadline;
    TextView mShipperDemandReceiver;
    TextView mShipperDemandReceiverPhone;
    TextView mShipperDemandThingWeight;
    TextView mShipperDemandPrice;
    TextView mShipperDemandDriver;
    TextView mShipperDemandDriverPhone;
    Button mShipperDemandPay;
    ImageView mShipperDemandAlreadyPay;

    Demand sDemand;
    Order sOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shipper_demand);

        sDemand = getIntent().getParcelableExtra("Demands");
        //  通过demand获取order
        mShipperDemandDescribe = (TextView) findViewById(R.id.shipper_demand_describe);
        mShipperDemandStartCity = (TextView) findViewById(R.id.shipper_demand_start_city);
        mShipperDemandDestination = (TextView) findViewById(R.id.shipper_demand_destination);
        mShipperDemandDeadline = (TextView) findViewById(R.id.shipper_demand_deadline);
        mShipperDemandReceiver = (TextView) findViewById(R.id.shipper_demand_receiver);
        mShipperDemandReceiverPhone = (TextView) findViewById(R.id.shipper_demand_receiver_phone);
        mShipperDemandThingWeight = (TextView) findViewById(R.id.shipper_demand_thing_weight);
        mShipperDemandPrice = (TextView) findViewById(R.id.shipper_demand_price);
        mShipperDemandDriver = (TextView) findViewById(R.id.shipper_demand_driver);
        mShipperDemandDriverPhone = (TextView) findViewById(R.id.shipper_demand_drive_phone);
        mShipperDemandPay = (Button) findViewById(R.id.shipper_demand_pay);
        mShipperDemandAlreadyPay = (ImageView) findViewById(R.id.shipper_demand_already_pay);

        if (sDemand.getStatus() == 2) {
            new fetchOrderTask().execute(sDemand.getXq_id());
            mShipperDemandPay.setVisibility(View.GONE);
        } else if (sDemand.getStatus() == 1) {
            new fetchOrderTask().execute(sDemand.getXq_id());
            mShipperDemandAlreadyPay.setVisibility(View.GONE);
        } else {
            mShipperDemandAlreadyPay.setVisibility(View.GONE);
            mShipperDemandPay.setVisibility(View.GONE);
        }

        mShipperDemandDescribe.setText(sDemand.getGrocery());
        mShipperDemandStartCity.setText(sDemand.getStart());
        mShipperDemandDestination.setText(sDemand.getEnd());
        mShipperDemandDeadline.setText(sDemand.getDdl());
        mShipperDemandReceiver.setText(sDemand.getRename());
        mShipperDemandReceiverPhone.setText(sDemand.getRephone());
        mShipperDemandThingWeight.setText(sDemand.getWeight() + "吨");
        mShipperDemandPrice.setText(sDemand.getPrice() + "RMB");

        mShipperDemandPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PayDialog(DetailShipperDemandActivity.this,
                        sDemand.getXq_id(), sDemand.getGrocery(),
                        sOrder.getZfb(), sDemand.getPrice()).show();
            }
        });
    }

    public void setOrder(){
        mShipperDemandDriver.setText(sOrder.getDriver_name());
        mShipperDemandDriverPhone.setText(sOrder.getDriver_phone());
    }

    public class fetchOrderTask extends AsyncTask<String,Void,Order> {
        @Override
        protected Order doInBackground(String... strings) {
            return  APIFetcher.getOrderBaseOnDemand(strings[0]);
        }

        @Override
        protected void onPostExecute(Order order) {
            sOrder = order;
            setOrder();
        }
    }
}
