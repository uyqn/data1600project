package fileManager;

import components.Component;

import java.io.IOException;
import java.util.ArrayList;

public interface FileOpener {
    ArrayList<Component> open() throws IOException;
}
