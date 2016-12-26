package com.android.lq.p2p.lili.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.lq.p2p.lili.R;


/**
 * 下载
 *
 * @author lung
 */
public class NetErrorDialogActivity extends Activity

{

    private TextView message = null;

    private Button cancel = null;

    private Button settingButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏。
        //设置窗口样式
        this.setTheme(R.style.Theme_Dialog);

        setContentView(R.layout.activity_net_error_dialog);

        CheckBox box = (CheckBox) findViewById(R.id.gprs_checkbox);
        box.setVisibility(View.GONE);

        message = (TextView) findViewById(R.id.message);
        cancel = (Button) findViewById(R.id.btn_quit);
        settingButton = (Button) findViewById(R.id.btn_ok);

        message.setText("网络错误，请检查网络设置");
        cancel.setText("取消");
        settingButton.setText("设置网络");

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        settingButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 设置网络
                Intent wifiSettingsIntent = new Intent("android.settings.SETTINGS");
                startActivity(wifiSettingsIntent);
                finish();
            }
        });
    }

}
