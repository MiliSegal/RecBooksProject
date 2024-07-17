package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasedRecommender implements IAlgoMachineLearning {

    private Map<Integer, String> items;
    private Map<Integer, Map<Integer, Double>> itemVectors;

    public void ContentBasedRecommender() {
        items = new HashMap<>();
        itemVectors = new HashMap<>();
    }


    public void train(Object data) {
        items = (Map<Integer, String>) data;
        calcItemVectors();
    }

    private void calcItemVectors() {
        for (Integer itemId : items.keySet()) {
            String info = items.get(itemId);
            Map<Integer, Double> vector = new HashMap<>();

            for (String word : info.split("\\s+")) {
                int hash = word.hashCode();
                vector.put(hash, 1.0);
            }
            itemVectors.put(itemId, vector);
        }
    }

    @Override
    public List<String> recommend(int userId, int numOfRecommendations) {

        int likedId = new ArrayList<>(items.keySet()).get(0);
        Map<Integer, Double> likedVector = itemVectors.get(likedId);

        return items.entrySet().stream()
                .sorted((e1, e2) -> {
                    double similarity1 = computeCosineSimilarity(itemVectors.get(e1.getKey()), likedVector);
                    double similarity2 = computeCosineSimilarity(itemVectors.get(e2.getKey()), likedVector);
                    return Double.compare(similarity2, similarity1);
                })
                .limit(numOfRecommendations)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private double computeCosineSimilarity(Map<Integer, Double> vector1, Map<Integer, Double> vector2) {
        double dotProduct = 0.0;
        double normal1 = 0.0;
        double normal2 = 0.0;

        for (int key : vector1.keySet()) {
            double value1 = vector1.getOrDefault(key, 0.0);
            double value2 = vector2.getOrDefault(key, 0.0);
            dotProduct += value1 * value2;
            normal1 += value1 * value1;
            normal2 += value2 * value2;
        }

        if (normal1 == 0 || normal2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normal1) * Math.sqrt(normal2));
    }
}
