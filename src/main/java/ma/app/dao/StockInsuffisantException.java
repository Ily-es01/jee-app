package ma.app.dao;

public class StockInsuffisantException extends RuntimeException {
    public StockInsuffisantException() {
        super("Stock insuffisant pour effectuer cette vente.");
    }
}