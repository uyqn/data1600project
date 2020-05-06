package fileManager;

import listManager.ItemList;
import main.App;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileSaverBin implements FileSaver {
    @Override
    public void save(ItemList list) throws IOException {
        try (OutputStream os = Files.newOutputStream(App.fileManager.getPath());
             ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(App.componentList);
        }
    }
}
