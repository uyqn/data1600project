package fileManager;

import components.Component;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Path;

public interface FileOpener {
    ObservableList<Component> open(Path path) throws IOException, ClassNotFoundException;
}