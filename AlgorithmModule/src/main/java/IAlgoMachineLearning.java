package main.java;

import java.util.List;

public interface IAlgoMachineLearning {
    public void train(Object data);
    public List<String> recommend(int userId, int numRecommendations);
}