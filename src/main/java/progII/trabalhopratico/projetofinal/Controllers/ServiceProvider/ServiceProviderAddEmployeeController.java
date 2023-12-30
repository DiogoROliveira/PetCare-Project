package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.BLL.EmployeeBLL;
import progII.trabalhopratico.projetofinal.Entities.Employee;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Exceptions.UserAlreadyExistsException;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceProviderAddEmployeeController implements Initializable {

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
    private ChoiceBox<String> employeeTypeCB;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label locationLabel;
    @FXML
    private TextField addressField;
    @FXML
    private Text addressText;
    @FXML
    private TextField ccField;
    @FXML
    private Text ccText;
    @FXML
    private PasswordField confirmPassField;
    @FXML
    private Text confirmPassText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text passwordText;
    @FXML
    private TextField nameField;
    @FXML
    private Text nameText;
    @FXML
    private TextField nifField;
    @FXML
    private Text nifText;
    @FXML
    private TextField phoneField;
    @FXML
    private Text phoneText;
    @FXML
    private TextField usernameField;
    @FXML
    private Text usernameText;
    @FXML
    private TextField vetField;
    @FXML
    private Text vetText;
    @FXML
    private Text employeeTypeText;
    @FXML
    private Text verifyPassText;
    private boolean passwordValid = false;
    @FXML
    private ListView<String> employeeNamesList;
    private final ListView<Employee> employeeList = new ListView<>();

    @FXML
    void addEmployee() {
        setFieldsVisible(true);
        addButton.setVisible(false);
        removeButton.setVisible(false);
        employeeTypeCB.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if(employeeTypeCB.getValue().equals("Veterinário")){
                vetField.setVisible(true);
                vetText.setVisible(true);
            }
            else {
                vetText.setVisible(false);
                vetField.setVisible(false);
            }
        });
    }

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);
    }

    @FXML
    void cancelAddEmployee() {
        clear();
        setFieldsVisible(false);
        vetField.setVisible(false);
        vetText.setVisible(false);
        removeButton.setVisible(true);
        addButton.setVisible(true);
    }

    @FXML
    void confirmChange(MouseEvent event) {
        Location l1 = locationsTable.getSelectionModel().getSelectedItem();
        Employee e1 = new Employee();

        if(!verifyFields()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos!");
            alert.showAndWait();
            clear();
        } else if(!passwordValid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Passwords não coincidem!");
            alert.showAndWait();
        }else {
            setData(e1, l1);
            try {
                EmployeeBLL.addEmployee(e1, l1);
            }catch (UserAlreadyExistsException ex){
                System.out.println(ex.getMessage());
            }
            back(event);
        }
    }

    @FXML
    void removeEmployee(MouseEvent event) {
        Location l1 = locationsTable.getSelectionModel().getSelectedItem();
        String employeeName = employeeNamesList.getSelectionModel().getSelectedItem();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja remover o funcionário?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                Iterator<Employee> iterator = l1.getEmployees().iterator();
                while (iterator.hasNext()) {
                    Employee e = iterator.next();
                    if ((e.getNome() + " --> " + e.getType()).equals(employeeName)) {
                        iterator.remove();
                        Repository.getInstance().getEmployees().remove(e.getUsername(), e);
                        Repository.getInstance().writeFile("src\\main\\resources\\Files\\repo.dat");
                    }
                }
            }

            back(event);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void verifyPass() {
        String password = String.valueOf(passwordField.getText());
        if(!password.equals(confirmPassField.getText())){
            verifyPassText.setVisible(true);
            passwordValid = false;
        }
        else {
            passwordValid = true;
            verifyPassText.setVisible(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clear();
        setFieldsVisible(false);
        vetField.setVisible(false);
        vetText.setVisible(false);
        employeeTypeCB.getItems().addAll("Veterinário", "Secretariado","Auxiliar","Educador de Animais");
        employeeTypeCB.setValue("");

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
            employeeList.getItems().clear();
            ObservableList<String> employeeNames = FXCollections.observableArrayList();
            ObservableList<Employee> employees = employeeList.getItems();
            if(l1.getEmployees().isEmpty()){
                employeeNamesList.getItems().clear();
                employeeNamesList.getItems().add("Local não tem funcionários!");
            }
            else {
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

    private void setFieldsVisible(boolean b){
        nameText.setVisible(b);
        nameField.setVisible(b);
        addressText.setVisible(b);
        addressField.setVisible(b);
        phoneText.setVisible(b);
        phoneField.setVisible(b);
        nifText.setVisible(b);
        nifField.setVisible(b);
        ccField.setVisible(b);
        ccText.setVisible(b);
        employeeTypeCB.setVisible(b);
        employeeTypeText.setVisible(b);
        usernameField.setVisible(b);
        usernameText.setVisible(b);
        passwordField.setVisible(b);
        passwordText.setVisible(b);
        confirmPassField.setVisible(b);
        confirmPassText.setVisible(b);
        verifyPassText.setVisible(b);
        confirmButton.setVisible(b);
        cancelButton.setVisible(b);
    }

    private void clear(){
        nameField.clear();
        addressField.clear();
        phoneField.clear();
        nifField.clear();
        ccField.clear();
        usernameField.clear();
        passwordField.clear();
        confirmPassField.clear();
        vetField.clear();
        employeeTypeCB.setValue("");
    }

    private void setData(Employee e, Location l){
        e.setLocation(l);
        e.setNome(nameField.getText());
        e.setMorada(addressField.getText());
        e.setType(employeeTypeCB.getValue());
        e.setNIF(Integer.parseInt(nifField.getText()));
        e.setTelefone(phoneField.getText());
        e.setNumCC(Integer.parseInt(ccField.getText()));
        e.setUsername(usernameField.getText());
        e.setPasswd(String.valueOf(passwordField.getText()));
        e.setNumCarteira(vetField.getText());
    }

    private boolean verifyFields(){
        return !nameField.getText().trim().isEmpty() &&
                !addressField.getText().trim().isEmpty() &&
                !phoneField.getText().trim().isEmpty() &&
                !nifField.getText().trim().isEmpty() &&
                !ccField.getText().trim().isEmpty() &&
                !usernameField.getText().trim().isEmpty() &&
                !passwordField.getText().trim().isEmpty() &&
                !confirmPassField.getText().trim().isEmpty() &&
                !employeeTypeCB.getValue().trim().isEmpty();
    }

}
