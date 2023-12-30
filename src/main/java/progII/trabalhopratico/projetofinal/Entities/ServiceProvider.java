package progII.trabalhopratico.projetofinal.Entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ServiceProvider extends User implements Serializable {

    private ArrayList<Location> localER;
    @Serial
    private static final long serialVersionUID = 1L;

    public ServiceProvider(){
        localER = new ArrayList<>();
    }

    public ArrayList<Location> getLocalER() {
        return localER;
    }

    public void setLocalER(ArrayList<Location> localER) {
        this.localER = localER;
    }


    public void listLocations(){
        for(Location l : localER){
            System.out.println("Nome: " + l.getName());
            System.out.println("Morada: " + l.getAddress());
            System.out.println("NIF: " + l.getNIF());
            System.out.println("Telefone: " + l.getPhoneNumber());
        }
    }





}



