package main.test;

import main.java.BasedCollaborativeFiltering;
import main.java.BasedRecommender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class IAlgoMachineLearningTest {

    private BasedRecommender contentBasedRecommender;
    private BasedCollaborativeFiltering basedCollaborativeFiltering;

    @BeforeEach
    public void setUp() {
        contentBasedRecommender = new BasedRecommender();
        basedCollaborativeFiltering = new BasedCollaborativeFiltering();
    }

    @Test
    public void testContentBasedRecommenderTrainAndRecommend() {
        Map<Integer, String> items = new HashMap<>();
        items.put(1, "apple banana orange");
        items.put(2, "banana orange fruit");
        items.put(3, "carrot vegetable");

        contentBasedRecommender.train(items);

        List<String> recommendations = contentBasedRecommender.recommend(1, 2);
        assertNotNull(recommendations);
        assertEquals(2, recommendations.size());
        assertTrue(recommendations.contains("banana orange fruit"));
        assertFalse(recommendations.contains("carrot vegetable"));
    }

    @Test
    public void testContentBasedRecommenderWithNoData() {
        contentBasedRecommender.train(new HashMap<>());

        List<String> recommendations = contentBasedRecommender.recommend(1, 2);
        assertNotNull(recommendations);
        assertEquals(0, recommendations.size());
    }

    @Test
    public void testBasedCollaborativeFilteringTrainAndRecommend() {
        Map<Integer, Map<Integer, Integer>> userItemRate = new HashMap<>();
        userItemRate.put(1, Map.of(1, 5, 2, 3));
        userItemRate.put(2, Map.of(1, 4, 3, 5));
        userItemRate.put(3, Map.of(2, 5, 3, 3));

        basedCollaborativeFiltering.train(userItemRate);

        List<String> recommendations = basedCollaborativeFiltering.recommend(1, 2);
        assertNotNull(recommendations);
        assertEquals(2, recommendations.size());
        assertTrue(recommendations.contains("3")); // assuming item 3 is recommended
        assertFalse(recommendations.contains("1")); // item 1 should not be recommended to user 1
    }

    @Test
    public void testBasedCollaborativeFilteringWithNoData() {
        basedCollaborativeFiltering.train(new HashMap<>());

        List<String> recommendations = basedCollaborativeFiltering.recommend(1, 2);
        assertNotNull(recommendations);
        assertEquals(0, recommendations.size());
    }
}
