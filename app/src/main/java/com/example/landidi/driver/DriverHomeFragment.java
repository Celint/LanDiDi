package com.example.landidi.driver;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landidi.APIFetcher;
import com.example.landidi.CurrentUser;
import com.example.landidi.Demand;
import com.example.landidi.DetailDemandActivity;
import com.example.landidi.Driver;
import com.example.landidi.R;

import java.util.ArrayList;
import java.util.List;

public class DriverHomeFragment extends Fragment {

    Driver mDriver;
    TextView mLocation;
    RecyclerView mDemands;
    List<Demand> demands = CurrentUser.getDemands();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_driver_home, container, false);
        mDriver = CurrentUser.getDriver();
        mLocation = (TextView) root.findViewById(R.id.driver_location);
        mLocation.setText(mDriver.getCity());
        mDemands = (RecyclerView) root.findViewById(R.id.driver_demands);
        mDemands.setLayoutManager(new LinearLayoutManager(getContext()));
        mDemands.setAdapter(new DemandsAdapter(new ArrayList<Demand>()));
        new FetchDemandsTask().execute();
        return root;
    }

    public class DemandsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int id;
        TextView mShipperName;
        TextView mShipperThing;
        TextView mShipperThingWeight;
        TextView mShipperCity;
        TextView mRecieverCity;

        public DemandsHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mShipperName = (TextView) itemView.findViewById(R.id.shipper_name);
            mShipperThing = (TextView) itemView.findViewById(R.id.shipper_thing);
            mShipperThingWeight = (TextView) itemView.findViewById(R.id.shipper_thing_weight);
            mShipperCity = (TextView) itemView.findViewById(R.id.shipper_city);
            mRecieverCity = (TextView) itemView.findViewById(R.id.receiver_city);
        }

        @Override
        public void onClick(View view) {
            // 点击之后跳转到详细页面
            Intent intent = new Intent(getActivity(), DetailDemandActivity.class);
            intent.putExtra("Demands", demands.get(id));
            startActivity(intent);
        }
    }

    public class DemandsAdapter extends RecyclerView.Adapter<DemandsHolder> {

        // 添加需求静态变量List<Demand>
        private List<Demand> demands;

        public DemandsAdapter(List<Demand> demandList) {
            demands = demandList;
        }

        @Override
        public DemandsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.demand_item, parent, false);
            DemandsHolder holder = new DemandsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(DemandsHolder holder, int position) {
            //  setText()
            Demand demand = demands.get(position);
            holder.id = position;
            holder.mShipperName.setText(demand.getUsername());
            holder.mShipperThing.setText(demand.getGrocery());
            holder.mShipperThingWeight.setText(""+demand.getWeight()+"吨");
            holder.mShipperCity.setText("出发城市: " + demand.getStart());
            holder.mRecieverCity.setText("目的地: " + demand.getEnd());
        }

        @Override
        public int getItemCount() {
            return demands.size();
        }
    }

    private void setupAdapter() {
        // 在下面的括号内添加静态变量
        mDemands.setAdapter(new DemandsAdapter(demands));
    }

    private class FetchDemandsTask extends AsyncTask<Void, Void, List<Demand>> {

        @Override
        protected List<Demand> doInBackground(Void... voids) {
            // 添加从后台获取需求的方法
            return APIFetcher.fetchDemandsForDriver(mDriver.getCity(),mDriver.getUserid());
        }

        @Override
        protected void onPostExecute(List<Demand> demandList) {
            // 静态变量 List<Demand>等于局部变量demandList
            demands = demandList;
            CurrentUser.setDemands(demands);//货主获取到列表之后保存在CurrentUser中。
            setupAdapter();
        }
    }
}