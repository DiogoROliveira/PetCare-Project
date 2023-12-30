package progII.trabalhopratico.projetofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import progII.trabalhopratico.projetofinal.BLL.AdminBLL;
import progII.trabalhopratico.projetofinal.Entities.*;
import progII.trabalhopratico.projetofinal.Exceptions.UserAlreadyExistsException;
import progII.trabalhopratico.projetofinal.Entities.AppointmentState;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Repository repo;
        try {
            Repository.readFile("src\\main\\resources\\Files\\repo.dat");
        }catch (ClassNotFoundException | IOException e){
            System.out.println(e.getMessage());
        }
        repo = Repository.getInstance();
        if(repo.getAdmins().isEmpty()){
            Admin a1 = new Admin();
            a1.setUsername("admin");
            a1.setPasswd("admin");
            a1.setMorada("morada1");
            a1.setNome("Administrador");
            a1.setNIF(12345);
            a1.setNumCC(12345);
            a1.setTelefone("12345");
            try {
                AdminBLL.addAdmin(a1);
            }catch (UserAlreadyExistsException e){
                System.out.println(e.getMessage());
            }
        }


        System.out.println("--------------------------------------------");
        System.out.println("-----------| Dados dos Clientes |-----------");

        int clientNum = 1;
        for(Client c : repo.getClients().values()){
            System.out.println("Cliente nº: " + clientNum++);
            listDataUser(c.getNome(), c.getNumCC(), c.getNIF(), c.getUsername(), c.getPasswd(), c.getMorada(), c.getTelefone());
            System.out.println("------------------------------------------");
        }

        System.out.println("------------- FIM DOS CLIENTES -------------");


        System.out.println("----------------------------------------------------------");
        System.out.println("-----------| Dados dos Prestadores de Serviço |-----------");

        int spNum = 1;
        for(ServiceProvider c : repo.getServiceProviders().values()){
            System.out.println("Prestador de Serviço nº: " + spNum++);
            listDataUser(c.getNome(), c.getNumCC(), c.getNIF(), c.getUsername(), c.getPasswd(), c.getMorada(), c.getTelefone());
            System.out.println("------------------------------------------");
        }

        System.out.println("------------- FIM DOS PRESTADORES DE SERVIÇO -------------");


        System.out.println("------------------------------------------------");
        System.out.println("-----------| Dados dos Funcionários |-----------");

        int employeeNum = 1;
        for(Employee c : repo.getEmployees().values()){
            System.out.println("Funcionário nº: " + employeeNum++);
            listDataUser(c.getNome(), c.getNumCC(), c.getNIF(), c.getUsername(), c.getPasswd(), c.getMorada(), c.getTelefone());
            System.out.println("Tipo: " + c.getType());
            System.out.println("Local: " + c.getLocation().getName());
            if(c.getType().equals("Veterinário")){
                System.out.println("Nº de Carteira: " + c.getNumCarteira());
            }
            System.out.println("------------------------------------------");
        }

        System.out.println("------------- FIM DOS FUNCIONÁRIOS -------------");


        System.out.println("------------------------------------------");
        System.out.println("-----------| Dados dos Admins |-----------");

        int adminNum = 1;
        for(Admin c : repo.getAdmins().values()){
            System.out.println("Admin nº: " + adminNum++);
            listDataUser(c.getNome(), c.getNumCC(), c.getNIF(), c.getUsername(), c.getPasswd(), c.getMorada(), c.getTelefone());
        }

        System.out.println("------------- FIM DOS ADMINS -------------");


        System.out.println("------------------------------------------");
        System.out.println("-----------| Dados dos Locais |-----------");

        int locationNum = 1;
        for(Location l : repo.getCompanies().values()){
            System.out.println("Local nº: " + locationNum++);
            System.out.println("Nome: " + l.getName());
            System.out.println("Morada: " + l.getAddress());
            System.out.println("NIF: " + l.getNIF());
            System.out.println("Tipo de Serviço: " + l.getServiceType());
            System.out.println("Nº Telefone: " + l.getPhoneNumber());
            System.out.println("Prestador de Serviço: " + l.getOwner().getNome());
            System.out.println("--------------------------------------------");

        }

        System.out.println("------------- FIM DOS LOCAIS -------------");

        System.out.println("--------------------------------------------");
        System.out.println("-----------| Dados dos Serviços |-----------");

        for (Service s : repo.getServices().values()){
            System.out.println("ID: " + s.getID());
            System.out.println("Nome: " + s.getName());
            System.out.println("Tipo: " + s.getType());
            System.out.println("Local: " + s.getLocation().getName());
            System.out.println("Preço: " + s.getPrice());
            System.out.println("--------------------------------------------");

        }

        System.out.println("------------- FIM DOS SERVIÇOS -------------");

        System.out.println("--------------------------------------------");
        System.out.println("-----------| Dados dos Produtos |-----------");

        int productNum = 1;
        for(Product p : repo.getProducts().values()){
            System.out.println("Produto nº: " + productNum++);
            System.out.println("ID: " + p.getID());
            System.out.println("Nome: " + p.getName());
            System.out.println("Preço: " + p.getPrice());
            System.out.println("Local: " + p.getLocation().getName());
            System.out.println("--------------------------------------------");

        }
        System.out.println("------------- FIM DOS PRODUTOS -------------");

        System.out.println("--------------------------------------------");
        System.out.println("-----------| Dados das Consultas |-----------");

        for(Appointment a : repo.getAppointments().values()){
            System.out.println("ID: " + a.getIdAppointment());
            System.out.println("Cliente: " + a.getClient().getNome());
            System.out.println("Funcionário: " + a.getEmployee());
            System.out.println("Data: " + a.getAppointmentDate());
            System.out.println("Funcionário: " + a.getEmployee());
            System.out.println("Local: " + a.getLocationName());
            System.out.println("Estado: " + a.getState());
            if(a.getState().equals(AppointmentState.CANCELADA)){
                System.out.println("Motivo: " + a.getReason());
            }
            System.out.println("Total: " + a.getAppointmentTotal());
            System.out.println("Serviço: " + a.getService());
            System.out.println("Tipo: " + a.getType());
            System.out.println("--------------------------------------------");

        }
        System.out.println("------------- FIM DAS CONSULTAS -------------");


        launch();
    }

    private static void listDataUser(String nome, int numCC, int nif, String username, String passwd, String morada, String telefone) {
        System.out.println("Nome: " + nome);
        System.out.println("CC: " + numCC);
        System.out.println("NIF: " + nif);
        System.out.println("Username: " + username);
        System.out.println("Password: " + passwd);
        System.out.println("Morada: " + morada);
        System.out.println("Nº Telefone: " + telefone);

    }
}