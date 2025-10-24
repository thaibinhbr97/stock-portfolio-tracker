package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Holding;
import model.Market;
import model.Portfolio;
import model.Stock;
import model.Transaction;

// Represents a reader that reads portfolio from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads portfolio as loaded from file
    // throws IOException if an error occurs reading data from file
    public Portfolio readPortfolio() throws IOException {
        JSONObject jsonObject = readFile(source);
        Portfolio portfolio = Portfolio.fromJson(jsonObject.getJSONObject("portfolio"));
        
        return portfolio;
    }   

    // EFFECTS: reads market as loaded from file
    // throws IOException if an error occurs reading data from file
    public Market readMarket() throws IOException {
        JSONObject jsonObject = readFile(source);
        Market market = Market.fromJson(jsonObject.getJSONObject("market"));

        return market;
    }   
  
    // EFFECTS: reads source file as string and returns it
    private JSONObject readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return new JSONObject(contentBuilder.toString());
    }

    // EFFECTS: parses portfolio from JSON object and returns it
    private Portfolio parsePortfolio(JSONObject obj) {
        String owner = obj.getString("ownerName");
        double cash = obj.getDouble("cashBalance");
        LocalDateTime lastUpdated = LocalDateTime.parse(obj.getString("lastUpdated"));

        Portfolio p = new Portfolio(owner, cash, lastUpdated);

        // holdings
        addHoldings(obj.getJSONArray("holdings"), p);

        // transactions (into portfolio's TransactionManager)
        if (obj.has("transactions")) {
            addTransactions(obj.getJSONArray("transactions"), p);
        }

        // ensure correct portfolio totals
        p.calculatePortfolioValue();
        return p;
    }    

    // MODIFIES: portfolio
    // EFFECTS: parses holdings from JSON array and adds it to portfolio 
    private void addHoldings(JSONArray arr, Portfolio p) {
        for (int i = 0; i < arr.length(); i++) {
            addHolding(arr.getJSONObject(i), p);
        }
    }    

    // MODIFIES: portfolio
    // EFFECTS: parses holding from JSON object and adds it to portfolio    
    private void addHolding(JSONObject obj, Portfolio p) {
        JSONObject s = obj.getJSONObject("stock");
        Stock stock = new Stock(
            s.getString("symbol"),
            s.getString("companyName"),
            s.getString("sector"),
            s.getDouble("currentPrice")
        );

        double shares = obj.getDouble("shares");
        double avg = obj.getDouble("averagePurchasePrice");

        Holding h = new Holding(stock, shares, avg);

        p.getHoldings().put(h.getSymbol(), h);
    }

    // MODIFIES: portfolio    
    // EFFECTS: parses transactions from JSON object and adds it to portfolio    
    private void addTransactions(JSONArray arr, Portfolio p) {
        for (int i = 0; i < arr.length(); i++) {
            addTransaction(arr.getJSONObject(i), p);
        }
    }    


    // MODIFIES: portfolio
    // EFFECTS: parses transaction from JSON object and adds it to portfolio 
    private void addTransaction(JSONObject jsonObject, Portfolio p) {
        Transaction t = new Transaction(
            jsonObject.getString("symbol"),
            jsonObject.getString("action"),
            jsonObject.getDouble("shares"),
            jsonObject.getDouble("price"),
            LocalDateTime.parse(jsonObject.getString("dateTime"))                        
        );
        p.getTransactionManager().addTransaction(t);
    }
}
