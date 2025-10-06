package model;

/*
 * Represents a single stock in the users's portfolio.
 * A Stock object stores basic information including stock's symbol,
 * company name, sector, number of shared owned, and currentPrice.
 * It provides methods for updating shares and calculating the stock's total market value.
 */
public class Stock {
    private String symbol;
    private String companyName;
    private String sector;
    private double shares;
    private double currentPrice;
    private double averagePurchasePrice;

    // EFFECTS: constructs a Stock with symbol, companyName, sector, shares, and currentPrice
    public Stock (String symbol, String companyName, String sector, double shares, double currentPrice) {
        // stub
    }

    // METHOD OVERLOADING
    // EFFECTS: constructs a Stock with symbol, companyName, sector and currentPrice with shares initialized to 0.
    // This can be considered as a watch stock.
    public Stock (String symbol, String companyName, String sector, double currentPrice) {
        // stub
    }    

    // REQUIRES: quantity > 0, currentPrice > 0
    // MODIFIES: this
    // EFFECTS: allow user to purchase stock shares based on quantity and stock currentPrice.
    public void buyShares(double quantity, double currentPrice) {
        // stub
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this
    // EFFECTS: allow user to sell stock shares based on quantity and stock currentPrice; it is invalid if the shares after sell are negative.
    public void sellShares(double quantity) {
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

    public double getSharesOwned() {
        return 0.0; // stub
    }

    public double getPrice() {
        return 0.0; // stub
    }

    public double getAveragePurchasePrice() {
        return 0.0; // stub
    }

    public double getMarketValue() {
        return 0.0; // stub
    }

    // setters
    public void updateStockPrice(double newPrice) {
        // stub
    }

}
