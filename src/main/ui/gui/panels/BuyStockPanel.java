package ui.gui.panels;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Market;
import model.Portfolio;
import model.Stock;

/**
 * Represents the panel for buying stocks.
 * Allows the user to input a stock symbol and quantity to purchase shares.
 * Can pre-fill the symbol from the MarketPanel selection.
 */
@ExcludeFromJacocoGeneratedReport
public class BuyStockPanel extends JPanel {
    private Portfolio portfolio;
    private Market market;
    private PortfolioPanel portfolioPanel;
    private MarketPanel marketPanel;
    private AccountInfoPanel accountInfoPanel;    

    private JTextField symbolInput;
    private JTextField qtyInput;
    private JLabel result;

    // EFFECTS: Constructs the BuyStockPanel with necessary dependencies.
    // Initializes the UI components.
    public BuyStockPanel(Portfolio portfolio, Market market,
            PortfolioPanel portfolioPanel, MarketPanel marketPanel,
            AccountInfoPanel accountInfoPanel) {
        this.portfolio = portfolio;
        this.market = market;
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
        addBuyButton();
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
    // EFFECTS: Adds the "Buy Stock" button which triggers the buy transaction.
    private void addBuyButton() {
        JButton buyBtn = new JButton("Buy Stock");
        result = new JLabel("");
        add(buyBtn);
        add(result);
        buyBtn.addActionListener(e -> buyStock());
    }

    // MODIFIES: this, portfolio, accountInfoPanel, portfolioPanel, marketPanel
    // EFFECTS: Attempts to buy the specified stock and quantity.
    // Updates the portfolio, refreshes panels, and displays a message on success.
    // Displays error messages for invalid input, insufficient funds, or invalid
    // stock.
    private void buyStock() {
        try {
            String symbol = symbolInput.getText().toUpperCase();
            double qty = Double.parseDouble(qtyInput.getText());
            Stock stock = validateStock(symbol);
            if (stock == null) {
                return;
            }
            if (!validateFunds(qty, stock)) {
                return;
            }
            executeBuy(stock, qty, symbol);
        } catch (Exception ex) {
            result.setText("Invalid input.");
        }
    }

    // EFFECTS: Checks if the stock exists in the market.
    // Returns the stock if found, otherwise sets error message and returns null.
    private Stock validateStock(String symbol) {
        Stock stock = market.getStock(symbol);
        if (stock == null) {
            result.setText("Stock not found.");
        }
        return stock;
    }

    // EFFECTS: Checks if the user has enough cash to buy the stock.
    // Returns true if sufficient funds, otherwise sets error message and returns
    // false.
    private boolean validateFunds(double qty, Stock stock) {
        double total = qty * stock.getCurrentPrice();
        if (portfolio.getCashBalance() < total) {
            result.setText("Insufficient cash.");
            return false;
        }
        return true;
    }

    // MODIFIES: this, portfolio, accountInfoPanel, portfolioPanel, marketPanel
    // EFFECTS: Executes the buy transaction, updates UI, and shows success message.
    private void executeBuy(Stock stock, double qty, String symbol) {
        portfolio.buyShare(stock, qty);
        result.setText("Bought " + qty + " of " + symbol);
        portfolioPanel.refresh();
        accountInfoPanel.refresh();
        JOptionPane.showMessageDialog(this, "Bought " + qty + " of " + symbol);
        marketPanel.refresh();
    }
}
