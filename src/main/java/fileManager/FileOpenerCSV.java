package fileManager;

import components.CPU;
import components.Component;
import components.Cooler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.App;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileOpenerCSV implements FileOpener{
    @Override
    public ObservableList<Component> open() throws IOException {
        List<String> list = Files.readAllLines(App.fileManager.getPath());
        ObservableList<Component> tempList = FXCollections.observableArrayList();

        for (String csvString : list) {
            switch (csvString.split(Formatter.DELIMITER)[0]) {
                case CPU.COMPONENT_TYPE:
                    tempList.add(createCPU(csvString));
                    break;
                case Cooler.COMPONENT_TYPE:
                    tempList.add(createCooler(csvString));
                    break;
            }
        }
        return tempList;
    }

    private Component createCooler(String csv) {
        return new Cooler(csv.split(Formatter.DELIMITER));
    }

    public CPU createCPU(String csv){
        return new CPU(csv.split(Formatter.DELIMITER));
    }
}
