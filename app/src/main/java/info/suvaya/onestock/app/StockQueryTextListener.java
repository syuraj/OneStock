package info.suvaya.onestock.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.SearchView;

public class StockQueryTextListener implements SearchView.OnQueryTextListener {
    NewsListFragment fragment;

    public StockQueryTextListener(NewsListFragment fragment) {
        this.fragment = fragment;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        try {
            SharedPreferences sharedPref = fragment.getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            if (query.substring(0, 1).equalsIgnoreCase("$")) {
                query = query.substring(1, query.length());
            }
            editor.putString(fragment.getString(R.string.stock_symbol_name), query);
            editor.commit();
        } catch (Exception e) {
        }

        fragment.RefreshNewsFeed(query);

        return false;
    }

}

