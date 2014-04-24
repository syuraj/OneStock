package info.suvaya.onestock.app;

import android.widget.SearchView;

public class StockQueryTextListener implements SearchView.OnQueryTextListener {
    MainActivity activity;

    public StockQueryTextListener(MainActivity activity) {
        this.activity = activity;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        try {
            ((OneStockApplication) activity.getApplication()).setStockSymbol(query);

            activity.Refresh();

        } catch (Exception e) {
        }

        return false;
    }

}

