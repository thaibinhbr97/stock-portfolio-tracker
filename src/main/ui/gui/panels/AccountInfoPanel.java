package ui.gui.panels;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Portfolio;

/**
 * Represents the panel that displays the user's account information,
 * including owner name, cash balance, and total portfolio value.
 */
@ExcludeFromJacocoGeneratedReport
public class AccountInfoPanel extends JPanel {
    private Portfolio portfolio;
    private JLabel ownerLabel;
    private JLabel cashLabel;
    private JLabel valueLabel;

    // EFFECTS: Constructs the AccountInfoPanel with the given portfolio.
    // Initializes the layout and components, and performs an initial refresh.
    public AccountInfoPanel(Portfolio portfolio) {
        this.portfolio = portfolio;
        setLayout(new GridLayout(3, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Account Information"));

        ownerLabel = new JLabel();
        cashLabel = new JLabel();
        valueLabel = new JLabel();

        add(ownerLabel);
        add(cashLabel);
        add(valueLabel);

        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Updates the labels with the latest information from the portfolio.
    public void refresh() {
        ownerLabel.setText("Owner: " + portfolio.getOwner());
        cashLabel.setText(String.format("Cash: $%.2f", portfolio.getCashBalance()));
        valueLabel.setText(String.format("Portfolio Value: $%.2f", portfolio.getPortfolioValue()));
    }
}
