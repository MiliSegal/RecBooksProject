package main.service;

import algorithm.IAlgoRecommender;
import main.dao.IDao;
import main.model.DataModel;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final IDao dao;
    private final IAlgoRecommender algoRecommender;

    public BookService(IDao dao, IAlgoRecommender algoRecommender) {
        this.dao = dao;
        this.algoRecommender = algoRecommender;
    }

    public void save(DataModel dataModel) {
        dao.save(dataModel);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public Optional<DataModel> retrieve(int id) {
        return dao.retrieve(id);
    }

    public List<String> recommend(int userId, int numRecommendations) {
        algoRecommender.train(dao.getAll());
        return algoRecommender.recommend(userId, numRecommendations);
    }
}
