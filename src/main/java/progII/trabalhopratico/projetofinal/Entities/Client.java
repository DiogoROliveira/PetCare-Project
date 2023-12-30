package progII.trabalhopratico.projetofinal.Entities;

import progII.trabalhopratico.projetofinal.Exceptions.ClientException;
import progII.trabalhopratico.projetofinal.OtherClasses.Validator;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client extends User implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Appointment> appointments = new ArrayList<>();

    public Client(){}

    public Client(String username, String passwd, String nome, int numCC, int NIF, String telefone, String morada, String localidade)
            throws ClientException {

        super(username, passwd, nome, numCC, NIF, telefone, morada, localidade);

        if (!(Validator.validaString(username) &&
                Validator.validaString(passwd) &&
                Validator.validaInt(numCC) &&
                Validator.validaInt(NIF) &&
                Validator.validaString(nome) &&
                Validator.validaString(morada) &&
                Validator.validaString(localidade) &&
                Validator.validaString(telefone))) {
            throw new ClientException("Todos os campos são obrigatórios");
        }
    }

    public List<Appointment> getAppointments(){return appointments;}


}

