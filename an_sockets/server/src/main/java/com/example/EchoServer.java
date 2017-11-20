package com.example;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(7070)) {

            try (Socket socket = serverSocket.accept()) {

                System.out.println("Client connected: " + socket.toString());
                Scanner scanner = new Scanner(socket.getInputStream());
                Writer writer = new OutputStreamWriter(socket.getOutputStream());
                String line = scanner.nextLine();
                System.out.println(line);
                writer.write(line);
                writer.flush();
                System.out.println("DONE");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
