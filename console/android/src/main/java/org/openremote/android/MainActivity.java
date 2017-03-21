package org.openremote.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.openremote.android.service.TokenService;


public class MainActivity extends Activity {

    private final ConnectivityChangeReceiver connectivityChangeReceiver = new ConnectivityChangeReceiver();
    private WebView webView;
    private View errorView;
    private View noConnectivityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webview);
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }
        else {
            loadIndex();
        }
        errorView = findViewById(R.id.errorView);
        noConnectivityView = findViewById(R.id.noConnectivityView);
        ConnectivityManager cm
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean hasConnectivity = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(
                connectivityChangeReceiver,
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(connectivityChangeReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
    }

    private void loadIndex() {

        webView.addJavascriptInterface(new WebAppInterface(this),"MobileInterface");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.e("WEBVIEW", "request url :" + request.getUrl().toString());
                    Log.e("WEBVIEW", "error :" + error.getErrorCode());
                    Log.e("WEBVIEW", "error :" + error.getDescription());
                }
                if (request.getUrl().toString().equals(getString(R.string.OR_BASE_SERVER)+getString(R.string.OR_CONSOLE_URL))) {
                    webView.loadUrl("about:blank");
                    errorView.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(getString(R.string.OR_BASE_SERVER)+getString(R.string.OR_CONSOLE_URL));

    }

    public void reloadPage(View view) {
        errorView.setVisibility(View.GONE);
        webView.loadUrl(getString(R.string.OR_BASE_SERVER)+getString(R.string.OR_CONSOLE_URL));
    }


    private class WebAppInterface {


        private final TokenService tokenService;

        public WebAppInterface(Activity activity) {
            tokenService = new TokenService(activity);
        }

        @JavascriptInterface
        public String getMobileToken() {
            return tokenService.getJsonToken();
       }

        @JavascriptInterface
        public void setMobileToken(String token, String  refreshToken, String idToken) {
            tokenService.saveToken(token,refreshToken,idToken);
        }
    }



    private void onConnectivityChanged(boolean connectivity) {
           if (connectivity) {
               noConnectivityView.setVisibility(View.GONE);
           } else {
               noConnectivityView.setVisibility(View.VISIBLE);
           }
    }

    private class ConnectivityChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            onConnectivityChanged(activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        }
    }

}

