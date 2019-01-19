package com.fishpond.smartapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fishpond.smartapp.R;
import com.fishpond.smartapp.bean.DeviceBeanInfo;
import com.fishpond.smartapp.manager.control.DeviceCommand;
import com.fishpond.smartapp.utils.CmdType;

import java.util.List;

/**
 * Created by zhouh on 2018/12/23.
 */
public class FishPondAdapter extends BaseAdapter{
    private Context context;
    private List<DeviceBeanInfo> bean;
    private ArrowCopyClickListener arrowCopyClickListener;

    public FishPondAdapter(Context context, List<DeviceBeanInfo> list) {
        this.context = context;
        this.bean = list;
    }

    public void setClickListener(ArrowCopyClickListener clickListener) {
        arrowCopyClickListener = clickListener;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int i) {
        return bean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.fishpond_item_layout, null);
            vh.imageView = (ImageView) view.findViewById(R.id.onoff_iv);
            vh.textView = (TextView) view.findViewById(R.id.name);
            vh.onoffBtn = (RelativeLayout) view.findViewById(R.id.onoff_ly);
            vh.arrowcopyIv = (RelativeLayout) view.findViewById(R.id.arrowcopy);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        final DeviceBeanInfo info = bean.get(i);
        String deviceName = info.getBean().getName();
        vh.textView.setText(deviceName);
        vh.arrowcopyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrowCopyClickListener != null) {
                    arrowCopyClickListener.onArrowCopyClick(i);
                }
            }
        });
        if (info.isOn()) {
            vh.imageView.setBackground(context.getResources()
                    .getDrawable(R.drawable.handle_on));
        } else {
            vh.imageView.setBackground(context.getResources()
                    .getDrawable(R.drawable.handle_off));
        }
        vh.onoffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info.isOn()) {
                    vh.imageView.setBackground(context.getResources()
                            .getDrawable(R.drawable.handle_off));
                    DeviceCommand.sendFishpondCmd(info.getBean(), CmdType.OFF);
                } else {
                    vh.imageView.setBackground(context.getResources()
                            .getDrawable(R.drawable.handle_on));
                    DeviceCommand.sendFishpondCmd(info.getBean(), CmdType.ON);
                }
            }
        });
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
        RelativeLayout onoffBtn;
        RelativeLayout arrowcopyIv;
    }

    public void notify(List<DeviceBeanInfo> b) {
        this.bean = b;
        notifyDataSetChanged();
    }

    public interface ArrowCopyClickListener{
        void onArrowCopyClick(int pos);
    }
}
