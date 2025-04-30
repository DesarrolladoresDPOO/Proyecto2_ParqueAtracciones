package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import persona.Empleado;

public class ArchivoPlano {

	public void escribir(String nombreArchivo, ArrayList<String> lineasTexto) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
			for(String linea : lineasTexto) {
				bw.write(linea);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> leer (String nombreArchivo){
		ArrayList<String> lineasTexto = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
			String linea;
			while((linea = br.readLine()) != null) {
				lineasTexto.add(linea);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineasTexto;
	}
	
	public void escribirEmpleadoAppend(String ruta, Empleado empleado) {
	    try (FileWriter fw = new FileWriter(ruta, true);
	         BufferedWriter bw = new BufferedWriter(fw);
	         PrintWriter out = new PrintWriter(bw)) {
	        
	        StringBuilder sb = new StringBuilder();
	        sb.append(empleado.getClass().getSimpleName()).append(",");
	        sb.append(empleado.getLogin()).append(",");
	        sb.append(empleado.getPassword()).append(",");
	        sb.append(empleado.getNombre()).append(",");
	        sb.append(empleado.getId()).append(",");
	        sb.append(empleado.getLugarTrabajo()).append(",");
	        sb.append(empleado.getTurno());

	        out.println(sb.toString());

	    } catch (IOException e) {
	        System.out.println("Error al escribir empleado: " + e.getMessage());
	    }
	}
	
}
