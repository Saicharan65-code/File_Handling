import java.io.*;
import java.util.Scanner;

public class FileHandler {

    // Scanner for user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String fileName = "example.txt"; // File to perform operations on

        while (true) {
            System.out.println("\n--- File Handling Menu ---");
            System.out.println("1. Write to File");
            System.out.println("2. Read from File");
            System.out.println("3. Modify File");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    writeFile(fileName);
                    break;
                case 2:
                    readFile(fileName);
                    break;
                case 3:
                    modifyFile(fileName);
                    break;
                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to write to a file
    public static void writeFile(String fileName) {
        try {
            System.out.print("Enter text to write into the file: ");
            String content = scanner.nextLine();

            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    // Method to read from a file
    public static void readFile(String fileName) {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("File does not exist.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            System.out.println("\nFile Content:");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    // Method to modify (append) to a file
    public static void modifyFile(String fileName) {
        try {
            System.out.print("Enter text to append to the file: ");
            String content = scanner.nextLine();

            FileWriter writer = new FileWriter(fileName, true); // 'true' enables append mode
            writer.write("\n" + content);
            writer.close();

            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while modifying the file.");
            e.printStackTrace();
        }
    }
}
