import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiConsumer {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a name to predict age: ");
        String name = scanner.nextLine();

        try {
            // Building the URL with user input
            String apiUrl = "https://api.agify.io?name=" + name;

            // Creating a connection to the API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET"); // Set request type to GET

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // If response is 200 OK

                // Reading response from API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Manual parsing of JSON response
                String jsonResponse = response.toString();

                // Extract "name"
                String fetchedName = extractValue(jsonResponse, "name");
                // Extract "age"
                String ageString = extractValue(jsonResponse, "age");
                int predictedAge = ageString.equals("null") ? -1 : Integer.parseInt(ageString);
                // Extract "count"
                int count = Integer.parseInt(extractValue(jsonResponse, "count"));

                // Display the structured output
                System.out.println("\n--- Predicted Age Details ---");
                System.out.println("Name: " + fetchedName);
                System.out.println("Predicted Age: " + (predictedAge == -1 ? "Not Available" : predictedAge));
                System.out.println("Data Based On: " + count + " people");

            } else {
                System.out.println("Failed to fetch data. HTTP Error Code: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("An error occurred while connecting to the API.");
            e.printStackTrace();
        }

        scanner.close();
    }

    // Helper method to extract values manually from JSON string
    private static String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) {
            return ""; // Key not found
        }
        startIndex += searchKey.length();

        // Check if value is string or number
        if (json.charAt(startIndex) == '"') {
            // Value is a String
            int endIndex = json.indexOf('"', startIndex + 1);
            return json.substring(startIndex + 1, endIndex);
        } else {
            // Value is a Number
            int endIndex = json.indexOf(',', startIndex);
            if (endIndex == -1) {
                endIndex = json.indexOf('}', startIndex);
            }
            return json.substring(startIndex, endIndex).trim();
        }
    }
}