package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class StraightFlushTest {
	@Test
	public void tieStraightFlushOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2c5c", "3c4c", "7c8c9cTcJc");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneHigherStraightFlush() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("TcJc", "6c5c", "7c8c9cTdJd");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoHigherStraightFlush() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("7c8c", "QcKc", "2d3d9cTcJc");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneAceLowStraightFlushVsHigherPlayerTwoStraightFlush() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2cAc", "6c7c", "3c5c4c7hJd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), gru.getPlayerTwoRanking());
	}
}
