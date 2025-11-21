package ui.gui.panels;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Holding;
import model.Portfolio;

/**
 * Represents the panel that displays the user's portfolio holdings.
 * Shows a table of owned stocks, including shares, average price, current
 * price,
 * market value, and profit/loss.
 * Allows the user to select a holding from the table.
 */
public class PortfolioPanel extends JPanel {
    private Portfolio portfolio;
    private JTable portfolioTable;
    private DefaultTableModel tableModel;

    // EFFECTS: Constructs the PortfolioPanel with the given portfolio.
    // Initializes the layout and table, and performs an initial refresh.
    public PortfolioPanel(Portfolio portfolio) {
        this.portfolio = portfolio;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Your Portfolio"));

        String[] columns = { "Symbol", "Shares", "Avg Price", "Cur Price", "Value", "P/L" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        portfolioTable = new JTable(tableModel);
        add(new JScrollPane(portfolioTable), BorderLayout.CENTER);

        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Refreshes the table with the latest holdings data from the
    // portfolio.
    // Calculates profit/loss for each holding.
    public void refresh() {
        tableModel.setRowCount(0);
        for (Holding h : portfolio.getHoldings().values()) {
            double currentPrice = h.getStock().getCurrentPrice();
            double marketValue = currentPrice * h.getShares();
            double costBasis = h.getAveragePrice() * h.getShares();
            double pl = marketValue - costBasis;

            Object[] row = {
                    h.getSymbol(),
                    h.getShares(),
                    String.format("$%.2f", h.getAveragePrice()),
                    String.format("$%.2f", currentPrice),
                    String.format("$%.2f", marketValue),
                    String.format("$%.2f", pl)
            };
            tableModel.addRow(row);
        }
    }

    // EFFECTS: Returns the symbol of the currently selected holding in the table.
    // Returns null if no row is selected.
    public String getSelectedSymbol() {
        int row = portfolioTable.getSelectedRow();
        if (row != -1) {
            return (String) tableModel.getValueAt(row, 0);
        }
        return null;
    }
}
