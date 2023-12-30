module progII.trabalhopratico.projetofinal {
    requires javafx.controls;
    requires javafx.fxml;

    opens progII.trabalhopratico.projetofinal to javafx.fxml;
    exports progII.trabalhopratico.projetofinal;
    exports progII.trabalhopratico.projetofinal.Exceptions;
    exports progII.trabalhopratico.projetofinal.Entities;
    opens progII.trabalhopratico.projetofinal.Entities to javafx.fxml;
    exports progII.trabalhopratico.projetofinal.BLL;
    opens progII.trabalhopratico.projetofinal.BLL to javafx.fxml;
    exports progII.trabalhopratico.projetofinal.OtherClasses;
    opens progII.trabalhopratico.projetofinal.OtherClasses to javafx.fxml;
    exports progII.trabalhopratico.projetofinal.Controllers.LoginAndRegister;
    opens progII.trabalhopratico.projetofinal.Controllers.LoginAndRegister to javafx.fxml;
    exports progII.trabalhopratico.projetofinal.Controllers.Client;
    opens progII.trabalhopratico.projetofinal.Controllers.Client to javafx.fxml;
    exports progII.trabalhopratico.projetofinal.Controllers.ServiceProvider;
    opens progII.trabalhopratico.projetofinal.Controllers.ServiceProvider to javafx.fxml;
    exports progII.trabalhopratico.projetofinal.Controllers.Admin;
    opens progII.trabalhopratico.projetofinal.Controllers.Admin to javafx.fxml;
    exports progII.trabalhopratico.projetofinal.Controllers.Employee;
    opens progII.trabalhopratico.projetofinal.Controllers.Employee to javafx.fxml;
}