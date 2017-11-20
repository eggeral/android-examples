package com.example;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiClientEchoServer {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(7070)) {

            while (true) {
                final Socket socket = serverSocket.accept();

                System.out.println("Client connected: " + socket.toString());

                final Thread communicatioThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Scanner scanner = new Scanner(socket.getInputStream());
                            Writer writer = new OutputStreamWriter(socket.getOutputStream());
                            String line = scanner.nextLine();
                            System.out.println(line);
                            writer.write(line);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Client disconnected: " + socket.toString());
                    }
                });

                communicatioThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
