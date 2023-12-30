package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.util.Optional;

public class ServiceProviderMenuController {

    @FXML
    void addEmployee(ActionEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderAddEmployee.fxml", "Adicionar Funcionário", event);
    }

    @FXML
    void addService(ActionEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderAddService.fxml", "Adicionar Serviço", event);
    }

    @FXML
    void createLocation(ActionEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderCreateLocation.fxml", "Criar Local", event);
    }

    @FXML
    void addProduct(ActionEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderAddProd.fxml", "Gerir Produtos", event);
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
    void manageLocations(ActionEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderManageLocations.fxml",
                "Locais de" + CurrentUser.serviceProvider.getNome(), event);
    }

    @FXML
    void manageAppointments(ActionEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderListLocations.fxml", "Escolher Local", event);
    }

}
