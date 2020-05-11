package fileManager;

import components.Component;
import listManager.ItemList;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSaver<S extends Component> {
    void save(Path path, ItemList<S> list) throws IOException;
}
