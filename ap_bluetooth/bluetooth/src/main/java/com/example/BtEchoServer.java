package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.UUID;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class BtEchoServer {

    // If the Android app connects but we do not see connection this might be
    // because of a hanging old EchoServer. Change the UUID here and at the Android side.

    private static String MY_UUID = "b9b323accbbd11e7abc4cec278b6b50d";


    public static void main(String[] args) {
        try {
            String url = "btspp://localhost:" + MY_UUID + ";name=EchoService";

            LocalDevice local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            System.out.println("Starting server at: " + url);
            final StreamConnectionNotifier service = (StreamConnectionNotifier) Connector.open(url);

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        service.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));

            while (true) {
                System.out.println("Waiting for connection.");
                final StreamConnection connection = service.acceptAndOpen();
                System.out.println("Client connected");

                Thread worker = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            InputStream in = connection.openInputStream();
                            Writer writer = new OutputStreamWriter(connection.openOutputStream());
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
                            connection.close();
                            System.out.println("Connection finished");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                worker.start();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
