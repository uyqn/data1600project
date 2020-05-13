package users;

import components.*;
import controllers.guiManager.DialogBox;
import fileManager.FileSaverCSV;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listManager.ItemList;
import listManager.ListableList;
import main.App;
import main.TestEnvironment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EndUser extends User {
    private boolean saved;
    private FileChooser fileChooser = new FileChooser();
    public static ListableList<Computer> listableList=new ListableList<>();
    private Path path;

    public EndUser(String username, String password) {
        super(username, password);

        File initialDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter csvExtensions =
                new FileChooser.ExtensionFilter("Comma Separated Values File (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvExtensions);

        this.saved = false;


    }

    @Override
    public void open() {
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            this.path = Paths.get(String.valueOf(file));
        }
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public <S extends Listable> void save(ItemList<S> list) {
        if (!saved || path == null) {
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                setPath(file);
                this.saved = true;
                FileSaverCSV<S> saver = new FileSaverCSV<>();
                try {
                    saver.save(path, list);
                    DialogBox.info("Successfully saved file", null,
                            "File saved to path: " + path);
                } catch (IOException e) {
                    DialogBox.error(e.getCause().toString(), null ,e.getMessage());
                }
            }
        }
    }

    @Override
    public <S extends Listable> void saveAs(ItemList<S> list) {
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            setPath(file);
            this.saved = true;
            FileSaverCSV<S> saver = new FileSaverCSV<>();
            try {
                saver.save(path, list);
                DialogBox.info("Successfully saved file", null,
                        "File saved to path: " + path);
            } catch (IOException e) {
                DialogBox.error(e.getCause().toString(), null ,e.getMessage());
            }
        }
    }

    private void setPath(File file) {
        this.path = Paths.get(String.valueOf(file));
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toCSV() {
        return null;
    }
}
