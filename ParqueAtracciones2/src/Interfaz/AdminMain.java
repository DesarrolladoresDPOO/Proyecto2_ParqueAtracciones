package Interfaz;

public class AdminMain {
    public static void main(String[] args) {
        InterfazAdmin interfaz = new InterfazAdmin();
        new vista.VentanaAdmin(interfaz);
    }
}