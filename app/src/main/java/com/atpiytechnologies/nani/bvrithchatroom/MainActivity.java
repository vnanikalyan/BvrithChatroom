package com.atpiytechnologies.nani.bvrithchatroom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    WebView wv;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv = findViewById(R.id.mywebview);
        wv.setWebViewClient(new MyBrowser());


        if(Config.isNetworkStatusAvailable (getApplicationContext())) {

            dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            String url = "http://192.168.43.10:3001/";

            wv.getSettings().setLoadsImagesAutomatically(true);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv.loadUrl(url);

            wv.setWebChromeClient(new WebChromeClient(){
                public void  onProgressChanged(WebView View, int progress){
                    if (progress == 100){
                        dialog.dismiss();
                    } else {
                        dialog.show();
                    }
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                    //System.exit(0);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
