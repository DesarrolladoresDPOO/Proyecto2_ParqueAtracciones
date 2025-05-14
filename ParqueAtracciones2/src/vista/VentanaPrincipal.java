package vista;

import javax.swing.*;
import java.awt.*;
import Interfaz.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("Parque de Atracciones");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("🎡 Parque de Atracciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titulo);

        JButton btnCliente = new JButton("Ingresar como Cliente");
        JButton btnEmpleado = new JButton("Ingresar como Empleado");
        JButton btnAdmin = new JButton("Ingresar como Administrador");

        btnCliente.addActionListener(e -> {
            InterfazCliente controlador = new InterfazCliente();
            new VentanaCliente(controlador);
            dispose(); // Cierra esta ventana
        });
        btnEmpleado.addActionListener(e -> {
            InterfazEmpleado controlador = new InterfazEmpleado();
            new VentanaAsistenciaEmpleado(controlador); 
            dispose();
        });
        btnAdmin.addActionListener(e -> {
            InterfazAdmin controlador = new InterfazAdmin();
            controlador.autenticarYIniciar(); // Si luego haces GUI admin, cámbialo
            // Aquí se queda en consola porque admin aún no tiene GUI
        });
        panel.add(btnCliente);
        panel.add(btnEmpleado);
        panel.add(btnAdmin);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}