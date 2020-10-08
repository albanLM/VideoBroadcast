package com.example.videobroadcast.download;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Environment;
import android.util.Size;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import xyz.neocrux.suziloader.SuziLoader;

public class FileUtils {
    public static ArrayList<File> scanForVideos(String videoDirectory) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + videoDirectory;
        File directory = new File(path);
        File[] files = directory.listFiles();
        ArrayList<File> arrayList = new ArrayList<>();
        if (files != null) {
            Collections.addAll(arrayList, files);
        }
        return arrayList;
    }

    public static ArrayList<Drawable> getThumbnails(Context context, ArrayList<File> videos) {
        ArrayList<Drawable> drawables = new ArrayList<>();
        SuziLoader loader = new SuziLoader();
        for (File video : videos) {
            drawables.add(new BitmapDrawable(context.getResources(), getVideoFrame(Uri.fromFile(video), context)));
        }
        return drawables;
    }

    public static ArrayList<VideoData> getVideoDatas(Context context, ArrayList<File> videos) {
        ArrayList<VideoData> datas = new ArrayList<>();
        ArrayList<Drawable> thumbnails = getThumbnails(context, videos);

        for (int i = 0; i < videos.size(); ++i) {
            datas.add(new VideoData(thumbnails.get(i), videos.get(i).getName()));
        }

        return datas;
    }

    public static Bitmap getVideoFrame(Uri uri, Context context) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, uri);
        return retriever.getFrameAtTime();
    }
}
