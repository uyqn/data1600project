package controllers.user.endUsers;

import components.Computer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PrevPcController implements Initializable {
    @FXML
    private TableView<Computer> tableView;

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
    private TableColumn<Computer, String> priceCol;

    @FXML
    private Label priceLabel;

    @FXML
    private TreeView<String> treeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        gpuCol.setCellValueFactory(new PropertyValueFactory<>("gpuName"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("cpuName"));
        memoryCol.setCellValueFactory(new PropertyValueFactory<>("totalRamString"));
        storageCol.setCellValueFactory(new PropertyValueFactory<>("totalStorageString"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceString"));

        tableView.setItems(App.user.getComputers().getList());
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

    @FXML
    void keyPressedComputer(KeyEvent event) {
        if(tableView.getSelectionModel().getSelectedItem() != null){
            treeView = tableView.getSelectionModel().getSelectedItem().setTreeView(treeView);
            priceLabel.setText(
                    "Price: " + String.format("%.2f",tableView.getSelectionModel().getSelectedItem().getPrice()) + " " +
                            "NOK"
            );
        }
        treeView.refresh();
    }

    @FXML
    void mouseClickedComputer(MouseEvent event) {
        if(tableView.getSelectionModel().getSelectedItem() != null){
            treeView = tableView.getSelectionModel().getSelectedItem().setTreeView(treeView);
            priceLabel.setText(
                    "Price: " + String.format("%.2f",tableView.getSelectionModel().getSelectedItem().getPrice()) + " NOK"
            );
        }
        treeView.refresh();
    }
}
