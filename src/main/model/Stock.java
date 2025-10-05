package model;

/*
 * Represents a single stock in the users's portfolio.
 * A Stock object stores basic information including stock's symbol,
 * company name, sector, number of shared owned, and current price.
 * It provides methods for updating shares and calculating the stock's total market value.
 */
public class Stock {
    private String symbol;
    private String companyName;
    private String sector;
    private double shares;
    private double currentPrice;

    public Stock (String symbol, String companyName, String sector, double shares, double currentPrice) {
        // stub
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this
    // EFFECTS: allow user to purchase stock shares based on quantity and stock price
    public void buyShares(int quantity, double price) {
        // stub
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this
    // EFFECTS: allow user to sell stock shares based on quantity and stock price; however, the shares cannot be negative
    public void sellShares(int quantity, double price) {
        // stub
    }

    // getters
    public String getSymbol() {
        return null; // stub
    }

    public String getCompanyName() {
        return null; // stub
    }

    public String getSector() {
        return null; // stub
    }

    public double getShares() {
        return 0.0; // stub
    }

    public double getCurrentPrice() {
        return 0.0; // stub
    }

    public double getMarketValue() {
        return 0.0; // stub
    }

    // setters
    public void setCurrentPrice(double price) {
        // stub
    }

}
