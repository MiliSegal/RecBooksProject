package main.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasedCollaborativeFiltering implements IAlgoMachineLearning {
    private Map<Integer, Map<Integer, Integer>> userItemRate; // user_id -> (item_id -> rating)
    private Map<Integer, Map<Integer, Double>> userSimilar;

    public BasedCollaborativeFiltering() {
        userItemRate = new HashMap<>();
        userSimilar = new HashMap<>();
    }

    @Override
    public void train(Object data) {
        userItemRate = (Map<Integer, Map<Integer, Integer>>) data;
        computeUserSimilarities();
    }

    private void computeUserSimilarities() {
        for (Integer user1 : userItemRate.keySet()) {
            for (Integer user2 : userItemRate.keySet()) {
                if (!user1.equals(user2)) {
                    userSimilar.computeIfAbsent(user1, k -> new HashMap<>()).put(user2, 1.0);
                }
            }
        }
    }

    @Override
    public List<String> recommend(int userId, int numRecommendations) {
        Map<Integer, Integer> userRatings = userItemRate.get(userId);
        List<Integer> similarUsers = userSimilar.get(userId).entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Map<Integer, Double> recommendedItems = new HashMap<>();
        for (Integer similarUser : similarUsers) {
            userItemRate.get(similarUser).forEach((itemId, rating) -> {
                if (!userRatings.containsKey(itemId)) {
                    recommendedItems.merge(itemId, rating.doubleValue(), Double::sum);
                }
            });
        }

        return recommendedItems.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(numRecommendations)
                .map(Map.Entry::getKey)
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}
