package ui.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Market;
import model.MarketCatalog;
import model.Portfolio;
import ui.gui.panels.AccountInfoPanel;
import ui.gui.panels.BuyStockPanel;
import ui.gui.panels.MarketPanel;
import ui.gui.panels.PortfolioPanel;
import ui.gui.panels.SaveLoadPanel;
import ui.gui.panels.SellStockPanel;
import ui.gui.panels.TransactionHistoryPanel;

/**
 * The MainFrame contains all the graphical user interface components
 * for the Stock Portfolio Application. It includes the main frame, various
 * panels for different functionalities (market, portfolio, buy/sell, history),
 * and handles user interactions.
 */
public class MainFrame extends JFrame {

    private Portfolio portfolio;
    private Market market;

    private AccountInfoPanel accountInfoPanel;
    private PortfolioPanel portfolioPanel;
    private MarketPanel marketPanel;
    private BuyStockPanel buyStockPanel;
    private SellStockPanel sellStockPanel;
    private TransactionHistoryPanel transactionHistoryPanel;
    private JFrame transactionHistoryFrame;

    // EFFECTS: Constructs the main frame, initializes data, sets up components,
    // and makes the window visible.
    public MainFrame() {
        setTitle("Stock Portfolio Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        initData();
        initComponents();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initialize market and portfolio and parse data to market
    private void initData() {
        market = new Market();
        portfolio = new Portfolio("Brad", 10_000, java.time.LocalDateTime.now());
        MarketCatalog.seed(market);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the layout and adds all panels to the frame.
    private void initComponents() {
        setLayout(new BorderLayout());
        initPanels();
        add(createTopPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Initializes all the sub-panels (portfolio, market, account info,
    // buy/sell, history).
    private void initPanels() {
        portfolioPanel = new PortfolioPanel(portfolio);
        marketPanel = new MarketPanel(market);
        accountInfoPanel = new AccountInfoPanel(portfolio);

        buyStockPanel = new BuyStockPanel(portfolio, market, portfolioPanel, marketPanel, accountInfoPanel);
        sellStockPanel = new SellStockPanel(portfolio, portfolioPanel, marketPanel, accountInfoPanel);
        transactionHistoryPanel = new TransactionHistoryPanel(portfolio);
    }

    // EFFECTS: Creates and returns the top panel containing Market and Portfolio
    // views.
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(marketPanel);
        topPanel.add(portfolioPanel);
        return topPanel;
    }

    // EFFECTS: Creates and returns the bottom panel containing Buy/Sell controls,
    // Account Info, and Save/Load options.
    private JPanel createBottomPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        actionPanel.add(buyStockPanel);
        actionPanel.add(sellStockPanel);

        SaveLoadPanel saveLoadPanel = createSaveLoadPanel();

        JPanel bottomWrapper = new JPanel(new BorderLayout());
        bottomWrapper.add(accountInfoPanel, BorderLayout.WEST);
        bottomWrapper.add(actionPanel, BorderLayout.CENTER);
        bottomWrapper.add(saveLoadPanel, BorderLayout.SOUTH);
        return bottomWrapper;
    }

    // EFFECTS: Creates and returns the SaveLoadPanel with the "View Transaction
    // History" button added.
    private SaveLoadPanel createSaveLoadPanel() {
        SaveLoadPanel saveLoadPanel = new SaveLoadPanel(portfolio, market, portfolioPanel, marketPanel,
                transactionHistoryPanel, accountInfoPanel);

        JButton historyBtn = new JButton("View Transaction History");
        historyBtn.addActionListener(e -> showTransactionHistoryWindow());
        saveLoadPanel.add(historyBtn);
        return saveLoadPanel;
    }

    // MODIFIES: this
    // EFFECTS: Shows the transaction history window. If it doesn't exist, creates
    // it.
    // Refreshes the transaction history panel before showing.
    private void showTransactionHistoryWindow() {
        if (transactionHistoryFrame == null) {
            transactionHistoryFrame = new JFrame("Transaction History");
            transactionHistoryFrame.setSize(900, 500);
            transactionHistoryFrame.setLayout(new BorderLayout());
            transactionHistoryFrame.add(transactionHistoryPanel, BorderLayout.CENTER);
            transactionHistoryFrame.setLocationRelativeTo(this);
        }
        transactionHistoryPanel.refresh(); // Refresh data before showing
        transactionHistoryFrame.setVisible(true);
        transactionHistoryFrame.toFront();
    }
}
