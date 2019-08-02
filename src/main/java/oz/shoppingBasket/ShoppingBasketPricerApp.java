package oz.shoppingBasket;

import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class ShoppingBasketPricerApp 
{
	private static String pricesFilePath	= "./src/main/resources/itemPrices.properties";
	private static String offersFilePath	= "./src/main/resources/specialOffers.csv";
	
	private final PrintStream out;
	
    public static void main( String[] args )
    {
    	initConfigs();
    	String[] items = args;
        new ShoppingBasketPricerApp(System.out).run(items);   
    }
    
    public ShoppingBasketPricerApp(PrintStream out) {
		this.out = out;
	}
    
    public void run(String[] items){
    	ShoppingBasket basket = new ShoppingBasket().putItems(items);
    	MarketData marketData = new MarketData(pricesFilePath, offersFilePath, BufferedReaderFactory.getInstance());
    	BasketPricer pricer = new BasketPricer(basket, marketData, out);
    	pricer.calculatePrice();
    }
    
    
    private static void initConfigs() {
    	if(System.getProperty("shoppingBasket.pricer.priceList") != null)
    		pricesFilePath = System.getProperty("shoppingBasket.pricer.priceList");
    	if(System.getProperty("shoppingBasket.pricer.offerList") != null)
    		offersFilePath = System.getProperty("shoppingBasket.pricer.offerList");
    }
    
}
