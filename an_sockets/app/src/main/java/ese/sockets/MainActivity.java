package ese.sockets;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 1 echo server
        // we use API 19 in order to get Java 7 APIs
        // create new Java lib module server
        // test server using telnet

        // == 2 simple test of server echo
        // <uses-permission android:name="android.permission.INTERNET" />
        // No Network communication is allowed on the main thread!
//        AsyncTask<String, Integer, Void> socketCommunication = new AsyncTask<String, Integer, Void>() {
//            @Override
//            protected Void doInBackground(String... params) {
//                try (Socket socket = new Socket("192.168.1.146", 7070)) {
//
//                    Reader reader = new InputStreamReader(socket.getInputStream());
//                    Writer writer = new OutputStreamWriter(socket.getOutputStream());
//                    writer.write(params[0]);
//                    writer.write(System.lineSeparator());
//                    writer.flush();
//
//                    char[] buffer = new char[1024];
//                    int read;
//
//                    while ((read = reader.read(buffer)) != -1) {
//                        for (int idx = 0; idx < read; idx++) {
//                            System.out.println(buffer[idx]);
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//
//        socketCommunication.execute("Hello World");

        // == 3 Server on Android (Simulator is NATed so use a real device to show this.)
//        AsyncTask<String, Integer, Void> socketCommunication = new AsyncTask<String, Integer, Void>() {
//            @Override
//            protected Void doInBackground(String... params) {
//                try (ServerSocket serverSocket = new ServerSocket(7070)) {
//
//                    try (Socket socket = serverSocket.accept()) {
//
//                        Scanner scanner = new Scanner(socket.getInputStream());
//                        Writer writer = new OutputStreamWriter(socket.getOutputStream());
//                        String line = scanner.nextLine();
//                        writer.write(line);
//                        writer.flush();
//
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }                return null;
//            }
//        };
//
//        socketCommunication.execute("Hello World");

        // == 4 Multiple connects

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<String, Integer, Void> socketCommunication = new AsyncTask<String, Integer, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        try (Socket socket = new Socket("192.168.134.250", 7070)) {

                            Reader reader = new InputStreamReader(socket.getInputStream());
                            Writer writer = new OutputStreamWriter(socket.getOutputStream());
                            writer.write(params[0]);
                            writer.write(System.lineSeparator());
                            writer.flush();

                            char[] buffer = new char[1024];
                            int read;

                            while ((read = reader.read(buffer)) != -1) {
                                for (int idx = 0; idx < read; idx++) {
                                    System.out.println(buffer[idx]);
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                socketCommunication.execute("Hello World");

            }
        });

        // Exercise: Chat app including server
    }
}
