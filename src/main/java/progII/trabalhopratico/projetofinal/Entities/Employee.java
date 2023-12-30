package progII.trabalhopratico.projetofinal.Entities;

import java.io.Serializable;

public class Employee extends User implements Serializable{

    private String type;
    private Location location;
    private String numCarteira;

    public Employee(){}

    public String getType(){return type;}

    public void setType(String type){this.type = type;}

    public Location getLocation(){return location;}

    public void setLocation(Location location){
        this.location = location;
    }

    public String getNumCarteira(){return numCarteira;}

    public void setNumCarteira(String numCarteira){this.numCarteira = numCarteira;}

}