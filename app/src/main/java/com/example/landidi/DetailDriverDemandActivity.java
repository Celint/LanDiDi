package com.example.landidi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailDriverDemandActivity extends AppCompatActivity {

    TextView mDriverDemandDescribe;
    TextView mDriverDemandStartCity;
    TextView mDriverDemandDestination;
    TextView mDriverDemandDeadline;
    TextView mDriverDemandReceiver;
    TextView mDriverDemandReceiverPhone;
    TextView mDriverDemandThingWeight;
    TextView mDriverDemandPrice;
    TextView mDriverDemandShipper;
    TextView mDriverDemandShipperPhone;
    ImageView mDriverDemandForPay;
    ImageView mDriverDemandAlreadyPay;

    Demand sDemand;
    Order sOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_driver_demand);

        sDemand = getIntent().getParcelableExtra("DriverDemands");
        //  通过demand获取order
        new fetchOrderTask().execute(sDemand.getXq_id());
        mDriverDemandDescribe = (TextView) findViewById(R.id.driver_demand_describe);
        mDriverDemandStartCity = (TextView) findViewById(R.id.driver_demand_start_city);
        mDriverDemandDestination = (TextView) findViewById(R.id.driver_demand_destination);
        mDriverDemandDeadline = (TextView) findViewById(R.id.driver_demand_deadline);
        mDriverDemandReceiver = (TextView) findViewById(R.id.driver_demand_receiver);
        mDriverDemandReceiverPhone = (TextView) findViewById(R.id.driver_demand_receiver_phone);
        mDriverDemandThingWeight = (TextView) findViewById(R.id.driver_demand_thing_weight);
        mDriverDemandPrice = (TextView) findViewById(R.id.driver_demand_price);
        mDriverDemandShipper = (TextView) findViewById(R.id.driver_demand_shipper);
        mDriverDemandShipperPhone = (TextView) findViewById(R.id.driver_demand_shipper_phone);
        mDriverDemandForPay = (ImageView) findViewById(R.id.driver_demand_for_pay);
        mDriverDemandAlreadyPay = (ImageView) findViewById(R.id.driver_demand_already_pay);

        mDriverDemandDescribe.setText(sDemand.getGrocery());
        mDriverDemandStartCity.setText(sDemand.getStart());
        mDriverDemandDestination.setText(sDemand.getEnd());
        mDriverDemandDeadline.setText(sDemand.getDdl());
        mDriverDemandReceiver.setText(sDemand.getRename());
        mDriverDemandReceiverPhone.setText(sDemand.getRephone());
        mDriverDemandThingWeight.setText(""+sDemand.getWeight()+"吨");
        mDriverDemandPrice.setText(sDemand.getPrice()+"RMB");

        if (sDemand.getStatus() == 2) {
            mDriverDemandForPay.setVisibility(View.GONE);
        } else {
            mDriverDemandAlreadyPay.setVisibility(View.GONE);
        }
    }
    public void setOrder(){
        mDriverDemandShipper.setText(sOrder.getHz_name());
        mDriverDemandShipperPhone.setText(sOrder.getHz_phone());
    }
    public class fetchOrderTask extends AsyncTask<String,Void,Order>{
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
