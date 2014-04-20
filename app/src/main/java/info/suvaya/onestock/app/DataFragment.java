package info.suvaya.onestock.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DataFragment extends Fragment {

    public DataFragment() {
    }

    public static DataFragment newInstance() {
        DataFragment fragment = new DataFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);

//            WebView webView = (WebView) rootView.findViewById(R.id.webView_data);
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.setWebViewClient(new WebViewClient());
//            webView.loadUrl("http://www.google.com");

        return rootView;
    }
}
