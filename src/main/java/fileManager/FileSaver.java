package fileManager;

import listManager.ItemList;

import java.io.IOException;

public interface FileSaver{
    void save(ItemList list) throws IOException;
}
