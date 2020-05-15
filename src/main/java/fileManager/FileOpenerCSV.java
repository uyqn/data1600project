package fileManager;

import exceptions.InvalidCsvException;
import components.*;
import components.Storage.HDD;
import components.Storage.SSD;
import controllers.guiManager.DialogBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileOpenerCSV implements FileOpener{

    public ObservableList<Component> open(Path path) throws IOException, InvalidCsvException {
        List<String> list = Files.readAllLines(path);
        ObservableList<Component> tempList = FXCollections.observableArrayList();

        if(list.isEmpty()){
            throw new InvalidCsvException("The following file: " + path.toString() + " is empty");
        }

        for (String line : list) {
            if (!line.isEmpty()) {
                if (line.equals("Computer")) {
                    tempList.add(null);
                } else {
                    Component component = parseInfo(line);
                    if (component != null) {
                        tempList.add(component);
                    }
                }
            }
        }
        return tempList;
    }

    private Component parseInfo(String csvLine) throws InvalidCsvException {
        String[] csv = csvLine.split(Formatter.DELIMITER);
        try {
            switch (csv[0]) {
                case Cabin.COMPONENT_TYPE:
                    return new Cabin(csv);
                case Cooler.COMPONENT_TYPE:
                    return new Cooler(csv);
                case CPU.COMPONENT_TYPE:
                    return new CPU(csv);
                case GPU.COMPONENT_TYPE:
                    return new GPU(csv);
                case Keyboard.COMPONENT_TYPE:
                    return new Keyboard(csv);
                case Memory.COMPONENT_TYPE:
                    return new Memory(csv);
                case Monitor.COMPONENT_TYPE:
                    return new Monitor(csv);
                case Motherboard.COMPONENT_TYPE:
                    return new Motherboard(csv);
                case Mouse.COMPONENT_TYPE:
                    return new Mouse(csv);
                case PSU.COMPONENT_TYPE:
                    return new PSU(csv);
                case SSD.COMPONENT_TYPE:
                    return new SSD(csv);
                case HDD.COMPONENT_TYPE:
                    return new HDD(csv);
                default:
                    DialogBox.error("Unrecognizable component",
                            "The following component cannot be recognized: ",
                            csvLine);
                    return null;
            }
        } catch (NumberFormatException e){
            throw new InvalidCsvException("The following csv: \n" + csvLine + "\n" +
                    "has issues due to the following entry: " +
                    e.getMessage().substring(e.getMessage().lastIndexOf(":")+2));
        }
    }
}
