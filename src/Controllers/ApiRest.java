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

import java.net.URLEncoder;
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

            // Verificar el código de respuesta (aceptamos tanto 200 como 201)
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_CREATED || codigoRespuesta == HttpURLConnection.HTTP_OK) {
                // Usuario creado exitosamente (200 o 201)
                return true;
            } else {
                // Manejar errores si es necesario
                System.out.println("Error: Código HTTP " + codigoRespuesta);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
    public boolean editarEstudiante(Estudiante estudiante, String urlServicio) {
        try {
            // Agregar la cédula del estudiante a la URL
            URL url = new URL(urlServicio + "?cedula=" + estudiante.getCedula());
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);  // Permitir datos en la solicitud
            conexion.setRequestMethod("PUT");  // Método PUT para actualización
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexion.setRequestProperty("Accept", "application/json");

            // Crear los datos de formulario que vamos a enviar
            String urlParameters = "nombre=" + URLEncoder.encode(estudiante.getNombre(), "UTF-8")
                    + "&apellido=" + URLEncoder.encode(estudiante.getApellido(), "UTF-8")
                    + "&direccion=" + URLEncoder.encode(estudiante.getDireccion(), "UTF-8")
                    + "&telefono=" + URLEncoder.encode(estudiante.getNumeroCelular(), "UTF-8");

            // Enviar los datos
            OutputStream os = conexion.getOutputStream();
            os.write(urlParameters.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            // Verificar el código de respuesta
            int responseCode = conexion.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error: Código HTTP " + responseCode);
            }

            // Leer la respuesta del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String inputLine;
            StringBuilder respuesta = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                respuesta.append(inputLine);
            }
            in.close();

            // Imprimir la respuesta
            //System.out.println("Respuesta del servidor: " + respuesta.toString());
            
            // Cerramos la conexión
            conexion.disconnect();

            return true; // Éxito

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error en la actualización
        }
    }

}
