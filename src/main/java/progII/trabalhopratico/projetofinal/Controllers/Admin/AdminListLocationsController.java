package progII.trabalhopratico.projetofinal.Controllers.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.*;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;
import progII.trabalhopratico.projetofinal.Entities.Repository;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminListLocationsController implements Initializable {

    @FXML
    private TableColumn<Location, String> addressColumn;
    @FXML
    private ListView<String> employeeNameList;
    @FXML
    private TableView<Location> locationsTable;
    @FXML
    private TableColumn<Location, String> nameColumn;
    @FXML
    private TableColumn<Location, Integer> nifColumn;
    @FXML
    private TableColumn<Location, Integer> phoneColumn;
    @FXML
    private ListView<String> productNameList;
    @FXML
    private ListView<String> serviceNameList;
    @FXML
    private TableColumn<Location, String> serviceTypeColumn;
    @FXML
    private TableColumn<Location, String> spColumn;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminMenu.fxml", "Menu Admin", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Repository repo = Repository.getInstance();

        locationsTable.getItems().clear();
        for(Location l : repo.getCompanies().values()){
            ObservableList<Location> locations = locationsTable.getItems();
            fillTable(l, nameColumn, serviceTypeColumn, phoneColumn, nifColumn, addressColumn, spColumn);
            locations.add(l);
            locationsTable.setItems(locations);
        }


        locationsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, location, t1) -> {
            Location l1 = locationsTable.getSelectionModel().getSelectedItem();
            employeeNameList.getItems().clear();
            productNameList.getItems().clear();
            serviceNameList.getItems().clear();
            ObservableList<String> employeeNames = employeeNameList.getItems();
            ObservableList<String> serviceNames = serviceNameList.getItems();
            ObservableList<String> productNames = productNameList.getItems();

            if(l1.getEmployees().isEmpty())
                employeeNameList.getItems().add("Local não tem funcionários");

            if(l1.getProducts().isEmpty())
                productNameList.getItems().add("Local não tem produtos");

            if(l1.getServices().isEmpty())
                serviceNameList.getItems().add("Local não tem serviços");


            for(Employee e : l1.getEmployees()){
                employeeNames.add(e.getNome());
                employeeNameList.setItems(employeeNames);
            }
            for(Product p : l1.getProducts().keySet()){
                productNames.add(p.getName());
                productNameList.setItems(productNames);
            }
            for(Service s : l1.getServices().values()){
                serviceNames.add(s.getName());
                serviceNameList.setItems(serviceNames);
            }
        });

    }

    private void fillTable(Location l, TableColumn<Location, String> name, TableColumn<Location, String> serviceType, TableColumn<Location, Integer> phone, TableColumn<Location, Integer> nif, TableColumn<Location, String> address, TableColumn<Location, String> sp){
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serviceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        nif.setCellValueFactory(new PropertyValueFactory<>("NIF"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        sp.setCellValueFactory(c -> new SimpleStringProperty(l.getOwner().getNome()));
    }


}
