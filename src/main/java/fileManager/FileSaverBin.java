package fileManager;

import components.Component;
import listManager.ItemList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSaverBin<S extends Component> implements FileSaver<S> {
    @Override
    public void save(Path path, ItemList<S> list) throws IOException {
        try (OutputStream os = Files.newOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(list);
        }
    }
}
