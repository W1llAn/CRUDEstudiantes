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
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost:8087/SOA/controllers/apiRest.php");
        DefaultTableModel modeloTable = (DefaultTableModel) this.vista.jtblEstudiantes.getModel();
        for (Estudiante estudiante : estudiantes) {
            modeloTable.addRow(new Object[]{estudiante.getCedula(), estudiante.getNombre(), estudiante.getApellido(), estudiante.getDireccion(), estudiante.getNumeroCelular()});
        }
        this.vista.jtblEstudiantes.setModel(modeloTable);
    }

    private void editarAlumno(int indice) {
        String urlServicio = "http://localhost:8087/SOA/controllers/apiRest.php";
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes(urlServicio);
        Estudiante estudiante = estudiantes.get(indice);
        this.editar.jTxfCedula.setText(estudiante.getCedula());
        this.editar.jTxfNombre.setText(estudiante.getNombre());
        this.editar.jTxfApellido.setText(estudiante.getApellido());
        this.editar.jTxfDireccion.setText(estudiante.getDireccion());
        this.editar.jTxfTelefono.setText(estudiante.getNumeroCelular());
    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        if (e.getSource() == vista.jbtnCrearUsuario) {

        }
        if (e.getSource() == vista.jbtnEditarUsuario) {
            if (this.vista.jtblEstudiantes.getSelectedRow() != -1) {
                this.editar.setVisible(true);
                this.editar.jTxfCedula.setEnabled(false);
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
            DefaultTableModel modeloTable = (DefaultTableModel) this.vista.jtblEstudiantes.getModel();
            String urlServicio = "http://localhost:8087/SOA/controllers/apiRest.php";
            Estudiante estudiante = new Estudiante();
            estudiante.setCedula(this.editar.jTxfCedula.getText());
            estudiante.setNombre(this.editar.jTxfNombre.getText());
            estudiante.setApellido(this.editar.jTxfApellido.getText());
            estudiante.setDireccion(this.editar.jTxfDireccion.getText());
            estudiante.setNumeroCelular(this.editar.jTxfTelefono.getText());
            boolean respuesta = this.api.editarEstudiante(estudiante, urlServicio);
            if (respuesta) {
                JOptionPane.showMessageDialog(null, "Datos guardados exitosamente.", "Exito",
                    JOptionPane.INFORMATION_MESSAGE);
                this.editar.dispose();
                modeloTable.setRowCount(0);
                this.llenarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Falló al guardar los datos.");
            }
        }
    }

}
