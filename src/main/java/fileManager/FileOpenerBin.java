package fileManager;

import components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import listManager.ItemList;
import main.App;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOpenerBin extends Task<ObservableList<Component>> implements FileOpener {

    @SuppressWarnings("unchecked")
    @Override
    public ObservableList<Component> open(Path path) throws IOException, ClassNotFoundException {
        ObservableList<Component> list = FXCollections.observableArrayList();

        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream oin = new ObjectInputStream(in))
        {
           ItemList<Component> tempList = (ItemList<Component>) oin.readObject();
           list.addAll(tempList.getList());
        }

        return list;
    }

    @Override
    protected ObservableList<Component> call() throws Exception {
        try{
            Thread.sleep(3000);
        } catch (InterruptedException ignored){}

        return open(App.fileManager.getPath());
    }
}
