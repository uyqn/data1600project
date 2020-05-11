package fileManager;

import components.Component;
import controllers.guiManager.DialogBox;
import javafx.collections.ObservableList;
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
                saver.save(getPath(), list);
                DialogBox.info("Successfully saved file", null,
                        "File saved to path: " + path);
            } catch (IOException e) {
                DialogBox.error(e.getCause().toString(), null ,e.getMessage());
            }
        }
    }

    public ObservableList<Component> open(){
        File openFromPath = fileChooser.showOpenDialog(new Stage());

        if(openFromPath != null){
            setPath(openFromPath);
            setSaved(true);
            return readFile();
        }
        else{
            return null;
        }
    }

    private ObservableList<Component> readFile() {
        FileOpener opener = null;

        switch (getExtension()){
            case ".csv":
                opener = new FileOpenerCSV();
                break;
            case ".bin":
                opener = new FileOpenerBin();
                break;
            default:
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Open error");
                error.setHeaderText("Unable to open file");
                error.setContentText("Unavailable extension: " + getExtension());
                error.showAndWait();
        }

        if(opener != null){
            try {
                return opener.open(getPath());
            } catch (IOException | ClassNotFoundException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle(e.getCause().toString());
                error.setHeaderText("Unable to open file");
                error.setContentText("Cause: " + e.getMessage());
                error.showAndWait();
                return null;
            }
        }
        else{
            return null;
        }

    }
}