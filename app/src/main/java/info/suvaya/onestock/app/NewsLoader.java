package info.suvaya.onestock.app;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NewsLoader implements INewsLoader {

    public List<Item> Items;
    NewsListFragment fragment;

    public NewsLoader(NewsListFragment fragment) {
        this.fragment = fragment;
    }

    public void loadNews(InputStream stream) {
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();
            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream, null);
            this.Items = parseXMLAndStoreIt(myparser);
            stream.close();

            fragment.handleState(this, 1);
        } catch (Exception e) {
        }
    }

    public List<Item> parseXMLAndStoreIt(XmlPullParser myParser) {
        List<Item> items = new ArrayList<Item>();
        Item item;
        String text = null;

        try {
            int event = myParser.next();

            while (event != XmlPullParser.END_DOCUMENT) {
                String eventName = myParser.getName();

                if (event == XmlPullParser.START_TAG && eventName.equalsIgnoreCase("item")) {
                    item = loadItem(myParser);

                    String yahoo_news_item_to_avoid = fragment.getString(R.string.yahoo_news_item_to_avoid);
                    if (!item.Title.contains(yahoo_news_item_to_avoid)) {
                        items.add(item);
                    }
                }

                event = myParser.next();

//            parsingComplete = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;

    }

    private Item loadItem(XmlPullParser xpp) throws Exception {

        Hashtable<String, String> keyValues = getKeyValues(xpp);

        Item item = new Item();
        item.Title = keyValues.get("title");
        item.Link = keyValues.get("link");
        item.Description = keyValues.get("description");
        item.PubDate = new SimpleDateFormat("EEE, dd MMM").parse(keyValues.get("pubDate"));

        return item;
    }

    private Hashtable getKeyValues(XmlPullParser xpp) throws Exception {
        int eventType = xpp.next();
        Hashtable<String, String> keyValues = new Hashtable<String, String>();

        while (eventType != XmlPullParser.END_TAG) {

            String key = xpp.getName();

            while (eventType != XmlPullParser.END_TAG) {
                if (eventType == XmlPullParser.TEXT) {
                    String value = xpp.getText();
                    keyValues.put(key, value);
                }
                eventType = xpp.next();
            }

            eventType = xpp.next();
        }

        return keyValues;
    }

}