package com.example.videobroadcast.download;

import android.graphics.drawable.Drawable;

import java.io.File;

public class VideoData {
    private File file;
    private Drawable thumbnail;
    private String title;

    public VideoData(File file, Drawable thumbnail, String title) {
        this.file = file;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
