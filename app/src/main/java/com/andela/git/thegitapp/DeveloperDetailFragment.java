package com.andela.git.thegitapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andela.git.thegitapp.dummy.DummyContent;
import com.andela.git.thegitapp.services.GitWebViewClient;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;

/**
 * A fragment representing a single Developer detail screen.
 * This fragment is either contained in a {@link DeveloperListActivity}
 * in two-pane mode (on tablets) or a {@link DeveloperDetailActivity}
 * on handsets.
 */
public class DeveloperDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_USER_URL = "url";
    public static final String ARG_USERNAME = "login";

    private String url;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeveloperDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_USER_URL)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            String username = getArguments().getString(ARG_USERNAME);
            url = getArguments().getString(ARG_USER_URL);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(username);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.developer_detail, container, false);
        WebView webview = (WebView) rootView.findViewById(R.id.webview);
        ProgressBar progressbar =  (ProgressBar) rootView.findViewById(R.id.loadingBar);

        webview.setWebViewClient(new GitWebViewClient(progressbar));
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("javascript:if (typeof(document.getElementsByClassName('js-header-wrapper')[0]) != 'undefined' && document.getElementsByClassName('js-header-wrapper')[0] != null){document.getElementsByClassName('js-header-wrapper')[0].style.display = 'none';} void 0");
       // webview.loadUrl("https://www.google.com");
        webview.loadUrl(url);
        webview.setVerticalScrollBarEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        return rootView;
    }
}
