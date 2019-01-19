package com.fishpond.smartapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fishpond.smartapp.R;
import com.fishpond.smartapp.view.BottomLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends Activity {
    @BindView(R.id.bottom_layout)
    BottomLayout bottomLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        bottomLayout.setSelect(3);
        bottomLayout.setBottomClick(new BottomLayout.BottomClick() {
            @Override
            public void onBottomClick(int pos) {
                if (pos == 2) {
                    startActivity(new Intent(SettingActivity.this, SelectDeviceActivity.class));
                    finish();
                } else if (pos == 1) {
                    startActivity(new Intent(SettingActivity.this, EnviomentActivity.class));
                    finish();
                }
            }
        });
    }

    @OnClick({R.id.rijiBtn, R.id.shuiwenBtn, R.id.touliaoBtn,
            R.id.shebeiBtn, R.id.jiankongBtn})
    void OnBtnClick(View v) {
        Toast.makeText(getApplicationContext(), "敬请期待!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
