package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents user's overall stock portfolio.
 * The Portfolio maintains a collection of holdings and provides
 * methods for buying, selling, showcase current holdings as well 
 * as calculating the total value of all owned stock holdings.
 */
public class Portfolio implements Writable {
    private String ownerName;
    private double cashBalance;
    private double portfolioValue;
    private LocalDateTime lastUpdated;
    private TransactionManager transactionManager;

    private Map<String, Holding> holdings;

    // EFFECTS: constructs a Portfolio with owner name, cash balance,
    // portfolioValue initilized to 0.0, lastUpdated initialized to
    // LocalDateTime.now(),
    // holdings initialized to HashMap<>() to keep track of key-value pairs String
    // symbol: Holding holding, initialize
    // TransactionManager object
    public Portfolio(String ownerName, double cashBalance, LocalDateTime lastUpdated) {
        this.ownerName = ownerName;
        this.cashBalance = cashBalance;
        this.portfolioValue = 0;
        this.lastUpdated = lastUpdated;
        this.holdings = new HashMap<>();
        this.transactionManager = new TransactionManager();
    }

    // REQUIRES: quantity > 0, total price (stock current price * quantity) <=
    // cashBalance
    // MODIFIES: this, Holding, TransactionManager
    // EFFECTS: buy shares of a stock, update portfolio value and cash balance for
    // owner once action is done.
    public void buyShare(Stock stock, double quantity) {
        if (stock == null)
            return;

        double totalPrice = stock.getCurrentPrice() * quantity;
        if (totalPrice > cashBalance)
            return;

        updateHoldingsForBuy(stock, quantity);

        cashBalance -= totalPrice;
        calculatePortfolioValue();
        lastUpdated = LocalDateTime.now();

        recordBuyTransaction(stock, quantity);
    }

    private void updateHoldingsForBuy(Stock stock, double quantity) {
        String symbol = stock.getSymbol();
        Holding holding = holdings.get(symbol);
        if (holding == null) {
            holding = new Holding(stock, quantity, stock.getCurrentPrice());
            holdings.put(symbol, holding);
        } else {
            holding.buyShare(quantity);
        }
    }

    private void recordBuyTransaction(Stock stock, double quantity) {
        Transaction transaction = new Transaction(stock.getSymbol(), "BUY", quantity, stock.getCurrentPrice(),
                lastUpdated);
        transactionManager.addTransaction(transaction);
    }

    // REQUIRES: symbol != null, quantity > 0, shares > 0, quantity <= current
    // shares for this stock holding
    // MODIFIES: this, Holding, TransactionManager
    // EFFECTS: sell shares of a stock symbol with stock's current price.
    // Update portfolio value and cash balance for owner once action is done.
    public void sellShare(String symbol, double quantity) {
        Holding holding = holdings.get(symbol);
        if (holding == null || quantity > holding.getShares()) {
            return; // no holding exits yet or not enough shares to sell
        }

        double currentPrice = holding.getStock().getCurrentPrice();
        double totalProceeds = currentPrice * quantity;

        holding.sellShare(quantity);
        cashBalance += totalProceeds;

        if (holding.getShares() == 0) {
            holdings.remove(symbol);
        }

        calculatePortfolioValue();
        lastUpdated = LocalDateTime.now();

        // create and record sell transaction
        Transaction transaction = new Transaction(symbol, "SELL", quantity, currentPrice, lastUpdated);
        transactionManager.addTransaction(transaction);

    }

    // MODIFIES: this, Transaction, Holding
    // EFFECTS: recalculates and updates portfolioValue once a successful BUY/SELL
    // action occurs.
    // (shares * current stock price)
    public void calculatePortfolioValue() {
        double total = 0.0;
        for (Holding h : holdings.values()) {
            Stock s = h.getStock();
            double price = 0.0;
            if (s != null) {
                price = s.getCurrentPrice();
            }
            total += price * h.getShares();
        }
        this.portfolioValue = total;
    }

    // getters
    public double getCashBalance() {
        return cashBalance;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public Map<String, Holding> getHoldings() {
        return holdings;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public String getOwner() {
        return ownerName;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    // MODIFIES: this
    // EFFECTS: for each holding, if the market has a matching symbol,
    // rebind to the market's stock and refresh value
    public void reattachHoldingsToMarket(Market market) {
        if (market == null) {
            return;
        }
        for (Holding h : holdings.values()) {
            Stock ms = market.getStock(h.getSymbol());
            if (ms != null) {
                h.setStock(ms);
            }
        }
        calculatePortfolioValue();
    }

    // EFFECTS: returns portfolio information with owner name, cash balance, and
    // portfolio.
    // with the format as below:
    // Owner name: Brad
    // Cash balance: $10000
    // Portfolio Value: $0
    public String getPortfolioInformation() {
        String portfolioInformationString = String.format("Owner Name: %s\nCash Balance: $%.2f\nPortfolio Value: $%.2f",
                ownerName,
                cashBalance,
                portfolioValue);
        return portfolioInformationString;
    }

    // EFFECTS: returns a header as a below format
    // | Symbol | Current Price | Average Price | Shares | Market Value |
    // Profit/Loss |
    public String getHeader() {
        String headerString = String.format("| %s | %s | %s | %s | %s | %s |",
                "Symbol", "Current Price", "Average Price", "Shares", "Market Value", "Profit/Loss");
        return headerString;
    }

    // EFFECTS: Overriding toString() method of Portfolio class
    // with the format as an example below:
    // ================================ Portfolio ==================================
    // Owner name: Brad
    // Cash Balance: $10000
    // | Symbol | Current Price | Average Price | Shares | Market Value |
    // Profit/Loss |
    // | AAPL | $100.00 | $150.00 | 2.00 | $300.00 | +$100.00 |
    @Override
    public String toString() {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("================================ Portfolio ==================================\n");
        contentBuilder.append(getPortfolioInformation()).append("\n");
        contentBuilder.append(getHeader()).append("\n");
        for (Holding h : holdings.values()) {
            contentBuilder.append(h.toString()).append("\n");
        }
        return contentBuilder.toString();
    }

    @Override
    // EFFECTS: turns a portfolio object to a JSON object
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ownerName", getOwner());
        jsonObject.put("cashBalance", getCashBalance());
        jsonObject.put("lastUpdated", getLastUpdated().toString());

        // holdings
        JSONArray holds = new JSONArray();
        for (Holding h : getHoldings().values()) {
            holds.put(h.toJson());
        }
        jsonObject.put("holdings", holds);

        // transactions
        jsonObject.put("transactions", transactionManager.toJsonArray());

        return jsonObject;
    }

    // EFFECTS: builds and returns a Portfolio from its JSON representation
    public static Portfolio fromJson(JSONObject jsonObject) {
        String owner = jsonObject.getString("ownerName");
        double cash = jsonObject.getDouble("cashBalance");
        LocalDateTime lastUpdated = LocalDateTime.parse(jsonObject.getString("lastUpdated"));

        Portfolio p = new Portfolio(owner, cash, lastUpdated);

        // holdings
        JSONArray holds = jsonObject.getJSONArray("holdings");
        if (holds != null) {
            parseHoldings(holds, p);
        }

        // transactions
        JSONArray transactionsArray = jsonObject.getJSONArray("transactions");
        if (transactionsArray != null) {
            parseTransactions(transactionsArray, p);
        }

        p.calculatePortfolioValue();
        return p;
    }

    // MODIFIES: p
    // EFFECTS: parses holdings array and inserts into portfolio by symbol
    private static void parseHoldings(JSONArray holds, Portfolio p) {
        for (int i = 0; i < holds.length(); i++) {
            JSONObject ho = holds.getJSONObject(i);
            Holding holding = Holding.fromJson(ho);
            p.getHoldings().put(holding.getSymbol(), holding);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses transactions array and appends to the portfolio's transaction
    // manager
    private static void parseTransactions(JSONArray transactionsArray, Portfolio p) {
        for (int i = 0; i < transactionsArray.length(); i++) {
            JSONObject to = transactionsArray.getJSONObject(i);
            Transaction t = new Transaction(
                    to.getString("symbol"),
                    to.getString("action"),
                    to.getDouble("shares"),
                    to.getDouble("price"),
                    LocalDateTime.parse(to.getString("dateTime")));
            p.getTransactionManager().addTransaction(t);
        }
    }

    // MODIFIES: this
    // EFFECTS: copy other portfolio to the current portfolio
    public void copyFrom(Portfolio other) {
        if (other == null) {
            return;
        }

        this.ownerName = other.ownerName;
        this.cashBalance = other.cashBalance;

        // Replace holdings
        this.holdings.clear();
        this.holdings.putAll(other.holdings);

        // Replace transaction manager
        this.transactionManager = other.transactionManager;
    }

}
