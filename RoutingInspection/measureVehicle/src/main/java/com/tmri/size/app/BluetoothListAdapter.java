package com.tmri.size.app;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack.Yan on 2017/10/31.
 */

public class BluetoothListAdapter extends BaseAdapter {
    private Context mContext;
    private List<BluetoothDevice> mList=new ArrayList<>();

    public BluetoothListAdapter(Context context, List<BluetoothDevice> list) {
        this.mContext = context;
        this.mList.clear();
        this.mList.addAll(list);
    }

    @Override
    public int getCount() {
        return  mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_measure_list, null);
            holder = new ViewHolder();
            holder.item_tv_name = (TextView) convertView.findViewById(R.id.item_tv_name);
            holder.item_tv_mac = (TextView) convertView.findViewById(R.id.item_tv_mac);
            holder.item_btn_conn = (Button) convertView.findViewById(R.id.item_btn_conn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BluetoothDevice device = mList.get(position);
        holder.item_tv_name.setText(device.getName());
        holder.item_tv_mac.setText(device.getAddress());
        holder.item_btn_conn.setOnClickListener(new OnItemClick(device,holder.item_btn_conn));
        return convertView;
    }

    private static class ViewHolder {
        TextView item_tv_name, item_tv_mac;
        Button item_btn_conn;
    }

    private class OnItemClick implements View.OnClickListener {
        private BluetoothDevice mDevice;
        private Button mButton;
        public OnItemClick(final BluetoothDevice device,final Button button) {
            mDevice = device;
            mButton=button;
        }

        @Override
        public void onClick(View v) {
            RequestAction.onItemClick(mDevice);
        }
    }
    public void updateList(List<BluetoothDevice> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
}
