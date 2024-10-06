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
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost:8087/SOAP/controllers/apiRest.php");
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

    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        if (e.getSource() == vista.jbtnCrearUsuario) {

            this.editar.jLabel1.setText("Crear Usuario");
            this.editar.jTxfCedula.setEnabled(true);
            this.editar.jTxfCedula.setText("");
            this.editar.jTxfNombre.setText("");
            this.editar.jTxfApellido.setText("");
            this.editar.jTxfDireccion.setText("");
            this.editar.jTxfTelefono.setText("");

            this.editar.setVisible(true);
        }
        if (e.getSource() == vista.jbtnEditarUsuario) {
            if (this.vista.jtblEstudiantes.getSelectedRow() != -1) {
                this.editar.setVisible(true);
                int indice = this.vista.jtblEstudiantes.getSelectedRow();
                editarAlumno(indice);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione al usuario a editar ");
            }

        }
        if (e.getSource() == vista.jbtnEliminarUsuario) {

        }

        //Editar estudiante
        if (e.getSource() == editar.jBtnCancelar) {
            this.editar.dispose();
        }
        if (e.getSource() == editar.jBtnGuardar) {
            if (editar.jLabel1.getText().equals("Crear Usuario")) {
                crearAlumno();
            }else{
                //Aqui agregar codigo para editar el estudiante :)
            }
        }

    }
}
