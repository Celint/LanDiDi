package com.example.landidi.shipper;

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
import com.example.landidi.DetailShipperDemandActivity;
import com.example.landidi.R;

import java.util.List;

public class ShipperMessageFragment extends Fragment {

    RecyclerView mMessageRecyclerView;

    // 从后台获取到的，已经接单或者已经付款的需求单
    List<Demand> demands = CurrentUser.getDemands();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shipper_message, container, false);
        new FetchDemandsTask().execute();
        mMessageRecyclerView = (RecyclerView) root.findViewById(R.id.shipper_message_recycler_view);
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageRecyclerView.setAdapter(new ShipperNotifyAdapter(demands));
        return root;
    }

    public class ShipperNotifyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int id;
        TextView mShipperDemandGrocery;
        TextView mShipperDemandStartCity;
        TextView mShipperDemandDestination;
        TextView mShipperDemandDriver;

        public ShipperNotifyHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mShipperDemandGrocery = (TextView) itemView.findViewById(R.id.shipper_demand_describe);
            mShipperDemandStartCity = (TextView) itemView.findViewById(R.id.shipper_demand_start_city);
            mShipperDemandDestination = (TextView) itemView.findViewById(R.id.shipper_demand_destination);
            mShipperDemandDriver = (TextView) itemView.findViewById(R.id.shipper_demand_driver);
        }

        @Override
        public void onClick(View view) {
            // 点击进入详细页面，查看或付款等
            Intent intent = new Intent(getActivity(), DetailShipperDemandActivity.class);
            intent.putExtra("Demands", demands.get(id));
            startActivity(intent);
        }
    }

    public class ShipperNotifyAdapter extends RecyclerView.Adapter<ShipperNotifyHolder> {

        private List<Demand> demands;

        public ShipperNotifyAdapter(List<Demand> demandList) {
            demands = demandList;
        }

        @Override
        public ShipperNotifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shipper_notify_item, parent, false);
            ShipperNotifyHolder holder = new ShipperNotifyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ShipperNotifyHolder holder, int position) {
            Demand demand = demands.get(position);
            holder.id = position;
            holder.mShipperDemandGrocery.setText(demand.getGrocery());
            holder.mShipperDemandStartCity.setText(demand.getStart());
            holder.mShipperDemandDestination.setText(demand.getEnd());
            if (demand.getStatus() != 0) {
                holder.mShipperDemandDriver.setText("已接单，点击查看详情");
            } else {
                holder.mShipperDemandDriver.setText("截止日期 " + demand.getDdl());
            }
            //这是要接单人的名字啊，那不就得要根据demand获取order了吗
        }

        @Override
        public int getItemCount() {
            return demands.size();
        }
    }

    private void setupAdapter() {
        mMessageRecyclerView.setAdapter(new ShipperNotifyAdapter(demands));
    }
    private class FetchDemandsTask extends AsyncTask<Void, Void, List<Demand>> {

        @Override
        protected List<Demand> doInBackground(Void... voids) {
            // 添加从后台获取需求的方法
            return APIFetcher.fetchDemandsForShipper(CurrentUser.getShipper().getUserid());
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