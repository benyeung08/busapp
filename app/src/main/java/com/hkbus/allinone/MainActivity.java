package com.hkbus.allinone;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    WebView wv;
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        wv = new WebView(this);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);
        // 安全修復
        wv.getSettings().setAllowFileAccess(false);
        wv.getSettings().setAllowContentAccess(false);
        wv.getSettings().setAllowFileAccessFromFileURLs(false);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(false);
        
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 只允許你自己域名
                if (url.contains("benyeung08.github.io") || url.contains("hketaapp.vercel.app")) {
                    return false;
                }
                return true; // 擋外部跳轉
            }
        });
        wv.loadUrl("https://benyeung08.github.io/hketaapp/");
        setContentView(wv);
    }
    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) wv.goBack();
        else super.onBackPressed();
    }
}
