package progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import progII.trabalhopratico.projetofinal.BLL.ProductBLL;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Product;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Exceptions.ProductException;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceProviderAddProdController implements Initializable {

    @FXML
    private TableView<Location> locationsTable;
    @FXML
    private TableColumn<Location, String> serviceTypeColumn;
    @FXML
    private TableColumn<Location, String> addressColumn;
    @FXML
    private TableColumn<Location, String> nameColumn;
    @FXML
    private TableColumn<Location, Integer> nifColumn;
    @FXML
    private TableColumn<Location, Integer> phoneColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button removeButton;
    @FXML
    private Label locationLabel;
    @FXML
    private TextField nameField;
    @FXML
    private Text nameText;
    @FXML
    private TextField priceField;
    @FXML
    private Text priceText;
    @FXML
    private ListView<String> productsNameList;
    private final ListView<Product> productListView = new ListView<>();


    @FXML
    void addProduct() {
        setFieldsVisible(true);
    }

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("serviceProviderMenu.fxml", "Menu Prestador Serviço", event);
    }

    @FXML
    void cancelChange() {
        clear();
        setFieldsVisible(false);
    }

    @FXML
    void confirmChange(MouseEvent event) {
        if(!nameField.getText().trim().isEmpty() && !priceField.getText().trim().isEmpty()){
            Location l1 = locationsTable.getSelectionModel().getSelectedItem();
            Product p1 = new Product();
            setData(p1, l1);
            try {
                ProductBLL.addProduct(p1, l1);
            }catch (ProductException ex){
                System.out.println(ex.getMessage());
            }
            back(event);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos!");
            alert.showAndWait();
            clear();
        }
    }

    @FXML
    void removeProduct(MouseEvent event) {
        Location l1 = locationsTable.getSelectionModel().getSelectedItem();
        String prodName = productsNameList.getSelectionModel().getSelectedItem();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja remover o produto?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                Iterator<Product> iterator = l1.getProducts().keySet().iterator();
                while (iterator.hasNext()){
                    Product p = iterator.next();
                    if((p.getName() + " --> " + p.getPrice() + "€").equals(prodName)){
                        iterator.remove();
                        Repository.getInstance().getCompanies().get(l1.getName()).getProducts().remove(p);
                        Repository.getInstance().writeFile("src\\main\\resources\\Files\\repo.dat");
                    }
                }
            }

            back(event);

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clear();
        setFieldsVisible(false);

        Repository repo = Repository.getInstance();
        for(Location l : repo.getLocations().get(CurrentUser.serviceProvider)){
            ObservableList<Location> locations = locationsTable.getItems();
            fillTable(nameColumn, serviceTypeColumn, phoneColumn, nifColumn, addressColumn);
            locations.add(l);
            locationsTable.setItems(locations);
        }

        locationsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, location, t1) -> {
            Location l1 = locationsTable.getSelectionModel().getSelectedItem();
            locationLabel.setText(l1.getName());
            productListView.getItems().clear();
            ObservableList<String> productNames = FXCollections.observableArrayList();
            ObservableList<Product> products = productListView.getItems();
            if(l1.getProducts().isEmpty()){
                productsNameList.getItems().clear();
                productsNameList.getItems().add("Local não tem produtos!");
            }
            else {
                for (Product p : l1.getProducts().keySet()) {
                    productNames.add(p.getName() + " --> " + p.getPrice() + "€");
                    products.add(p);
                }
                productListView.setItems(products);
                productsNameList.setItems(productNames);
            }
        });
    }

    private void fillTable(TableColumn<Location, String> name, TableColumn<Location, String> serviceType, TableColumn<Location, Integer> phone, TableColumn<Location, Integer> nif, TableColumn<Location, String> address){
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serviceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        nif.setCellValueFactory(new PropertyValueFactory<>("NIF"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void clear(){
        nameField.clear();
        priceField.clear();
    }

    private void setFieldsVisible(Boolean b){
        nameField.setVisible(b);
        nameText.setVisible(b);
        priceField.setVisible(b);
        priceText.setVisible(b);
        cancelButton.setVisible(b);
        confirmButton.setVisible(b);
        addButton.setVisible(!b);
        removeButton.setVisible(!b);
    }

    private void setData(Product p, Location l){
        p.setID(l.getProducts().size() + 1);
        p.setLocation(l);
        p.setName(nameField.getText());
        p.setPrice(Double.parseDouble(priceField.getText()));
    }

}
