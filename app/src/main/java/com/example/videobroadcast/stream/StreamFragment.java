package com.example.videobroadcast.stream;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.videobroadcast.R;
import com.example.videobroadcast.databinding.FragmentStreamBinding;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class StreamFragment extends Fragment {
    private static final UUID MY_UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66"); // TODO

    private FragmentStreamBinding binding;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<BluetoothDevice> discoveredList;
    private ArrayList<BluetoothDevice> pairedList;

    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            bluetoothAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Create the result Intent and include the MAC address
            Intent intent = new Intent(getActivity(), Pairing.class);
            intent.putExtra("SELECTED DEVICE", discoveredList.get(arg2));
            //setResult(Activity.RESULT_OK, intent);
            startActivity(intent);
        }
    };

    final BroadcastReceiver receiver = new BroadcastReceiver() { //DISCOVERY
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                Log.d("Client", "Found a device: " + device.getName());
                itemsAdapter.add(device.getName() + "\n" + device.getAddress());
                discoveredList.add(device);
                //}

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                getActivity().setProgressBarIndeterminateVisibility(false);
                if (itemsAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    itemsAdapter.add(noDevices);
                }
                Toast.makeText(getActivity(), "End Discovery", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStreamBinding.inflate(inflater, container, false);

//        getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        discoveredList = new ArrayList<>();
        pairedList = new ArrayList<>();

        getActivity().setResult(Activity.RESULT_CANCELED);

        Button scanButton = binding.buttonScan;
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                discover();
                v.setVisibility(View.GONE);
            }
        });

        ArrayAdapter<String> pairedDevicesArrayAdapter =
                new ArrayAdapter<String>(getContext(), R.layout.device_name);
        itemsAdapter = new ArrayAdapter<String>(getContext(), R.layout.device_name);

        ListView pairedListView = binding.pairedDevices;
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        ListView newDevicesListView = binding.newDevices;
        newDevicesListView.setAdapter(itemsAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(receiver, filter);
        //filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //this.registerReceiver(receiver, filter);


        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            binding.titlePairedDevices.setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            pairedDevicesArrayAdapter.add(noDevices);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }
        getActivity().unregisterReceiver(receiver);
    }

    private void discover() {
        Log.d("Client", "discover()");

//        getActivity().setProgressBarIndeterminateVisibility(true); //TODO
        binding.titleNewDevices.setVisibility(View.VISIBLE);

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        bluetoothAdapter.startDiscovery();
    }

}
