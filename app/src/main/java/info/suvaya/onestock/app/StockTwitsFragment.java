package info.suvaya.onestock.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class StockTwitsFragment extends Fragment {
    public StockTwitsFragment() {
    }

    public static StockTwitsFragment newInstance() {
        return new StockTwitsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stocktwits, container, false);

        setupWebView(rootView);

        return rootView;
    }

    public void RefreshStream() {
        View rootView = getActivity().findViewById(R.id.fragment_stocktwits);
        setupWebView(rootView);
    }

    private void setupWebView(View rootView) {
        WebView webView = (WebView) rootView.findViewById(R.id.webview_stocktwits);

        webView.setWebViewClient(new AppWebViewClient(getActivity()));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        webView.addJavascriptInterface(new StockSymbolJavaScriptInterface(this.getActivity()), "StockSymbolJavaScriptInterface");
        webView.loadUrl(getString(R.string.stocktwits_stream_url));
    }
}
