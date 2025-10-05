package service;

import model.Portfolio;

/**
 * The PortfolioManager class coordinates all operations related to portfolio management.
 * It acts as a controller between the Portfolio (which holds Stock data)
 * and the TransactionManager (which records buy/sell history).
 * 
 * Responsibilities:
 * - Execute buy/sell operations
 * - Update stock prices
 * - Maintain consistency between Portfolio and Transaction history
 * - Provide access to the current Portfolio and its TransactionManager
 */
public class PortfolioManager {
    private Portfolio portfolio;
    private TransactionManager transactionManager;

    public PortfolioManager() {
        // stub
    }

    // MODIFIES: Stock, Portfolio, TransactionManager
    // EFFECTS: purchases a stock given symbol, a quantity of shares and current price of that stock if it exists in our portfolio
    // . Otherwise, create a new Stock object, add it to our portfolio, and buyStock with quantity and price.
    // Add a buy record in TransactionManager.
    public void buyStock(String symbol, double quantity, double price) {
        // stub
    }

    // MODIFIES: Stock, Portfolio, TransactionManager
    // EFFECTS: sells a stock given symbol, quantity, price. Quantity cannot exceed current amount of shares in Stock.
    // Add a sell record in TransactionManager.
    public void sellStock(String symbol, double quantity, double price) {
        // stub
    }

    // MODIFIES: Portfolio, Stock
    // EFFECTS: updates stock price given stock symbol.
    public void updateStockPrice(String symbol, double newPrice) {
        // stub
    }
}
