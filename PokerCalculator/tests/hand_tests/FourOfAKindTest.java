package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class FourOfAKindTest {
	@Test
	public void tieFourOfAKindOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("7d3h", "8h2c", "JcJsJhKdJd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherKickerFourOfAKindOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ad3h", "8h2c", "JcJsJhKdJd");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherKickerFourOfAKindOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Kd3h", "Ah2c", "JcJsJhQdJd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherQuads() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("KdKh", "2s2c", "KsKc2h3d2d");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherQuads() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("3c3s", "KdKh", "KsKc3h3d2d");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
}
