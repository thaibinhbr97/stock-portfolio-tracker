package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.Holding;
import model.Market;
import model.Portfolio;
import model.Transaction;

public class TestJsonReader extends TestJson {
    @Test
    void testReaderNonExistentFile() {
        try {
            JsonReader reader = new JsonReader("./data/noSuchFile.json");            
            Portfolio portfolio = reader.readPortfolio();
            Market market = reader.readMarket();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPortfolio() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderEmptyPortfolio.json");            
            Portfolio portfolio = reader.readPortfolio();
            assertEquals("Brad", portfolio.getOwner());
            assertEquals(10000, portfolio.getCashBalance());
            assertEquals(FIXED_TIME, portfolio.getLastUpdated());
            assertEquals(0, portfolio.getHoldings().size());
            assertEquals(0, portfolio.getPortfolioValue());
            assertEquals(0, portfolio.getTransactionManager().getTransactions().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPortfolio() {
        try {
            String dest = "./data/testReaderGeneralPortfolio.json";
            Market market = makeMarket();
            Portfolio portfolio = makePortfolioWithTrades(market);

            // write
            JsonWriter writer = new JsonWriter(dest);
            writer.open();
            writer.write(market, portfolio);
            writer.close();

            // read
            JsonReader reader = new JsonReader(dest);
            Portfolio loadedP = reader.readPortfolio();
            Market loadedM = reader.readMarket();    

            assertNotNull(loadedP);
            assertNotNull(loadedM);

            // Market checks
            assertNotNull(loadedM.getStock("AAPL"));
            assertEquals(200.00, loadedM.getStock("AAPL").getCurrentPrice(), EPSILON);
            assertEquals("Apple Inc.", loadedM.getStock("AAPL").getCompanyName());

            // Holdings
            Map<String, Holding> holds = loadedP.getHoldings();
            assertEquals(2, holds.size());
            double aaplAvg = (10*180.0 + 5*200.0)/15.0;
            assertHolding(holds.get("AAPL"), "AAPL", 15.0, aaplAvg, 200.0);
            assertHolding(holds.get("AMZN"), "AMZN", 15.0, 130.0, 130.0);

            // Transactions
            List<Transaction> txs = loadedP.getTransactionManager().getTransactions();
            assertEquals(4, txs.size());
            assertTransaction(txs.get(0), "AAPL", "BUY", 10.0, 180.0);
            assertTransaction(txs.get(1), "AAPL", "BUY", 5.0, 200.0);
            assertTransaction(txs.get(2), "AMZN", "BUY", 20.0, 130.0);
            assertTransaction(txs.get(3), "AMZN", "SELL", 5.0, 130.0);

            // Cash & value
            assertEquals(5250.0, loadedP.getCashBalance(), EPSILON);
            double expectedValue = 15.0 * loadedM.getStock("AAPL").getCurrentPrice()
                                + 15.0 * loadedM.getStock("AMZN").getCurrentPrice();
            assertEquals(expectedValue, loadedP.getPortfolioValue(), EPSILON);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }    
}
