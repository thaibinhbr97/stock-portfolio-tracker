package model;

/*
 * Represents a single stock in user's holding.
 * A Stock object stores basic information including stock's symbol,
 * company name, sector, number of shared owned, and currentPrice.
 * It provides methods for updating shares and calculating the stock's total market value.
 */
public class Stock {
    private String symbol;
    private String companyName;
    private String sector;
    private double currentPrice;

    // EFFECTS: constructs a Stock with symbol, companyName, sector, shares, currentPrice
    public Stock (String symbol, String companyName, String sector, double currentPrice) {
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

    public double getCurrentPrice() {
        return 0.0; // stub
    }

    // setters
    public void updateStockPrice(double newPrice) {
        // stub
    }

    // EFFECTS: print out Stock object with the format below:
    // | Symbol | CompanyName | Sector | CurrentPrice
    public printStock() {
        
    }
}
