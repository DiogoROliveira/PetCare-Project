package progII.trabalhopratico.projetofinal.Controllers.LoginAndRegister;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.*;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;
import progII.trabalhopratico.projetofinal.Entities.Repository;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> userTypeCB;

    public void initialize(URL url, ResourceBundle rb) {
        userTypeCB.getItems().addAll("Cliente", "Funcionário", "Prestador de Serviço", "Admin");
    }


    @FXML
    private void clearText() {
        usernameField.clear();
        passwordField.clear();
        userTypeCB.setValue("");
    }

    @FXML
    void login(ActionEvent event) {

        boolean found = false;
        try {
            Repository repo;
            repo = Repository.getInstance();
            if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de login");
                alert.setHeaderText("Preencha todos os campos");
                alert.showAndWait();
                return;
            }
            if(userTypeCB.getValue().equals("Cliente")) {

                for (Client c : repo.getClients().values()) {
                    if (usernameField.getText().equals(c.getUsername()) && passwordField.getText().equals(c.getPasswd())) {
                        found = true;
                        CurrentUser.client = c;
                        System.out.println("Login efetuado com sucesso!");
                        LoadFXML.getInstance().loadResource("clientMenu.fxml", "Menu Cliente", event);
                    }
                }
            }else if(userTypeCB.getValue().equals("Prestador de Serviço")){

                for(ServiceProvider sp : repo.getServiceProviders().values()){
                    if(usernameField.getText().equals(sp.getUsername()) && passwordField.getText().equals(sp.getPasswd())){
                        found = true;
                        CurrentUser.serviceProvider = sp;
                        System.out.println("Login efetuado com sucesso!");
                        LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);
                    }
                }
            } else if (userTypeCB.getValue().equals("Funcionário")) {

                for (Employee em : repo.getEmployees().values()) {
                    if (usernameField.getText().equals(em.getUsername()) && passwordField.getText().equals(em.getPasswd())) {
                        found = true;
                        CurrentUser.employee = em;
                        System.out.println("Login efetuado com sucesso!");
                        LoadFXML.getInstance().loadResource("employeeMenu.fxml", "Menu Funcionário", event);
                    }
                }
            } else if(userTypeCB.getValue().equals("Admin")){

                for(Admin ad : repo.getAdmins().values()){
                    if(usernameField.getText().equals(ad.getUsername()) && passwordField.getText().equals(ad.getPasswd())){
                        found = true;
                        CurrentUser.admin = ad;
                        System.out.println("Login efetuado com sucesso!");
                        LoadFXML.getInstance().loadResource("adminMenu.fxml", "Menu Admin", event);
                    }
                }
            }
            if(!found){
                System.out.println("Username/Password inválidos. Tente novamente");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de login");
                alert.setHeaderText("Username ou password inválidos");
                alert.showAndWait();
            }
        } catch (Exception e){
            System.out.println("ERRO ao verificar dados de login, tente novamente!");
        }

    }

    @FXML
    void register(MouseEvent event) {
        LoadFXML.getInstance().loadResource("register.fxml", "Registar", event);
    }

}
