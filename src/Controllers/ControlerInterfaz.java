/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Estudiante;
import Views.Interfaz_Editar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Views.Interfaz_Principal;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
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
        editar.jBtnCancelar.addActionListener(this);
        editar.jBtnGuardar.addActionListener(this);

        this.llenarTabla();
    }

    private void llenarTabla() {
        // Obtener el modelo de la tabla
        DefaultTableModel modeloTable = (DefaultTableModel) this.vista.jtblEstudiantes.getModel();

        // Limpiar la tabla antes de agregar nuevos datos
        modeloTable.setRowCount(0);

        // Obtener la lista de estudiantes desde la API
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost:8080/ProyectosArita/SOAP/controllers/apiRest.php");

        // Llenar la tabla con los nuevos datos
        for (Estudiante estudiante : estudiantes) {
            modeloTable.addRow(new Object[]{
                estudiante.getCedula(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getDireccion(),
                estudiante.getNumeroCelular()
            });
        }

        // Actualizar el modelo de la tabla
        this.vista.jtblEstudiantes.setModel(modeloTable);
        this.vista.jbtnEliminarUsuario.setEnabled(false);
    }

    private void editarAlumno(int indice) {
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost:8080/ProyectosArita/SOAP/controllers/apiRest.php");
        String cedula = estudiantes.get(indice).getCedula();
        this.editar.jLblCedula.setText(cedula);
    }

    private void eliminarAlumno(String cedula) {
        boolean exito = this.api.eliminarEstudiante("http://localhost:8080/ProyectosArita/SOAP/controllers/apiRest.php", cedula);
        if (exito) {
            JOptionPane.showMessageDialog(null, "Estudiante eliminado exitosamente");
            this.llenarTabla(); // Refrescar la tabla después de eliminar
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar estudiante");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jbtnCrearUsuario) {
            // Lógica para crear usuario
        }
        if (e.getSource() == vista.jbtnEditarUsuario) {
            if (this.vista.jtblEstudiantes.getSelectedRow() != -1) {
                this.editar.setVisible(true);
                int indice = this.vista.jtblEstudiantes.getSelectedRow();
                editarAlumno(indice);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione al usuario a editar");
            }
        }
        if (e.getSource() == vista.jbtnEliminarUsuario) {
            if (this.vista.jtblEstudiantes.getSelectedRow() != -1) {
                String cedula_a_eliminar = this.vista.jtblEstudiantes.getValueAt(this.vista.jtblEstudiantes.getSelectedRow(), 0).toString();
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar al estudiante con cédula " + cedula_a_eliminar + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    eliminarAlumno(cedula_a_eliminar);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione al usuario a eliminar");
            }
        }

        // Lógica para la edición de estudiante
        if (e.getSource() == editar.jBtnCancelar) {
            this.editar.dispose();
        }
        if (e.getSource() == editar.jBtnGuardar) {
            // Guardar cambios del estudiante
        }
    }

}
