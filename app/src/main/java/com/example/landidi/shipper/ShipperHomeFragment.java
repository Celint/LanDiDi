package com.example.landidi.shipper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landidi.APIFetcher;
import com.example.landidi.CurrentUser;
import com.example.landidi.DetailDriverActivity;
import com.example.landidi.Driver;
import com.example.landidi.R;
import com.example.landidi.Shipper;

import java.util.ArrayList;
import java.util.List;

public class ShipperHomeFragment extends Fragment {

    Shipper mShipper;
    TextView mLocation;
    RecyclerView mShipperDrivers;
    // 1.添加静态变量List<Driver>
    List<Driver> mDrivers = new ArrayList<>();
    ShipperDriverAdapter mShipperDriverAdapter = new ShipperDriverAdapter(mDrivers);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShipper = CurrentUser.getShipper();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shipper_home, container, false);

        mLocation = (TextView) root.findViewById(R.id.shipper_location);
        mLocation.setText(mShipper.getCity());
        mShipperDrivers = (RecyclerView) root.findViewById(R.id.shipper_drivers);
        mShipperDrivers.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShipperDrivers.setAdapter(mShipperDriverAdapter);
        new fetchTask().execute(mShipper.getCity());
        return root;
    }

    public class ShipperDriverHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int id;
        TextView mDriverName;
        TextView mDriverCarId;
        TextView mDriverCarWeight;
        TextView mDriverCity;

        public ShipperDriverHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDriverName = (TextView) itemView.findViewById(R.id.driver_name);
            mDriverCarId = (TextView) itemView.findViewById(R.id.driver_car_id);
            mDriverCarWeight = (TextView) itemView.findViewById(R.id.driver_car_weight);
            mDriverCity = (TextView) itemView.findViewById(R.id.driver_city);
        }

        @Override
        public void onClick(View view) {
            // 点击之后跳转到详细页面
            Intent intent = new Intent(getActivity(), DetailDriverActivity.class);
            intent.putExtra("Driver", mDrivers.get(id));
            startActivity(intent);
        }
    }

    public class ShipperDriverAdapter extends RecyclerView.Adapter<ShipperDriverHolder> {

        private List<Driver> drivers;

        public ShipperDriverAdapter(List<Driver> driverList) {
            drivers = driverList;
        }

        @Override
        public ShipperDriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.driver_item, parent, false);
            ShipperDriverHolder holder = new ShipperDriverHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ShipperDriverHolder holder, int position) {
            Driver driver = drivers.get(position);
            holder.id = position;
            holder.mDriverName.setText(driver.getName());
            holder.mDriverCarId.setText(driver.getCar_id());
            holder.mDriverCarWeight.setText("" + driver.getLimit() + "吨");
            holder.mDriverCity.setText(driver.getCity());
        }

        @Override
        public int getItemCount() {
            System.out.println(drivers.size());
            return drivers.size();
        }
    }
    public class fetchTask extends AsyncTask<String,Void,List<Driver>> {
        @Override
        protected List<Driver> doInBackground(String... strings) {
            CurrentUser.setDemands(APIFetcher.fetchDemandsForShipper(mShipper.getUserid()));
            return APIFetcher.parseDrivers(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Driver> drivers) {
            mDrivers = drivers;
            CurrentUser.setDrivers(mDrivers);
            mShipperDriverAdapter = new ShipperDriverAdapter(mDrivers);
            mShipperDrivers.setAdapter(mShipperDriverAdapter);
        }
    }
}