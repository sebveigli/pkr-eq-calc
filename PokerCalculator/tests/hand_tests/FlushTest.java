package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class FlushTest {
	
	@Test
	public void tieFlushOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2c5c", "3c4c", "7c8cAcTcJc");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherFlush() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2cAc", "3cKc", "7c8c4cTcJc");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherFlush() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("QcKc", "3cAc", "7d8d2cTcJc");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherCards() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("6c5c", "3c4c", "7d8d2cTcJc");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
}
