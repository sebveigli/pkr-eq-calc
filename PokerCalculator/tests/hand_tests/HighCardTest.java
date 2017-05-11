package hand_tests;

import org.junit.Test;
import org.junit.Assert;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class HighCardTest {
	
	@Test
	public void tieHand() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2d3d", "2c5s", "7h9d8dAhQh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHighCard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2d5s", "2c3d", "7h9d4sAhQh");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHighCard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2c3d", "2d5s", "7h9d4sAhQh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsTwoHigh() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("KcTd", "Ks8d", "7h9d4sAhQh");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsTwoHigh() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ks8d", "KcTd", "7h9d4sAhQh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.HIGH_CARD.ordinal(), gru.getPlayerTwoRanking());
	}
}
