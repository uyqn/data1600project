package fileManager;

import components.Listable;
import listManager.ItemList;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSaver<S extends Listable> {
    void save(Path path, ItemList<S> list) throws IOException;
}
