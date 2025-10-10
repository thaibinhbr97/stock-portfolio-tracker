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
    public Stock(String symbol, String companyName, String sector, double currentPrice) {
        this.symbol = symbol.toUpperCase();
        this.companyName = companyName;
        this.sector = sector;
        this.currentPrice = currentPrice;
    }

    // getters
    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSector() {
        return sector;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    // setters
    public void updateCurrentPrice(double newPrice) {
        if (newPrice < 0) {
            return;
        }
        this.currentPrice = newPrice;
    }

    // EFFECTS: Overriding toString() method of Stock class as example below
    // | Symbol |          CompanyName          |          Sector          |          CurrentPrice       |    
    @Override
    public String toString() {
        String stockString = String.format("| %s | %s | %s | $%.2f |", 
                symbol, companyName, sector, currentPrice);
        return stockString;
    }
}
