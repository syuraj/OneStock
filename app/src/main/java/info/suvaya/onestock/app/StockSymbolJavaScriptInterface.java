package info.suvaya.onestock.app;

import android.app.Activity;
import android.webkit.JavascriptInterface;


public class StockSymbolJavaScriptInterface {
    Activity activity;

    StockSymbolJavaScriptInterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public String getStockSymbol() {
        return ((OneStockApplication) this.activity.getApplication()).getStockSymbol();
    }
}
