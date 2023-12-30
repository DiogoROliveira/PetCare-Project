package progII.trabalhopratico.projetofinal.BLL;

import progII.trabalhopratico.projetofinal.Entities.Location;
import progII.trabalhopratico.projetofinal.Entities.Product;
import progII.trabalhopratico.projetofinal.Exceptions.ProductException;
import progII.trabalhopratico.projetofinal.Entities.Repository;

public class ProductBLL {

    public static void addProduct(Product p, Location l) throws ProductException{
        Repository repo = Repository.getInstance();
        if(repo.getProducts().containsKey(p.getID())){
            throw new ProductException("Produto já existe!");
        }
        repo.getProducts().put(p.getID(), p);
        repo.getCompanies().get(l.getName()).getProducts().put(p, p.getPrice());
        repo.writeFile("src\\main\\resources\\Files\\repo.dat");
    }

}
