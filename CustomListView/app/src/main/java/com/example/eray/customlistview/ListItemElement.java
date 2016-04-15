package com.example.eray.customlistview;


import android.os.Parcel;
import android.os.Parcelable;

public class ListItemElement implements Parcelable{

    private String name, thumbnailUrl;
    private String year;
    private String source;
    private String worth;
    private String content;
    private String tag;

    public ListItemElement() {

    }

    protected ListItemElement(Parcel in) {
        name = in.readString();
        thumbnailUrl = in.readString();
        year = in.readString();
        source = in.readString();
        worth = in.readString();
        content = in.readString();
        tag = in.readString();
    }

    public static final Creator<ListItemElement> CREATOR = new Creator<ListItemElement>() {
        @Override
        public ListItemElement createFromParcel(Parcel in) {
            return new ListItemElement(in);
        }

        @Override
        public ListItemElement[] newArray(int size) {
            return new ListItemElement[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(thumbnailUrl);
        dest.writeString(year);
        dest.writeString(source);
        dest.writeString(worth);
        dest.writeString(content);
        dest.writeString(tag);
    }
}
