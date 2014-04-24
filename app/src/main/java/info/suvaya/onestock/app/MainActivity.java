package info.suvaya.onestock.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class MainActivity extends Activity {
    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    NewsListFragment newsListFragment;
    ChartFragment chartFragment;
    StockTwitsFragment stockTwitsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_action_buttons, menu);

        setupSearchView(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_refresh) {
            Refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }

    public void Refresh() {
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    private void setupSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint("Enter Stock Symbol");

        try {
            mSearchView.setOnQueryTextListener(new StockQueryTextListener(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
