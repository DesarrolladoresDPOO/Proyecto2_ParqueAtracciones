package Interfaz;

import java.util.ArrayList;
import java.util.List;
import tiquetes.Cliente;

public class ClienteMain {
    public static void main(String[] args) {
        // Crear lista de clientes vacía 
        List<Cliente> listaClientes = new ArrayList<>();

        InterfazCliente interfaz = new InterfazCliente(listaClientes);
        interfaz.iniciar();
    }
}
