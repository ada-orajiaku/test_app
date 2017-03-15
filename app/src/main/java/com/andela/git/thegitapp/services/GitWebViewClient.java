package com.andela.git.thegitapp.services;

import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by adaobifrank on 3/15/17.
 */

public class GitWebViewClient extends WebViewClient
{
    ProgressBar _progressBar;

    public GitWebViewClient(ProgressBar progressBar)
    {
        _progressBar = progressBar;
        _progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error)
    {
        super.onReceivedSslError(view,handler,error);
        handler.proceed();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
    {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse)
    {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        super.onPageFinished(view, url);
        view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('js-header-wrapper').style.display='none'; "+
                "})()");

        _progressBar.setVisibility(View.GONE);
    }
}