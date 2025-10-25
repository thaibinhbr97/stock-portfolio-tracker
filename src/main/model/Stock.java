package model;

import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents a single stock in user's holding.
 * A Stock object stores basic information including stock's symbol,
 * company name, sector, number of shared owned, and currentPrice.
 * It provides methods for updating shares and calculating the stock's total market value.
 */
public class Stock implements Writable {
    private String symbol;
    private String companyName;
    private String sector;
    private double currentPrice;

    // EFFECTS: constructs a Stock with symbol, companyName, sector, shares, currentPrice
    public Stock(String symbol, String companyName, String sector, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.currentPrice = currentPrice;
    }

    // getters
    public String getSymbol() {
        return symbol.toUpperCase();
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
    // MODIFIES: this
    // EFFECTS: update current price to be a new price
    public void updateCurrentPrice(double newPrice) {
        this.currentPrice = newPrice;
    }

    // EFFECTS: Overriding toString() method of Stock class as example below
    // | Symbol | CompanyName | Sector | CurrentPrice |    
    @Override
    public String toString() {
        String stockString = String.format("| %s | %s | %s | $%.2f |", 
                symbol, companyName, sector, currentPrice);
        return stockString;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("symbol", getSymbol());
        jsonObject.put("companyName", getCompanyName());
        jsonObject.put("sector", getSymbol());
        jsonObject.put("currentPrice", getCurrentPrice());
        return jsonObject;

    }

    // EFFECTS: returns a stock object from a JSON object
    public static Stock fromJson(JSONObject jsonObject) {
        return new Stock(
            jsonObject.getString("symbol"), 
            jsonObject.getString("companyName"), 
            jsonObject.getString("sector"), 
            jsonObject.getDouble("currentPrice")
        ); 
    }
}
