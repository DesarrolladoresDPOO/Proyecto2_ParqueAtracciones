package Interfaz;

public class ClienteMain {
    public static void main(String[] args) {
        InterfazCliente interfaz = new InterfazCliente();
        new vista.VentanaCliente(interfaz);
    }
}