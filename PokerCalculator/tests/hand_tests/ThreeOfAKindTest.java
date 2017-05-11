package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class ThreeOfAKindTest {
	@Test
	public void tieHandSameThreeOfAKind() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AsKh", "AdKc", "7h8d9cKsKd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandSameThreeOfAKindSameBoardKickers() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("6sKh", "2dKc", "7h8d9cKsKd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherKicker() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AsKh", "2dKc", "7h8d9cKsKd");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherKicker() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("2dKc", "AsKh", "7h8d9cKsKd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherTrips() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("8s8h", "7c7s", "7h8d9c3sKd");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherTrips() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("7s7c", "8s8h", "7h8d9c3sKd");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), gru.getPlayerTwoRanking());
	}
}
