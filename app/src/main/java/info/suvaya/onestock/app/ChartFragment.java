package info.suvaya.onestock.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ChartFragment extends Fragment {

    public ChartFragment() {
    }

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        setupWebView(rootView);

        return rootView;
    }

    public void RefreshChart() {
        View rootView = getActivity().findViewById(R.id.fragment_chart);
        setupWebView(rootView);
    }

    private void setupWebView(View rootView) {
        WebView webView = (WebView) rootView.findViewById(R.id.webview_chart);

        webView.setWebViewClient(new AppWebViewClient(getActivity()));
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        webView.addJavascriptInterface(new CandleStickChartJavaScriptInterface(this.getActivity()), "CandleStickChartJavaScriptInterface");
        webView.loadUrl(getString(R.string.candle_stick_chart_url));
    }
}
