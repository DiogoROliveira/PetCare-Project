package progII.trabalhopratico.projetofinal.Controllers.Employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.util.Optional;

public class EmployeeMenuController {

    @FXML
    void logout(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de saída");
            alert.setHeaderText("Deseja terminar sessão?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                LoadFXML.getInstance().loadResource("login.fxml", "Login", event);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void manageAps(ActionEvent event) {
        LoadFXML.getInstance().loadResource("employeeManageAps.fxml", "Lista de Consultas", event);
    }

    @FXML
    void showStats(ActionEvent event) {
        LoadFXML.getInstance().loadResource("employeeStats.fxml", "Estatistícas", event);
    }
}
