package stock;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {

    @Mock
    IStockmarketService stockMarket;

    @InjectMocks
    StocksPortfolio stocksPortfolio;

    @Test
    void getTotalValue() {
//        1.Prepare a mock to substitute the remote service (@Mock annotation)
        IStockmarketService stockMarketService = mock(IStockmarketService.class);

//        2.Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        StocksPortfolio stocks = new StocksPortfolio(stockMarketService);

//        3.Load the mock with the proper expectations (when...thenReturn)
        when(stockMarketService.getPrice("EDP")).thenReturn(5.0);
        when(stockMarketService.getPrice("Mota-Engil")).thenReturn(1.5);
//        4.Execute the test (use the service in the SuT)
        stocks.addStock(new Stock("EDP", 4));
        stocks.addStock(new Stock("Mota-Engil", 2));
        double result = stocks.getTotalValue();

//        5.Verify the result (assert) and the use of the mock (verify)
        assertEquals(23, result);
        verify(stockMarketService, times(2)).getPrice(anyString());
    }

    /**
     * Test using Mock injection
     */
    @Test
    void getTotalValueMock() {
//        3.Load the mock with the proper expectations (when...thenReturn)
        when(stockMarket.getPrice("EDP")).thenReturn(5.0);
        when(stockMarket.getPrice("Mota-Engil")).thenReturn(1.5);

//        4.Execute the test (use the service in the SuT)
        stocksPortfolio.addStock(new Stock("EDP", 4));
        stocksPortfolio.addStock(new Stock("Mota-Engil", 2));
        double result = stocksPortfolio.getTotalValue();

//        5.Verify the result (assert) and the use of the mock (verify)
        assertEquals(23, result);
        verify(stockMarket, times(2)).getPrice(anyString());
    }

    /**
     * Test using Hamcrest
     */
    @Test
    void getTotalValueHamcrest() {
//        1.Prepare a mock to substitute the remote service (@Mock annotation)
        IStockmarketService stockMarketService = mock(IStockmarketService.class);

//        2.Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        StocksPortfolio stocks = new StocksPortfolio(stockMarketService);

//        3.Load the mock with the proper expectations (when...thenReturn)
        when(stockMarketService.getPrice("EDP")).thenReturn(5.0);
        when(stockMarketService.getPrice("Mota-Engil")).thenReturn(1.5);
//        4.Execute the test (use the service in the SuT)
        stocks.addStock(new Stock("EDP", 4));
        stocks.addStock(new Stock("Mota-Engil", 2));
        double result = stocks.getTotalValue();

//        5.Verify the result (assert) and the use of the mock (verify)
        // this api needs the decimal part to interpret as double
        assertThat(result, is(23.0));
    }

    /**
     * Fail test on purpose
     */
    @Test
    void failTestBecauseImABadProgrammer() {
        fail();
    }
}