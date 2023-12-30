package progII.trabalhopratico.projetofinal.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.util.Optional;

public class AdminMenuController {

    @FXML
    void listAppointments(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminListAP.fxml", "Lista de Consultas", event);
    }

    @FXML
    void listClients(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminListClients.fxml", "Lista de Clientes", event);
    }

    @FXML
    void listLocations(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminListLocations.fxml", "Lista de Locais", event);
    }

    @FXML
    void listServiceProviders(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminListSP.fxml", "Lista de Prestadores de Serviço", event);
    }

    @FXML
    void listServices(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminListServices.fxml", "Lista de Serviços", event);
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de saída");
            alert.setHeaderText("Deseja terminar sessão?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                LoadFXML.getInstance().loadResource("login.fxml", "Login", event);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void registerAdmin(ActionEvent event) {
        LoadFXML.getInstance().loadResource("registerAdmin.fxml", "Registar Admin", event);
    }

}
