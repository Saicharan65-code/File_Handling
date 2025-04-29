
// ChatClient.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            System.out.println("Connected to the chat server!");

            // Create a thread to listen for messages from server
            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread to send messages
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String userMessage = scanner.nextLine();
                out.println(userMessage);
            }

        } catch (IOException e) {
            System.out.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
