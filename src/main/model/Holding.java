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
    private double averagePurchasePrice;
    private String symbol;

    // EFFECTS: initializes a holding bound to a market stock
    public Holding(Stock stock, double shares, double averagePurchasePrice) {
        this.stock = stock;
        if (stock != null) {
            this.symbol = stock.getSymbol();
        } else {
            stock = null;
        }
        this.shares = shares;
        this.averagePurchasePrice = averagePurchasePrice;
    }

    // EFFECTS: initializes a detached holding with only a symbol (stock can be reattached later)
    public Holding(String symbol, double shares, double averagePurchasePrice) {
        this.symbol = symbol;
        this.shares = shares;
        this.averagePurchasePrice = averagePurchasePrice;
        this.stock = null;
    }

    // REQUIRES: quantity > 0
    // MODIFIES: this
    // EFFECTS: buy quantity shares of a stock, and update average purchase price and shares
    public void buyShare(double quantity) {
        double price;
        if (stock != null) {
            price = stock.getCurrentPrice();
        } else {
            price = averagePurchasePrice;
        }
        double totalCostBefore = averagePurchasePrice * shares;
        double totalCostAfter = totalCostBefore + price * quantity;
        shares += quantity;
        averagePurchasePrice = totalCostAfter / shares;
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
        double price = 0.0;
        if (stock != null) {
            price = stock.getCurrentPrice();
        }
        return price * shares;
    }

    // MODIFIES: this
    // EFFECTS: get unrealized profit/loss of a holding (profit/loss based on current stock's price)
    public double getUnrealizedProfit() {
        double price = 0.0;
        if (stock != null) {
            price = stock.getCurrentPrice();
        }
        return (price - averagePurchasePrice) * shares;
    }

    // MODIFIES: this
    // EFFECTS: update the average price to be the new average purchase price
    public void updateAveragePrice(double newAveragePurchasePrice) {
        averagePurchasePrice = newAveragePurchasePrice;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
        if (stock != null) {
            this.symbol = stock.getSymbol();
        }
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
        double currentPrice = 0.0;
        if (stock != null) {
            currentPrice = stock.getCurrentPrice();
        }
        String holdingString = String.format("| %s | $%.2f | $%.2f | %.2f | $%.2f | %s |", 
                getSymbol(), currentPrice, getAveragePrice(), getShares(), getMarketValue(), profitString);
        return holdingString;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("shares", getShares());
        jsonObject.put("averagePurchasePrice", getAveragePrice());
        jsonObject.put("symbol",getSymbol());
        return jsonObject;
    }

    // EFFECTS: returns a holding object from a JSON object
    public static Holding fromJson(JSONObject jsonObject) {
        String symbol = jsonObject.getString("symbol");
        double shares = jsonObject.getDouble("shares");
        double avg = jsonObject.getDouble("averagePurchasePrice");
        // Detached holding; will be reattached to Market's stock later        
        return new Holding(symbol, shares, avg);
    }    
}
