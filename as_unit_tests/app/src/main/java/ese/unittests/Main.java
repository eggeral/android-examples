package ese.unittests;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        launch(System.in, System.out);
    }

    public static void launch(InputStream is, OutputStream os) {
        PrintWriter writer = new PrintWriter(os);
        try (Scanner scanner = new Scanner(is)) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            Calculator calculator = new Calculator(a, b);
            writer.println("Result: " + calculator.sum());
            writer.flush();
        }

    }
}
