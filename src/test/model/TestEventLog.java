package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestEventLog {
    private EventLog eventLog;
    private Portfolio portfolio;
    private Stock stock;

    private boolean decreasedFound = false;
    private boolean removedFound = false;
    private boolean transactionFound = false;
    private boolean addedFound = false;
    private boolean increasedFound = false;

    @BeforeEach
    public void setUp() {
        eventLog = EventLog.getInstance();
        eventLog.clear();
        portfolio = new Portfolio("Test Owner", 10000, LocalDateTime.now());
        stock = new Stock("AAPL", "Apple Inc.", "Technology", 150.0);
    }

    @Test
    public void testLogEvent() {
        Event e = new Event("Test Event");
        eventLog.logEvent(e);

        List<Event> events = new ArrayList<>();
        for (Event event : eventLog) {
            events.add(event);
        }

        assertTrue(events.contains(e));
    }

    @Test
    public void testPortfolioAddStockLogging() {
        portfolio.buyShare(stock, 10);

        List<Event> events = new ArrayList<>();
        for (Event event : eventLog) {
            events.add(event);
        }
        for (Event e : events) {
            if (e.getDescription().contains("Added AAPL @ 150.0 to portfolio.")) {
                addedFound = true;
            }
            if (e.getDescription().contains("Increased 10.0 shares of AAPL @ $150.0")) {
                increasedFound = true;
            }
            if (e.getDescription().contains("Recorded transaction: BUY 10.0 shares of AAPL @ $150.0")) {
                transactionFound = true;
            }
        }
        assertTrue(addedFound, "Should log adding stock");
        assertTrue(increasedFound, "Should log increasing shares");
        assertTrue(transactionFound, "Should log transaction");
    }

    @Test
    public void testPortfolioRemoveStockLogging() {
        portfolio.buyShare(stock, 10);
        eventLog.clear(); // Clear logs from setup

        portfolio.sellShare("AAPL", 10);

        List<Event> events = new ArrayList<>();
        for (Event event : eventLog) {
            events.add(event);
        }
        for (Event e : events) {
            String description = e.getDescription();
            if (description.contains("Decreased 10.0 shares of AAPL @ $150.0")) {
                decreasedFound = true;
            }
            if (description.contains("Removed AAPL from portfolio")) {
                removedFound = true;
            }
            if (description.contains("Recorded transaction: SELL 10.0 shares of AAPL @ $150.0")) {
                transactionFound = true;
            }
        }
        assertTrue(decreasedFound, "Should log decreasing shares");
        assertTrue(removedFound, "Should log removing stock");
        assertTrue(transactionFound, "Should log transaction");
    }

    @Test
    public void testUpdateStockPriceLogging() {
        stock.updateCurrentPrice(160.0);

        List<Event> events = new ArrayList<>();
        for (Event event : eventLog) {
            events.add(event);
        }

        boolean updateFound = false;
        for (Event e : events) {
            if (e.getDescription().contains("Updated AAPL price: $150.0 ---> $160.0")) {
                updateFound = true;
            }
        }

        assertTrue(updateFound, "Should log price update");
    }
}
