package info.suvaya.onestock.app;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.net.URLDecoder;

public class NewsListFragment extends ListFragment {
    NewsListAdapter adapter;

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message inputMessage) {
            NewsLoader newsLoader = (NewsLoader) inputMessage.obj;

            adapter = new NewsListAdapter(getActivity(), newsLoader.Items);
            setListAdapter(adapter);
        }
    };

    public NewsListFragment() {
    }

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RefreshNewsFeed(null);
    }

    public void handleState(INewsLoader newsLoader, int state) {
        Message completeMessage = mHandler.obtainMessage(state, newsLoader);
        completeMessage.sendToTarget();
    }

    public void RefreshNewsFeed(String stock_symbol) {
        try {
            if (stock_symbol == null || stock_symbol.isEmpty()) {
                stock_symbol = getStockSymbolFromStorage();
            }

            String yahoo_feed_url = URLDecoder.decode(getString(R.string.yahoo_feed_url) + stock_symbol, "UTF-8");

            Thread thread = new Thread(new NetworkRunnable(yahoo_feed_url, new NewsLoader(this)));
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        Item item = adapter.getItem(position);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.Link));
        startActivity(browserIntent);
    }

    private String getStockSymbolFromStorage() {
        try {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String default_stock_symbol = getString(R.string.default_stock_symbol);
            String stock_symbol_name = getString(R.string.stock_symbol_name);

            return sharedPref.getString(stock_symbol_name, default_stock_symbol);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
