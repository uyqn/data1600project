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
    private TableView<Computer> computerView;

    @FXML
    private TableColumn<Computer, String> nameCol;

    @FXML
    private TableColumn<Computer, Double> priceComputerCol;

    @FXML
    private Button selectPc;

    @FXML
    private TreeView<String> treeView=new TreeView<>();

    private ObservableList<Computer> computerList=EndUser.listableList.getList();




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
      /* filterBox.getItems().setAll("Name", "Price (NOK) ≤", "CPU", "GPU", "Motherboard", "Memory", "Storage", "Cooler", "Power supply", "Cabin", "Monitor", "Keyboard", "Mouse");
       filterBox.setValue(null);*/

       computerList=EndUser.listableList.getList();


        nameCol.setCellValueFactory(new PropertyValueFactory<Computer, String >("name"));
        priceComputerCol.setCellValueFactory(new PropertyValueFactory<Computer, Double>("price"));
//        computerView.setItems(computerList);

        try{
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();

        }catch (NullPointerException ignored){}


    }

    @FXML
    void selectPc(ActionEvent event) throws IOException{


       // if ()





        //treeView.setItems(computerView.getSelectionModel().getSelectedItem().getComponents());


    }

}
