package ui.gui.panels;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Holding;
import model.Portfolio;

/**
 * Represents the panel for selling stocks.
 * Allows the user to input a stock symbol and quantity to sell shares.
 * Can pre-fill the symbol from the PortfolioPanel selection.
 */
@ExcludeFromJacocoGeneratedReport
public class SellStockPanel extends JPanel {
    private Portfolio portfolio;
    private PortfolioPanel portfolioPanel;
    private MarketPanel marketPanel;

    private JTextField symbolInput;
    private JTextField qtyInput;
    private JLabel result;

    private AccountInfoPanel accountInfoPanel;

    // EFFECTS: Constructs the SellStockPanel with necessary dependencies.
    // Initializes the UI components.
    public SellStockPanel(Portfolio portfolio,
            PortfolioPanel portfolioPanel,
            MarketPanel marketPanel,
            AccountInfoPanel accountInfoPanel) {
        this.portfolio = portfolio;
        this.portfolioPanel = portfolioPanel;
        this.marketPanel = marketPanel;
        this.accountInfoPanel = accountInfoPanel;
        initPanel();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the layout and adds input fields and buttons.
    private void initPanel() {
        setLayout(new GridLayout(4, 2, 5, 5));
        add(new JLabel("Stock Symbol:"));
        symbolInput = new JTextField();
        add(symbolInput);
        add(new JLabel("Quantity:"));
        qtyInput = new JTextField();
        add(qtyInput);
        addUseSelectedButton();
        addSellButton();
    }

    // MODIFIES: this
    // EFFECTS: Adds the "Use Selected" button which populates the symbol field
    // from the PortfolioPanel's selection.
    private void addUseSelectedButton() {
        JButton useSelectedBtn = new JButton("Use Selected");
        useSelectedBtn.addActionListener(e -> {
            String selected = portfolioPanel.getSelectedSymbol();
            if (selected != null) {
                symbolInput.setText(selected);
            }
        });
        add(useSelectedBtn);
        add(new JLabel("")); // Spacer
    }

    // MODIFIES: this
    // EFFECTS: Adds the "Sell Stock" button which triggers the sell transaction.
    private void addSellButton() {
        JButton sellBtn = new JButton("Sell Stock");
        result = new JLabel("");
        add(sellBtn);
        add(result);
        sellBtn.addActionListener(e -> sellStock());
    }

    // MODIFIES: this, portfolio, accountInfoPanel, portfolioPanel, marketPanel
    // EFFECTS: Attempts to sell the specified stock and quantity.
    // Updates the portfolio, refreshes panels, and displays a message on success.
    // Displays error messages for invalid input, insufficient shares, or missing
    // holding.
    private void sellStock() {
        try {
            String symbol = symbolInput.getText().toUpperCase();
            double qty = Double.parseDouble(qtyInput.getText());
            Holding h = validateHolding(symbol);
            if (h == null) {
                return;
            }
            if (!validateShares(qty, h)) {
                return;
            }

            executeSell(symbol, qty);
        } catch (Exception ex) {
            result.setText("Invalid input.");
        }
    }

    // EFFECTS: Checks if the user holds the specified stock.
    // Returns the holding if found, otherwise sets error message and returns null.
    private Holding validateHolding(String symbol) {
        Holding h = portfolio.getHoldings().get(symbol);
        if (h == null) {
            result.setText("No holdings.");
        }
        return h;
    }

    // EFFECTS: Checks if the user has enough shares to sell.
    // Returns true if sufficient shares, otherwise sets error message and returns
    // false.
    private boolean validateShares(double qty, Holding h) {
        if (qty > h.getShares()) {
            result.setText("Not enough shares.");
            return false;
        }
        return true;
    }

    // MODIFIES: this, portfolio, accountInfoPanel, portfolioPanel, marketPanel
    // EFFECTS: Executes the sell transaction, updates UI, and shows success
    // message.
    private void executeSell(String symbol, double qty) {
        portfolio.sellShare(symbol, qty);
        portfolioPanel.refresh();
        accountInfoPanel.refresh();
        JOptionPane.showMessageDialog(this, "Sold " + qty + " of " + symbol);
        marketPanel.refresh();
    }
}
