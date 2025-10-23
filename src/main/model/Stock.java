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

    // @Override
    // public JSONObject toJson() {
    //     JSONObject json = new JSONObject();
    //     json.put("name", name);
    //     json.put("thingies", thingiesToJson());
    //     return json;
    // }

    // // EFFECTS: returns things in this workroom as a JSON array
    // private JSONArray thingiesToJson() {
    //     JSONArray jsonArray = new JSONArray();

    //     for (Thingy t : thingies) {
    //         jsonArray.put(t.toJson());
    //     }

    //     return jsonArray;
    // }
    
    @Override
    public JSONObject toJson() {
        return null; // stub
    }

    // EFFECTS: returns a stock object from a JSON object
    public static Stock fromJson(JSONObject o) {
        return null; // stub
    }
}
