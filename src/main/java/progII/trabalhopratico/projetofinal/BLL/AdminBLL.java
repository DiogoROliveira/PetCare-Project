package progII.trabalhopratico.projetofinal.BLL;

import progII.trabalhopratico.projetofinal.Entities.Admin;
import progII.trabalhopratico.projetofinal.Exceptions.UserAlreadyExistsException;
import progII.trabalhopratico.projetofinal.Entities.Repository;


public class AdminBLL {

    public static void addAdmin(Admin admin) throws UserAlreadyExistsException {
        Repository repo = Repository.getInstance();
        if(repo.getAdmins().containsKey(admin.getUsername())){
            throw new UserAlreadyExistsException("Admin já existe!");
        }
        repo.getAdmins().put(admin.getUsername(), admin);
        repo.writeFile("src\\main\\resources\\Files\\repo.dat");
    }
}
