package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.Entities.Appointment;
import progII.trabalhopratico.projetofinal.Entities.Employee;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceProviderManageLocationsController implements Initializable {

    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Location> locationsTable;
    @FXML
    private TableColumn<Location, String> addressColumn;
    @FXML
    private TableColumn<Location, String> serviceTypeColumn;
    @FXML
    private TableColumn<Location, String> nameColumn;
    @FXML
    private TableColumn<Location, Integer> nifColumn;
    @FXML
    private TableColumn<Location, Integer> phoneColumn;
    @FXML
    private TextField addressField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField nifField;
    @FXML
    private TextField serviceTypeField;
    @FXML
    private Text nameText;
    @FXML
    private Text addressText;
    @FXML
    private Text nifText;
    @FXML
    private Text phoneText;
    @FXML
    private Text serviceTypeText;
    @FXML
    private Label spLabel;
    @FXML
    private ListView<String> employeeNamesList;
    private final ListView<Employee> employeeList = new ListView<>();

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);
    }

    @FXML
    void deleteLocation(ActionEvent event) {
        Repository repo = Repository.getInstance();
        Location l1 = locationsTable.getSelectionModel().getSelectedItem();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja eliminar o local?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Location l : repo.getLocations().get(CurrentUser.serviceProvider)) {
                    if (l.equals(l1)) {
                        int selectedID = locationsTable.getSelectionModel().getSelectedIndex();
                        locationsTable.getItems().remove(selectedID);
                        repo.getLocations().get(CurrentUser.serviceProvider).remove(l);
                        repo.getCompanies().values().remove(l);
                        repo.writeFile("src\\main\\resources\\Files\\repo.dat");
                    }
                }

                for (Appointment a : repo.getAppointments().values()) {
                    if (a.getLocationName().equals(l1.getName())) {
                        repo.getAppointments().values().remove(a);
                        repo.writeFile("src\\main\\resources\\Files\\repo.dat");
                    }
                }

                LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void editLocationData() {
        setFieldsVisible(true);
        deleteButton.setVisible(false);
        editButton.setVisible(false);
        confirmButton.setVisible(true);
        cancelButton.setVisible(true);
        Location l1 = locationsTable.getSelectionModel().getSelectedItem();
        fillFields(l1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setVisible(false);
        confirmButton.setVisible(false);
        intializeFields();

        Repository repo = Repository.getInstance();
        for (Location l : repo.getLocations().get(CurrentUser.serviceProvider)) {
            spLabel.setText("Locais de " + CurrentUser.serviceProvider.getNome());


            ObservableList<Location> locations = locationsTable.getItems();
            fillTable(nameColumn, serviceTypeColumn, phoneColumn, nifColumn, addressColumn);
            locations.add(l);
            locationsTable.setItems(locations);
        }

        locationsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, location, t1) -> {
            Location l1 = locationsTable.getSelectionModel().getSelectedItem();
            employeeList.getItems().clear();
            ObservableList<String> employeeNames = FXCollections.observableArrayList();
            ObservableList<Employee> employees = employeeList.getItems();
            if (l1.getEmployees().isEmpty()) {
                employeeNamesList.getItems().clear();
                employeeNamesList.getItems().add("Local não tem funcionários!");
            } else {
                for (Employee e : l1.getEmployees()) {
                    employeeNames.add(e.getNome() + " --> " + e.getType());
                    employees.add(e);
                }
                employeeList.setItems(employees);
                employeeNamesList.setItems(employeeNames);
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


    public void cancelChange() {
        clearFields();
        setFieldsVisible(false);
    }

    public void confirmChange(MouseEvent event) {
        if (!nameField.getText().trim().isEmpty() && !phoneField.getText().trim().isEmpty() && !addressField.getText().trim().isEmpty() &&
                !nifField.getText().trim().isEmpty() && !serviceTypeField.getText().trim().isEmpty()) {
            for (Location l : Repository.getInstance().getLocations().get(CurrentUser.serviceProvider)) {
                if (l.getName().equals(locationsTable.getSelectionModel().getSelectedItem().getName())) {
                    l.setName(nameField.getText());
                    l.setAddress(addressField.getText());
                    l.setServiceType(serviceTypeField.getText());
                    l.setPhoneNumber(Integer.parseInt(phoneField.getText()));
                    l.setNIF(Integer.parseInt(nifField.getText()));
                }
            }
            back(event);
            Repository.getInstance().writeFile("src\\main\\resources\\Files\\repo.dat");
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos!");
            alert.show();
        }
    }

    private void setFieldsVisible(boolean b){
        nameText.setVisible(b);
        nameField.setVisible(b);
        addressText.setVisible(b);
        addressField.setVisible(b);
        phoneText.setVisible(b);
        phoneField.setVisible(b);
        nifText.setVisible(b);
        nifField.setVisible(b);
        serviceTypeText.setVisible(b);
        serviceTypeField.setVisible(b);
    }

    private void fillFields(Location l){
        nameField.setText(l.getName());
        addressField.setText(l.getAddress());
        phoneField.setText(String.valueOf(l.getPhoneNumber()));
        nifField.setText(String.valueOf(l.getNIF()));
        serviceTypeField.setText(l.getServiceType());
    }

    private void intializeFields(){
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        nifField.setText("");
        serviceTypeField.setText("");
    }

    private void clearFields(){
        nameField.clear();
        addressField.clear();
        phoneField.clear();
        nifField.clear();
        serviceTypeField.clear();
    }

}
