package oz.shoppingBasket;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class BufferedReaderFactory{
	
	private static final BufferedReaderFactory INSTANCE = new BufferedReaderFactory();

    private BufferedReaderFactory() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static BufferedReaderFactory getInstance() {
        return INSTANCE;
    }
	
	public BufferedReader getBufferedReader(String filePath) throws FileNotFoundException {
		return new BufferedReader(new FileReader(filePath));
	}
}