package fileManager;

import components.Component;
import listManager.ItemList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSaverCSV<S extends Component> implements FileSaver<S>{
    @Override
    public void save(Path path, ItemList<S> list) throws IOException {
        Files.write(path, list.toCSV().getBytes());
    }
}
