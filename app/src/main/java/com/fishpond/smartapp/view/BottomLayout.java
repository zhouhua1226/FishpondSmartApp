package com.fishpond.smartapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.fishpond.smartapp.R;

/**
 * Created by zhouh on 2019/1/3.
 */
public class BottomLayout extends RelativeLayout {
    private ImageButton one, two, three;
    private BottomClick bottomClick;
    public BottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_layout, this);
        one = (ImageButton) findViewById(R.id.one);
        two = (ImageButton) findViewById(R.id.two);
        three = (ImageButton) findViewById(R.id.three);
        one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect(1);
                if (bottomClick != null) {
                    bottomClick.onBottomClick(1);
                }
            }
        });
        two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect(2);
                if (bottomClick != null) {
                    bottomClick.onBottomClick(2);
                }
            }
        });
        three.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect(3);
                if (bottomClick != null) {
                    bottomClick.onBottomClick(3);
                }
            }
        });
    }

    public void setSelect(int i) {
        one.setImageResource(R.drawable.one_n);
        two.setImageResource(R.drawable.two_n);
        three.setImageResource(R.drawable.three_n);
        if (i == 1) {
            one.setImageResource(R.drawable.one_p);
        } else if (i == 2) {
            two.setImageResource(R.drawable.two_p);
        } else if (i == 3) {
            three.setImageResource(R.drawable.three_p);
        }
    }

    public interface BottomClick{
        void onBottomClick(int pos);
    }

    public void setBottomClick(BottomClick bottomClick) {
        this.bottomClick = bottomClick;
    }
}
