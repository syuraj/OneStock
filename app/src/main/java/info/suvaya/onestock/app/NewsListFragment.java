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
        return new NewsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RefreshNewsFeed();
    }

    public void handleState(INewsLoader newsLoader, int state) {
        Message completeMessage = mHandler.obtainMessage(state, newsLoader);
        completeMessage.sendToTarget();
    }

    public void RefreshNewsFeed() {
        try {
            String stock_symbol = ((OneStockApplication) this.getActivity().getApplication()).getStockSymbol();

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


}
