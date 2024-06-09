package main.dao;

import main.model.DataModel;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyDMFileImpl implements IDao {
    private final String pathFile;
    private Map<Integer, DataModel> dataMap;

    public MyDMFileImpl(String pathFile) {
        this.pathFile = pathFile;
        this.dataMap = new HashMap<>();
        loadFromFile();
    }

    @Override
    public void save(DataModel dataModel) {
        dataMap.put(dataModel.getId(), dataModel);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        dataMap.remove(id);
        saveToFile();
    }

    @Override
    public Optional<DataModel> retrieve(int id) {
        return Optional.ofNullable(dataMap.get(id));
    }

    @Override
    public List<DataModel> getAll() {
        return dataMap.values().stream().collect(Collectors.toList());
    }

    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathFile))) {
            dataMap = (Map<Integer, DataModel>) ois.readObject();
        } catch (FileNotFoundException e) {
            dataMap = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathFile))) {
            oos.writeObject(dataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
