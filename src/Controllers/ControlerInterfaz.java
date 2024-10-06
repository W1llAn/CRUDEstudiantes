/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Views.Interfaz_Principal;
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
