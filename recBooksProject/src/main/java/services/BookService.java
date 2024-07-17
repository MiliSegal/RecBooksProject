package main.java.services;

import main.java.dm.DataModel;
import main.java.dao.IDao;

public class BookService {

    private final IDao dao;

    public BookService(IDao dao) {
        this.dao = dao;
    }

    public void saveDataModel(DataModel dataModel) {
        dao.save(dataModel);
    }

    public DataModel getDataModelById(int id) {
        return dao.getById(id);
    }

    public void updateDataModel(DataModel dataModel) {
        dao.update(dataModel);
    }

    public void deleteDataModel(int id) {
        dao.delete(id);
    }
}