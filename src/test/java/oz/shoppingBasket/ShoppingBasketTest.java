package oz.shoppingBasket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ShoppingBasketTest {
	
	@Test
	public void ShoppingBasketCanCountSoup() {
		//Given
		String[] items = {"Soup"};
		ShoppingBasket basket = new ShoppingBasket();
		
		//When
		basket.putItems(items);
		
		//Then
		Assertions.assertThat(basket.getItemsCount().get(ShoppingItems.Soup)).isEqualTo(1);
	}
	
	@Test
	public void ShoppingBasketCanCountBread() {
		//Given
		String[] items = {"Bread"};
		ShoppingBasket basket = new ShoppingBasket();
		
		//When
		basket.putItems(items);
		
		//Then
		Assertions.assertThat(basket.getItemsCount().get(ShoppingItems.Bread)).isEqualTo(1);
	}
	
	@Test
	public void ShoppingBasketCanCountTwoApples() {
		//Given
		String[] items = {"Apples", "Apples"};
		ShoppingBasket basket = new ShoppingBasket();
		
		//When
		basket.putItems(items);
		
		//Then
		Assertions.assertThat(basket.getItemsCount().get(ShoppingItems.Apples)).isEqualTo(2);
	}
	
	@Test
	public void ShoppingBasketCanCountApplesAndMilk() {
		//Given
		String[] items = {"Apples", "Milk"};
		ShoppingBasket basket = new ShoppingBasket();
		
		//When
		basket.putItems(items);
		
		//Then
		Assertions.assertThat(basket.getItemsCount().get(ShoppingItems.Apples)).isEqualTo(1);
		Assertions.assertThat(basket.getItemsCount().get(ShoppingItems.Milk)).isEqualTo(1);
	}
	
	
	@Test
	public void ShoppingBasketRejectsOrange() throws IOException {
		//Given
		String[] items = {"Orange"};
		ShoppingBasket basket = new ShoppingBasket();
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bo));
		
		//When
		basket.putItems(items);
		
		//Then
		bo.flush();
		String allWrittenLines = new String(bo.toByteArray()); 
        Assertions.assertThat(allWrittenLines).contains("Orange is not allowed in basket");
	}
	
	@Test
	public void ShoppingBasketRejectsOrangeButAcceptMilk() throws IOException {
		//Given
		String[] items = {"Orange", "Milk"};
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bo));
		ShoppingBasket basket = new ShoppingBasket();
		
		//When
		basket.putItems(items);
		
		//Then
		bo.flush();
		String allWrittenLines = new String(bo.toByteArray()); 
        Assertions.assertThat(allWrittenLines).contains("Orange is not allowed in basket");
        Assertions.assertThat(basket.getItemsCount().get(ShoppingItems.Milk)).isEqualTo(1);
	}
	
}