package com.example.videobroadcast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;

public class SelectionViewModel extends ViewModel {
    private MutableLiveData<File> selectedFile = new MutableLiveData<>();

    public LiveData<File> getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile.setValue(selectedFile);
    }
}
