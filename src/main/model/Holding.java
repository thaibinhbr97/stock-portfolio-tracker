package model;

import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents how much of a stock you own, including your purchase price.
 * Holding has the ability to buy or sell shares.
 */
public class Holding implements Writable {
    private Stock stock;
    private double shares;
    private double averagePurchasePrice; // cost basis per share
    private String symbol;

    // EFFECTS: Initialized with symbol to be stock symbol, stock, shares, and averagePurchasesPrice
    public Holding(Stock stock, double shares, double averagePurchasePrice) {
        this.stock = stock;
        this.shares = shares;
        this.averagePurchasePrice = averagePurchasePrice;
        this.symbol = stock.getSymbol();
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this
    // EFFECTS: buy quantity shares of a stock, and update average purchase price and shares
    public void buyShare(double quantity) {
        double totalShares = shares + quantity;
        double totalCost = (averagePurchasePrice * shares) + (stock.getCurrentPrice() * quantity);
        averagePurchasePrice = totalCost / totalShares;
        shares = totalShares;
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this
    // EFFECTS: sell quantity shares of a stock, and update shares if quanity <= shares. Otherwise, do nothing.
    public void sellShare(double quantity) {
        if (quantity <= shares) {
            shares = shares - quantity;
        }
    }

    public Stock getStock() {
        return stock;
    }

    public String getSymbol() {
        return symbol;
    }

    // EFFECTS: get total shares of this stock holding
    public double getShares() {
        return shares;
    }

    // EFFECTS: get an average price of this holding
    public double getAveragePrice() {
        return averagePurchasePrice;
    }

    // EFFECTS: get the market value of a stock in a holding
    public double getMarketValue() {
        return stock.getCurrentPrice() * shares;
    }

    // MODIFIES: this
    // EFFECTS: get unrealized profit/loss of a holding (profit/loss based on current stock's price)
    public double getUnrealizedProfit() {
        return (stock.getCurrentPrice() - averagePurchasePrice) * shares;
    }

    // setters

    // MODIFIES: this
    // EFFECTS: update the average price to be the new average purchase price
    public void updateAveragePrice(double newAveragePurchasePrice) {
        averagePurchasePrice = newAveragePurchasePrice;
    }
    
    // EFFECTS: Overriding toString() method of Holding class as example below
    // | Symbol | CurrentPrice | AveragePrice | Shares | MarketValue| Gain/Loss | 
    @Override
    public String toString() {
        double profit = getUnrealizedProfit();
        String profitString = "";
        if (profit > 0) {
            profitString += "+";
        } else if (profit < 0) {
            profitString += "-";            
        }
        profitString += String.format("$%.2f", Math.abs(profit));
        String holdingString = String.format("| %s | $%.2f | $%.2f | %.2f | $%.2f | %s |", 
                getSymbol(), stock.getCurrentPrice(), getAveragePrice(), getShares(), getMarketValue(), profitString);
        return holdingString;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stock", stock.toJson());
        jsonObject.put("shares", getShares());
        jsonObject.put("averagePurchasePrice", getAveragePrice());
        jsonObject.put("symbol",getSymbol());
        return jsonObject;
    }

    // EFFECTS: returns a holding object from a JSON object
    public static Holding fromJson(JSONObject jsonObject) {
        Stock s = Stock.fromJson(jsonObject.getJSONObject("stock"));
        double shares = jsonObject.getDouble("shares");
        double avg = jsonObject.getDouble("averagePurchasePrice");
        return new Holding(s, shares, avg);
    }    
}
