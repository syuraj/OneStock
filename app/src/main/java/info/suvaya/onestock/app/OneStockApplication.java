package info.suvaya.onestock.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class OneStockApplication extends Application {
    private String stockSymbol;

    public String getStockSymbol() {
        if (stockSymbol == null || stockSymbol.isEmpty()) {
            stockSymbol = getStockSymbolFromStorage();
        }
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        setStockSymbolToStorage(stockSymbol);
        this.stockSymbol = stockSymbol;
    }

    private String getStockSymbolFromStorage() {
        try {
            SharedPreferences sharedPref = this.getSharedPreferences("OneStockFile", Context.MODE_PRIVATE);

            String default_stock_symbol = getString(R.string.default_stock_symbol);
            String stock_symbol_name = getString(R.string.stock_symbol_name);

            return sharedPref.getString(stock_symbol_name, default_stock_symbol);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setStockSymbolToStorage(String stockSymbol) {
        SharedPreferences sharedPref = this.getSharedPreferences("OneStockFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (stockSymbol.substring(0, 1).equalsIgnoreCase("$")) {
            stockSymbol = stockSymbol.substring(1, stockSymbol.length());
        }

        editor.putString(getString(R.string.stock_symbol_name), stockSymbol);
        editor.commit();
    }
}
