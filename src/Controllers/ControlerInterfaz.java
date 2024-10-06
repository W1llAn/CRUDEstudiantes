/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Estudiante;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Views.Interfaz_Principal;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author William
 */
public class ControlerInterfaz implements ActionListener{
    Interfaz_Principal vista = new Interfaz_Principal();
    ApiRest api = new ApiRest();
    public ControlerInterfaz(Interfaz_Principal vista, ApiRest api){
        this.vista= vista;
        this.api = api;
        vista.jbtnCrearUsuario.addActionListener(this);
        vista.jbtnEditarUsuario.addActionListener(this);
        vista.jbtnEliminarUsuario.addActionListener(this);
        this.llenarTabla();
    }
    private void llenarTabla(){
        ArrayList<Estudiante> estudiantes = this.api.obtenerEstudiantes("http://localhost:8080/SOA/controllers/apiRest.php");
        DefaultTableModel modeloTable = (DefaultTableModel) this.vista.jtblEstudiantes.getModel();
        for (Estudiante estudiante : estudiantes) {
            modeloTable.addRow(new Object[]{estudiante.getCedula(), estudiante.getNombre(),estudiante.getApellido(),estudiante.getDireccion(),estudiante.getNumeroCelular()});
        }
        this.vista.jtblEstudiantes.setModel(modeloTable);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.jbtnCrearUsuario) {
            
        }
        if (e.getSource()==vista.jbtnEditarUsuario) {
            
        }
        if (e.getSource()==vista.jbtnEliminarUsuario) {
            
        }
    }
    
}
