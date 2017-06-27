package com.lyun.library.mvvm.bindingadapter.webview;

import android.databinding.BindingAdapter;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by kelin on 16-4-29.
 */
public class ViewBindingAdapter {
    @BindingAdapter({"render"})
    public static void loadHtml(WebView webView, final String html) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    @BindingAdapter({"loadUrl"})
    public static void loadUrl(WebView webView, final String url) {
        if (!("".equals(url))) {
            webView.loadUrl(url);
        }

    }

    @BindingAdapter({"JsEnabled"})
    public static void setJavaScriptEnabled(WebView webView, final Boolean mBoolean) {
        webView.getSettings().setJavaScriptEnabled(mBoolean);
    }

    @BindingAdapter("webViewClient")
    public static void setWebViewClient(WebView webView, final Boolean mBoolean) {
        if (mBoolean) {
            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }
            });
        } else {

        }
    }
}
