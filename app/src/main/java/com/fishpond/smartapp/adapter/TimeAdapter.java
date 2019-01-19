package com.fishpond.smartapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fishpond.smartapp.R;
import com.fishpond.smartapp.bean.time.TriggerDos;

import java.util.List;

/**
 * Created by YANYI on 2019/1/1.
 */
public class TimeAdapter extends BaseAdapter{
    private List<TriggerDos> triggerDoses;
    private Context context;

    public TimeAdapter(Context ctx, List<TriggerDos> dosList) {
        context = ctx;
        triggerDoses = dosList;
    }

    public void notify(List<TriggerDos> t) {
        triggerDoses = t;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return triggerDoses.size();
    }

    @Override
    public Object getItem(int i) {
        return triggerDoses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.time_item_layout, null);
            vh.time = (TextView) view.findViewById(R.id.ttime);
            vh.day = (TextView) view.findViewById(R.id.tday);
            vh.status = (TextView) view.findViewById(R.id.tstatus);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        final TriggerDos dos = triggerDoses.get(i);
        String s = dos.getDescription();
        if (TextUtils.isEmpty(s)) {
            vh.day.setText("配置为空");
            return view;
        }
        String[] ss = s.split("-");
        if (ss.length != 3) {
            vh.day.setText("配置有误");
            return view;
        }
        vh.time.setText(ss[0]);
        vh.day.setText(ss[2]);
        vh.status.setText(ss[1]);
        return view;
    }



    class ViewHolder {
        private TextView time;
        private TextView day;
        private TextView status;
    }
}
