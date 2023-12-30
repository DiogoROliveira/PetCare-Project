package progII.trabalhopratico.projetofinal.BLL;

import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Service;
import progII.trabalhopratico.projetofinal.Exceptions.ServiceException;
import progII.trabalhopratico.projetofinal.Entities.Repository;

public class ServiceBLL {
    public static void createService(Service service, Location location) throws ServiceException {
        Repository repo = Repository.getInstance();
        service.setLocation(location);
        if(repo.getServices().containsKey(service.getID())){
            throw new ServiceException("Serviço já existe");
        }

        repo.getServices().put(service.getID(), service);
        repo.getCompanies().get(location.getName()).getServices().put(service.getID(), service);
        repo.writeFile("src\\main\\resources\\Files\\repo.dat");
    }
}
