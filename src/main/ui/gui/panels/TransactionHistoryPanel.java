package ui.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Holding;
import model.Portfolio;
import model.Transaction;

/**
 * Represents the panel for displaying transaction history.
 * Shows a table of all transactions (BUY/SELL).
 * Provides filtering options by Date, Action, and Symbol.
 */
@ExcludeFromJacocoGeneratedReport
public class TransactionHistoryPanel extends JPanel {

    private Portfolio portfolio;

    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterOptions;
    private JTextField startDateField;
    private JTextField endDateField;
    private JComboBox<String> actionCombo;
    private JComboBox<String> symbolCombo;
    private JButton applyBtn;
    private JButton cancelBtn;

    // EFFECTS: Constructs the TransactionHistoryPanel with the given portfolio.
    // Initializes the panel layout and components.
    public TransactionHistoryPanel(Portfolio portfolio) {
        this.portfolio = portfolio;
        initPanel();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the main layout, table, and filter panel.
    // Refreshes the display to show all transactions initially.
    private void initPanel() {
        setLayout(new BorderLayout());
        setupTable();
        setupFilterPanel();
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Initializes the transaction table and adds it to the center of the
    // panel.
    private void setupTable() {
        String[] columns = { "Date", "Symbol", "Action", "Shares", "Price", "Total" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the filter panel with all components and adds it to the
    // top.
    private void setupFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout());
        initializeFilterComponents(filterPanel);
        addFilterComponents(filterPanel);
        add(filterPanel, BorderLayout.NORTH);
        hideOptionalFields();
    }

    // MODIFIES: this
    // EFFECTS: Creates and initializes all filter input fields and buttons.
    private void initializeFilterComponents(JPanel filterPanel) {
        filterOptions = new JComboBox<>(new String[] { "Select filter", "Date", "Action", "Symbol" });
        filterOptions.addActionListener(e -> updateFilterFields());
        startDateField = new JTextField(10);
        endDateField = new JTextField(10);
        actionCombo = new JComboBox<>(new String[] { "BUY", "SELL" });
        symbolCombo = new JComboBox<>();
        applyBtn = new JButton("Apply");
        cancelBtn = new JButton("Cancel");
        applyBtn.addActionListener(e -> applyFilter());
        cancelBtn.addActionListener(e -> resetFilter());
    }

    // MODIFIES: filterPanel
    // EFFECTS: Adds all filter components to the filter panel.
    private void addFilterComponents(JPanel filterPanel) {
        filterPanel.add(filterOptions);
        filterPanel.add(startDateField);
        filterPanel.add(endDateField);
        filterPanel.add(actionCombo);
        filterPanel.add(symbolCombo);
        filterPanel.add(applyBtn);
        filterPanel.add(cancelBtn);
    }

    // MODIFIES: this
    // EFFECTS: Hides all filter input fields initially.
    private void hideOptionalFields() {
        startDateField.setVisible(false);
        endDateField.setVisible(false);
        actionCombo.setVisible(false);
        symbolCombo.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Updates the visibility of filter fields based on the selected filter
    // option.
    // Populates the symbol combo box if "Symbol" is selected.
    private void updateFilterFields() {
        String selected = (String) filterOptions.getSelectedItem();
        startDateField.setVisible(false);
        endDateField.setVisible(false);
        actionCombo.setVisible(false);
        symbolCombo.setVisible(false);

        if ("Date".equals(selected)) {
            startDateField.setVisible(true);
            endDateField.setVisible(true);
        } else if ("Action".equals(selected)) {
            actionCombo.setVisible(true);
        } else if ("Symbol".equals(selected)) {
            symbolCombo.removeAllItems();
            for (Holding h : portfolio.getHoldings().values()) {
                symbolCombo.addItem(h.getSymbol());
            }
            symbolCombo.setVisible(true);
        }

        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: Applies the selected filter and updates the table with filtered
    // transactions.
    // Shows an error message if input is invalid.
    private void applyFilter() {
        try {
            List<Transaction> filtered = getFilteredTransactions();
            displayTransactions(filtered);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input for filter. Use YYYY-MM-DD for dates.");
        }
    }

    // EFFECTS: Returns a list of transactions filtered based on the user's
    // selection.
    private List<Transaction> getFilteredTransactions() {
        String selected = (String) filterOptions.getSelectedItem();
        List<Transaction> filtered = portfolio.getTransactionManager().getTransactions();

        if ("Date".equals(selected)) {
            LocalDate start = LocalDate.parse(startDateField.getText());
            LocalDate end = LocalDate.parse(endDateField.getText());
            LocalDateTime startTime = start.atStartOfDay();
            LocalDateTime endTime = end.atTime(23, 59, 59, 999_999_999);
            filtered = portfolio.getTransactionManager().filterByDateTime(startTime, endTime);
        } else if ("Action".equals(selected)) {
            String action = (String) actionCombo.getSelectedItem();
            filtered = portfolio.getTransactionManager().filterByAction(action);
        } else if ("Symbol".equals(selected)) {
            String symbol = (String) symbolCombo.getSelectedItem();
            if (symbol != null) {
                filtered = portfolio.getTransactionManager().filterBySymbol(symbol);
            }
        }
        return filtered;
    }

    // MODIFIES: this
    // EFFECTS: Resets all filter fields and shows all transactions.
    private void resetFilter() {
        startDateField.setText("");
        endDateField.setText("");
        filterOptions.setSelectedIndex(0);
        updateFilterFields();
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Refreshes the table to show all transactions from the portfolio.
    public void refresh() {
        displayTransactions(portfolio.getTransactionManager().getTransactions());
    }

    // MODIFIES: this
    // EFFECTS: Updates the table model with the provided list of transactions.
    private void displayTransactions(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        for (Transaction t : transactions) {
            Object[] row = {
                    t.getDateTime(),
                    t.getSymbol(),
                    t.getAction(),
                    t.getShares(),
                    String.format("%.2f", t.getPrice()),
                    String.format("%.2f", t.getTotal())
            };
            tableModel.addRow(row);
        }
    }
}
