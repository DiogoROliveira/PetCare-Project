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
import progII.trabalhopratico.projetofinal.Entities.Service;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminListServicesController implements Initializable {

    @FXML
    private TableColumn<Service, Integer> idColumn;

    @FXML
    private ListView<String> locationNameList;

    @FXML
    private TableColumn<Service, String> nameColumn;

    @FXML
    private TableColumn<Service, Double> priceColumn;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, String> typeColumn;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminMenu.fxml", "Menu Admin", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();
        locationNameList.getItems().clear();
        ObservableList<String> locationNames = locationNameList.getItems();
        for(Location l : repo.getCompanies().values()){
            locationNames.add(l.getName());
        }
        locationNameList.setItems(locationNames);

        locationNameList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String locationName = locationNameList.getSelectionModel().getSelectedItem();
            serviceTable.getItems().clear();
            ObservableList<Service> services = serviceTable.getItems();
            for(Service s1 : repo.getServices().values()){
                if(locationName.equalsIgnoreCase(s1.getLocation().getName())){
                    fillTable(idColumn, nameColumn, priceColumn, typeColumn);
                    services.add(s1);
                }
            }
            serviceTable.setItems(services);
        });
    }

    private void fillTable(TableColumn<Service, Integer> id, TableColumn<Service, String> name, TableColumn<Service, Double> price, TableColumn<Service, String> type){
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
    }


}
