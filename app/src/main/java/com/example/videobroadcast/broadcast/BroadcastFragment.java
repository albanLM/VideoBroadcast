package com.example.videobroadcast.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.videobroadcast.R;
import com.example.videobroadcast.SelectionViewModel;
import com.example.videobroadcast.databinding.FragmentBroadcastBinding;

public class BroadcastFragment extends Fragment {
    private SelectionViewModel selectionViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectionViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBroadcastBinding binding = FragmentBroadcastBinding.inflate(inflater, container, false);

        selectionViewModel.getSelectedFile().observe(getViewLifecycleOwner(), file -> {
            binding.testView.setText(file.getName());
        });

        // Add button onClick() method
        final Button button = binding.bluetoothBroadcastButton;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String aDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
                startActivityForResult(new Intent(aDiscoverable), 1);

                // Code here executes on main thread after user presses button
                BroadcastThread cth = new BroadcastThread(getContext(), getActivity(), selectionViewModel.getSelectedFile().getValue());
                cth.start();
            }
        });

        return binding.getRoot();
    }
}
