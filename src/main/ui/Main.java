package ui;

import javax.swing.SwingUtilities;

import ui.gui.MainFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        // new StockPortfolioApp();
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
