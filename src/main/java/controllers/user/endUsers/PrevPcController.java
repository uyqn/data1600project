package controllers.user.endUsers;

import components.Computer;
import javafx.beans.binding.Bindings;
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
    private Button removeButton;

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
    private Menu userAccount;

    @FXML
    private Label priceLabel;

    @FXML
    private TreeView<String> treeView;

    @FXML
    void open(ActionEvent event) {
        try {
            App.user.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tableView.setItems(App.user.getComputers().getList());
        tableView.refresh();
        if(treeView.getRoot() != null){
            treeView.getRoot().setExpanded(false);
            priceLabel.setText("Price: ");
        }
    }

    @FXML
    void save(ActionEvent event) {
        App.user.save(App.user.getComputers());
    }

    @FXML
    void saveAs(ActionEvent event) {
        App.user.saveAs(App.user.getComputers());
    }

    @FXML
    void remove(ActionEvent event) {
        App.user.getComputers().remove(tableView.getSelectionModel().getSelectedItem());
        tableView.getSelectionModel().clearSelection();
        tableView.refresh();
        priceLabel.setText("Price: ");
        treeView.getRoot().setExpanded(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        removeButton.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        gpuCol.setCellValueFactory(new PropertyValueFactory<>("gpuName"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("cpuName"));
        memoryCol.setCellValueFactory(new PropertyValueFactory<>("totalRamString"));
        storageCol.setCellValueFactory(new PropertyValueFactory<>("totalStorageString"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceString"));

        tableView.setItems(App.user.getComputers().getList());
        userAccount.setText(App.user.getUsername());
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
