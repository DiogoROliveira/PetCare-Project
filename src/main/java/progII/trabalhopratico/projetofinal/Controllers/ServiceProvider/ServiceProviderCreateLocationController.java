package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.BLL.LocationBLL;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Exceptions.LocationException;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.util.Optional;

public class ServiceProviderCreateLocationController {

    @FXML
    private TextField addressTF;

    @FXML
    private TextField locationNameTF;

    @FXML
    private TextField locationNifTF;

    @FXML
    private TextField locationPhoneTF;

    @FXML
    private TextField serviceTypeTF;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);
    }

    @FXML
    void registerLocation(ActionEvent event) {
        Repository repo = Repository.getInstance();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmação");
        alert1.setHeaderText("Deseja criar o local?");

        Optional<ButtonType> result = alert1.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            for (Location l : repo.getCompanies().values()) {
                if (l.getName().equals(locationNameTF.getText())) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Local já existe");
                    alert2.setHeaderText("Nome de local já está em uso!");
                    alert2.showAndWait();
                    clear();
                }
            }

            Location location = new Location();
            setData(location);

            try {
                LocationBLL.createLocation(location, CurrentUser.serviceProvider);
            }catch (LocationException e){
                System.out.println(e.getMessage());
            }

            LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);

        }
    }

    private void clear(){
        locationNameTF.clear();
        addressTF.clear();
        locationPhoneTF.clear();
        serviceTypeTF.clear();
        locationNifTF.clear();
    }

    private void setData(Location l){
        l.setName(locationNameTF.getText());
        l.setOwner(CurrentUser.serviceProvider);
        l.setPhoneNumber(Integer.parseInt(locationPhoneTF.getText()));
        l.setNIF(Integer.parseInt(locationNifTF.getText()));
        l.setAddress(addressTF.getText());
        l.setServiceType(serviceTypeTF.getText());
    }


}
