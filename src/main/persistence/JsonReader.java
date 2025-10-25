package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONObject;

import model.Market;
import model.Portfolio;

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
}
