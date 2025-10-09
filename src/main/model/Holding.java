package model;

/*
 * Represents how much of a stock you own, including your purchase price.
 * Holding has the ability to buy or sell shares.
 */
public class Holding {
    private Stock stock;
    private double shares;
    private double purchasePrice; // cost basis per share

    // initialize symbol to be stock symbol
    public Holding(Stock stock, double shares) {
        
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this, Portfolio, Transaction, TransactionManager
    // EFFECTS: allow user to purchase stock shares based on quantity and current stock's price.
    public void buyShare(double quantity) {
        // stub
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this, Portfolio, Transaction, TransactionManager
    // EFFECTS: allow user to sell stock shares based on quantity. Quantity cannot be greater than shares.
    public void sellShare(double quantity) {
        // stub
    }

    public Stock getStock() {
        return null; // stub
    }

    public String getSymbol() {
        return null; // stub
    }

    // EFFECTS: get total shares of this stock holding
    public double getShares() {
        return 0.0; // stub
    }

    // EFFECTS: get an average price of this holding
    public double getAveragePrice() {
        return 0.0;
    }

    // EFFECTS: get the market value of a stock in a holding
    public double getMarketValue() {
        return 0.0; // stub
    }

    // EFFECTS: get unrealized profit/loss of a holding (profit/loss based on current stock's price)
    public double getUnrealizedProfit() {
        return 0.0; // stub
    }

    // EFFECTS: Overriding toString() method of Holding class as below
    // | Symbol | CurrentPrice | AveragePrice | Shares | MarketValue | Gain/Loss |  
    @Override
    public String toString() {
        return null;
    }
}
