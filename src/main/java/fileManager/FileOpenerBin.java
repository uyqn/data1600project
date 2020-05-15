package fileManager;

import components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import listManager.ItemList;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOpenerBin extends Task<ObservableList<Component>> implements FileOpener{
    private Path path;

    @SuppressWarnings("unchecked")
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

    public void setPath(Path path){
        this.path = Paths.get(String.valueOf(path));
    }

    @Override
    protected ObservableList<Component> call() throws Exception {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}

            return open(this.path);
    }
}
