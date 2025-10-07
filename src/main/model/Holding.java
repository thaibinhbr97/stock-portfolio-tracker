package model;

/*
 * Represents how much of a stock you own, including your average purchase price.
 * Holding has the ability to buy or sell shares.
 */
public class Holding {
    private Stock stock;
    private double shares;
    private double averagePrice; // cost basis per share

    public Holding(Stock stock, double shares, double averagePrice) {
        
    }

    // REQUIRES: quantity > 0, currentPrice > 0
    // MODIFIES: this, Portfolio, Transaction
    // EFFECTS: allow user to purchase stock shares based on quantity and stock price.
    public void buyShare(double quantity, double price) {
        // stub
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this, Portfolio, Transaction
    // EFFECTS: allow user to sell stock shares based on quantity. Quantity cannot be greater than shares.
    public void sellShare(double quantity) {
        // stub
    }

    // EFFECTS: get the market value of a stock in a holding
    public double getMarketValue() {
        return 0.0; // stub
    }

    // EFFECTS: get unrealized profile of a holding (profit/loss based on current stock's price)
    public double getUnrealizedProfit() {
        return 0.0; // stub
    }

    // EFFECTS: print out Holding object with the format below
    // | Symbol | PurchasePrice | AveragePrice | Shares | MarketValue | Gain/Loss |     
    public void printHolding() {

    }
}
