package vista;
import java.awt.GridLayout;

import javax.swing.*;

import Interfaz.InterfazEmpleado;


public class VentanaAsistenciaEmpleado extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnRegistrar, btnHistorial;
    private InterfazEmpleado controlador;

    public VentanaAsistenciaEmpleado(InterfazEmpleado controlador) {
        this.controlador = controlador;

        setTitle("Asistencia Cliente - Empleado");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(new JLabel("Usuario del Cliente:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnRegistrar = new JButton("Registrar Asistencia");
        btnHistorial = new JButton("Ver Historial");

        panel.add(btnRegistrar);
        panel.add(btnHistorial);

        add(panel);

        btnRegistrar.addActionListener(e -> {
            String u = txtUsuario.getText();
            String p = new String(txtPassword.getPassword());
            boolean exito = controlador.registrarAsistenciaGUI(u, p);
            mostrar(exito ? "Asistencia registrada." : "Ya fue registrado o error.");
        });

        btnHistorial.addActionListener(e -> {
            String u = txtUsuario.getText();
            String p = new String(txtPassword.getPassword());
            String h = controlador.historialAsistenciaGUI(u, p);
            mostrar(h);
        });

        setVisible(true);
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}