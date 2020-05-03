/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diary_crud;

import javax.swing.UIManager;

/**
 *
 * @author decastrodaniel
 */
public class DIARY_CRUD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception e) {

        }
        Login login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

}
