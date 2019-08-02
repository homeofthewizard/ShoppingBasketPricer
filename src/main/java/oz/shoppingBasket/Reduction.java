package oz.shoppingBasket;

public class Reduction{
	
	ShoppingItems item;
	Integer amount;
	Double reduction;
	Double percent;
	
	public Reduction(ShoppingItems item, Integer amount, Double percent, Double reduction) {
		super();
		this.item = item;
		this.amount = amount;
		this.reduction = reduction;
		this.percent = percent;
	}
	
	
	@Override
	public String toString() {
		return amount + " " + item.toString() + " %" + percent*100 + " off: -" + (reduction >=1 ? (reduction + "£") : (Math.round(reduction*100) + "p"));  
	}
}