package controllers.user.endUsers;

import components.Component;
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
import main.App;
import users.EndUser;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;



public class PrevPcController implements Initializable {



    @FXML
    private Button backHome;

    @FXML
    private Button addPc;

    @FXML
    private TableColumn<Computer, String> gpuCol;

    @FXML
    private TableColumn<Computer, String> cpuCol;

    @FXML
    private TableColumn<Computer, String> memoryCol;

    @FXML
    private TableColumn<Computer, String> storageCol;

    @FXML
    private TableColumn<Computer, Double> priceCol;

    @FXML
    private Label priceLabel;

    @FXML
    private TreeView<String> treeView;

    private ObservableList<Computer> computerList=EndUser.listableList.getList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

       computerList=EndUser.listableList.getList();

        gpuCol.setCellValueFactory(new PropertyValueFactory<>("gpuName"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("cpuName"));
        memoryCol.setCellValueFactory(new PropertyValueFactory<>("memoriesName"));
        storageCol.setCellValueFactory(new PropertyValueFactory<>("ssdName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    @FXML
    void selectPc(ActionEvent event) throws IOException{

        try{
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();

        }catch (NullPointerException ignored){}


    }

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

}
