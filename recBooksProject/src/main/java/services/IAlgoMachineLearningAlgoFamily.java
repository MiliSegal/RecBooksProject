package main.java.services;

import java.util.List;

public interface IAlgoMachineLearningAlgoFamily {
    void add(Object data);  // Example method
    Object get(int id);     // Example method
    List<String> compute();
    List<String> recommend(int userId, int numRecommendations);
}
