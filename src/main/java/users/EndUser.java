package users;

import components.Component;
import components.Computer;
import components.Listable;
import controllers.guiManager.DialogBox;
import exceptions.EmptyCsvException;
import exceptions.InvalidCsvException;
import exceptions.NotCompatibleException;
import fileManager.FileOpenerCSV;
import fileManager.FileSaverCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listManager.ItemList;
import listManager.ListableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EndUser extends User {
    private boolean saved;
    private FileChooser fileChooser = new FileChooser();
    private ListableList<Computer> listableList = new ListableList<>();
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

    public void add(Computer computer){
        listableList.add(computer);
    }

    public ObservableList<Computer> getList(){
        return listableList.getList();
    }

    @Override
    public void remove(Computer computer) {
        listableList.remove(computer);
    }

    @Override
    public ListableList<Computer> getComputers(){
        return listableList;
    }

    @Override
    public void open() throws IOException {
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            this.path = Paths.get(String.valueOf(file));
            this.saved = true;
            FileOpenerCSV opener = new FileOpenerCSV();
            try {
                listableList.setList(analyzeList(opener.open(getPath())));
            } catch (EmptyCsvException ignored){

            } catch (InvalidCsvException e){
                DialogBox.error("Failed to open",
                        "Cannot open " + getPath(),
                        e.getMessage());
            } catch (NotCompatibleException e){
                DialogBox.error("Failed to create PC", null, e.getMessage());
            }
        }
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    private ObservableList<Computer> analyzeList(ObservableList<Component> list){
        int numberOfComputers = 0;
        for(Component component : list){
            if(numberOfComputers > 1){
                break;
            }
            if(component == null){
                numberOfComputers++;
            }
        }

        ObservableList<Computer> newList = FXCollections.observableArrayList();

        Computer computer = new Computer();
        if(numberOfComputers > 1) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) == null) {
                    newList.add(computer);
                    computer = new Computer();
                } else {
                    computer.addComponent(list.get(i));
                }
            }
            newList.add(computer);
        } else {
            for(Component component : list){
                if(component != null){
                    computer.addComponent(component);
                }
            }
            newList.add(computer);
        }

        return newList;
    }

    @Override
    public <S extends Listable> void save(ItemList<S> list) {
        FileSaverCSV<S> saver = new FileSaverCSV<>();
        if (!saved || path == null) {
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                setPath(file);
                this.saved = true;
                saver = new FileSaverCSV<>();
                try {
                    saver.save(path, list);
                    DialogBox.info("Successfully saved file", null,
                            "File saved to path: " + path);
                } catch (IOException e) {
                    DialogBox.error(e.getCause().toString(), null ,e.getMessage());
                }
            }
        }
        else {
            try {
                saver.save(path, list);
            } catch (IOException e) {
                DialogBox.error(e.getCause().toString(), null ,e.getMessage());
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
