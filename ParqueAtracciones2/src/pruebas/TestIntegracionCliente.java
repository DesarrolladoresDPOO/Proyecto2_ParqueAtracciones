package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tiquetes.Cliente;
import tiquetes.TiqueteBasico;
import java.util.ArrayList;

public class TestIntegracionCliente {

    @Test
    public void flujoCliente() {
        // Crear cliente con constructor válido
        ArrayList<tiquetes.Tiquete> listaTiquetes = new ArrayList<>();
        Cliente cliente = new Cliente("Pedro1", "123", "Pedro Mantilla", listaTiquetes);

        // Comprar tiquete básico
        TiqueteBasico tiquete = new TiqueteBasico(false);
        cliente.comprarTiquete(tiquete);

        // Validar que fue añadido
        assertEquals(1, cliente.getTiquetes().size());

        // Usar el tiquete
        boolean fueUsado = cliente.usarTiquete(tiquete);
        assertTrue(fueUsado);
        assertTrue(tiquete.isUsado());
    }
}