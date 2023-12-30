package progII.trabalhopratico.projetofinal.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.BLL.AppointmentBLL;
import progII.trabalhopratico.projetofinal.Entities.*;
import progII.trabalhopratico.projetofinal.Exceptions.AppointmentException;
import progII.trabalhopratico.projetofinal.Entities.AppointmentState;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;
import progII.trabalhopratico.projetofinal.Entities.Repository;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientMakeAppointmentController implements Initializable {
    @FXML
    public DatePicker datePicker;
    @FXML
    public ChoiceBox<String> serviceCB;
    @FXML
    public ChoiceBox<String> employeeCB;
    @FXML
    public Label pickedLocation;
    @FXML
    public Label specialtyLabel;
    @FXML
    public Label finalPriceLabel;
    @FXML
    public ChoiceBox<String> productsComboBox;
    @FXML
    public CheckBox addProductsCheckBox;
    @FXML
    public Button confirmButton;
    public static Service currentService;
    public static Product currentProduct = new Product();
    final double[] appTotal = {0};


    public void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("listLocations.fxml", "Locais de entrega/recolha", event);
    }

    public void createAppointment(ActionEvent event){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja pedir a consulta?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Appointment appointment = getAppointment(pickedLocation, serviceCB, employeeCB, datePicker, finalPriceLabel, specialtyLabel);
                AppointmentBLL.registerAppointment(appointment, CurrentUser.client);

                LoadFXML.getInstance().loadResource("clientMenu.fxml", "Menu Cliente", event);

            }
        }catch (AppointmentException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static Appointment getAppointment(Label pickedLocation, ChoiceBox<String> serviceCB, ChoiceBox<String> employeeCB, DatePicker datePicker, Label finalPriceLabel, Label specialtyLabel) {
        Appointment appointment = new Appointment();

        appointment.setClient(CurrentUser.client);
        appointment.setIdAppointment(Repository.getInstance().getAppointments().size() + 1);
        appointment.setLocationName(pickedLocation.getText());
        appointment.setService(serviceCB.getValue());
        appointment.setEmployee(employeeCB.getValue());
        appointment.setAppointmentDate(datePicker.getValue().toString());
        appointment.setAppointmentTotal(Double.parseDouble(finalPriceLabel.getText()));
        appointment.setState(AppointmentState.PENDENTE);
        appointment.setType(specialtyLabel.getText());
        return appointment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();
        Client c1 = new Client();


        c1.setNome(CurrentUser.client.getNome());
        pickedLocation.setText(ClientListLocationsController.currentLocation);
        finalPriceLabel.setVisible(true);


        for (Employee e : repo.getEmployees().values()){
            if(pickedLocation.getText().equals(e.getLocation().getName()))
                employeeCB.getItems().add(e.getNome());
        }

        for (Service s : repo.getServices().values()) {
            if (pickedLocation.getText().equalsIgnoreCase(s.getLocation().getName())) {
                serviceCB.getItems().addAll(s.getName());
                specialtyLabel.setText(s.getType());
                specialtyLabel.setVisible(true);
            }
            serviceCB.getSelectionModel().selectedItemProperty().addListener((observableValue, s12, t1) -> {

                for(Service s1 : repo.getServices().values()) {
                    if(s1.getLocation().getName().equalsIgnoreCase(pickedLocation.getText()) && s1.getName().equalsIgnoreCase(serviceCB.getSelectionModel().getSelectedItem())) {
                        currentService = s1;
                        appTotal[0] += currentService.getPrice();
                        finalPriceLabel.setText(String.valueOf(appTotal[0]));
                    }
                }
            });
        }


        for(Product p : repo.getProducts().values()) {
            if (p.getLocation().getName().equalsIgnoreCase(ClientListLocationsController.currentLocation)) {
                productsComboBox.getItems().addAll(p.getName());
                productsComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                    for (Product p1 : repo.getProducts().values()) {
                        if (p1.getLocation().getName().equalsIgnoreCase(pickedLocation.getText()) && p1.getName().equalsIgnoreCase(productsComboBox.getSelectionModel().getSelectedItem())) {
                            currentProduct = p1;
                            appTotal[0] += p1.getPrice();
                            finalPriceLabel.setText(String.valueOf(appTotal[0]));
                        }

                    }
                });

            }
        }
    }

    public void showProductsCB(){
        productsComboBox.setVisible(true);
        if(!addProductsCheckBox.isSelected() && productsComboBox.getValue() != null) {
            appTotal[0] = appTotal[0] - currentProduct.getPrice();
            finalPriceLabel.setText(String.valueOf(appTotal[0]));
            productsComboBox.setVisible(false);
            productsComboBox.setValue(null);

        }
    }

}
