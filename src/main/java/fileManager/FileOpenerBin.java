package fileManager;

import components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import listManager.ItemList;
import main.App;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;

public class FileOpenerBin implements FileOpener {

    @SuppressWarnings("unchecked")
    @Override
    public ObservableList<Component> open() throws IOException, ClassNotFoundException {
        ObservableList<Component> list = FXCollections.observableArrayList();

        try (InputStream in = Files.newInputStream(App.fileManager.getPath());
             ObjectInputStream oin = new ObjectInputStream(in))
        {
           ItemList tempList = (ItemList) oin.readObject();
           list.addAll(tempList.getList());
        }

        return list;
    }
}
