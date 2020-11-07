package com.motion.app.Classes;

public class ExploreGrid {

    private String userName, contentUrl;
    private long viewsCount;

    public ExploreGrid(String userName, long viewsCount, String contentUrl){

        this.userName = userName;
        this.viewsCount = viewsCount;
        this.contentUrl = contentUrl;

    }

    public String getUserName() {
        return userName;
    }

    public long getViewsCount() {
        return viewsCount;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}
