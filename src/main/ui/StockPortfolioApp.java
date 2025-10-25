package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

import model.Holding;
import model.Market;
import model.MarketCatalog;
import model.Portfolio;
import model.Stock;
import model.Transaction;
import model.TransactionManager;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represents Stock Portfolio application
public class StockPortfolioApp {
    private static final String JSON_STORE = "./data/portfolio.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    
    private Market market;
    private Portfolio portfolio;
    private Scanner input;
    

    public StockPortfolioApp() {
        runStockPortfolioApp();
    }

    public void runStockPortfolioApp() {
        
        try {
            init();
            System.out.println("\nWelcome to the Stock Portfolio Application!\n");
            boolean keepGoing = true;
            while (keepGoing) {
                displayMenu();
                String command = readLine("Select an option: ");
                keepGoing = processCommand(command);
            }
            System.out.println("Goodbye!");
            if (input != null) {
                input.close();
            }                
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file at " + JSON_STORE);
        }    
        
    }

    // EFFECTS: initialize portfolio
    public void init() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        input.useDelimiter("\r?\n|\r");
        
        // create demo portfolio
        portfolio = new Portfolio("Brad", 10_000.00, LocalDateTime.now());
        market = new Market();
        MarketCatalog.seed(market);
    }

    // EFFECTS: print market by listing each stock to the console
    public void printMarket() {
        for (Stock stock: market.getAllStocks()) {
            System.out.printf("| %s | %s | %s | $%.2f |\n",
                    stock.getSymbol(), stock.getCompanyName(), stock.getSector(), stock.getCurrentPrice());   
        }
    }

    // EFFECTS: displays main menu by listing all options for this app
    private void displayMenu() {
        System.out.println("\n================== Main Menu ==================");
        System.out.println("1: View Portfolio");
        System.out.println("2: Buy Stock");
        System.out.println("3: Sell Stock");
        System.out.println("4: View Stock Market");
        System.out.println("5: Update Stock Price");
        System.out.println("6: View Transaction History");
        System.out.println("7: Filter Transactions");
        System.out.println("8: Save to File");
        System.out.println("9: Load from File");
        System.out.println("q: Quit");
        System.out.println("===============================================");
    }    
    
    // MODIFIES: this
    // EFFECTS: process user command
    @SuppressWarnings("methodlength")
    private boolean processCommand(String command) {
        switch (command) {
            case "1":
                viewPortfolio();
                break;
            case "2":
                buyStock();
                break;
            case "3":
                sellStock();
                break;
            case "4":
                viewStockMarket();
                break;
            case "5":
                updateStockPrice();
                break;
            case "6":
                viewTransactionHistory();
                break;
            case "7":
                filterTransactions();
                break;
            case "8":
                saveStockPortfolioApp();
                break;
            case "9":
                loadStockPortfolioApp();
                break;                
            case "q":
                return false;
            default:
                System.out.println("Invalid option. Please choose 1-9 or q to quit.");
        }  
        return true;      
    }


    // Main menu actions

    // EFFECTS: view portfolio
    private void viewPortfolio() {
        portfolio.calculatePortfolioValue();
        System.out.println();
        System.out.println(portfolio.toString());
    }

    // EFFECTS: buy stock
    private void buyStock() {
        Stock stock = resolveOrCreateStockForBuy();
        if (stock == null) {
            return;
        }
        updatePriceOptionally(stock, "buying");

        double quantity = readPositiveDouble("Quantity to buy: ");
        double total = quantity * stock.getCurrentPrice();
        if (!isFundSufficientToBuy(total)) {
            return;
        }

        double beforeCash = portfolio.getCashBalance();
        portfolio.buyShare(stock, quantity);              
        System.out.printf(
                "Bought %.2f of %s @ $%.2f (Cost: $%.2f). Cash: $%.2f → $%.2f%n",
                quantity, stock.getSymbol(), stock.getCurrentPrice(), total, beforeCash, portfolio.getCashBalance()
        );
    }

    // EFFECTS: sell stock
    private void sellStock() {
        viewPortfolio();
        Holding holding = resolveHoldingForSell();
        if (holding == null) {
            return;
        }

        // optional price update
        updatePriceOptionally(holding.getStock(), "selling");

        double quantity = readPositiveDouble("Quantity to sell: ");
        if (!hasEnoughShares(holding, quantity)) {
            return;
        }

        double beforeCash = portfolio.getCashBalance();
        String symbol = holding.getSymbol();
        portfolio.sellShare(symbol, quantity);
        System.out.printf("Sold %.2f of %s. Cash: $%.2f → $%.2f%n",
                quantity, symbol, beforeCash, portfolio.getCashBalance());
    }

    // EFFECTS: prompts for a symbol and returns the user's Holding if present
    // . Otherwise, prints a message and returns null
    private Holding resolveHoldingForSell() {
        String symbol = readSymbol("Enter stock symbol to SELL: ").toUpperCase(Locale.ROOT);
        Holding holding = portfolio.getHoldings().get(symbol);
        if (holding == null) {
            System.out.println("You don't hold that stock symbol.");
            return null;
        }
        System.out.printf("You hold %.2f of %s. Current price $%.2f, Avg price $%.2f%n",
                holding.getShares(), symbol, holding.getStock().getCurrentPrice(), holding.getAveragePrice());
        return holding;        
    }

    // EFFECTS: returns true if the holding has at least qty shares; otherwise prints an error
    private boolean hasEnoughShares(Holding holding, double quantity) {
        if (quantity <= holding.getShares()) {
            return true;
        }
        System.out.printf("Cannot sell %.2f; only %.2f held.%n", quantity, holding.getShares());
        return false;
    }

    private void viewStockMarket() {
        if (market.isEmpty()) {
            System.out.println("(No market stocks available)");
            return;
        }
        System.out.println("\n| Symbol | Company | Sector | Price |");
        for (Stock stock : market.getAllStocks()) {
            System.out.printf("| %s | %s | %s | $%.2f |%n",
                    stock.getSymbol(), stock.getCompanyName(), stock.getSector(), stock.getCurrentPrice());            
        }
    }

    // EFFECTS: update stock market price
    private void updateStockPrice() {
        String symbol = readSymbol("Enter stock symbol to update price: ").toUpperCase(Locale.ROOT);
        Stock stock = market.getStock(symbol);
        if (stock == null) {
            System.out.println("Unknown symbol in market.");
            return;
        }
        double newPrice = readPositiveDouble("New price: $");
        stock.updateCurrentPrice(newPrice);
        portfolio.calculatePortfolioValue();
        System.out.printf("%s price updated to $%.2f%n", symbol, stock.getCurrentPrice());
    }    

    // EFFECTS: view transactions history
    private void viewTransactionHistory() {
        TransactionManager transactionManager = portfolio.getTransactionManager();
        if (transactionManager.getTransactions().isEmpty()) {
            System.out.println("(No transactions yet)");
            return;
        }
        System.out.println();
        System.out.println(transactionManager.toString());
    }    

    // EFFECTS: filterTransaction based on user commands
    private void filterTransactions() {
        TransactionManager transactionManager = portfolio.getTransactionManager();
        if (transactionManager.getTransactions().isEmpty()) {
            System.out.println("(No transactions to filter)");
            return;
        }
        System.out.println("Filter by: 1) Action (BUY/SELL)  2) Symbol  3) Date");
        String option = readLine("Choose: ").trim();
        switch (option) {
            case "1": {
                filterByAction(transactionManager);
                break;
            }
            case "2": {
                filterBySymbol(transactionManager);
                break;
            }
            case "3": {
                filterByDate(transactionManager);
                break;
            }
            default:
                // back or invalid
                break;
        }
    }    

    // EFFECTS: prompts for symbol; returns existing market stock,
    // or (if not found) optionally creates a new one.
    // Returns null if user declines creation.
    private Stock resolveOrCreateStockForBuy() {
        String symbol = readSymbol("Enter stock symbol to buy: ").toUpperCase(Locale.ROOT);
        Stock stock = market.getStock(symbol);
        if (stock != null) {
            return stock;
        }

        System.out.printf("Symbol '%s' not found in market.%n", symbol);
        if (!isYesNo("Create it now? (y/n): ")) {
            return null;
        }
        String company = readLine("Company name: ");
        String sector = readLine("Sector: ");
        double price = readPositiveDouble("Current price: $");
        stock = new Stock(symbol, company, sector, price);
        market.addOrReplace(stock);
        return stock;
    }

    // MODIFIES: stock
    // EFFECTS: optionally updates current price before an action (e.g., buying/selling)
    private void updatePriceOptionally(Stock stock, String action) {
        System.out.printf("Current price for %s is $%.2f%n", stock.getSymbol(), stock.getCurrentPrice());
        if (isYesNo("Update the price before " + action + "? (y/n): ")) {
            double newPrice = readPositiveDouble("New price: $");
            stock.updateCurrentPrice(newPrice);
            System.out.println(stock.getCurrentPrice());            
        }
    }
    
    // EFFECTS: ensure sufficient fund from cash balance before buying a stock
    private boolean isFundSufficientToBuy(double total) {
        double cash = portfolio.getCashBalance();
        if (total <= cash) {
            return true;
        }
        System.out.printf("Insufficient cash. Need $%.2f, have $%.2f%n", total, cash);
        return false;        
    }

    // EFFECTS: filter transactions by action (BUY/SELL)
    private void filterByAction(TransactionManager transactionManager) {
        String action = readLine("Enter action (BUY/SELL): ").trim();
        System.out.println(transactionManager.getHeader());
        for (Transaction transaction : transactionManager.filterByAction(action)) {
            System.out.println(transaction.toString());
        }
    }

    // EFFECTS: filter transactions by stock symbol
    private void filterBySymbol(TransactionManager transactionManager) {
        String symbol = readSymbol("Enter symbol: ").toUpperCase(Locale.ROOT);
        System.out.println(transactionManager.getHeader());
        for (Transaction transaction : transactionManager.filterBySymbol(symbol)) {
            System.out.println(transaction.toString());
        }
    }

    // EFFECTS: filter transactions by date start and date end
    private void filterByDate(TransactionManager transactionManager) {
        String startInput = readLine("Enter start date (YYYY-MM-DD): ");
        String endInput = readLine("Enter end date (YYYY-MM-DD): ");
        try {
            java.time.LocalDate startDate = java.time.LocalDate.parse(startInput);
            java.time.LocalDate endDate = java.time.LocalDate.parse(endInput);
            java.time.LocalDateTime startOfDay = startDate.atStartOfDay();
            java.time.LocalDateTime endOfDay = endDate.atTime(23, 59, 59, 999_999_999);

            if (endOfDay.isBefore(startOfDay)) {
                System.out.println("End date cannot be before start date.");
                return;
            }

            System.out.println(transactionManager.getHeader());
            for (Transaction t : transactionManager.filterByDateTime(startOfDay, endOfDay)) {
                System.out.println(t.toString());
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }
    
    // =========================================================
    // Input helpers
    // EFFECTS: read prompt and process input
    private String readLine(String prompt) {
        System.out.print(prompt);
        return input.next().trim().toLowerCase(Locale.ROOT);
    }

    // EFFECTS: read prompt and process symbol
    private String readSymbol(String prompt) {
        while (true) {
            String s = readLine(prompt).toUpperCase(Locale.ROOT);
            // 6 letters for IPO stock
            if (s.matches("[A-Z]{1,6}")) {
                return s;
            }
            System.out.println("Invalid symbol. Use 1-6 letters.");
        }
    }

    // EFFECTS: read prompt and process positive double number
    private double readPositiveDouble(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                double v = Double.parseDouble(s);
                if (v > 0.0) {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input can't be converted to real number");
            }
            System.out.println("Please enter a positive number");
        }
    }

    // EFFECTS: read prompt and process yes/no input
    private boolean isYesNo(String prompt) {
        while (true) {
            String s = readLine(prompt).toLowerCase(Locale.ROOT);
            if (s.equals("y") || s.equals("yes")) {
                return true;
            }
            if (s.equals("n") || s.equals("no")) {
                return false;
            }
            System.out.println("Please answer y/n.");
        }
    }    

    // EFFECTS: saves stock portfolio data to file
    private void saveStockPortfolioApp() {
        try {
            jsonWriter.open();
            jsonWriter.write(market, portfolio);
            jsonWriter.close();
            System.out.println("Save StockPortfolioApp state to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads stock portfolio data from file
    private void loadStockPortfolioApp() {
        try {
            Portfolio loadedPortfolio = jsonReader.readPortfolio();
            Market loadedMarket = jsonReader.readMarket();
            if (loadedPortfolio != null) {
                this.portfolio = loadedPortfolio;
            }
            if (loadedMarket != null) {
                this.market = loadedMarket;
            } else {
                // Fallback: ensure market is available even if file lacked a market section
                if (this.market == null || this.market.isEmpty()) {
                    this.market = new Market();
                    MarketCatalog.seed(this.market);
                }
            }
            if (this.portfolio != null && this.market != null) {
                this.portfolio.reattachHoldingsToMarket(this.market);
            }
            System.out.println("Loaded StockPortfolioApp state from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
