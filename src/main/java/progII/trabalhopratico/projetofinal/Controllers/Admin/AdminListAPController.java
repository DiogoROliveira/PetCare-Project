package progII.trabalhopratico.projetofinal.Controllers.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.Appointment;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Entities.AppointmentState;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminListAPController implements Initializable {

    @FXML
    private TableView<Appointment> apTable;

    @FXML
    private TableColumn<Appointment, String> clientColumn;

    @FXML
    private TableColumn<Appointment, String> dateColumn;

    @FXML
    private TableColumn<Appointment, String> employeeColumn;

    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> serviceColumn;

    @FXML
    private TableColumn<Appointment, AppointmentState> stateColumn;

    @FXML
    private TableColumn<Appointment, Double> totalColumn;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("adminMenu.fxml", "Menu Admin", event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();
        apTable.getItems().clear();
        ObservableList<Appointment> appointments = apTable.getItems();
        for(Appointment a : repo.getAppointments().values()){
            fillTable(a, idColumn, clientColumn, dateColumn, employeeColumn, locationColumn, totalColumn, stateColumn, serviceColumn);
            appointments.add(a);
        }
        apTable.setItems(appointments);
    }

    private void fillTable(Appointment a, TableColumn<Appointment, Integer> id, TableColumn<Appointment, String> client, TableColumn<Appointment, String> date, TableColumn<Appointment, String> employee, TableColumn<Appointment, String> location, TableColumn<Appointment, Double> total, TableColumn<Appointment, AppointmentState> state, TableColumn<Appointment, String> service){
        id.setCellValueFactory(new PropertyValueFactory<>("idAppointment"));
        client.setCellValueFactory(c -> new SimpleStringProperty(a.getClient().getNome()));
        date.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        employee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        location.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        total.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
    }


}
