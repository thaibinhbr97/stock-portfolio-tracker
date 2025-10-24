package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.json.JSONObject;

import model.Market;
import model.Portfolio;


// Represents a portfolio that writes JSON representation of portfolio to file
public class JsonWriter {
    private static final int TAB = 2;
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of portfolio to file
    public void write(Market market, Portfolio portfolio) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastUpdated", LocalDateTime.now().toString());        
        jsonObject.put("market", market.toJson());
        jsonObject.put("portfolio", portfolio.toJson());
        saveToFile(jsonObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
