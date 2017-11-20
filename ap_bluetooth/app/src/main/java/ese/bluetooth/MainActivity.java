package ese.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "ese.bluetooth";
    private static UUID MY_UUID1 = UUID.fromString("b9b323ac-cbbd-11e7-abc4-cec278b6b50d");
    private static UUID MY_UUID2 = UUID.fromString("b9b323ac-cbbd-11e7-abc4-cec278b6b50b");
    //private static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // well known serial port id


    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 - Discover bluetooth devices
        // <uses-permission android:name="android.permission.BLUETOOTH" />
        // <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
        // <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(receiver, filter);


        Button scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  // Only ask for these permissions on runtime when running Android 6.0 or higher
                    if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
                bluetoothAdapter.startDiscovery();
            }
        });

        // == 02 Connect to server
        Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel discovery because it otherwise slows down the connection.
                bluetoothAdapter.cancelDiscovery();


                BluetoothSocket socket = null;
                BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice("60:03:08:8B:78:39"); // MacBook Pro
                try {

                    socket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID1);
                    Log.i(TAG, "onClick: Connecting to: " + bluetoothDevice.getName());
                    socket.connect();
                    Log.i(TAG, "onClick: Connected");
                    try (Writer writer = new OutputStreamWriter(socket.getOutputStream());
                         Scanner scanner = new Scanner(socket.getInputStream())) {

                        writer.write("Hello World\n");
                        writer.flush();
                        Log.i(TAG, "onClick: Request sent");
                        String result = scanner.nextLine();
                        Log.i(TAG, "onClick: " + result);

                        writer.write("Exit\n");
                        writer.flush();
                        Log.i(TAG, "onClick: Connection exit");

                    }

                } catch (IOException connectException) {
                    Log.e(TAG, "Could not connect to the client socket", connectException);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException closeException) {
                            Log.e(TAG, "Could not close the client socket", closeException);
                        }
                    }
                }
            }
        });

        // == 03 Server
        // Use BtScanner in order to get the right connection URL. We need to be discoverable for that time!
//        Intent discoverableIntent =
//                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//        startActivity(discoverableIntent);

        AsyncTask<String, Integer, Void> bluetoothServer = new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                BluetoothServerSocket serverSocket = null;

                try {

                    serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("BT TEST", MY_UUID2);

                    while (true) {
                        Log.i(TAG, "Waiting for client connected!");
                        final BluetoothSocket socket = serverSocket.accept();
                        Log.i(TAG, "Client connected!");

                        Thread worker = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    InputStream in = socket.getInputStream();
                                    Writer writer = new OutputStreamWriter(socket.getOutputStream());
                                    Scanner scanner = new Scanner(in);
                                    String line = scanner.nextLine();
                                    System.out.println("Got " + line);
                                    writer.write(line);
                                    writer.write("\n");
                                    writer.flush();
                                    System.out.println("Waiting for client answer");
                                    line = scanner.nextLine();
                                    System.out.println("Client answered " + line);
                                    writer.close();
                                    scanner.close();
                                    socket.close();
                                    System.out.println("Connection finished");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        worker.start();

                    }

                } catch (IOException connectException) {
                    Log.e(TAG, "Could not connect to the client socket", connectException);
                } finally {
                    try {
                        serverSocket.close();
                    } catch (IOException closeException) {
                        Log.e(TAG, "Could not close the client socket", closeException);
                    }
                }
                return null;
            }
        };
        bluetoothServer.execute();

        // Exercise: Chat between Android devices.
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "onReceive: " + action);
            if (action == null)
                return;

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.i(TAG, "onReceive: " + deviceName + " - [" + deviceHardwareAddress + "]");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}
