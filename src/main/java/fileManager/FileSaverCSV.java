package fileManager;

import main.App;
import listManager.ItemList;

import java.io.IOException;
import java.nio.file.Files;

public class FileSaverCSV implements FileSaver{
    @Override
    public void save(ItemList list) throws IOException {
        Files.write(App.fileManager.getPath(), list.toCSV().getBytes());
    }
}
