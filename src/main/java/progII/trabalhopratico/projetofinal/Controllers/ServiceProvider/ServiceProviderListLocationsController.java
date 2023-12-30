package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceProviderListLocationsController implements Initializable {

    @FXML
    private Label locationLabel;

    @FXML
    private ListView<String> locationList;
    public static String selectedLocation;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);
    }

    @FXML
    void goToAppointments(ActionEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderListAp.fxml", "Consultas em " + selectedLocation, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();

        for(Location l : repo.getLocations().get(CurrentUser.serviceProvider)){
            locationList.getItems().clear();
            locationList.getItems().addAll(l.getName());
            locationList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                selectedLocation = locationList.getSelectionModel().getSelectedItem();
                locationLabel.setText(selectedLocation);
            });
        }

    }
}
