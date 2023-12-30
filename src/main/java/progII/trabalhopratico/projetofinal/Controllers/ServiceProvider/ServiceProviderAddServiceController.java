package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.BLL.ServiceBLL;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Entities.Service;
import progII.trabalhopratico.projetofinal.Exceptions.ServiceException;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceProviderAddServiceController implements Initializable {

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
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label locationLabel;
    @FXML
    private TextField nameField;
    @FXML
    private Text nameText;
    @FXML
    private TextField priceField;
    @FXML
    private Text priceText;
    @FXML
    private Button removeButton;
    @FXML
    private ListView<String> serviceNameList;
    private final ListView<Service> serviceListView = new ListView<>();
    @FXML
    private TextField serviceTypeField;
    @FXML
    private Text serviceTypeText;


    @FXML
    void addService() {
        setFieldsVisible(true);
        cancelButton.setVisible(true);
        confirmButton.setVisible(true);
        addButton.setVisible(false);
        removeButton.setVisible(false);
    }

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);
    }

    @FXML
    void cancelAddService() {
        clear();
        setFieldsVisible(false);
        cancelButton.setVisible(false);
        confirmButton.setVisible(false);
        removeButton.setVisible(true);
        addButton.setVisible(true);
    }

    @FXML
    void confirmChange(MouseEvent event) {
        Location l1 = locationsTable.getSelectionModel().getSelectedItem();
        Service s1 = new Service();

        setData(s1, l1);
        try {
            ServiceBLL.createService(s1, l1);
        }catch (ServiceException ex){
            System.out.println(ex.getMessage());
        }
        back(event);
    }


    @FXML
    void removeService(MouseEvent event) {
        Location l1 = locationsTable.getSelectionModel().getSelectedItem();
        String serviceName = serviceNameList.getSelectionModel().getSelectedItem();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja remover o serviço?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                Iterator<Service> iterator = l1.getServices().values().iterator();
                while (iterator.hasNext()) {
                    Service s = iterator.next();
                    if ((s.getName()).equals(serviceName)) {
                        iterator.remove();
                        Repository.getInstance().getServices().remove(s.getID(), s);
                        Repository.getInstance().writeFile("src\\main\\resources\\Files\\repo.dat");
                    }
                }
            }

            back(event);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clear();
        setFieldsVisible(false);
        confirmButton.setVisible(false);
        cancelButton.setVisible(false);

        Repository repo = Repository.getInstance();
        for(Location l : repo.getLocations().get(CurrentUser.serviceProvider)){
            ObservableList<Location> locations = locationsTable.getItems();
            fillTable(nameColumn, serviceTypeColumn, phoneColumn, nifColumn, addressColumn);
            locations.add(l);
            locationsTable.setItems(locations);
        }

        locationsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, location, t1) -> {
            Location l1 = locationsTable.getSelectionModel().getSelectedItem();
            locationLabel.setText(l1.getName());
            serviceListView.getItems().clear();
            ObservableList<String> serviceNames = serviceNameList.getItems();
            ObservableList<Service> services = serviceListView.getItems();
            if(l1.getServices().isEmpty()){
                serviceNameList.getItems().clear();
                serviceNameList.getItems().add("Local não tem serviços!");
            }
            else {
                for (Service s : l1.getServices().values()) {
                    serviceNames.add(s.getName());
                    services.add(s);
                }
                serviceNameList.setItems(serviceNames);
                serviceListView.setItems(services);
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

    private void setFieldsVisible(Boolean b){
        nameText.setVisible(b);
        nameField.setVisible(b);
        priceField.setVisible(b);
        priceText.setVisible(b);
        serviceTypeField.setVisible(b);
        serviceTypeText.setVisible(b);
    }

    private void clear(){
        nameField.clear();
        priceField.clear();
        serviceTypeField.clear();
    }

    private void setData(Service s, Location l){
        s.setLocation(l);
        s.setID(l.getServices().size() + 1);
        s.setType(serviceTypeField.getText());
        s.setPrice(Double.parseDouble(priceField.getText()));
        s.setName(nameField.getText());
    }

}
