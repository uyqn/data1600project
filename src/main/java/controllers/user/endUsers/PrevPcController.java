package controllers.user.endUsers;

import components.Computer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import users.EndUser;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;



public class PrevPcController implements Initializable {



    @FXML
    private TableView<Computer> tableView;

    @FXML
    private TableColumn<Computer, String> nameCol;

    @FXML
    private TableColumn<Computer, Double> priceCol;

    @FXML
    private TableColumn<Computer, String> cpuCol;

    @FXML
    private TableColumn<Computer, String> gpuCol;
    @FXML
    private TableColumn<Computer, String> mbCol;
    @FXML
    private TableColumn<Computer, String> memoryCol;
    @FXML
    private TableColumn<Computer, String>ssdCol;
    @FXML
    private TableColumn<Computer, String> hddCol;

    @FXML
    private TableColumn<Computer, String>coolerCol;

    @FXML
    private TableColumn<Computer, String> psCol;

    @FXML
    private TableColumn<Computer, String> cabinCol;

    @FXML
    private TableColumn<Computer, String> monitorCol;

    @FXML
    private TableColumn<Computer, String> keyboardCol;

    @FXML
    private TableColumn<Computer, String> mouseCol;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;


    private ObservableList<Computer> computerList=EndUser.listableList.getList().stream().filter(computer ->
            computer.getName().equals(computer.getName())
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));


    @FXML
    void addPc(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/buildPc.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void backHome(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUser.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
       filterBox.getItems().setAll("Name", "Price (NOK) ≤", "CPU", "GPU", "Motherboard", "Memory", "Storage", "Cooler", "Power supply", "Cabin", "Monitor", "Keyboard", "Mouse");
       filterBox.setValue(null);

       computerList=EndUser.listableList.getList();


        nameCol.setCellValueFactory(new PropertyValueFactory<Computer, String >("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Computer, Double>("price"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("cpuName"));
        gpuCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("gpuName"));
        mbCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("motherboardName"));
        memoryCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("memoryName"));
        ssdCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("ssdName"));
        hddCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("hddName"));
        coolerCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("coolerName"));
        psCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("psuName"));
        cabinCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("cabinName"));
        monitorCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("monitorName"));
        keyboardCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("keyboardName"));
        mouseCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("mouseName"));

        tableView.setItems(computerList);


    }

    @FXML
    void filterEvt(KeyEvent event){
        String search = filterText.getText().toLowerCase();
        int filterIndex=filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(computerList.stream().filter(computer -> {
            if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem()==null){
                return true;
            }
            else {
                switch (filterIndex){
                    case 0:
                        return computer.getName().toLowerCase().contains(search);
                    case 1:
                        try {
                            return computer.getPrice() < Double.parseDouble(search);
                        }catch (NumberFormatException e){
                            return false;
                        }
                    case 2:
                        return computer.getCpuName().toLowerCase().contains(search);
                    case 3:
                        return computer.getGpuName().toLowerCase().contains(search);
                    case 4:
                        return computer.getMotherboardName().toLowerCase().contains(search);
                    case 5:
                        return computer.getMemoriesName().toLowerCase().contains(search);
                    case 6:
                        return computer.getStorageName().toLowerCase().contains(search);
                    case 7:
                        return computer.getCoolerName().toLowerCase().contains(search);
                    case 8:
                        return computer.getPsuName().toLowerCase().contains(search);
                    case 9:
                        return computer.getCabinName().toLowerCase().contains(search);
                    case 10:
                        return computer.getMonitorName().toLowerCase().contains(search);
                    case 11:
                        return computer.getKeyboardName().toLowerCase().contains(search);
                    case 12:
                        return computer.getMouseName().toLowerCase().contains(search);

                    default:
                        return false;
                }
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }


}
