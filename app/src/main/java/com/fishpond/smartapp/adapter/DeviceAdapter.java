package com.fishpond.smartapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fishpond.smartapp.R;
import com.fishpond.smartapp.bean.UserConfigBean;

import java.util.List;


/**
 * Created by zhouh on 2018/12/23.
 */
public class DeviceAdapter extends
        RecyclerView.Adapter<DeviceAdapter.ViewHolder>{
    private List<UserConfigBean> devices;
    private Context mContext;
    private OnRecyclerItemClick itemClick;

    public DeviceAdapter(Context ctx, List<UserConfigBean> devices) {
        this.devices = devices;
        mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.device_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        UserConfigBean device = devices.get(position);
        String name = device.getStoreName();
        vh.textView.setText(name);
        if (position % 4 == 1) {
            vh.imageView.setBackgroundResource(R.drawable.device1);
        } else if (position % 4 == 2){
            vh.imageView.setBackgroundResource(R.drawable.device2);
        } else if (position % 4 == 3){
            vh.imageView.setBackgroundResource(R.drawable.device3);
        } else if (position % 4 == 0){
            vh.imageView.setBackgroundResource(R.drawable.device4);
        } else {
            vh.imageView.setBackgroundResource(R.drawable.device1);
        }
        vh.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void setClick(OnRecyclerItemClick click) {
        this.itemClick = click;
    }

    public interface OnRecyclerItemClick {
        void onItemClick(int pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            textView = (TextView) itemView.findViewById(R.id.device_name_tv);
            imageView = (ImageView) itemView.findViewById(R.id.device_imageview);
        }
    }
}
