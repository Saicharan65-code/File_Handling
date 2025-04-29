
// RecommendationSystem.java
import java.util.*;

public class RecommendationSystem {
    // Sample data: userId -> List of favorite products
    private static Map<String, List<String>> userPreferences = new HashMap<>();

    public static void main(String[] args) {
        // Initialize sample data
        initializeData();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your user ID:");
        String userId = scanner.nextLine();

        List<String> recommendations = getRecommendations(userId);

        if (recommendations.isEmpty()) {
            System.out.println("No recommendations found. Please add more preferences!");
        } else {
            System.out.println("Recommended products for you:");
            for (String product : recommendations) {
                System.out.println("- " + product);
            }
        }
    }

    // Method to initialize some sample user preferences
    private static void initializeData() {
        userPreferences.put("user1", Arrays.asList("Laptop", "Smartphone", "Headphones"));
        userPreferences.put("user2", Arrays.asList("Books", "Stationery", "Laptop"));
        userPreferences.put("user3", Arrays.asList("Smartphone", "Camera", "Laptop"));
    }

    // Method to generate recommendations
    private static List<String> getRecommendations(String userId) {
        List<String> currentUserProducts = userPreferences.get(userId);
        if (currentUserProducts == null) {
            return Collections.emptyList();
        }

        Map<String, Integer> productScores = new HashMap<>();

        // Compare with other users
        for (Map.Entry<String, List<String>> entry : userPreferences.entrySet()) {
            if (!entry.getKey().equals(userId)) {
                for (String product : entry.getValue()) {
                    if (!currentUserProducts.contains(product)) {
                        productScores.put(product, productScores.getOrDefault(product, 0) + 1);
                    }
                }
            }
        }

        // Sort recommendations based on popularity
        List<String> recommendedProducts = new ArrayList<>(productScores.keySet());
        recommendedProducts.sort((a, b) -> productScores.get(b) - productScores.get(a));

        return recommendedProducts;
    }
}
