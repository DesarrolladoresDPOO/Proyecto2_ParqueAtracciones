package vista;

import javax.swing.*;
import java.awt.*;
import Interfaz.InterfazCliente;

public class VentanaCliente extends JFrame {
    private InterfazCliente controlador;

    public VentanaCliente(InterfazCliente controlador) {
        this.controlador = controlador;

        setTitle("Menú Cliente");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JButton btnComprar = new JButton("Comprar Tiquete");
        JButton btnConsultar = new JButton("Consultar Tiquetes");
        JButton btnRegistrar = new JButton("Registrar Cliente");
        JButton btnSalir = new JButton("Salir");

        btnComprar.addActionListener(e -> mostrarCompra());
        btnConsultar.addActionListener(e -> mostrarConsulta());
        btnRegistrar.addActionListener(e -> {
            String resultado = controlador.registrarseComoClienteSwing(this);
            JOptionPane.showMessageDialog(this, resultado);
        });
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnComprar);
        panel.add(btnConsultar);
        panel.add(btnRegistrar);
        panel.add(btnSalir);

        add(panel);
        setVisible(true);
    }

    private void mostrarCompra() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del cliente:");
        String[] tipos = {"Basico", "Familiar", "Oro", "Diamante"};
        int tipo = JOptionPane.showOptionDialog(this, "Tipo de tiquete:", "Compra",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, tipos, tipos[0]) + 1;

        int resp = JOptionPane.showConfirmDialog(this, "¿Desea FastPass?", "FastPass",
                JOptionPane.YES_NO_OPTION);
        boolean fastPass = resp == JOptionPane.YES_OPTION;

        String resultado = controlador.comprarTiqueteSwing(nombre, tipo, fastPass);
        JOptionPane.showMessageDialog(this, resultado);
    }

    private void mostrarConsulta() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del cliente:");
        String resultado = controlador.consultarTiquetesPorNombre(nombre);
        JOptionPane.showMessageDialog(this, resultado);
    }
}