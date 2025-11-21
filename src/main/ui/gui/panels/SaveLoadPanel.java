package ui.gui.panels;

import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Market;
import model.Portfolio;
import persistence.JsonReader;
import persistence.JsonWriter;

/**
 * Represents the panel for saving and loading the portfolio data.
 * Provides buttons to save the current state to a JSON file or load from it.
 */
public class SaveLoadPanel extends JPanel {
    private final Portfolio portfolio;
    private final Market market;
    private final PortfolioPanel portfolioPanel;
    private final MarketPanel marketPanel;
    private final TransactionHistoryPanel transactionHistoryPanel;
    private final AccountInfoPanel accountInfoPanel;

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/portfolio.json";

    // EFFECTS: Constructs the SaveLoadPanel with necessary dependencies.
    // Initializes the JSON writer and reader, and sets up the panel layout.
    public SaveLoadPanel(Portfolio portfolio, Market market,
            PortfolioPanel portfolioPanel, MarketPanel marketPanel,
            TransactionHistoryPanel transactionHistoryPanel,
            AccountInfoPanel accountInfoPanel) {
        this.portfolio = portfolio;
        this.market = market;
        this.portfolioPanel = portfolioPanel;
        this.marketPanel = marketPanel;
        this.transactionHistoryPanel = transactionHistoryPanel;
        this.accountInfoPanel = accountInfoPanel;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the layout and adds Save and Load buttons.
    private void setupPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        saveButton.addActionListener(e -> saveData());
        loadButton.addActionListener(e -> loadData());

        add(saveButton);
        add(loadButton);
    }

    // EFFECTS: Saves the market and portfolio data to the JSON file.
    // Shows a success message on completion, or an error message if saving fails.
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(market, portfolio);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Portfolio saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save data to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: portfolio, market
    // EFFECTS: Loads the market and portfolio data from the JSON file.
    // Updates the current portfolio and market with loaded data.
    // Reattaches holdings to market stocks and refreshes all panels.
    // Shows a success message on completion, or an error message if loading fails.
    private void loadData() {
        try {
            Portfolio loadedPortfolio = jsonReader.readPortfolio();
            Market loadedMarket = jsonReader.readMarket();
            if (loadedPortfolio != null) {
                portfolio.copyFrom(loadedPortfolio); // use copyFrom to update the current portfolio
            }
            if (loadedMarket != null) {
                market.copyFrom(loadedMarket);
            }
            portfolio.reattachHoldingsToMarket(market);
            refreshAllPanels();
            JOptionPane.showMessageDialog(this, "Portfolio loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load data from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: portfolioPanel, marketPanel, transactionHistoryPanel,
    // accountInfoPanel
    // EFFECTS: Refreshes all other panels to reflect the loaded data.
    private void refreshAllPanels() {
        portfolioPanel.refresh();
        marketPanel.refresh();
        transactionHistoryPanel.refresh();
        accountInfoPanel.refresh();
    }
}
