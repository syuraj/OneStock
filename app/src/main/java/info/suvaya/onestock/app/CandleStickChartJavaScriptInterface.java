package info.suvaya.onestock.app;

import android.app.Activity;
import android.webkit.JavascriptInterface;


public class CandleStickChartJavaScriptInterface {
    Activity activity;

    CandleStickChartJavaScriptInterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public String getStockSymbol() {
        return ((OneStockApplication) this.activity.getApplication()).getStockSymbol();
    }
}
