package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tiquetes.Cliente;
import tiquetes.TiqueteBasico;
import tiquetes.Tiquete;
import java.util.ArrayList;
import java.util.List;

public class TestCliente {

    @Test
    public void testConstructorCliente() {
        List<Tiquete> tiquetesIniciales = new ArrayList<>();
        Cliente cliente = new Cliente("user123", "pass123", "Juan Perez", tiquetesIniciales);

        assertEquals("user123", cliente.getLogin());
        assertEquals("pass123", cliente.getPassword());
        assertEquals("Juan Perez", cliente.getNombre());
        assertEquals(0, cliente.getTiquetes().size());
    }

    @Test
    public void testComprarTiquete() {
        List<Tiquete> tiquetesIniciales = new ArrayList<>();
        Cliente cliente = new Cliente("user123", "pass123", "Juan Perez", tiquetesIniciales);

        Tiquete nuevoTiquete = new TiqueteBasico(false); 
        cliente.comprarTiquete(nuevoTiquete);

        assertEquals(1, cliente.getTiquetes().size());
        assertTrue(cliente.getTiquetes().contains(nuevoTiquete));
    }

    @Test
    public void testUsarTiqueteExitoso() {
        List<Tiquete> tiquetesIniciales = new ArrayList<>();
        Cliente cliente = new Cliente("user123", "pass123", "Juan Perez", tiquetesIniciales);

        Tiquete nuevoTiquete = new TiqueteBasico(false);
        cliente.comprarTiquete(nuevoTiquete);

        boolean pudoUsar = cliente.usarTiquete(nuevoTiquete);

        assertTrue(pudoUsar);
        assertTrue(nuevoTiquete.isUsado());
    }

    @Test
    public void testUsarTiqueteYaUsado() {
        List<Tiquete> tiquetesIniciales = new ArrayList<>();
        Cliente cliente = new Cliente("user123", "pass123", "Juan Perez", tiquetesIniciales);

        Tiquete nuevoTiquete = new TiqueteBasico(false);
        cliente.comprarTiquete(nuevoTiquete);

        // Primera vez usar
        cliente.usarTiquete(nuevoTiquete);

        // Segunda vez usar (ya debería estar usado)
        boolean pudoUsarOtraVez = cliente.usarTiquete(nuevoTiquete);

        assertFalse(pudoUsarOtraVez);
    }
}