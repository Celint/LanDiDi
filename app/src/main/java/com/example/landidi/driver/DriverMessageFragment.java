package com.example.landidi.driver;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.landidi.Demand;
import com.example.landidi.DetailDriverActivity;
import com.example.landidi.DetailDriverDemandActivity;
import com.example.landidi.Driver;
import com.example.landidi.R;

import java.util.List;

public class DriverMessageFragment extends Fragment {

    RecyclerView mMessageRecyclerView;

    // 从后台获取到的司机接的订单
    List<Demand> demands = CurrentUser.getAcceptedByDriver();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_driver_message, container, false);

        mMessageRecyclerView = (RecyclerView) root.findViewById(R.id.driver_message_recycler_view);
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new FetchDemandsTask().execute();
        return root;
    }

    public class DriverNotifyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int id;
        TextView mDriverDemandGrocery;
        TextView mDriverDemandStartCity;
        TextView mDriverDemandDestination;
        TextView mDriverDemandShipper;

        public DriverNotifyHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDriverDemandGrocery = (TextView) itemView.findViewById(R.id.driver_demand_describe);
            mDriverDemandStartCity = (TextView) itemView.findViewById(R.id.driver_demand_start_city);
            mDriverDemandDestination = (TextView) itemView.findViewById(R.id.driver_demand_destination);
            mDriverDemandShipper = (TextView)itemView.findViewById(R.id.driver_demand_shipper);
        }

        @Override
        public void onClick(View view) {
            //  点击进去的详细订单
            Intent intent = new Intent(getActivity(), DetailDriverDemandActivity.class);
            intent.putExtra("DriverDemands", demands.get(id));
            startActivity(intent);
        }
    }

    public class DriverNotifyAdapter extends RecyclerView.Adapter<DriverNotifyHolder> {

        private List<Demand> demands;

        public DriverNotifyAdapter(List<Demand> demandList) {
            demands = demandList;
        }

        @Override
        public DriverNotifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.driver_notify_item, parent, false);
            DriverNotifyHolder holder = new DriverNotifyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(DriverNotifyHolder holder, int position) {
            Demand demand = demands.get(position);
            holder.id = position;
            holder.mDriverDemandGrocery.setText(demand.getGrocery());
            holder.mDriverDemandStartCity.setText( demand.getStart());
            holder.mDriverDemandDestination.setText( demand.getEnd());
            holder.mDriverDemandShipper.setText("需求人: " + demand.getUsername());
        }

        @Override
        public int getItemCount() {
            return demands.size();
        }
    }

    private void setupAdapter() {
        mMessageRecyclerView.setAdapter(new DriverNotifyAdapter(demands));
    }

private class FetchDemandsTask extends AsyncTask<Void, Void, List<Demand>> {

    @Override
    protected List<Demand> doInBackground(Void... voids) {
        // 添加从后台获取需求的方法
        return APIFetcher.fetchDemandsForDriver(CurrentUser.getDriver().getCity(),CurrentUser.getDriver().getUserid());
    }

    @Override
    protected void onPostExecute(List<Demand> demandList) {
        // 静态变量 List<Demand>等于局部变量demandList
        CurrentUser.setDemands(demands);//货主获取到列表之后保存在CurrentUser中。
        setupAdapter();
    }
}
}