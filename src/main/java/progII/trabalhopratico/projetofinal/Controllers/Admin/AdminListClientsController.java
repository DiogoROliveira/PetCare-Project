package progII.trabalhopratico.projetofinal.Controllers.Admin;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.Client;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminListClientsController implements Initializable {

    @FXML
    private TableColumn<Client, String> addressColumn;

    @FXML
    private TableColumn<Client, Integer> ccColumn;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, Integer> nifColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TableColumn<Client, String> usernameColumn;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminMenu.fxml", "Menu Admin", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();
        clientTable.getItems().clear();
        ObservableList<Client> clients = clientTable.getItems();
        for(Client c : repo.getClients().values()){
            fillTable(usernameColumn, nameColumn, ccColumn, nifColumn, phoneColumn, addressColumn);
            clients.add(c);
        }
        clientTable.setItems(clients);

    }

    private void fillTable(TableColumn<Client, String> username, TableColumn<Client, String> name, TableColumn<Client, Integer> cc, TableColumn<Client, Integer> nif, TableColumn<Client, String> phone, TableColumn<Client, String> address){
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        name.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cc.setCellValueFactory(new PropertyValueFactory<>("numCC"));
        nif.setCellValueFactory(new PropertyValueFactory<>("NIF"));
        phone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        address.setCellValueFactory(new PropertyValueFactory<>("morada"));
    }


}
