/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

import Views.Interfaz_Principal;
import Controllers.ApiRest;
import Controllers.ControlerInterfaz;
import Views.Interfaz_Editar;

/**
 *
 * @author William
 */
public class Principal {

    public static void main(String[] args) {
        Interfaz_Principal vista = new Interfaz_Principal();
        ApiRest api = new ApiRest();
        Interfaz_Editar editar = new Interfaz_Editar();
        ControlerInterfaz controlador = new ControlerInterfaz(vista, api,editar);
        vista.setVisible(true);
    }
}
