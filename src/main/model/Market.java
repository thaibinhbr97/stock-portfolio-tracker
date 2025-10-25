package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents a stock market containing available stocks.
 * Internally uses a TreeMap to keep stocks sorted alphabetically by their symbol.
 * This class is the single source of truth for available listings that the UI and Portfolio consult.
 */
public class Market implements Writable {
    private final Map<String, Stock> market;

    // EFFECTS: constructs an empty market
    public Market() {
        market = new TreeMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds or replaces the given stock in the market (keyed by uppercase symbol)
    public void addOrReplace(Stock stock) {
        if (stock == null) {
            return;
        }
        String symbol = stock.getSymbol();
        if (symbol.isEmpty()) {
            return;
        }
        market.put(symbol.toUpperCase(), stock);
    }

    // MODIFIES: this
    // EFFECTS: adds all given stocks (nulls ignored)
    public void addAll(Collection<Stock> stocks) {
        if (stocks == null) {
            return;
        }
        for (Stock s : stocks) {
            addOrReplace(s);
        }
    }

    // EFFECTS: returns the stock with the given symbol, or null if not found
    public Stock getStock(String symbol) {
        if (symbol == null) {
            return null;
        }
        return market.get(symbol.toUpperCase());
    }


    // EFFECTS: returns an unmodifiable view of all stocks in the market (sorted by symbol)
    public Collection<Stock> getAllStocks() {
        return Collections.unmodifiableCollection(market.values());
    }

    // EFFECTS: returns true if the market is empty
    public boolean isEmpty() {
        return market.isEmpty();
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Stock s : getAllStocks()) {
            jsonArray.put(s.toJson());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stocks", jsonArray);
        return jsonObject;
    }

    // EFFECTS: returns a market object from JSON object
    public static Market fromJson(JSONObject jsonObject) {
        Market m = new Market();
        JSONArray jsonArray = jsonObject.getJSONArray("stocks");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                m.addOrReplace(Stock.fromJson(jsonArray.getJSONObject(i)));
            }
        }
        return m;
    }

}
