package progII.trabalhopratico.projetofinal.BLL;

import progII.trabalhopratico.projetofinal.Entities.Employee;
import progII.trabalhopratico.projetofinal.Exceptions.UserAlreadyExistsException;
import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Repository;

public class EmployeeBLL {
    public static void addEmployee(Employee employee, Location location) throws UserAlreadyExistsException {
        Repository repo = Repository.getInstance();
        if(repo.getEmployees().containsKey(employee.getUsername())){
            throw new UserAlreadyExistsException("Funcionário já existe!");
        }
        repo.getEmployees().put(employee.getUsername(), employee);
        repo.getCompanies().get(location.getName()).getEmployees().add(employee);
        Repository.getInstance().writeFile("src\\main\\resources\\Files\\repo.dat");
    }
}
