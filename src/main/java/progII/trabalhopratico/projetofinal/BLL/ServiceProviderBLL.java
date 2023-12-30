package progII.trabalhopratico.projetofinal.BLL;


import progII.trabalhopratico.projetofinal.Entities.ServiceProvider;
import progII.trabalhopratico.projetofinal.Exceptions.UserAlreadyExistsException;
import progII.trabalhopratico.projetofinal.Entities.Repository;

public class ServiceProviderBLL {

    public static void addServiceProvider(ServiceProvider serviceProvider) throws UserAlreadyExistsException {
        Repository repo = Repository.getInstance();
        if(repo.getServiceProviders().containsKey(serviceProvider.getUsername()))
            throw new UserAlreadyExistsException("Prestador de Serviços ja existe!");

        repo.getServiceProviders().put(serviceProvider.getUsername(), serviceProvider);
        repo.getLocations().put(serviceProvider, serviceProvider.getLocalER());
        System.out.println("Prestador de serviço criado com sucesso!");
        repo.writeFile("src\\main\\resources\\Files\\repo.dat");

    }
}
