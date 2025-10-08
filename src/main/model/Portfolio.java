package model;

import java.time.LocalDateTime;
import java.util.Map;

/*
 * Represents auser's overall stock portfolio.
 * The Portfolio maintains a collection of holdings and provides
 * methods for buying, selling, showcase  as well as calculating the total value of all owned stocks.
 */
public class Portfolio { 
    private String ownerName;
    private double cashBalance;
    private double portfolioValue;
    private LocalDateTime lastUpdated;

        private Map<String, Holding> holdings;

    
    // EFFECTS: constructs a Portfolio with holdings, watch, lastUpdated.
    public Portfolio(String ownerName, double cashBalance,  double totalValue, LocalDateTime lastUpdated) {
        // stub
    }

    // // EFFECTS: returns Stock with given symbol, or null if not found.
    // public Stock getStock(String symbol) {
    //     return null; // stub
    // }

    // REQUIRES: shares and price >= 0
    // MODIFIES: this, Transaction
    // EFFECTS: buy shares of stock with price. 
    public void buyShare(Stock stock, double shares, double price) {
        // stub
    }
    
    // REQUIRES: shares and price >= 0
    // MODIFIES: this
    // EFFECTS: sell shares of stock with price. 
    public void sellShare(Stock stock, double shares, double price) {
        // stub
    }

    // MODIFIES: this, Holding
    // EFFECTS: recalculates and updates portfolioValue.
    // (shares * current price)
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

    // EFFECTS: display owner information in the console
    // with the format as below:
    // This portfolio owned by ownerName with total value $ and format it to 2 decimal places
    public String showOwnerInformation() {
        // return "This portfolio owned by " + ownerName + " with total value $" 
        //     + String.format("%.2f", totalValue);
        return null; // stub
    }

    // EFFECTS: display portfolio information in the console
    // with the format as an example below:
    // ================================ Porfolio ==================================
    // Owner name: 
    // Cash Balance: $10000
    // | Symbol | CurrentPrice | AveragePrice | Shares | MarketValue | Profit/Loss | 
    // | AAPL | $100 | $150 | 2.0 | $300.00 | +$100 |
    // | AMZN | $200 | $400 | 1.0 | $300.00 | +$200 |
    public String showPortfolio() {
        return null;
    }
}
