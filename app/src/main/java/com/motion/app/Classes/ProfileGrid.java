package com.motion.app.Classes;

public class ProfileGrid {

    private String contentUrl;
    private long viewsCount;

    public ProfileGrid(long viewsCount, String contentUrl){

        this.viewsCount = viewsCount;
        this.contentUrl = contentUrl;

    }

    public long getViewsCount() {
        return viewsCount;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}
