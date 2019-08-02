package oz.shoppingBasket;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class BasketPricerTest {
	
	@Test
	public void BasketPricerCanPriceEmptyBasket() {
		//Given
		PrintStream mockOut= Mockito.mock(PrintStream.class);
		
		Map<ShoppingItems, Integer> itemsCountMap = new HashMap<ShoppingItems, Integer>();
		ShoppingBasket mockBasket = Mockito.mock(ShoppingBasket.class);
		when(mockBasket.getItemsCount()).thenReturn(itemsCountMap);

		Map<ShoppingItems, Double> itemsPriceMap = new HashMap<ShoppingItems, Double>();
		MarketData mockMarketData = Mockito.mock(MarketData.class);
		when(mockMarketData.getPriceMap()).thenReturn(itemsPriceMap);
		
		BasketPricer pricer = new BasketPricer(mockBasket, mockMarketData, mockOut);
		
		
		//When
		pricer.calculatePrice();
		
		//Then
		verify(mockOut).printf(eq("Subtotal: £%.2f %n"), eq(0.0));
		verify(mockOut).println(eq("(No offers available)"));
		verify(mockOut).printf(eq("Total: £%.2f %n"), eq(0.0));
	}
	
	
	@Test
	public void BasketPricerCanPriceSubtotalForApples() {
		//Given
		PrintStream mockOut= Mockito.mock(PrintStream.class);
		
		Map<ShoppingItems, Integer> itemsCountMap = new HashMap<ShoppingItems, Integer>();
		itemsCountMap.put(ShoppingItems.Apples, 1);
		ShoppingBasket mockBasket = Mockito.mock(ShoppingBasket.class);
		when(mockBasket.getItemsCount()).thenReturn(itemsCountMap);

		Map<ShoppingItems, Double> itemsPriceMap = new HashMap<ShoppingItems, Double>();
		itemsPriceMap.put(ShoppingItems.Apples, 1.0);
		MarketData mockMarketData = Mockito.mock(MarketData.class);
		when(mockMarketData.getPriceMap()).thenReturn(itemsPriceMap);
		
		BasketPricer pricer = new BasketPricer(mockBasket, mockMarketData, mockOut);
		
		
		//When
		pricer.calculatePrice();
		
		//Then
		verify(mockOut).printf(eq("Subtotal: £%.2f %n"), eq(1.0));
	}
	
	
	@Test
	public void BasketPricerCanPriceTotalForApples() {
		//Given
		PrintStream mockOut= Mockito.mock(PrintStream.class);
		
		Map<ShoppingItems, Integer> itemsCountMap = new HashMap<ShoppingItems, Integer>();
		itemsCountMap.put(ShoppingItems.Apples, 1);
		ShoppingBasket mockBasket = Mockito.mock(ShoppingBasket.class);
		when(mockBasket.getItemsCount()).thenReturn(itemsCountMap);

		Map<ShoppingItems, Double> itemsPriceMap = new HashMap<ShoppingItems, Double>();
		itemsPriceMap.put(ShoppingItems.Apples, 1.0);
		MarketData mockMarketData = Mockito.mock(MarketData.class);
		when(mockMarketData.getPriceMap()).thenReturn(itemsPriceMap);
		
		BasketPricer pricer = new BasketPricer(mockBasket, mockMarketData, mockOut);
		
		
		//When
		pricer.calculatePrice();
		
		//Then
		verify(mockOut).printf(eq("Total: £%.2f %n"), eq(1.0));
	}
	
	
	
	@Test
	public void BasketPricerCanPriceTotalForApplesAndMilk() {
		//Given
		PrintStream mockOut= Mockito.mock(PrintStream.class);
		
		Map<ShoppingItems, Integer> itemsCountMap = new HashMap<ShoppingItems, Integer>();
		itemsCountMap.put(ShoppingItems.Apples, 1);
		itemsCountMap.put(ShoppingItems.Milk, 1);
		ShoppingBasket mockBasket = Mockito.mock(ShoppingBasket.class);
		when(mockBasket.getItemsCount()).thenReturn(itemsCountMap);

		Map<ShoppingItems, Double> itemsPriceMap = new HashMap<ShoppingItems, Double>();
		itemsPriceMap.put(ShoppingItems.Apples, 1.0);
		itemsPriceMap.put(ShoppingItems.Milk, 1.3);
		MarketData mockMarketData = Mockito.mock(MarketData.class);
		when(mockMarketData.getPriceMap()).thenReturn(itemsPriceMap);
		
		BasketPricer pricer = new BasketPricer(mockBasket, mockMarketData, mockOut);
		
		
		//When
		pricer.calculatePrice();
		
		//Then
		verify(mockOut).printf(eq("Total: £%.2f %n"), eq(2.3));
	}
	
	
	@Test
	public void BasketPricerCanPriceTotalForApplesWithSpecialOffer() {
		//Given
		PrintStream mockOut= Mockito.mock(PrintStream.class);
		
		Map<ShoppingItems, Integer> itemsCountMap = new HashMap<ShoppingItems, Integer>();
		itemsCountMap.put(ShoppingItems.Apples, 1);
		ShoppingBasket mockBasket = Mockito.mock(ShoppingBasket.class);
		when(mockBasket.getItemsCount()).thenReturn(itemsCountMap);

		Map<ShoppingItems, Double> itemsPriceMap = new HashMap<ShoppingItems, Double>();
		itemsPriceMap.put(ShoppingItems.Apples, 1.0);
		String[] offerValues = {"Apples","1","Apples","1","0.1"};
		SpecialOffer spo = new SpecialOffer( offerValues );
		List<SpecialOffer> offerList = new ArrayList<>();
		offerList.add(spo);
		
		MarketData mockMarketData = Mockito.mock(MarketData.class);
		when(mockMarketData.getPriceMap()).thenReturn(itemsPriceMap);
		when(mockMarketData.getOffersList()).thenReturn(offerList);
		
		BasketPricer pricer = new BasketPricer(mockBasket, mockMarketData, mockOut);
		
		
		//When
		pricer.calculatePrice();
		
		//Then
		verify(mockOut).printf(eq("Subtotal: £%.2f %n"), eq(1.0));
		verify(mockOut).println(eq("1 Apples %10.0 off: -10p"));
		verify(mockOut).printf(eq("Total: £%.2f %n"), eq(0.9));
	}
	
}