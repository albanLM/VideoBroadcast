package com.example.videobroadcast.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.videobroadcast.R;
import com.example.videobroadcast.databinding.FragmentBroadcastBinding;
import com.example.videobroadcast.databinding.FragmentDownloadBinding;

public class BroadcastFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBroadcastBinding binding = FragmentBroadcastBinding.inflate(inflater, container, false);

        String aDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
        startActivityForResult(new Intent(aDiscoverable), 1);

        final Button button = binding.bluetoothButton;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (bluetoothAdapter == null) {

                    // Device doesn't support Bluetooth
                    System.out.println("This device doesn't support bluetooth");
                }

                //request user to enable blutotooh if blutooth is disabled without quitting the app

                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                // Code here executes on main thread after user presses button
                AcceptThread cth = new AcceptThread();
                cth.start();
            }
        });

        return null;
    }

}
