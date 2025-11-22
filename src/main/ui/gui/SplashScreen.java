package ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 * Represents the splash screen displayed when the application starts.
 * Shows the application logo in a borderless window.
 */
public class SplashScreen extends JWindow {

    // EFFECTS: Constructs the splash screen.
    public SplashScreen() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(java.awt.Color.WHITE);

        // Load and scale image
        ImageIcon originalIcon = new ImageIcon("data/splash.png");
        Image originalImage = originalIcon.getImage();

        int newWidth = (int) (originalIcon.getIconWidth() * 0.6);
        int newHeight = (int) (originalIcon.getIconHeight() * 0.6);

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the window's bounds to match scaled image, centering the window
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - newWidth) / 2;
        int y = (screen.height - newHeight) / 2;
        setBounds(x, y, newWidth, newHeight);

        // Build the splash screen
        JLabel label = new JLabel(scaledIcon);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(label, BorderLayout.CENTER);
    }
}
