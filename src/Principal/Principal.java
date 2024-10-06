/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;
import Views.Interfaz_Principal;
import Controllers.ApiRest;
import Controllers.ControlerInterfaz;
/**
 *
 * @author William
 */
public class Principal {
    public static void main(String[] args) {
        Interfaz_Principal vista = new Interfaz_Principal();
        ApiRest api = new ApiRest();
        ControlerInterfaz controlador= new ControlerInterfaz(vista, api);
        vista.setVisible(true);
    }
}
