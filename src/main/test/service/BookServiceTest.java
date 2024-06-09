package main.test.service;

import algorithm.ContentBasedRecommender;
import algorithm.UserBasedCollaborativeFilteringRecommender;
import main.dao.IDao;
import main.dao.MyDMFileImpl;
import main.model.DataModel;
import main.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    private ContentBasedRec contentBasedRecommender;
    private UserBasedCollaborativeFilteringRec basedCollaborativeFiltering;
    private BookService service;
    private IDao dao;

    @BeforeEach
    public void setUp() {
        contentBasedRecommender = new ContentBasedRec();
        basedCollaborativeFiltering = new UserBasedCollaborativeFilteringRec();
        dao = new MyDMFileImpl("path/to/txt.DataSource"); // Replace with the actual path to your file
        service = new BookService(dao, contentBasedRecommender); // You can change the algorithm as needed
    }

    @Test
    public void testContentBasedRecommenderTrainAndRecommend() {
        DataModel book1 = new DataModel(1, "Title1", "apple banana orange", "Author1");
        DataModel book2 = new DataModel(2, "Title2", "banana orange fruit", "Author2");
        DataModel book3 = new DataModel(3, "Title3", "carrot vegetable", "Author3");

        service.save(book1);
        service.save(book2);
        service.save(book3);

        List<String> recommendations = service.recommend(1, 2);
        assertNotNull(recommendations);
        assertEquals(2, recommendations.size());
        assertTrue(recommendations.contains("2")); // Assuming the title of the book is used as the ID
        assertFalse(recommendations.contains("3")); // Assuming the title of the book is used as the ID
    }

    @Test
    public void testContentBasedRecommenderWithNoData() {
        List<String> recommendations = service.recommend(1, 2);
        assertNotNull(recommendations);
        assertEquals(0, recommendations.size());
    }

    @Test
    public void testServiceSaveAndRetrieve() {
        DataModel model1 = new DataModel(1, "Title1", "Data 1", "Author1");
        service.save(model1);

        Optional<DataModel> retrievedModel = service.retrieve(1);
        assertTrue(retrievedModel.isPresent());
        assertEquals("Data 1", retrievedModel.get().getDescription());
    }

    @Test
    public void testServiceDelete() {
        DataModel model2 = new DataModel(2, "Title2", "Data 2", "Author2");
        service.save(model2);

        service.delete(2);
        Optional<DataModel> retrievedModel = service.retrieve(2);
        assertFalse(retrievedModel.isPresent());
    }
}
