package info.suvaya.onestock.app;


import java.util.Date;

public class Item {
    public String Title;
    public String Link;
    public String Description;
    public Date PubDate;

    public Item() {
    }

    public Item(String t, String l, String d) {
        this.Title = t;
        this.Link = l;
        this.Description = d;
    }
}
