package oz.shoppingBasket;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class MarketData{
	
	
	private static final String COMMA_DELIMITER = ",";
	private String pricesFilePath;
	private String offersFilePath;
	private Map<ShoppingItems, Double> priceMap;
	private List<SpecialOffer> offersList;
	private BufferedReaderFactory brf;
	
	
	public MarketData(String pricesFilePath, String offersFilePath, BufferedReaderFactory brFactory) {
		this.priceMap = new HashMap<ShoppingItems, Double>();
		this.offersList = new ArrayList<SpecialOffer>();
		this.pricesFilePath = pricesFilePath;
		this.offersFilePath = offersFilePath;
		this.brf = brFactory;
		
		if(pricesFilePath == null)
			throw new IllegalStateException("Basket Cannot be used without Price Market Data");
		else
			initPrices();
		if(offersFilePath != null) initOffers();
	}
	
	
	private void initOffers() {
		try (BufferedReader br = brf.getBufferedReader(offersFilePath)) {
		    readOffersFile(br);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Offers file coult not be read. No special offer will be taken into account");
		}
	}
	

	private void readOffersFile(BufferedReader br) throws IOException {
		String line;
	    while ((line = br.readLine()) != null) {
	        String[] values = line.split(COMMA_DELIMITER);
	        processOffersLine(values);
	    }
	}

	
	private void processOffersLine(String[] values) {
		try {
        	offersList.add( new SpecialOffer(values) );
        }catch(Exception ex) {
        	System.out.println("Following Offers from file coult not be parsed and will be taken into account.");
        	System.out.println(Arrays.toString(values));
        	System.out.print(ex.getMessage());
        }
	}
	

	private void initPrices(){
		Properties properties = new Properties();
		
	    try(BufferedReader file = brf.getBufferedReader(pricesFilePath)){
	    	properties.load(file);
	    } catch (IOException e) {
	    	System.out.println("prices file coult not be read. Default attributes will be used for items' prices");
		}
	    
	    processProperties(properties);
	}
	
	
	private void processProperties(Properties properties) {
		
		Arrays.asList(ShoppingItems.values()).stream()
			.forEach(item -> parseAndValidatePriceProperty(item, properties));
		
	}
	
	
	private void parseAndValidatePriceProperty(ShoppingItems item, Properties properties) {
		Double itemPrice = 0.0;
		
		try{
			itemPrice = Double.parseDouble( properties.getProperty(item.name(), "0") );
		}catch(NumberFormatException ex) {
			System.out.println("price of item: " + item.name() + " in price file is not correctly formatted");
		}
		
		priceMap.put(item, trimToZeroIfNegatif( itemPrice ));
	}
	
	
	private Double trimToZeroIfNegatif(Double propPrice) {
		return propPrice >= 0 ? propPrice : 0;
	}

	
	public Map<ShoppingItems, Double> getPriceMap() {
		return priceMap;
	}
	
	public  List<SpecialOffer> getOffersList() {
		return offersList;
	}
}