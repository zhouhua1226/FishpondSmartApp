package com.fishpond.smartapp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fishpond.smartapp.R;


/**
 * Created by zhouh on 2018/6/20.
 */
public class TimeDialog extends Dialog{
    private Button sure;
    private Context ctx;
    private OnSureClick onSureClick;

    private int pos;

    public TimeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.ctx = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_dialog_layout);
        sure = (Button) findViewById(R.id.sure_btn);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSureClick.onTimeDeleteClick(pos);
            }
        });
    }

    public void setSureClick(OnSureClick sureClick) {
        this.onSureClick = sureClick;
    }

    public interface OnSureClick {
        void onTimeDeleteClick(int pos);
    }

    public void _show(int pos) {
        this.pos = pos;
        show();
    }
}
