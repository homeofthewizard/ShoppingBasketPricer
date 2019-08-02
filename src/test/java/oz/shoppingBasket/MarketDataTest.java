package oz.shoppingBasket;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.StringReader;


public class MarketDataTest {
	
	private static final String pricesFilePath	= "./src/main/resources/itemPrices.properties";
	private static final String offersFilePath	= "./src/main/resources/specialOffers.csv";
	
	@Test(expected = IllegalStateException.class)
	public void MarketDataThrowErrorIfNoPricesFile() {
		//Given
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		
		//When
		new MarketData(null, null, mockBrf);
		//Then
	}
	
	
	@Test
	public void MarketDataReturnDefaultPricesIfEmptyFile() throws FileNotFoundException {
		//Given
		MarketData marketData;
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		BufferedReader mockBr = new BufferedReader(new StringReader(""));
		when(mockBrf.getBufferedReader(any())).thenReturn(mockBr);
		
		//When
		marketData = new MarketData("mockPath", null, mockBrf);
		
		//Then
		Assertions.assertThat(marketData.getPriceMap().get(ShoppingItems.Apples)).isEqualTo(0.0);
	}
	
	
	@Test
	public void MarketDataReturnNoSpecialOfferIfNoFile() throws FileNotFoundException {
		//Given
		MarketData marketData;
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		BufferedReader mockBr = new BufferedReader(new StringReader(""));
		when(mockBrf.getBufferedReader(any())).thenReturn(mockBr);
		
		//When
		marketData = new MarketData("mockPath", null, mockBrf);
		
		//Then
		Assertions.assertThat(marketData.getOffersList()).isEmpty();
	}
	
	
	@Test
	public void MarketDataReturnDefaultValueIfPriceNegativeInFile() throws FileNotFoundException {
		//Given
		MarketData marketData;
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		BufferedReader mockBr = new BufferedReader(new StringReader("Apples=-1"));
		when(mockBrf.getBufferedReader(any())).thenReturn(mockBr);
		
		//When
		marketData = new MarketData("mockPath", null, mockBrf);
		
		//Then
		Assertions.assertThat(marketData.getPriceMap().get(ShoppingItems.Apples)).isEqualTo(0);
	}
	
	
	@Test
	public void MarketDataReturnDefaultValueIfPriceIsNotNumericInFile() throws FileNotFoundException {
		//Given
		MarketData marketData;
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		BufferedReader mockBr = new BufferedReader(new StringReader("Apples=@test"));
		when(mockBrf.getBufferedReader(any())).thenReturn(mockBr);
		
		//When
		marketData = new MarketData("mockPath", null, mockBrf);
		
		//Then
		Assertions.assertThat(marketData.getPriceMap().get(ShoppingItems.Apples)).isEqualTo(0);
	}
	
	
	@Test
	public void MarketDataDontConsiderOfferIfItemConditionIsNotAValidItem() throws FileNotFoundException {
		//Given
		MarketData marketData;
		String mockOffersFilePath = "mockOfferPath";
		String mockPriceFilePath = "mockPricePath";
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		BufferedReader mockBrOff = new BufferedReader(new StringReader("Orange,1,Apples,1,0.1"));
		BufferedReader mockBrPrc = new BufferedReader(new StringReader(""));
		when(mockBrf.getBufferedReader(mockOffersFilePath)).thenReturn(mockBrOff);
		when(mockBrf.getBufferedReader(mockPriceFilePath)).thenReturn(mockBrPrc);
		
		//When
		marketData = new MarketData(mockPriceFilePath, mockOffersFilePath, mockBrf);
		
		//Then
		Assertions.assertThat(marketData.getOffersList()).isEmpty();
	}
	
	
	@Test
	public void MarketDataDontConsiderOfferIfAmountConditionIsNotAValidNumber() throws FileNotFoundException {
		//Given
		MarketData marketData;
		String mockOffersFilePath = "mockOfferPath";
		String mockPriceFilePath = "mockPricePath";
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		BufferedReader mockBrOff = new BufferedReader(new StringReader("Apples,-1,Apples,1,0.1"));
		BufferedReader mockBrPrc = new BufferedReader(new StringReader(""));
		when(mockBrf.getBufferedReader(mockOffersFilePath)).thenReturn(mockBrOff);
		when(mockBrf.getBufferedReader(mockPriceFilePath)).thenReturn(mockBrPrc);
		
		//When
		marketData = new MarketData(mockPriceFilePath, mockOffersFilePath, mockBrf);
		
		//Then
		Assertions.assertThat(marketData.getOffersList()).isEmpty();
	}
	
	
	@Test
	public void MarketDataDontConsiderOfferIfPercentOfferedIsNotAPercentage() throws FileNotFoundException {
		//Given
		MarketData marketData;
		String mockOffersFilePath = "mockOfferPath";
		String mockPriceFilePath = "mockPricePath";
		BufferedReaderFactory mockBrf = Mockito.mock(BufferedReaderFactory.class);
		BufferedReader mockBrOff = new BufferedReader(new StringReader("Apples,1,Apples,1,11"));
		BufferedReader mockBrPrc = new BufferedReader(new StringReader(""));
		when(mockBrf.getBufferedReader(mockOffersFilePath)).thenReturn(mockBrOff);
		when(mockBrf.getBufferedReader(mockPriceFilePath)).thenReturn(mockBrPrc);
		
		//When
		marketData = new MarketData(mockPriceFilePath, mockOffersFilePath, mockBrf);
		
		//Then
		Assertions.assertThat(marketData.getOffersList()).isEmpty();
	}
	
	
	@Test
	public void MarketDataReturnPriceMapCorrectly_Integration() {
		//Given
		MarketData marketData;
		
		//When
		marketData = new MarketData(pricesFilePath, null, BufferedReaderFactory.getInstance());
		
		//Then
		Assertions.assertThat(marketData.getPriceMap().get(ShoppingItems.Apples)).isEqualTo(1.0);
		Assertions.assertThat(marketData.getPriceMap().get(ShoppingItems.Milk)).isEqualTo(1.3);
		Assertions.assertThat(marketData.getPriceMap().get(ShoppingItems.Soup)).isEqualTo(0.65);
		Assertions.assertThat(marketData.getPriceMap().get(ShoppingItems.Bread)).isEqualTo(0.8);
	}
	
	
	@Test
	public void MarketDataReturnOffersCorrectly_Integration() {
		//Given
		MarketData marketData;
		
		//When
		marketData = new MarketData("mockPath", offersFilePath, BufferedReaderFactory.getInstance());
		
		//Then
		Assertions.assertThat(marketData.getOffersList().get(0)).hasFieldOrProperty("itemCondition");
		Assertions.assertThat(marketData.getOffersList().get(0)).hasFieldOrProperty("amountCondition");
		Assertions.assertThat(marketData.getOffersList().get(0)).hasFieldOrProperty("itemOffered");
		Assertions.assertThat(marketData.getOffersList().get(0)).hasFieldOrProperty("amountOffered");
		Assertions.assertThat(marketData.getOffersList().get(0)).hasFieldOrProperty("withPercentageOffered");
		Assertions.assertThat(marketData.getOffersList().get(0)).hasNoNullFieldsOrProperties();
	}
}