package Controller;

import java.util.*;
import javax.swing.*;

public class gestionarIdioma {

    /**
     * Este metodo traduce los menu superiores
     * 
     * @param listMenu      Lista de menus a traducir
     * @param listMenuItems Lista de menu items a traducir
     */
    public static void traducirMenu(ArrayList<JMenu> listMenu, ArrayList<JMenuItem> listMenuItems) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("data.language.language");
            for (JMenu mn : listMenu) {
                String valor = rb.getString(mn.getName());
                mn.setText(valor);

            }
            for (JMenuItem mnIt : listMenuItems) {
                String valor = rb.getString(mnIt.getName());
                mnIt.setText(valor);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } // try/catch

    }// END traducirMenu(listMenu, listMenuItems)

    /**
     * Este metodo traduce todos los botones
     * 
     * @param listButtons Lista de botones a traducir
     */
    public static void traducirBotones(ArrayList<JButton> listButtons) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("data.language.language");
            for (JButton btn : listButtons) {
                String valor = rb.getString(btn.getName());
                btn.setText(valor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } // try/catch
    }// END traducirBotones(listButtons)

    /**
     * Este metodo traduce las etiquetas del sistema
     * 
     * @param listLabel Lista de etiquetas a traducir
     */

    public static void traducirEtiquetas(ArrayList<JLabel> listLabel) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("data.language.language");
            for (JLabel lbl : listLabel) {
                String valor = rb.getString(lbl.getName());
                lbl.setText(valor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } // try/catch

    }// END traducirEtiquetas(listLabel)

}// END gestionarIdioma class
