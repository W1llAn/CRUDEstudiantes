/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Estudiante;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author William
 */
public class ApiRest {

    public ArrayList<Estudiante> obtenerEstudiantes(String urlServicio) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
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
                        estudiante.getString("apellido"),
                        estudiante.getString("direccion"),
                        estudiante.getString("telefono")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return estudiantes;
    }

    public boolean crearUsuario(String urlServicio, Estudiante estudiante) {
        try {
            // Crear conexión
            URL url = new URL(urlServicio);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json; utf-8");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setDoOutput(true); // Permitir enviar datos en la solicitud

            // Crear JSON con los datos del estudiante
            String jsonInputString = new JSONObject()
                    .put("cedula", estudiante.getCedula())
                    .put("nombre", estudiante.getNombre())
                    .put("apellido", estudiante.getApellido())
                    .put("direccion", estudiante.getDireccion())
                    .put("telefono", estudiante.getNumeroCelular())
                    .toString();

            // Enviar el JSON a la API
            try (OutputStream os = conexion.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Verificar el código de respuesta
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_CREATED) {
                // Usuario creado exitosamente (asumimos que el código 201 indica éxito)
                return true;
            } else {
                // Manejar errores si es necesario
                System.out.println("Error: Código HTTP " + codigoRespuesta);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
