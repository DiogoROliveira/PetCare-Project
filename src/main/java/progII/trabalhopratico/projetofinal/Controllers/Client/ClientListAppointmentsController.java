package progII.trabalhopratico.projetofinal.Controllers.Client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.Entities.Appointment;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Entities.AppointmentState;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientListAppointmentsController implements Initializable {

    @FXML
    private Text reasonText;
    @FXML
    private Label reasonLabel;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private Label clientLabel;
    @FXML
    private ChoiceBox<String> stateChoiceBox;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<String, String> employeeColumn;
    @FXML
    private TableColumn<String, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> serviceColumn;
    @FXML
    private TableColumn<Appointment, AppointmentState> stateColumn;
    @FXML
    private TableColumn<Appointment, Double> totalColumn;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("clientMenu.fxml", "Menu Cliente", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reasonText.setVisible(false);
        reasonLabel.setVisible(false);
        Repository repo = Repository.getInstance();
        stateChoiceBox.getItems().addAll("Pendente", "Confirmada", "Cancelada", "NãoPaga", "Paga", "Todos");
        stateChoiceBox.setValue("");

        stateChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            appointmentsTable.getItems().clear();
            reasonText.setVisible(false);
            reasonLabel.setVisible(false);
            for (Appointment a : repo.getAppointments().values()) {
                if (CurrentUser.client.getUsername().equals(a.getClient().getUsername())) {
                    clientLabel.setText("Consultas de " + CurrentUser.client.getNome());
                    ObservableList<Appointment> appointments = appointmentsTable.getItems();
                    if (stateChoiceBox.getValue().equals("Pendente") && a.getState().equals(AppointmentState.PENDENTE)) {
                        fillTable(locationColumn, dateColumn, serviceColumn, stateColumn, totalColumn, employeeColumn);
                        appointments.add(a);
                        appointmentsTable.setItems(appointments);
                    } else if (stateChoiceBox.getValue().equals("Confirmada") && a.getState().equals(AppointmentState.CONFIRMADA)) {
                        fillTable(locationColumn, dateColumn, serviceColumn, stateColumn, totalColumn, employeeColumn);
                        appointments.add(a);
                        appointmentsTable.setItems(appointments);
                    } else if (stateChoiceBox.getValue().equals("Cancelada") && a.getState().equals(AppointmentState.CANCELADA)) {
                        fillTable(locationColumn, dateColumn, serviceColumn, stateColumn, totalColumn, employeeColumn);
                        appointments.add(a);
                        appointmentsTable.setItems(appointments);
                        reasonLabel.setVisible(true);
                        reasonText.setVisible(true);
                        appointmentsTable.getSelectionModel().selectedItemProperty().addListener((observableValue1, appointment, t11) -> {
                            Appointment a1 = appointmentsTable.getSelectionModel().getSelectedItem();
                            reasonLabel.setText(a1.getReason());
                        });

                        reasonLabel.setText(a.getReason());

                    } else if (stateChoiceBox.getValue().equals("NãoPaga") && a.getState().equals(AppointmentState.NAOPAGA)) {
                        fillTable(locationColumn, dateColumn, serviceColumn, stateColumn, totalColumn, employeeColumn);
                        appointments.add(a);
                        appointmentsTable.setItems(appointments);
                    } else if (stateChoiceBox.getValue().equals("Paga") && a.getState().equals(AppointmentState.PAGA)) {
                        fillTable(locationColumn, dateColumn, serviceColumn, stateColumn, totalColumn, employeeColumn);
                        appointments.add(a);
                        appointmentsTable.setItems(appointments);
                    } else if (stateChoiceBox.getValue().equals("Todos")) {
                        fillTable(locationColumn, dateColumn, serviceColumn, stateColumn, totalColumn, employeeColumn);
                        appointments.add(a);
                        appointmentsTable.setItems(appointments);
                    }
                }
            }
        });


    }

    private void fillTable(TableColumn<String, String> local, TableColumn<Appointment, String> data, TableColumn<Appointment, String> servico, TableColumn<Appointment, AppointmentState> estado, TableColumn<Appointment, Double> total, TableColumn<String, String> funcionario) {
        local.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        data.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        servico.setCellValueFactory(new PropertyValueFactory<>("service"));
        estado.setCellValueFactory(new PropertyValueFactory<>("state"));
        total.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
        funcionario.setCellValueFactory(new PropertyValueFactory<>("employee"));

    }
}
