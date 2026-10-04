package uo.ri.cws.application.ui.manager;


import uo.ri.cws.application.ui.manager.mechanic.MechanicMenu;
import uo.ri.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

    public MainMenu() {
        System.out.println("Working directory: " +
                System.getProperty("user.dir"));
        menuOptions = new Object[][] { { "Manager", null },

                { "Mechanics management", MechanicMenu.class },
                { "Spare parts management", UnsupportedOperationException.class },
                { "Vehicle types management", UnsupportedOperationException.class }, };
    }

    public static void main(String[] args) {
        new MainMenu().execute();
    }

}
