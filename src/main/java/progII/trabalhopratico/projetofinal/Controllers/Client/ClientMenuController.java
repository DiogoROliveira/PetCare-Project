package progII.trabalhopratico.projetofinal.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.util.Optional;

public class ClientMenuController {

    @FXML
    void goToPayments(ActionEvent event) {
        LoadFXML.getInstance().loadResource("clientPayment.fxml", "Pagar Consulta", event);
    }

    @FXML
    void listAppointments(ActionEvent event) {
        LoadFXML.getInstance().loadResource("clientListAppointments.fxml", "Listar Consultas", event);
    }

    @FXML
    void listLocations(ActionEvent event) {
        LoadFXML.getInstance().loadResource("listLocations.fxml", "Locais de entrega/recolha", event);
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de saída");
            alert.setHeaderText("Deseja terminar sessão?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                LoadFXML.getInstance().loadResource("login.fxml", "Login", event);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
