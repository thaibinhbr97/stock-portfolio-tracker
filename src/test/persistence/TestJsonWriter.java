package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Market;
import model.Portfolio;

@ExcludeFromJacocoGeneratedReport
public class TestJsonWriter extends TestJson {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");            
            writer.open();
            fail("IOException was expected");            
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPortfolio() {
        try {
            String dest = "./data/testWriterEmptyPortfolio.json";            
            Market market = new Market();
            Portfolio portfolio = makeEmptyPortfolio();

            JsonWriter writer = new JsonWriter(dest);
            writer.open();
            writer.write(market, portfolio);
            writer.close();

            String content = Files.readString(Path.of(dest));
            JSONObject root = new JSONObject(content);
            assertTrue(root.has("lastUpdated"));
            assertTrue(root.has("market"));
            assertTrue(root.has("portfolio"));

            JSONArray stocks = root.getJSONObject("market").getJSONArray("stocks");
            assertEquals(0, stocks.length());
            JSONArray holdings = root.getJSONObject("portfolio").getJSONArray("holdings");
            assertEquals(0, holdings.length());
            JSONArray transactions = root.getJSONObject("portfolio").getJSONArray("transactions");
            assertEquals(0, transactions.length());
        } catch (IOException e) {
            fail("Couldn't write to file");
        }
    }

    @Test
    void testWriterGeneralPortfolio() {
        try {
            String dest = "./data/testWriterGeneralPortfolio.json";
            Market m = makeMarket();
            Portfolio p = makePortfolioWithTrades(m);

            JsonWriter writer = new JsonWriter(dest);
            writer.open();
            writer.write(m, p);
            writer.close();

            String content = Files.readString(Path.of(dest));
            JSONObject root = new JSONObject(content);

            JSONArray stocks = root.getJSONObject("market").getJSONArray("stocks");
            assertTrue(stocks.length() >= 3);

            JSONObject portfolio = root.getJSONObject("portfolio");
            assertEquals("Brad", portfolio.getString("ownerName"));
            JSONArray transactions = portfolio.getJSONArray("transactions");
            assertEquals(4, transactions.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }    
}
