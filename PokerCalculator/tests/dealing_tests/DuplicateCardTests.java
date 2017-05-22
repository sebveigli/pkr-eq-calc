package dealing_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;

public class DuplicateCardTests {
	@Test(expected = InvalidHandException.class)
	public void sameHandInvalidHandExceptionExhaustive() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("6c6d", "6c6d", "7h9s3dAh3h");
		
		gru.run();
	}
	
	@Test(expected = InvalidHandException.class)
	public void sameFirstCardInvalidHandExceptionExhaustive() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("6c7d", "6c2h", "7h9s3dAh3h");
		
		gru.run();
	}
	
	@Test(expected = InvalidHandException.class)
	public void sameSecondCardInvalidHandExceptionExhaustive() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Kc7d", "6c7d", "7h9s3dAh3h");
		
		gru.run();
	}
	
	@Test(expected = InvalidHandException.class)
	public void sameFirstHandAsBoardInvalidHandExceptionExhaustive() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("4cKc", "6c7d", "7h9s4cAh3h");
		
		gru.run();
	}
	
	@Test(expected = InvalidHandException.class)
	public void sameSecondHandAsBoardInvalidHandExceptionExhaustive() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("4cKc", "6c7d", "7h9s6cAh3h");
		
		gru.run();
	}
	
	@Test(expected = InvalidHandException.class)
	public void sameHandInvalidHandExceptionMonteCarlo() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("6c6d", "6c6d", "");
		
		gru.run();
	}
	
	@Test(expected = InvalidHandException.class)
	public void sameFirstCardInvalidHandExceptionMonteCarlo() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("6c7d", "6c2h", "");
		
		gru.run();
	}
	
	@Test(expected = InvalidHandException.class)
	public void sameSecondCardInvalidHandExceptionMonteCarlo() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Kc7d", "6c7d", "");
		
		gru.run();
	}
	
	@Test
	public void twoHandsWithOneSameExhaustive() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("3h2d, 7s4c", "7s4c, 2c5d", "Ac6h3d2hAd");
		
		gru.run();
		
		// 3h2d beats 7s4c, 2c5d. 7s4c loses to 2c5d
		Assert.assertEquals(2, gru.getPlayerOneWins()); 
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		
		// no ties, 7s4c vs 7s4c is invalid
		Assert.assertEquals(0, gru.getTies());
	}
	
	@Test
	public void twoHandsWithOneSameMonteCarlo() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("3h2d, 7s4c", "7s4c, 2c5d", "");
		
		gru.run();
		
		Assert.assertEquals(100000, gru.getPlayerOneWins() + gru.getPlayerTwoWins() + gru.getTies());
	}
}
