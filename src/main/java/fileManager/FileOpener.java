package fileManager;

import components.Component;
import javafx.collections.ObservableList;

import java.io.IOException;

public interface FileOpener {
    ObservableList<Component> open() throws IOException, ClassNotFoundException;
}
