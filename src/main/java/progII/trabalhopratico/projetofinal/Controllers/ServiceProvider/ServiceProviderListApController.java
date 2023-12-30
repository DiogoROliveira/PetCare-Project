package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.Entities.Appointment;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Entities.AppointmentState;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static progII.trabalhopratico.projetofinal.Controllers.ServiceProvider.ServiceProviderListLocationsController.selectedLocation;

public class ServiceProviderListApController implements Initializable {

    @FXML
    private Text reasonText;
    @FXML
    private TextField reasonField;
    @FXML
    private TableColumn<Appointment, Integer> idColumn;
    @FXML
    private TableColumn<Appointment, String> clientColumn;
    @FXML
    private TableColumn<Appointment, String> employeeColumn;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<Appointment, String> serviceColumn;
    @FXML
    private TableColumn<Appointment, Double> totalColumn;
    @FXML
    private TableColumn<Appointment, AppointmentState> stateColumn;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button editButton;
    @FXML
    private Text editStateText;
    @FXML
    private ChoiceBox<AppointmentState> editStateChoiceBox;
    @FXML
    private Label locationLabel;
    @FXML
    private TableView<Appointment> apTableView;
    @FXML
    private Text stateText;
    @FXML
    private ChoiceBox<String> stateChoiceBox;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderListLocations.fxml", "Escolher Local", event);
    }

    @FXML
    void cancelChanges() {
        editStateChoiceBox.setVisible(false);
        editStateText.setVisible(false);
        confirmButton.setVisible(false);
        cancelButton.setVisible(false);
        editButton.setVisible(true);
        stateChoiceBox.setVisible(true);
        stateText.setVisible(true);
    }

    @FXML
    void confirmChanges(MouseEvent event) {
        Appointment a1 = apTableView.getSelectionModel().getSelectedItem();
        if(editStateChoiceBox.getValue().equals(a1.getState())){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Consulta já se encontrava " + editStateChoiceBox.getValue() + "!");
            alert.showAndWait();
            editStateChoiceBox.setValue(null);

        }else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja alterar a consulta?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                if (editStateChoiceBox.getValue().equals(AppointmentState.PENDENTE)) {
                    a1.setState(AppointmentState.PENDENTE);
                } else if (editStateChoiceBox.getValue().equals(AppointmentState.CONFIRMADA)) {
                    a1.setState(AppointmentState.CONFIRMADA);
                } else if (editStateChoiceBox.getValue().equals(AppointmentState.CANCELADA)) {
                    a1.setState(AppointmentState.CANCELADA);
                    a1.setReason(reasonField.getText());
                } else if (editStateChoiceBox.getValue().equals(AppointmentState.NAOPAGA)) {
                    a1.setState(AppointmentState.NAOPAGA);
                } else if (editStateChoiceBox.getValue().equals(AppointmentState.PAGA)) {
                    a1.setState(AppointmentState.PAGA);
                }
            }
            Repository.getInstance().writeFile("src\\main\\resources\\Files\\repo.dat");
            back(event);

        }
    }

    @FXML
    void editAp() {
        editStateChoiceBox.setVisible(true);
        editStateText.setVisible(true);
        confirmButton.setVisible(true);
        cancelButton.setVisible(true);
        editButton.setVisible(false);
        stateChoiceBox.setVisible(false);
        stateText.setVisible(false);
        editStateChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, appointmentState, t1) -> {
            if(editStateChoiceBox.getValue().equals(AppointmentState.CANCELADA)){
                reasonText.setVisible(true);
                reasonField.setVisible(true);
            }else {
                reasonField.setVisible(false);
                reasonText.setVisible(false);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editStateChoiceBox.getItems().addAll(AppointmentState.values());
        stateChoiceBox.getItems().addAll("Pendente", "Confirmada", "Cancelada", "NãoPaga", "Paga", "Todos");
        stateChoiceBox.setValue("");
        locationLabel.setText("Consultas de " + selectedLocation);

        cancelButton.setVisible(false);
        confirmButton.setVisible(false);
        editStateChoiceBox.setVisible(false);
        editStateText.setVisible(false);
        reasonField.setVisible(false);
        reasonText.setVisible(false);

        Repository repo = Repository.getInstance();
        stateChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            apTableView.getItems().clear();
            for (Appointment a : repo.getAppointments().values()) {
                if (a.getLocationName().equals(selectedLocation)) {
                    ObservableList<Appointment> appointments = apTableView.getItems();
                    if (stateChoiceBox.getValue().equals("Pendente") && a.getState().equals(AppointmentState.PENDENTE)){
                        fillTable(a, idColumn, clientColumn, employeeColumn, dateColumn, serviceColumn, totalColumn, stateColumn);
                        appointments.add(a);
                        apTableView.setItems(appointments);
                    }else if (stateChoiceBox.getValue().equals("Confirmada") && a.getState().equals(AppointmentState.CONFIRMADA)){
                        fillTable(a, idColumn, clientColumn, employeeColumn, dateColumn, serviceColumn, totalColumn, stateColumn);
                        appointments.add(a);
                        apTableView.setItems(appointments);
                    }else if (stateChoiceBox.getValue().equals("Cancelada") && a.getState().equals(AppointmentState.CANCELADA)){
                        fillTable(a, idColumn, clientColumn, employeeColumn, dateColumn, serviceColumn, totalColumn, stateColumn);
                        appointments.add(a);
                        apTableView.setItems(appointments);
                    }else if (stateChoiceBox.getValue().equals("NãoPaga") && a.getState().equals(AppointmentState.NAOPAGA)){
                        fillTable(a, idColumn, clientColumn, employeeColumn, dateColumn, serviceColumn, totalColumn, stateColumn);
                        appointments.add(a);
                        apTableView.setItems(appointments);
                    }else if (stateChoiceBox.getValue().equals("Paga") && a.getState().equals(AppointmentState.PAGA)){
                        fillTable(a, idColumn, clientColumn, employeeColumn, dateColumn, serviceColumn, totalColumn, stateColumn);
                        appointments.add(a);
                        apTableView.setItems(appointments);
                    }else if (stateChoiceBox.getValue().equals("Todos")){
                        fillTable(a, idColumn, clientColumn, employeeColumn, dateColumn, serviceColumn, totalColumn, stateColumn);
                        appointments.add(a);
                        apTableView.setItems(appointments);
                    }
                }
            }
        });
    }

    private void fillTable(Appointment a, TableColumn<Appointment, Integer> id, TableColumn<Appointment, String> client, TableColumn<Appointment, String> employee, TableColumn<Appointment, String> date, TableColumn<Appointment, String> service, TableColumn<Appointment, Double> total, TableColumn<Appointment, AppointmentState> state){
        id.setCellValueFactory(new PropertyValueFactory<>("idAppointment"));
        client.setCellValueFactory(c -> new SimpleStringProperty(a.getClient().getNome()));
        employee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        date.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        total.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
    }


}
