package fileManager;

import controllers.guiManager.DialogBox;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listManager.ItemList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private Path path;
    private boolean saved;
    private FileChooser fileChooser = new FileChooser();

    public FileManager() {
        File initialDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter csvExtensions =
                new FileChooser.ExtensionFilter("Comma Separated Values File (*.csv)", "*.csv");
        FileChooser.ExtensionFilter binExtensions =
                new FileChooser.ExtensionFilter("Binary File (*.bin)", "*.bin");
        fileChooser.getExtensionFilters().addAll(csvExtensions, binExtensions);

        this.saved = false;
    }

    public FileManager(File file, boolean saved) {
        setPath(file);
        this.saved = saved;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setPath(File file) {
        this.path = Paths.get(String.valueOf(file));
    }

    public Path getPath() {
        return path;
    }

    public String getExtension() {
        try {
            return path.toString().substring(path.toString().indexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void save(ItemList list) {
        if (!saved || path == null) {
            File saveToPath = fileChooser.showSaveDialog(new Stage());
            if (saveToPath != null) {
                setPath(saveToPath);
                setSaved(true);
                writeFile(list);
            }
        }
    }

    public void saveAs(ItemList list) {
        File saveToPath = fileChooser.showSaveDialog(new Stage());
        if(saveToPath != null){
            setPath(saveToPath);
            setSaved(true);
            writeFile(list);
        }
    }

    private void writeFile(ItemList list) {
        FileSaver saver = null;
        switch (getExtension()) {
            case ".csv":
                saver = new FileSaverCSV();
                break;
            case ".bin":
                saver = new FileSaverBin();
                break;
            default:
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Save error");
                error.setHeaderText("Unable to save file");
                error.setContentText("Unavailable extension: " + getExtension());
                error.showAndWait();
        }

        if(saver != null){
            try {
                saver.save(list);
                DialogBox.info("Successfully saved file", null,
                        "File saved to path: " + path);
            } catch (IOException e) {
                DialogBox.error(e.getCause().toString(), null ,e.getMessage());
            }
        }
    }
}