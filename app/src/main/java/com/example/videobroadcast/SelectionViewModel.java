package com.example.videobroadcast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.videobroadcast.download.VideoData;

public class SelectionViewModel extends ViewModel {
    private MutableLiveData<VideoData> selectedVideo = new MutableLiveData<>();

    public LiveData<VideoData> getSelectedVideo() {
        return selectedVideo;
    }

    public void setSelectedVideo(VideoData selectedVideo) {
        this.selectedVideo.setValue(selectedVideo);
    }
}
