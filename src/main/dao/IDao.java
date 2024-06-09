package main.dao;

import main.model.DataModel;

import java.util.List;
import java.util.Optional;

public interface IDao {
    void save(DataModel dataModel);
    void delete(int id);
    Optional<DataModel> retrieve(int id);
    List<DataModel> getAll();
}

