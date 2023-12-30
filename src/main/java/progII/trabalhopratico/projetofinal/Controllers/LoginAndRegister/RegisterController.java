package progII.trabalhopratico.projetofinal.Controllers.LoginAndRegister;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.BLL.ClientBLL;
import progII.trabalhopratico.projetofinal.BLL.ServiceProviderBLL;
import progII.trabalhopratico.projetofinal.Entities.Client;
import progII.trabalhopratico.projetofinal.Entities.ServiceProvider;
import progII.trabalhopratico.projetofinal.Entities.User;
import progII.trabalhopratico.projetofinal.Exceptions.UserAlreadyExistsException;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private boolean passwordValid = false;
    @FXML
    private ChoiceBox<String> accountTypeCF;

    @FXML
    private TextField moradaField;

    @FXML
    private TextField nifField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField numCCField;

    @FXML
    private PasswordField password1Field;

    @FXML
    private PasswordField password2Field;

    @FXML
    private TextField telefoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text passwordError;


    public void initialize(URL url, ResourceBundle rb) {
        accountTypeCF.getItems().addAll("Cliente", "Prestador de Serviço");
    }

    @FXML
    void back(ActionEvent event) {
        LoadFXML.getInstance().loadResource("login.fxml", "Login", event);
    }

    @FXML
    void register(ActionEvent event) {

        if(!passwordValid){
            alert("Password inválida!");
            clear();
            return;
        }

        if(!verifyFields()){
            alert("Preencha todos os campos");
            clear();
            return;
        }

        if(accountTypeCF.getValue().equals("Cliente")){
            Client client = new Client();
            setData(client);
            try {
                ClientBLL.addClient(client);
                registerAlert("Cliente");
                back(event);
            }catch (UserAlreadyExistsException e){
                System.out.println(e.getMessage());
                alert("Username ja está em uso, por favor tente com outro!");
                clear();
            }
        } else if (accountTypeCF.getValue().equals("Prestador de Serviço")) {
            ServiceProvider serviceProvider = new ServiceProvider();
            setData(serviceProvider);
            try {
                ServiceProviderBLL.addServiceProvider(serviceProvider);
                registerAlert("Prestador de Serviço");
                back(event);
            }catch (UserAlreadyExistsException e){
                System.out.println(e.getMessage());
                alert("Username ja está em uso, por favor tente com outro!");
                clear();
            }
        }
    }


    private void setData(User u){
        u.setNome(nomeField.getText());
        u.setUsername(usernameField.getText());
        u.setPasswd(password1Field.getText());
        u.setMorada(moradaField.getText());
        u.setTelefone(telefoneField.getText());
        u.setNumCC(Integer.parseInt(numCCField.getText()));
        u.setNIF(Integer.parseInt(nifField.getText()));
    }


    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro de registo!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void registerAlert(String accountType){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Registo efetuado");
        alert.setHeaderText(accountType + " registado com sucesso!");
        alert.showAndWait();
    }

    private void clear(){
        nomeField.clear();
        moradaField.clear();
        telefoneField.clear();
        numCCField.clear();
        nifField.clear();
        password1Field.clear();
        password2Field.clear();
        usernameField.clear();
        accountTypeCF.setValue(null);
    }

    @FXML
    private void verifyPassword() {
        String password = password1Field.getText();
        if(!password.equals(password2Field.getText())){
            passwordError.setVisible(true);
            passwordValid = false;
        }
        else {
            passwordValid = true;
            passwordError.setVisible(false);
        }
    }

    private boolean verifyFields(){
        return !nomeField.getText().trim().isEmpty() &&
                !moradaField.getText().trim().isEmpty() &&
                !telefoneField.getText().trim().isEmpty() &&
                !numCCField.getText().trim().isEmpty() &&
                !nifField.getText().trim().isEmpty() &&
                !password1Field.getText().trim().isEmpty() &&
                !password2Field.getText().trim().isEmpty() &&
                !usernameField.getText().trim().isEmpty();
    }

}
