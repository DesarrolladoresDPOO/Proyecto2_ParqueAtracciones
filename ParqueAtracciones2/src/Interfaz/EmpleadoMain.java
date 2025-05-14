package Interfaz;

public class EmpleadoMain {
    public static void main(String[] args) {
        InterfazEmpleado controlador = new InterfazEmpleado();
        new vista.VentanaAsistenciaEmpleado(controlador);
    }
}