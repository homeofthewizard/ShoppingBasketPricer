package oz.shoppingBasket;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class BasketPricer{
	
	private final PrintStream out;
	private ShoppingBasket basket;
	private MarketData marketData;
	
	public BasketPricer(ShoppingBasket basket, MarketData marketData, PrintStream out) {
		this.basket = basket;
		this.out = out;
		this.marketData = marketData;
	}
	
	
	public void calculatePrice() {
		Double subtotal = calculateSubtotal();
	
		List<Reduction> reductions = calculateReductions();
		Double totalReduction = reductions.stream().map(reduction -> reduction.reduction).reduce(0.0, Double::sum);
	
		printResult(subtotal, reductions, subtotal - totalReduction);
	}

	
	private Double calculateSubtotal() {
		Double subtotal = 0.0;
		for(Entry<ShoppingItems, Integer> item : this.basket.getItemsCount().entrySet()) {
			subtotal = subtotal + (marketData.getPriceMap().get(item.getKey()) * item.getValue());
		}
		return subtotal;
	}


	private List<Reduction> calculateReductions() {
		List<Reduction> result = new ArrayList<>();
		
		for(SpecialOffer offer : marketData.getOffersList()) {
			if(basket.getItemsCount().keySet().contains(offer.itemCondition)) {
				
				int offerCount = basket.getItemsCount().get(offer.itemCondition) / offer.amountCondition;
				offerCount = offerCount * offer.amountOffered;
				
				if(basket.getItemsCount().keySet().contains(offer.itemOffered)) {
					offerCount = Math.min(basket.getItemsCount().get(offer.itemOffered) , offerCount);
					Double reducted = offerCount * marketData.getPriceMap().get(offer.itemOffered) * offer.withPercentageOffered;
					Reduction reduction = new Reduction(offer.itemOffered, offerCount, offer.withPercentageOffered, reducted);
					result.add(reduction);
				}
			}
		}
		return result;
	}
	

	private void printResult(Double subtotal, List<Reduction> reductions, Double total) {
		out.printf("Subtotal: £%.2f %n", subtotal);
		if(reductions.isEmpty())
			out.println("(No offers available)");
		else
			reductions.stream().forEach( (reduction) -> out.println(reduction.toString()) );
		out.printf("Total: £%.2f %n", total);
	}
	
	
}