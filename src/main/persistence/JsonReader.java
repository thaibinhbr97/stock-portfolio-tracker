package persistence;

import java.io.IOException;

import org.json.JSONObject;

import model.Portfolio;

// Represents a reader that reads portfolio from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads Portfolio from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Portfolio read() throws IOException {
        return null; // stub
    }
`
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null; // stub
    }

    // EFFECTS: parses portfolio from JSON object and returns it
    private Portfolio parsePortfolio(JSONObject jsonObject) {
        return null; // stub
    }

    // MODIFIES: portfolio    
    // EFFECTS: parses stock from JSON object and adds it to portfolio
    private void addStock(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: portfolio
    // EFFECTS: parses holding from JSON object and adds it to portfolio    
    private void addHolding(JSONObject jsonObject) {
         // stub
    }

    // MODIFIES: portfolio
    // EFFECTS: parses holdings from JSON object and adds it to portfolio 
    private void addHoldings(JSONObject jsonObject) {
         // stub
    }

    // MODIFIES: portfolio
    // EFFECTS: parses transaction from JSON object and adds it to portfolio 
    private void addTransaction(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: portfolio    
    // EFFECTS: parses transactions from JSON object and adds it to portfolio    
    private void addTransactions(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: portfolio    
    // EFFECTS: parses market from JSON object and adds it to portfolio     
    private void addMarket(JSONObject jsonObject) {
        // stub
    }
}
