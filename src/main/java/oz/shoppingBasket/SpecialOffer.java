package oz.shoppingBasket;


public class SpecialOffer{
	
	ShoppingItems itemCondition; 
	Integer amountCondition;
	ShoppingItems itemOffered;
	Integer amountOffered;
	Double withPercentageOffered;
	
	public SpecialOffer(String[] csvLine) {
		this.itemCondition = ShoppingItems.valueOf(csvLine[0]); 
		this.amountCondition = amountCheck(Integer.parseInt(csvLine[1]));
		this.itemOffered = ShoppingItems.valueOf(csvLine[2]);
		this.amountOffered = amountCheck(Integer.parseInt(csvLine[3]));
		this.withPercentageOffered = percentCheck(Double.parseDouble(csvLine[4]));
	}
	
	Integer amountCheck(Integer integer) {
		if(integer == null || integer < 0)
			throw new IllegalArgumentException(integer + " is not an accepted amount of unit");
		else return integer;
	}
	
	Double percentCheck(Double percent) {
		if(percent == null || percent > 1.0)
			throw new IllegalArgumentException(percent + " is not an percentage. you must enter a percentage (lower then 1.0) for a discount on an item");
		else return percent;
	}

	public ShoppingItems getItemCondition() {
		return itemCondition;
	}
	public void setItemCondition(ShoppingItems itemCondition) {
		this.itemCondition = itemCondition;
	}
	public Integer getAmountCondition() {
		return amountCondition;
	}
	public void setAmountCondition(Integer amountCondition) {
		this.amountCondition = amountCondition;
	}
	public ShoppingItems getItemOffered() {
		return itemOffered;
	}
	public void setItemOffered(ShoppingItems itemOffered) {
		this.itemOffered = itemOffered;
	}
	public Integer getAmountOffered() {
		return amountOffered;
	}
	public void setAmountOffered(Integer amountOffered) {
		this.amountOffered = amountOffered;
	}
	public Double getPercentageOffered() {
		return withPercentageOffered;
	}
	public void setPercentageOffered(Double percentageOffered) {
		this.withPercentageOffered = percentageOffered;
	}
}