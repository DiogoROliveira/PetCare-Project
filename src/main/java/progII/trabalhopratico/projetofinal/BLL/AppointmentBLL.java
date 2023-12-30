package progII.trabalhopratico.projetofinal.BLL;

import progII.trabalhopratico.projetofinal.Entities.Client;
import progII.trabalhopratico.projetofinal.Entities.Appointment;
import progII.trabalhopratico.projetofinal.Exceptions.AppointmentException;
import progII.trabalhopratico.projetofinal.Entities.Repository;

public class AppointmentBLL {
    public static void registerAppointment(Appointment appointment, Client client) throws AppointmentException {
        Repository repo = Repository.getInstance();
        if(repo.getAppointments().containsKey(appointment.getIdAppointment()))
            throw new AppointmentException("Código de consulta já existe");

        repo.getAppointments().put(appointment.getIdAppointment(), appointment);
        repo.getClients().get(client.getUsername()).getAppointments().add(appointment);
        repo.writeFile("src\\main\\resources\\Files\\repo.dat");

    }
}



