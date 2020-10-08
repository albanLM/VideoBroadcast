package com.example.videobroadcast.download;

import android.app.Application;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.yausername.ffmpeg.FFmpeg;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;
import com.yausername.youtubedl_android.YoutubeDLRequest;

import java.io.File;

public class DownloadUtils {
    public static void downloadFromURL(Application app, String url, String location) throws YoutubeDLException, InterruptedException {
        YoutubeDL youtubeDL = YoutubeDL.getInstance();
        FFmpeg fFmpeg = FFmpeg.getInstance();
        youtubeDL.init(app);
        fFmpeg.init(app);

        File youtubeDLDir = getDownloadLocation(location);
        YoutubeDLRequest request = new YoutubeDLRequest(url);
        request.addOption("-o", youtubeDLDir.getAbsolutePath() + "/%(title)s.%(ext)s");

        // TODO : Add callback
        youtubeDL.execute(request, (progress, etaInSeconds) -> {
            System.out.println(String.valueOf(progress) + "% (ETA " + String.valueOf(etaInSeconds) + " seconds)");
        });
    }

    @NonNull
    private static File getDownloadLocation(String path) {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File youtubeDLDir = new File(downloadsDir, path);
        if (!youtubeDLDir.exists()) youtubeDLDir.mkdir();
        return youtubeDLDir;
    }
}
