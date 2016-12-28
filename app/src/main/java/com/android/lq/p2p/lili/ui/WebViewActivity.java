package com.android.lq.p2p.lili.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.BaseActivity;
import com.android.lq.p2p.lili.view.SlowlyProgressBar;

public class WebViewActivity extends BaseActivity {
    private SlowlyProgressBar slowlyProgressBar;
    private static String WEB_URL = "web_url";
    private static String  web_url = null;

    public static void startActivity(Activity activity,String data){
        Intent intent = new Intent(activity,WebViewActivity.class);
        intent.putExtra(WEB_URL,data);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web_url = getIntent().getStringExtra(WEB_URL);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initCenterView() {
        setCenterView(R.layout.activity_web_view);
        titleBarView.setRightViewVisibility();
        titleBarView.setCenterTitleVisibility(false);
        slowlyProgressBar = new SlowlyProgressBar
                (
                        findViewById(R.id.p),
                        getWindowManager().getDefaultDisplay().getWidth()
                )
                .setViewHeight(1);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                slowlyProgressBar.setProgress(newProgress);
            }

        });
        if(!TextUtils.isEmpty(web_url))  webView.loadUrl(web_url);
    }

    @Override
    public void finish() {
        super.finish();
        if(slowlyProgressBar!=null){
            slowlyProgressBar.destroy();
            slowlyProgressBar = null;
        }
    }

}
