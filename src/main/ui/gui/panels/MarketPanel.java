package ui.gui.panels;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Market;
import model.Stock;

/**
 * Represents the panel that displays the stock market.
 * Shows a table of available stocks with their current prices.
 * Allows the user to select a stock from the table.
 */
@ExcludeFromJacocoGeneratedReport
public class MarketPanel extends JPanel {
    private Market market;
    
    private JTable marketTable;
    private DefaultTableModel tableModel;

    // EFFECTS: Constructs the MarketPanel with the given market.
    // Initializes the layout and table, and performs an initial refresh.
    public MarketPanel(Market market) {
        this.market = market;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Market"));

        String[] columns = { "Symbol", "Company", "Sector", "Price" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        marketTable = new JTable(tableModel);
        add(new JScrollPane(marketTable), BorderLayout.CENTER);
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Refreshes the table with the latest stock data from the market.
    public void refresh() {
        tableModel.setRowCount(0);
        for (Stock s : market.getAllStocks()) {
            Object[] row = {
                    s.getSymbol(),
                    s.getCompanyName(),
                    s.getSector(),
                    String.format("$%.2f", s.getCurrentPrice())
            };
            tableModel.addRow(row);
        }
    }

    // EFFECTS: Returns the symbol of the currently selected stock in the table.
    // Returns null if no row is selected.
    public String getSelectedSymbol() {
        int row = marketTable.getSelectedRow();
        if (row != -1) {
            return (String) tableModel.getValueAt(row, 0);
        }
        return null;
    }
}
