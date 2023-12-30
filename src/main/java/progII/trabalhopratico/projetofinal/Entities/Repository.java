package progII.trabalhopratico.projetofinal.Entities;


import java.io.*;
import java.util.*;


public class Repository implements Serializable{

    private final Map<String, User> users;
    private final Map<String, Client> clients;
    private final Map<String, Location> companies;
    private final Map<String, ServiceProvider> serviceProviders;
    private final Map<ServiceProvider, ArrayList<Location>> locations = new HashMap<>();
    private final Map<String, Employee> employees;
    private final Map<String, Admin> admins;
    private final Map<Integer, Appointment> appointments;
    private final Map<Integer, Service> services;
    private final Map<Integer, Product> products = new HashMap<>();
    private static Repository repo = null;

    private Repository(){
        this.users = new HashMap<>();
        this.clients = new HashMap<>();
        this.companies =new HashMap<>();
        this.serviceProviders = new HashMap<>();
        this.employees = new HashMap<>();
        this.admins = new HashMap<>();
        this.appointments = new HashMap<>();
        this.services = new HashMap<>();
    }

    public static Repository getInstance(){
        if (repo == null){
            repo = new Repository();

        }
        return repo;
    }

    public Map<String, User> getUsers(){return users;}

    public Map<String, Client> getClients(){return clients;}

    public Map<String, Location> getCompanies() {
        return companies;
    }

    public Map<String, ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public Map<ServiceProvider, ArrayList<Location>> getLocations() {return locations;}

    public Map<Integer, Appointment> getAppointments() {
        return appointments;
    }

    public Map<String, Employee> getEmployees() {
        return employees;
    }

    public Map<String, Admin> getAdmins() {
        return admins;
    }

    public Map<Integer, Service> getServices() {
        return services;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void writeFile(String filename){
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Dados serializados no ficheiro " + filename + "\n");
        } catch(IOException ex){
            System.out.println("ErrorSerialize: " + ex.getMessage());
        }
    }

    public static Repository readFile(String filename) throws ClassNotFoundException, IOException {

        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        repo = (Repository) in.readObject();
        in.close();
        fileIn.close();
        return repo;
    }
}

