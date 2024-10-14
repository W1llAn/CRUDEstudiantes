/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Estudiante;
import Views.Interfaz_Editar;
import Views.Interfaz_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para la interfaz principal y la de edición de estudiantes
 * 
 * @author William
 */
public class ControlerInterfaz implements ActionListener {

    Interfaz_Principal vista = new Interfaz_Principal();
    Interfaz_Editar editar = new Interfaz_Editar();
    ApiRest api = new ApiRest();

    public ControlerInterfaz(Interfaz_Principal vista, ApiRest api, Interfaz_Editar editar) {
        this.vista = vista;
        this.api = api;
        this.editar = editar;
        
        vista.jbtnCrearUsuario.addActionListener(this);
        vista.jbtnEditarUsuario.addActionListener(this);
        vista.jbtnEliminarUsuario.addActionListener(this);
        vista.jbtnBuscarEstudiante.addActionListener(this); //  listener para el boton de buscar estudiante xd
        editar.jBtnCancelar.addActionListener(this);
        editar.jBtnGuardar.addActionListener(this);

        this.llenarTabla();
    }

    // Método para llenar la tabla de estudiantes
    private void llenarTabla() {
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost:8087/SOAP/controllers/apiRest.php");
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost/SOA/Controllers/apiRest.php");
        DefaultTableModel modeloTable = (DefaultTableModel) this.vista.jtblEstudiantes.getModel();
        modeloTable.setRowCount(0);
        for (Estudiante estudiante : estudiantes) {
            modeloTable.addRow(new Object[]{estudiante.getCedula(), estudiante.getNombre(), estudiante.getApellido(), estudiante.getDireccion(), estudiante.getNumeroCelular()});
        }
        this.vista.jtblEstudiantes.setModel(modeloTable);
    }

    private void editarAlumno(int indice) {
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost:8087/SOAP/controllers/apiRest.php");
        String cedula = estudiantes.get(indice).getCedula();
        this.editar.jTxfCedula.setEnabled(false);
        this.editar.jTxfCedula.setText(cedula);
    }

    private void crearAlumno() {
        String cedula = editar.jTxfCedula.getText();
        String nombre = editar.jTxfNombre.getText();
        String apellido = editar.jTxfApellido.getText();
        String direccion = editar.jTxfDireccion.getText();
        String numeroCelular = editar.jTxfTelefono.getText();

        Estudiante nuevoEstudiante = new Estudiante(cedula, nombre, apellido, direccion, numeroCelular);

        boolean exito = api.crearUsuario("http://localhost:8087/SOAP/controllers/apiRest.php", nuevoEstudiante);

        if (exito) {
            
            llenarTabla();
            JOptionPane.showMessageDialog(null, "Usuario creado con éxito");
            this.editar.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al crear el usuario");
        }

        String urlServicio = "http://localhost/SOA/Controllers/apiRest.php";
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes(urlServicio);
        Estudiante estudiante = estudiantes.get(indice);
        this.editar.jTxfCedula.setText(estudiante.getCedula());
        this.editar.jTxfNombre.setText(estudiante.getNombre());
        this.editar.jTxfApellido.setText(estudiante.getApellido());
        this.editar.jTxfDireccion.setText(estudiante.getDireccion());
        this.editar.jTxfTelefono.setText(estudiante.getNumeroCelular());
    }

    // Estudiante por cedula
    private void buscarPorCedula(String cedulaBuscar) {
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost/SOA/Controllers/apiRest.php");
        
        // Filtrar estudiantes por la cedula ingresada
        ArrayList<Estudiante> estudiantesFiltrados = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCedula().equals(cedulaBuscar)) {
                estudiantesFiltrados.add(estudiante);
            }
        }

        // Actualizar tabla
        DefaultTableModel modeloTable = (DefaultTableModel) this.vista.jtblEstudiantes.getModel();
        modeloTable.setRowCount(0); // Limpiar la tabla

        if (estudiantesFiltrados.size() > 0) {
            for (Estudiante estudiante : estudiantesFiltrados) {
                modeloTable.addRow(new Object[]{estudiante.getCedula(), estudiante.getNombre(), estudiante.getApellido(), estudiante.getDireccion(), estudiante.getNumeroCelular()});
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún estudiante con la cédula ingresada.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica para crear un nuevo estudiante
        if (e.getSource() == vista.jbtnCrearUsuario) {

            this.editar.jLabel1.setText("Crear Usuario");
            this.editar.jTxfCedula.setEnabled(true);
            this.editar.jTxfCedula.setText("");
            this.editar.jTxfNombre.setText("");
            this.editar.jTxfApellido.setText("");
            this.editar.jTxfDireccion.setText("");
            this.editar.jTxfTelefono.setText("");

            this.editar.setVisible(true);
            // Implementar la lógica de crear estudiante aquí
        }

        // Lógica para editar un estudiante
        if (e.getSource() == vista.jbtnEditarUsuario) {
            if (this.vista.jtblEstudiantes.getSelectedRow() != -1) {
                this.editar.setVisible(true);
                this.editar.jTxfCedula.setEnabled(false); // Deshabilitar el campo de cédula para no permitir modificarlo
                int indice = this.vista.jtblEstudiantes.getSelectedRow();
                editarAlumno(indice);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione al usuario a editar ");
                JOptionPane.showMessageDialog(null, "Seleccione al usuario a editar.");
            }
        }

        // Lógica para eliminar un estudiante
        if (e.getSource() == vista.jbtnEliminarUsuario) {
            // Implementar la lógica de eliminar estudiante aquí
        }

        // Lógica para buscar un estudiante por cédula
        if (e.getSource() == vista.jbtnBuscarEstudiante) {
            String cedulaBuscar = this.vista.textCedula.getText(); // OJO cambiar a public el textCedula como java.awt.TextField
            if (!cedulaBuscar.isEmpty()) {
                buscarPorCedula(cedulaBuscar); // Llamar al método de búsqueda
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cédula.");
            }
        }

        // Cancelar la edición de estudiante
        if (e.getSource() == editar.jBtnCancelar) {
            this.editar.dispose();
        }

        // Guardar cambios en un estudiante
        if (e.getSource() == editar.jBtnGuardar) {
            if (editar.jLabel1.getText().equals("Crear Usuario")) {
                crearAlumno();
            }else{
                //Aqui agregar codigo para editar el estudiante :)
            }
        }

            DefaultTableModel modeloTable = (DefaultTableModel) this.vista.jtblEstudiantes.getModel();
            String urlServicio = "http://localhost/SOA/Controllers/apiRest.php";
            Estudiante estudiante = new Estudiante();
            estudiante.setCedula(this.editar.jTxfCedula.getText());
            estudiante.setNombre(this.editar.jTxfNombre.getText());
            estudiante.setApellido(this.editar.jTxfApellido.getText());
            estudiante.setDireccion(this.editar.jTxfDireccion.getText());
            estudiante.setNumeroCelular(this.editar.jTxfTelefono.getText());

            // Enviar los cambios a la API
            boolean respuesta = this.api.editarEstudiante(estudiante, urlServicio);
            if (respuesta) {
                JOptionPane.showMessageDialog(null, "Datos guardados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.editar.dispose();
                modeloTable.setRowCount(0); // Limpiar la tabla antes de volver a llenarla
                this.llenarTabla(); // Volver a llenar la tabla con los datos actualizados
            } else {
                JOptionPane.showMessageDialog(null, "Falló al guardar los datos.");
            }
        }
    }
}
