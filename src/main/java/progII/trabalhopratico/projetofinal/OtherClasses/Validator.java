package progII.trabalhopratico.projetofinal.OtherClasses;

public class Validator {
    public static boolean validaString(String s) {
        return (s != null && !s.trim().isEmpty());
    }

    public static boolean validaInt(int x) {
        return x > 0;
    }
}