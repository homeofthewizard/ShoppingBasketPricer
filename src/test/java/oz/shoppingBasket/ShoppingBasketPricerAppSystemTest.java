package oz.shoppingBasket;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class ShoppingBasketPricerAppSystemTest {
	
	public String run(final String[] items) {
		ByteArrayOutputStream dummyOutput = new ByteArrayOutputStream();
		ShoppingBasketPricerApp app = new ShoppingBasketPricerApp(new PrintStream(dummyOutput));
		app.run(items);
		
		try {
			return dummyOutput.toString("UTF-8");
		}
		catch(UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void ShoppingBasketPricerShouldPrintPriceForApples() {
		//Given
		String[] items = {"Apples"};
		
		//When
		String result = run(items);
		
		//Then
		Assertions.assertThat(result).contains("Total: £0.90");
	}
}