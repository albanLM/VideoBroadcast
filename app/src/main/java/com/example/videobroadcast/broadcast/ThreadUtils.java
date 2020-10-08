package com.example.videobroadcast.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ThreadUtils extends Thread {
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private File videoFile;

    private static final String APP_NAME = "BTChat";
    private  final UUID MY_UUID=UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    OutputStream mmOutStream = null;


    private final BluetoothServerSocket mmServerSocket;
    private static final String TAG="SERVER SOCKET: ";
    public ThreadUtils() {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;

        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                // Manage the socket
                connected(socket);


                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }

    private void connected(BluetoothSocket socket) {
//            Toast.makeText(Server.this, "Connected to client", Toast.LENGTH_SHORT).show();
        try {
            mmOutStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
            /*try {


                int size = 1024;
                String example = "A String";
                byte[] bytes = example.getBytes();
                mmOutStream.write(bytes);
                mmOutStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }*/

        try {
            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();


            //File localFile = new File(rootFile, ServerActivity.OUTPUT_FILE_NAME);
                /*File localFile = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "Video/" + OUTPUT_FILE_NAME);
                int size = (int) localFile.length();
                if (size == 0) {
                    System.out.println("File is empty ...");
                    Log.d("DEBUG FILE", "EMPTY");
                } else {
                    System.out.println("File is not empty ...");
                    Log.d("DEBUG FILE", "NOT EMPTY");
                }*/
            byte[] bytes = getByte(Environment.getExternalStorageDirectory()
                    + File.separator + "Video/" + OUTPUT_FILE_NAME);

            //String s = new String(bytes);
            //System.out.println(s);
            //Log.d("DEBUG", s);

                /*try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(localFile));
                    buf.read(bytes, 0, bytes.length);
                    System.out.println(bytes);
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            try {
                mmOutStream.write(bytes);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mmOutStream.close();

            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getByte(String path) {
        byte[] getBytes = {};
        try {
            File file = new File(path);
            getBytes = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            is.read(getBytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBytes;
    }
}