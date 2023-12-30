package progII.trabalhopratico.projetofinal.Controllers.LoginAndRegister;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.BLL.AdminBLL;
import progII.trabalhopratico.projetofinal.Entities.Admin;
import progII.trabalhopratico.projetofinal.Exceptions.UserAlreadyExistsException;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

public class RegisterAdminController {

    private boolean passwordValid = false;

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
    private Text passwordError;

    @FXML
    private TextField telefoneField;

    @FXML
    private TextField usernameField;

    @FXML
    void back(ActionEvent event) {
        LoadFXML.getInstance().loadResource("adminMenu.fxml", "Menu Admin", event);
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

        Admin a1 = new Admin();
        setData(a1);
        try {
            AdminBLL.addAdmin(a1);
            registerAlert();
            back(event);
        }catch (UserAlreadyExistsException ex){
            System.out.println(ex.getMessage());
            alert("Username ja está em uso, por favor tente com outro!");
            clear();
        }
    }

    private void setData(Admin u){
        u.setNome(nomeField.getText());
        u.setUsername(usernameField.getText());
        u.setPasswd(password1Field.getText());
        u.setMorada(moradaField.getText());
        u.setTelefone(telefoneField.getText());
        u.setNumCC(Integer.parseInt(numCCField.getText()));
        u.setNIF(Integer.parseInt(nifField.getText()));
    }

    @FXML
    void verifyPassword() {
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

    private void clear(){
        nomeField.clear();
        moradaField.clear();
        telefoneField.clear();
        numCCField.clear();
        nifField.clear();
        password1Field.clear();
        password2Field.clear();
        usernameField.clear();
    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro de registo!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void registerAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Registo efetuado");
        alert.setHeaderText("Admin" + " registado com sucesso!");
        alert.showAndWait();
    }
}
