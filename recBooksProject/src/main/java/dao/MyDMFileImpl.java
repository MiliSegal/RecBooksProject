package dao;


import dm.DataModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyDMFileImpl implements main.java.dao.IDao {

    private String pathFile;

    public MyDMFileImpl(String pathFile) {
        this.pathFile = pathFile;
    }

    @Override
    public void save(DataModel dataModel) {
        List<DataModel> dataModels = getAll();
        dataModels.add(dataModel);
        writeToFile(dataModels);
    }

    @Override
    public DataModel getById(int id) {
        List<DataModel> dataModels = getAll();
        return dataModels.stream()
                .filter(dm -> dm.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<DataModel> getAll() {
        List<DataModel> dataModels;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathFile))) {
            dataModels = (List<DataModel>) ois.readObject();
        } catch (FileNotFoundException e) {
            dataModels = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            dataModels = new ArrayList<>();
        }
        return dataModels;
    }

    @Override
    public void update(DataModel dataModel) {
        List<DataModel> dataModels = getAll();
        for (int i = 0; i < dataModels.size(); i++) {
            if (dataModels.get(i).getId() == dataModel.getId()) {
                dataModels.set(i, dataModel);
                writeToFile(dataModels);
                return;
            }
        }
        throw new IllegalArgumentException("DataModel with id " + dataModel.getId() + " not found");
    }

    @Override
    public void delete(int id) {
        List<DataModel> dataModels = getAll();
        dataModels.removeIf(dm -> dm.getId() == id);
        writeToFile(dataModels);
    }

    private void writeToFile(List<DataModel> dataModels) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathFile))) {
            oos.writeObject(dataModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
