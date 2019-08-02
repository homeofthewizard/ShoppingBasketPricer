package oz.shoppingBasket;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket{
	
	private final Map<ShoppingItems, Integer> itemsCount;
	
	public ShoppingBasket() {
		this.itemsCount = new HashMap<ShoppingItems, Integer>();
	}

	public ShoppingBasket putItems(String[] items) {
		validateItems(items);
		return this;
	}
	
	private void validateItems(String[] items) {
		Arrays.asList(items).stream()
		.map(item -> validateItem(item))
		.filter(item -> item!=null)
		.forEach(item -> countItem(item));
	}

	private void countItem(ShoppingItems item) {
		Integer count = itemsCount.get(item);
		if (count == null) {
			itemsCount.put(item, 1);
		}
		else {
			itemsCount.put(item, count + 1);
		}
	}

	private ShoppingItems validateItem(String item) {
		try {
			return ShoppingItems.valueOf(item);
		}catch(IllegalArgumentException ex) {
			System.out.println(item + " is not allowed in basket");
		}
		return null;
	}
	
	
	public Map<ShoppingItems, Integer> getItemsCount() {
		return itemsCount;
	}
    
}