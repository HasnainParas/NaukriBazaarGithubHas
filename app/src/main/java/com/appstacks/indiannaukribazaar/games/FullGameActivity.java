package com.appstacks.indiannaukribazaar.games;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.appstacks.indiannaukribazaar.R;

public class FullGameActivity extends AppCompatActivity {
private WebView webView;
private ProgressBar progressBar;
private String gameUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_game);

        initViews();
       gameUrl=getIntent().getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(gameUrl);
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress ==100)
                    progressBar.setVisibility(View.GONE);


                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void initViews() {

        webView=findViewById(R.id.game_webView);
        progressBar=findViewById(R.id.game_progressBar);

    }
}