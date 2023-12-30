package progII.trabalhopratico.projetofinal.Entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    protected Map<String, User> users;
    private String passwd;
    private String username;
    private String nome;
    private int numCC;
    private int NIF;
    private String telefone;
    private String morada;
    private String localidade;


    protected User(){
        users = new HashMap<>();
    }

    public User(String username, String passwd, String nome, int numCC, int NIF, String telefone, String morada, String localidade) {
        this.username = username;
        this.passwd = passwd;
        this.nome = nome;
        this.numCC = numCC;
        this.NIF = NIF;
        this.telefone = telefone;
        this.morada = morada;
        this.localidade = localidade;
    }

    public Map<String, User> getUsers(){return users;}
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumCC() {
        return numCC;
    }

    public void setNumCC(int numCC) {
        this.numCC = numCC;
    }

    public int getNIF() {
        return NIF;
    }

    public void setNIF(int NIF) {
        this.NIF = NIF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @Override
    public String toString() {
        return " " +
                "passwd='" + passwd + '\'' +
                ", username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", numCC=" + numCC +
                ", NIF=" + NIF +
                ", telefone=" + telefone +
                ", morada='" + morada + '\''
                ;
    }
}

