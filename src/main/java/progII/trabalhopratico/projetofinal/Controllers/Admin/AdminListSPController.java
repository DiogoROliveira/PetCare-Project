package progII.trabalhopratico.projetofinal.Controllers.Admin;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Entities.ServiceProvider;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminListSPController implements Initializable {

    @FXML
    private TableView<Location> locationsTable;
    @FXML
    private TableColumn<Location, String> addressColumn;
    @FXML
    private TableColumn<Location, String> nameColumn;
    @FXML
    private TableColumn<Location, String> serviceTypeColumn;
    @FXML
    private TableColumn<Location, Integer> nifColumn;
    @FXML
    private TableColumn<Location, Integer> phoneColumn;
    @FXML
    private ListView<String> spNameList;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminMenu.fxml", "Menu Admin", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Repository repo = Repository.getInstance();
        spNameList.getItems().clear();
        ObservableList<String> spNames = spNameList.getItems();
        for(ServiceProvider sp : repo.getServiceProviders().values()){
            spNames.add(sp.getNome());
        }
        spNameList.setItems(spNames);

        spNameList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            locationsTable.getItems().clear();
            String spName = spNameList.getSelectionModel().getSelectedItem();
            ObservableList<Location> locations = locationsTable.getItems();
            for(Location l : repo.getCompanies().values()){
                if(l.getOwner().getNome().equalsIgnoreCase(spName)){
                    fillTable(nameColumn, serviceTypeColumn, phoneColumn, nifColumn, addressColumn);
                    locations.add(l);
                    locationsTable.setItems(locations);
                }
            }
        });
    }

    private void fillTable(TableColumn<Location, String> name, TableColumn<Location, String> serviceType, TableColumn<Location, Integer> phone, TableColumn<Location, Integer> nif, TableColumn<Location, String> address){
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serviceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        nif.setCellValueFactory(new PropertyValueFactory<>("NIF"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

}
