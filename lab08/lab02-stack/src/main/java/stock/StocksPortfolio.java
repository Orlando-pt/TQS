package stock;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {

    private String name;
    private IStockmarketService stockmarketService;
    private List<Stock> stocks = new ArrayList<Stock>();

    public StocksPortfolio(IStockmarketService market) {
        this.stockmarketService = market;
    }

    public IStockmarketService getMarketService() {
        return this.stockmarketService;
    }

    public void setMarketService(IStockmarketService market) {
        this.stockmarketService = market;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double getTotalValue() {
        double res = 0;
        for (Stock stock : this.stocks){
            res += stock.getQuantity() * this.stockmarketService.getPrice(stock.getName());
        }

        return res;
    }

}
