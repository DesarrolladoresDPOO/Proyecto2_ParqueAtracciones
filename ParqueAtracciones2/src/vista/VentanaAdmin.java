package vista;

import javax.swing.*;
import java.awt.*;
import Interfaz.InterfazAdmin;

public class VentanaAdmin extends JFrame {
    private InterfazAdmin controlador;

    public VentanaAdmin(InterfazAdmin controlador) {
        this.controlador = controlador;

        setTitle("Menu Administrador");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JButton btnCrearAtraccion = new JButton("Crear Atraccion");
        JButton btnModificarAtraccion = new JButton("Modificar Atraccion");
        JButton btnEliminarAtraccion = new JButton("Eliminar Atraccion");
        JButton btnCrearTurno = new JButton("Crear Turno");
        JButton btnAsignarTurno = new JButton("Asignar Turno");
        JButton btnRegistrarEmpleado = new JButton("Registrar Empleado");
        JButton btnSalir = new JButton("Salir");

        btnCrearAtraccion.addActionListener(e -> mostrarFormularioCrearAtraccion());
        btnModificarAtraccion.addActionListener(e -> mostrarFormularioModificarAtraccion());
        btnEliminarAtraccion.addActionListener(e -> mostrarFormularioEliminarAtraccion());
        btnCrearTurno.addActionListener(e -> controlador.crearTurnoDesdeSwing());
        btnAsignarTurno.addActionListener(e -> controlador.asignarTurnoDesdeSwing());
        btnRegistrarEmpleado.addActionListener(e -> controlador.registrarEmpleadoDesdeSwing());
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnCrearAtraccion);
        panel.add(btnModificarAtraccion);
        panel.add(btnEliminarAtraccion);
        panel.add(btnCrearTurno);
        panel.add(btnAsignarTurno);
        panel.add(btnRegistrarEmpleado);
        panel.add(btnSalir);

        add(panel);
        setVisible(true);
    }

    private void mostrarFormularioCrearAtraccion() {
        String[] opciones = {"Mecanica", "Cultural"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Tipo de atraccion:", "Crear Atraccion",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (tipo == null) return;

        String nombre = JOptionPane.showInputDialog(this, "Nombre de la atraccion:");
        String cupoStr = JOptionPane.showInputDialog(this, "Cupo maximo:");
        String empleadosStr = JOptionPane.showInputDialog(this, "Cantidad de empleados:");
        String[] climaOpciones = {"true", "false"};
        String clima = (String) JOptionPane.showInputDialog(this, "Disponible con clima adverso:",
                "Clima", JOptionPane.QUESTION_MESSAGE, null, climaOpciones, climaOpciones[0]);
        String exclusividad = JOptionPane.showInputDialog(this, "Nivel de exclusividad:");

        if (tipo.equals("Mecanica")) {
            String minAltura = JOptionPane.showInputDialog(this, "Altura minima (cm):");
            String maxAltura = JOptionPane.showInputDialog(this, "Altura maxima (cm):");
            String minPeso = JOptionPane.showInputDialog(this, "Peso minimo (kg):");
            String maxPeso = JOptionPane.showInputDialog(this, "Peso maximo (kg):");
            String salud = JOptionPane.showInputDialog(this, "Restricciones de salud:");
            String riesgo = JOptionPane.showInputDialog(this, "Nivel de riesgo:");

            controlador.crearAtraccionDesdeSwing(tipo, nombre, cupoStr, empleadosStr, clima,
                    exclusividad, minAltura, maxAltura, minPeso, maxPeso, salud, riesgo);

        } else {
            String edad = JOptionPane.showInputDialog(this, "Edad minima requerida:");

            controlador.crearAtraccionDesdeSwing(tipo, nombre, cupoStr, empleadosStr, clima,
                    exclusividad, edad);
        }
    }

    private void mostrarFormularioModificarAtraccion() {
        String nombreModificar = JOptionPane.showInputDialog(this, "Nombre de la atraccion a modificar:");
        if (nombreModificar == null || nombreModificar.trim().isEmpty()) return;
        controlador.modificarAtraccionDesdeSwing(nombreModificar);
    }

    private void mostrarFormularioEliminarAtraccion() {
        String nombreEliminar = JOptionPane.showInputDialog(this, "Nombre de la atraccion a eliminar:");
        if (nombreEliminar == null || nombreEliminar.trim().isEmpty()) return;
        controlador.eliminarAtraccionDesdeSwing(nombreEliminar);
    }
}



