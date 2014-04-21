package info.suvaya.onestock.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.Locale;

public class MainActivity extends Activity {

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private String getStockSymbol() {
        String stock_symbol = ((OneStockApplication) getApplication()).getStockSymbol();
        if (stock_symbol == null || stock_symbol.isEmpty()) {
            try {
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                String default_stock_symbol = getString(R.string.default_stock_symbol);
                String stock_symbol_name = getString(R.string.stock_symbol_name);

                return sharedPref.getString(stock_symbol_name, default_stock_symbol);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return stock_symbol;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_action_buttons, menu);

        setupSearchView(menu);

        return true;
    }

    private void setupSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint("Enter Stock Symbol");

        try {
            mSearchView.setOnQueryTextListener(new StockQueryTextListener(newsListFragment, chartFragment));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_refresh) {
            newsListFragment.RefreshNewsFeed();
            chartFragment.RefreshChart();
        }
        return super.onOptionsItemSelected(item);
    }

    NewsListFragment newsListFragment;
    ChartFragment chartFragment;

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                newsListFragment = NewsListFragment.newInstance();
                return newsListFragment;
            } else {
                chartFragment = ChartFragment.newInstance();
                return chartFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

}
