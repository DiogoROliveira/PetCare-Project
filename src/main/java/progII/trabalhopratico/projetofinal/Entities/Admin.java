package progII.trabalhopratico.projetofinal.Entities;

import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin(){}

    public Admin(String username, String passwd, String nome, int numCC, int NIF, String telefone, String morada, String localidade){
        super(username, passwd, nome, numCC, NIF, telefone, morada, localidade);
    }
}
