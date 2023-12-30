package progII.trabalhopratico.projetofinal.Controllers.Client;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.Appointment;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Entities.AppointmentState;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientPaymentController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private Label clientLabel;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<Appointment, String> employeeColumn;
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

    @FXML
    void payAppointment(ActionEvent event) {
        Repository repo = Repository.getInstance();

        Appointment a1 = appointmentsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja efetuar o pagamento da consulta de " + a1.getService() + " do dia " + a1.getAppointmentDate());
        Optional<ButtonType> result = alert.showAndWait();

       if(result.isPresent() && result.get() == ButtonType.OK) {
           for (Appointment a : repo.getAppointments().values()) {
               if (a.equals(a1)) {
                   a.setState(AppointmentState.PAGA);
                   repo.writeFile("src\\main\\resources\\Files\\repo.dat");
                   int selectedID = appointmentsTable.getSelectionModel().getSelectedIndex();
                   appointmentsTable.getItems().remove(selectedID);
               }
           }

           LoadFXML.getInstance().loadResource("clientMenu.fxml", "Lista de Consultas", event);

       }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();
        for (Appointment a : repo.getAppointments().values()) {
            if (CurrentUser.client.getUsername().equals(a.getClient().getUsername())) {
                clientLabel.setText("Consultas de " + CurrentUser.client.getNome());
                ObservableList<Appointment> appointments = appointmentsTable.getItems();
                if (a.getState().equals(AppointmentState.NAOPAGA)) {
                    fillTable(locationColumn, dateColumn, serviceColumn, stateColumn, totalColumn, employeeColumn);
                    appointments.add(a);
                    appointmentsTable.setItems(appointments);
                }
            }
        }
    }


    private void fillTable(TableColumn<String, String> local, TableColumn<Appointment, String> data, TableColumn<Appointment, String> servico, TableColumn<Appointment, AppointmentState> estado, TableColumn<Appointment, Double> total, TableColumn<Appointment, String> funcionario) {
        local.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        data.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        servico.setCellValueFactory(new PropertyValueFactory<>("service"));
        estado.setCellValueFactory(new PropertyValueFactory<>("state"));
        total.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
        funcionario.setCellValueFactory(new PropertyValueFactory<>("employee"));
    }

}

