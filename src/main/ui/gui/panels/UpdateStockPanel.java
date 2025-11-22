package ui.gui.panels;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Market;
import model.Stock;

/**
 * Represents the panel for updating stock prices.
 * Allows the user to input a stock symbol and a new price.
 * Can pre-fill the symbol from the MarketPanel selection.
 */
@ExcludeFromJacocoGeneratedReport
public class UpdateStockPanel extends JPanel {
    private Market market;
    private MarketPanel marketPanel;
    private PortfolioPanel portfolioPanel;

    private JTextField symbolInput;
    private JTextField priceInput;
    private JLabel result;

    // EFFECTS: Constructs the UpdateStockPanel with necessary dependencies.
    // Initializes the UI components.
    public UpdateStockPanel(Market market, MarketPanel marketPanel, PortfolioPanel portfolioPanel) {
        this.market = market;
        this.marketPanel = marketPanel;
        this.portfolioPanel = portfolioPanel;
        initPanel();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the layout and adds input fields and buttons.
    private void initPanel() {
        setLayout(new GridLayout(4, 2, 5, 5));
        add(new JLabel("Stock Symbol:"));
        symbolInput = new JTextField();
        add(symbolInput);
        add(new JLabel("New Price:"));
        priceInput = new JTextField();
        add(priceInput);
        addUseSelectedButton();
        addUpdateButton();
    }

    // MODIFIES: this
    // EFFECTS: Adds the "Use Selected" button which populates the symbol field
    // from the MarketPanel's selection.
    private void addUseSelectedButton() {
        JButton useSelectedBtn = new JButton("Use Selected");
        useSelectedBtn.addActionListener(e -> {
            String selected = marketPanel.getSelectedSymbol();
            if (selected != null) {
                symbolInput.setText(selected);
            }
        });
        add(useSelectedBtn);
        add(new JLabel("")); // Spacer
    }

    // MODIFIES: this
    // EFFECTS: Adds the "Update Price" button which triggers the price update.
    private void addUpdateButton() {
        JButton updateBtn = new JButton("Update Price");
        result = new JLabel("");
        add(updateBtn);
        add(result);
        updateBtn.addActionListener(e -> updatePrice());
    }

    // MODIFIES: this, market, marketPanel, portfolioPanel
    // EFFECTS: Attempts to update the price of the specified stock.
    // Updates the market, refreshes panels, and displays a message on success.
    // Displays error messages for invalid input or if the stock is not found.
    private void updatePrice() {
        try {
            String symbol = symbolInput.getText().toUpperCase();
            double newPrice = Double.parseDouble(priceInput.getText());

            Stock stock = market.getStock(symbol);
            if (stock == null) {
                result.setText("Stock not found.");
                return;
            }

            if (newPrice < 0) {
                result.setText("Invalid price.");
                return;
            }

            stock.updateCurrentPrice(newPrice);
            result.setText("Updated " + symbol);
            marketPanel.refresh();
            portfolioPanel.refresh();
            JOptionPane.showMessageDialog(this, "Updated " + symbol + " price to $" + String.format("%.2f", newPrice));
        } catch (NumberFormatException ex) {
            result.setText("Invalid number.");
        } catch (Exception ex) {
            result.setText("Error updating.");
        }
    }
}
