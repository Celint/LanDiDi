package com.example.landidi.shipper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.landidi.CurrentUser;
import com.example.landidi.R;
import com.example.landidi.Shipper;

public class ShipperInfoFragment extends Fragment {

    Shipper mShipper;
    TextView mShipperInfoName;
    TextView mShipperInfoTel;
    TextView mShipperInfoCity;
    TextView mShipperInfoIdCard;
    Button mShipperLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shipper_info, container, false);

        mShipper = CurrentUser.getShipper();
        mShipperInfoName = (TextView) root.findViewById(R.id.shipper_info_name);
        mShipperInfoTel = (TextView) root.findViewById(R.id.shipper_info_tel);
        mShipperInfoCity = (TextView) root.findViewById(R.id.shipper_info_city);
        mShipperInfoIdCard = (TextView) root.findViewById(R.id.shipper_info_id_card);
        mShipperLogout = (Button) root.findViewById(R.id.shipper_logout);

        mShipperInfoName.setText("姓名: " + mShipper.getName());
        mShipperInfoTel.setText("电话号码: " + mShipper.getPhone());
        mShipperInfoCity.setText("地址: " + mShipper.getCity());
        mShipperInfoIdCard.setText("身份证号码: " + mShipper.getId_card());

        mShipperLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 退出登录并删除mShipper的信息
                CurrentUser.logout();
                getActivity().finish();
            }
        });
        return root;
    }
}