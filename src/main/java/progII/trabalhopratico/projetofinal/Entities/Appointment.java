package progII.trabalhopratico.projetofinal.Entities;

import java.io.Serializable;

public class Appointment implements Serializable {
    private int idAppointment;
    private Client client;
    private String appointmentDate;
    private String employee;
    private String locationName;
    private double appointmentTotal;
    private AppointmentState state;
    private String service;
    private String type;
    private String reason;


    public Appointment(){}

    public Appointment(int idAppointment, Client client, String appointmentDate, String employee, String locationName, double appointmentTotal, AppointmentState state, String service, String type) {
        this.idAppointment = idAppointment;
        this.client = client;
        this.appointmentDate = appointmentDate;
        this.employee = employee;
        this.locationName = locationName;
        this.appointmentTotal = appointmentTotal;
        this.state = state;
        this.service = service;
        this.type = type;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getAppointmentTotal() {
        return appointmentTotal;
    }

    public void setAppointmentTotal(double appointmentTotal) {
        this.appointmentTotal = appointmentTotal;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String  getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public AppointmentState getState() {
        return state;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
