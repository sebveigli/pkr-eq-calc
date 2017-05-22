package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class PairTest {
	
	@Test
	public void tieHandSamePairInHand() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AsAh", "AdAc", "7h9d8d2h3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandSameHighCard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("As2s", "Ad3d", "7h9d8dAhQh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandPlayingBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("3s2s", "4d3d", "7h9d8sAhAd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AsAh", "KdKc", "7h9d8d2h3h");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("KdKc", "AsAh", "7h9d8d2h3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsSamePairHigherKicker() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("9cAc", "9hKs", "7h9d8d2h3h");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsSamePairHigherKicker() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("9hKs", "9cAc", "7h9d8d2h3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherKicker() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AcKc", "QsAh", "7h9d9s2h3h");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherKicker() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AhQs", "AcKc", "7h9d9s2h3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
}
