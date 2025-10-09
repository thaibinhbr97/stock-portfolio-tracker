package model;

import java.time.LocalDateTime;
import java.util.Map;

/*
 * Represents user's overall stock portfolio.
 * The Portfolio maintains a collection of holdings and provides
 * methods for buying, selling, showcase  as well as calculating the total value of all owned stocks.
 */
public class Portfolio { 
    private static String ownerName;
    private static double cashBalance;
    private static double portfolioValue;
    private static LocalDateTime lastUpdated;

    private static Map<String, Holding> holdings;

    
    // EFFECTS: constructs a Portfolio with owner name, cash balance, 
    // portfolioValue initilized to 0.0, lastUpdated initialized to LocalDateTime.now(), 
    // holdings initialized to HashMap<>() to keep track of key-value pairs String symbol: Holding holding
    public Portfolio(String ownerName, double cashBalance, LocalDateTime lastUpdated) {
        // stub
    }

    // REQUIRES: shares > 0, total price (stock current price * shares) <= cashBalance
    // MODIFIES: this, Transaction, Holding, TransactionManager
    // EFFECTS: buy shares of a stock symbol with stock's current price.
    // Update portfolio value and cash balance for owner once action is done.
    public void buyShare(String symbol, double shares) {
        // stub
    }

    // REQUIRES: shares > 0, shares <= shares in this stock holding
    // MODIFIES: this, Transaction, Holding, TransactionManager
    // EFFECTS: sell shares of a stock symbol with stock's current price. 
    // Update portfolio value and cash balance for owner once action is done.
    public void sellShare(String symbol, double shares) {
        // stub
    }

    // MODIFIES: this, Transaction, Holding
    // EFFECTS: recalculates and updates portfolioValue once a successful BUY/SELL action occurs.
    // (shares * current stock price)
    public void calculatePortfolioValue() {
        // stub
    }

    // getters 
    public double getCashBalance() {
        return 0.0; // stub
    }

    public double getPortfolioValue() {
        return 0.0; // stub
    }

    public Map<String, Holding> getHoldings() {
        return null; // stub
    }

    public LocalDateTime getLastUpdated() {
        return null; // stub
    }
    
    public String getOwner() {
        return null; // stub
    }

    // EFFECTS: returns owner information
    // with the format as below:
    // This portfolio owned by ownerName with total value $ and format it to 2 decimal places
    public String getOwnerInformation() {
        // return "This portfolio owned by " + ownerName + " with total value $" 
        //     + String.format("%.2f", totalValue);
        return null; // stub
    }

    // EFFECTS: Overriding toString() method of Portfolio class
    // with the format as an example below:
    // ================================ Porfolio ==================================
    // Owner name: Brad
    // Cash Balance: $10000
    // | Symbol | CurrentPrice | AveragePrice | Shares | MarketValue | Profit/Loss |
    // | AAPL | $100 | $150 | 2.0 | $300.00 | +$100 |
    @Override
    public String toString() {
        return null;
    }
}
