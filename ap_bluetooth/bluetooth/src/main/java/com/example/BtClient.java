package com.example;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class BtClient {

    public static void main(String[] args) {
        try {


            String url = "btspp://2C598A9CE1E6:8;authenticate=false;encrypt=false;master=false";
            StreamConnection con = null;
            try {
                System.out.println("Connecting to: " + url);
                con = (StreamConnection) Connector.open(url);
                System.out.println("Connected");

                try (Writer writer = new OutputStreamWriter(con.openOutputStream());
                     Scanner scanner = new Scanner(con.openInputStream())) {

                    writer.write("Hello World\n");
                    writer.flush();
                    System.out.println("Request sent");
                    String result = scanner.nextLine();
                    System.out.println("Result: " + result);

                    writer.write("Exit\n");
                    writer.flush();
                    System.out.println("Connection exit");

                }

            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (IOException closeException) {
                        closeException.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
