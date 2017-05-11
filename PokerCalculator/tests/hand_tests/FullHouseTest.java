package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class FullHouseTest {
	//has to have at least pair
	
	@Test
	public void tieHandSameFullHouseOnePairOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Js7s", "Jh7d", "JdJc7h5hAh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandSameFullHouseTwoPairsOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("JsKs", "Jh3d", "JdJc7hAdAh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandFullHouseOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("JsKs", "Jh3d", "7c7d7hAdAh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherFullHouseOnePairOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("7d7s", "5c5s", "JdJc7h5hAh");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherFullHouseOnePairOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("5s5c", "7d7s", "JdJc7h5hAh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherFullHouseTwoPairsOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ad2c", "Js3h", "JdJc7hAcAh");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherFullHouseTwoPairsOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("3s7s", "KdAh", "3d3c7hKcKh");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsFullHouseOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ad2c", "Js3h", "KcKdKhAhAc");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsFullHouseOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ad2c", "Ks3h", "6h6d6sKhKc");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsPairFullHouse() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("KdKc", "Ks3h", "6h6d6sKhAc");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsPairFullHouse() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("KcAd", "KsKd", "6h6d6sKhAc");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.FULL_HOUSE.ordinal(), gru.getPlayerTwoRanking());
	}
}
