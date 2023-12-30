package progII.trabalhopratico.projetofinal.BLL;

import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Exceptions.LocationException;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.Entities.ServiceProvider;

import java.util.*;

public class LocationBLL {
    public static void createLocation(Location location, ServiceProvider serviceProvider) throws LocationException {
        Repository repo = Repository.getInstance();
        location.setOwner(serviceProvider);
        if(repo.getCompanies().containsKey(location.getName())){
            throw new LocationException("Localidade já existe!");
        }
        if(!repo.getLocations().containsKey(serviceProvider)){
            repo.getLocations().put(serviceProvider, new ArrayList<>());
        }

        repo.getCompanies().put(location.getName(), location);
        repo.getLocations().get(serviceProvider).add(location);
        repo.writeFile("src\\main\\resources\\Files\\repo.dat");

    }
}

