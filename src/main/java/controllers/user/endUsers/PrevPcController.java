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
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.App;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.ResourceBundle;

class ComputerRegister implements Serializable{
    private transient ObservableList<Computer> computers= FXCollections.observableArrayList();

    public List<Computer> getRegister(){return computers;}

    public void attatchTableView(TableView tv){tv.setItems(computers);}


}
public class PrevPcController<T> implements Initializable {


    private TableView<Computer> computerView=new TableView<>();

    @FXML
    private GridPane viewComputersGui;

    @FXML
    private Button homeBtn;

    @FXML
    private Button addBtn;

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

    private void getComputerView(){
        TableColumn<Computer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        computerView.getColumns().add(nameCol);

        TableColumn<Computer, String> cpuCol = new TableColumn<>("CPU");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        computerView.getColumns().add(cpuCol);

        TableColumn<Computer, Double> priceCol = new TableColumn<>("Price");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        computerView.getColumns().add(priceCol);



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        getComputerView();



        viewComputersGui.add(computerView,1,1);



    }
   /* public class ComponentView<T>{


        private TableView<Computer> computerView = new TableView<>();
    }

    private void getComputerView(){

    }*/




}
