package progII.trabalhopratico.projetofinal.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientListLocationsController implements Initializable {

    @FXML
    private ListView<String> locationList;
    static String currentLocation;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("clientMenu.fxml", "Menu Cliente", event);
    }

    @FXML
    void createAppointment(ActionEvent event) {
        LoadFXML.getInstance().loadResource("makeAppointment.fxml", "Marcar Consulta", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();
        for(Location l : repo.getCompanies().values()){
            locationList.getItems().addAll(l.getName());
            locationList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> currentLocation = locationList.getSelectionModel().getSelectedItem());
        }
    }
}