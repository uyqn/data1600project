package users;

import components.Computer;
import components.Listable;
import controllers.guiManager.DialogBox;
import fileManager.FileSaverBin;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listManager.ItemList;
import listManager.ListableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SuperUser extends User {
    private Path path;
    private boolean saved;
    private FileChooser fileChooser = new FileChooser();

    public SuperUser(String username, String password) {
        super(username, password);

        File initialDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter binExtensions =
                new FileChooser.ExtensionFilter("Binary File (*.bin)", "*.bin");
        fileChooser.getExtensionFilters().add(binExtensions);

        this.saved = false;
    }

    public void open(){
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            this.path = Paths.get(String.valueOf(file));
        }
    }

    public Path getPath() {
        return path;
    }
    public <S extends Listable> void save(ItemList<S> list) {
        if (!saved || path == null) {
            File saveToPath = fileChooser.showSaveDialog(new Stage());
            if (saveToPath != null) {
                setPath(saveToPath);
                this.saved = true;
                FileSaverBin<S> saver = new FileSaverBin<>();
                try {
                    saver.save(path, list);
                    DialogBox.info("Successfully saved file", null,
                            "File saved to path: " + path);
                } catch (IOException e) {
                    DialogBox.error(e.getCause().toString(), null ,e.getMessage());;
                }
            }
        }
    }

    public <S extends Listable> void saveAs(ItemList<S> list) {
        File saveToPath = fileChooser.showSaveDialog(new Stage());
        if(saveToPath != null){
            setPath(saveToPath);
            this.saved = true;
            FileSaverBin<S> saver = new FileSaverBin<>();
            try {
                saver.save(path, list);
                DialogBox.info("Successfully saved file", null,
                        "File saved to path: " + path);
            } catch (IOException e) {
                DialogBox.error(e.getCause().toString(), null ,e.getMessage());;
            }
        }
    }

    @Override
    public void remove(Computer computer) {

    }

    @Override
    public ListableList<Computer> getComputers() {
        return null;
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
