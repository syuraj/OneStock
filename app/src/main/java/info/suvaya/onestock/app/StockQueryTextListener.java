package info.suvaya.onestock.app;

import android.widget.SearchView;

public class StockQueryTextListener implements SearchView.OnQueryTextListener {
    NewsListFragment newsListFragment;
    ChartFragment chartFragment;

    public StockQueryTextListener(NewsListFragment newsListFragment, ChartFragment chartFragment) {
        this.newsListFragment = newsListFragment;
        this.chartFragment = chartFragment;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        try {
            ((OneStockApplication) newsListFragment.getActivity().getApplication()).setStockSymbol(query);

            newsListFragment.RefreshNewsFeed();
            chartFragment.RefreshChart();

        } catch (Exception e) {
        }

        return false;
    }

}

