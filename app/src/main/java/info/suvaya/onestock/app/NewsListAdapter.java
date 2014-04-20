package info.suvaya.onestock.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsListAdapter extends ArrayAdapter<Item> {
    private final Context context;
    List<Item> items;

    public NewsListAdapter(Context context, List<Item> items) {
        super(context, R.layout.news_item, items);

        this.context = context;
        this.items = items;
    }
//
//    @Override
//    public long getItemId(int position) {
//        return 1;
//        String item = getItem(position);
//        return mIdMap.get(item);
//    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_item, parent, false);

        Item item = this.getItem(position);

        TextView text_title = (TextView) rowView.findViewById(R.id.text_title);
        text_title.setText(item.Title);

        TextView text_description = (TextView) rowView.findViewById(R.id.text_description);
        text_description.setText(item.Description);

        TextView text_date = (TextView) rowView.findViewById(R.id.text_date);
        text_date.setText(new SimpleDateFormat("EEE, dd MMM").format(item.PubDate));

        return rowView;
    }
}
