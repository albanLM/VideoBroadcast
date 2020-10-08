package com.example.videobroadcast.download;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videobroadcast.R;
import com.example.videobroadcast.SelectionViewModel;
import com.example.videobroadcast.databinding.FragmentDownloadBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yausername.youtubedl_android.YoutubeDLException;

import java.io.File;
import java.util.ArrayList;

public class DownloadFragment extends Fragment implements AddVideoDialog.AddVideoDialogListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ExtendedFloatingActionButton fab;

    private SelectionViewModel selectionViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectionViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentDownloadBinding binding = FragmentDownloadBinding.inflate(inflater, container, false);

        // Initialize the recycler view
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true); // Best performances
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        updateRecyclerView();

        fab = binding.fabAddVideo;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddVideoDialog(v);
            }
        });

        return binding.getRoot();
    }

    private void updateRecyclerView() {
        ArrayList<File> files = FileUtils.scanForVideos(getString(R.string.video_directory));
        ArrayList<VideoData> videos = FileUtils.getVideoDatas(getContext(), files);

        adapter = new VideoRecyclerView(videos, selectionViewModel);
        recyclerView.setAdapter(adapter);
    }

    public void showAddVideoDialog(View view) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new AddVideoDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "AddVideoDialogFragment");
        dialog.setTargetFragment(this, 1);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String url) {
        // User touched the dialog's positive button

        // Verifies if the url is valid
        url = url.trim();
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(getActivity(), "please enter a valid url", Toast.LENGTH_LONG).show();
            return;
        }

        dialog.dismiss(); // Dismisses the dialog window

        // Tries to download the file
        try {
            Toast.makeText(getActivity(), "downloading the video", Toast.LENGTH_SHORT).show();
            DownloadUtils.downloadFromURL(getActivity().getApplication(), url, getString(R.string.video_directory));
            updateRecyclerView();
        } catch (YoutubeDLException | InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "failed to download the video", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
