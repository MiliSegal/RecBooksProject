package dao;


import dm.DataModel;

import java.util.List;

public interface IDao {
    void save(DataModel dataModel);
    DataModel getById(int id);
    List<DataModel> getAll();
    void update(DataModel dataModel);
    void delete(int id);
}
