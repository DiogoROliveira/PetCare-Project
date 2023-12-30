package progII.trabalhopratico.projetofinal.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location implements Serializable {
    private String name;
    private String address;
    private int phoneNumber;
    private int NIF;
    private String serviceType;
    private ServiceProvider owner;
    private List<Employee> employees;
    private Map<Integer, Service> services;
    private Map<Product, Double> products;

    public Location() {
        employees = new ArrayList<>();
        services = new HashMap<>();
        products = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNIF() {
        return NIF;
    }

    public void setNIF(int NIF) {
        this.NIF = NIF;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceProvider getOwner() {
        return owner;
    }

    public void setOwner(ServiceProvider owner) {
        this.owner = owner;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Map<Integer, Service> getServices() {
        return services;
    }

    public void setServices(Map<Integer, Service> services) {
        this.services = services;
    }

    public Map<Product, Double> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Double> products) {
        this.products = products;
    }
}
