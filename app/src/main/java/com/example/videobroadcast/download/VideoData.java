package com.example.videobroadcast.download;

import android.graphics.drawable.Drawable;

public class VideoData {
    private Drawable thumbnail;
    private String title;

    public VideoData(Drawable thumbnail, String title) {
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
