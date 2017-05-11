package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class TwoPairTest {
	@Test
	public void tieHandSamePairInHand() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AsAh", "AdAc", "7h9d9c2h3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandSameTwoPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("As7d", "Ad7c", "7h9d9c2h3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandSameTwoPairInHand() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("As7d", "Ad7c", "7h9dAc2h3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandTwoPairPlayingBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("4s5s", "2d6h", "7h9d9c7cKh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AdAh", "QhQs", "7h9d9c7cKh");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("QhQs", "AdAh", "7h9d9c7cKh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherTwoPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ks7s", "Qs9h", "7h9dQcTcKh");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherTwoPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("9s7s", "Qs9h", "7h9dQcTcKh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsLowerPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("3h3s", "2h2s", "7hAsQcQdKh");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherBoardPair() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("3h7s", "2hKs", "7hAsQcQdKh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.TWO_PAIR.ordinal(), gru.getPlayerTwoRanking());
	}
	
}
