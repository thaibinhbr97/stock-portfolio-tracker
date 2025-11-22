package ui;

import javax.swing.SwingUtilities;

import ui.gui.MainFrame;
import ui.gui.SplashScreen;

public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splash.setVisible(false);
        splash.dispose();

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
