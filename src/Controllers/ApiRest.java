/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Estudiante;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author William
 */
public class ApiRest {
    
    public  ArrayList<Estudiante> obtenerEstudiantes(String urlServicio) {
        ArrayList<Estudiante> estudiantes = null;
        try {
            // Crear conexión
            URL url = new URL(urlServicio);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("Accept", "application/json");

            // Verificar el código de respuesta
            if (conexion.getResponseCode() != 200) {
                throw new RuntimeException("Error: Código HTTP " + conexion.getResponseCode());
            }

            // Leer la respuesta del servicio
            BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
            StringBuilder resultado = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                resultado.append(linea);
            }

            // Cerrar la conexión
            conexion.disconnect();

            // Leer el resultado JSON y convertirlo en un Array de estudiantes
            JSONArray listaEstudiantes = new JSONArray(resultado.toString());
            for (int i = 0; i < listaEstudiantes.length(); i++) {
                JSONObject estudiante = listaEstudiantes.getJSONObject(i);
                estudiantes.add(new Estudiante(estudiante.getString("cedula"),
                                                                     estudiante.getString("nombre"),
                                                                    estudiante.getString("apelllido"),
                                                                    estudiante.getString("direccion"),
                                                                    estudiante.getString("telefono")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return estudiantes;
    }
}
