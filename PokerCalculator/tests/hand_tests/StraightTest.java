package hand_tests;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.InvalidHandException;
import persistence.HandRank;

public class StraightTest {
	@Test
	public void tieHandSameStraight() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("TsJs", "TdJd", "7h8d9c2d3h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandSameStraightOnBoard() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("5h6h", "TdJd", "7h8d9cTsJs");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void tieHandSameCardToMakeStraight() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("9h6h", "9dJd", "7h8dAcTsJs");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(1, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherStraight() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ts6h", "ThJd", "7h8d3c2d9h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsHigherStraight() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("JdTh", "6hTs", "7h8d3c2d9h");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerOneWinsAceLowStraight() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ad4d", "8c8s", "7h8d3c2d5h");
		gru.run();
		
		Assert.assertEquals(1, gru.getPlayerOneWins());
		Assert.assertEquals(0, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}	
	
	@Test
	public void playerTwoWinsHigherStraightVsAceLowStraight() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Ad4d", "4c6s", "7h8d3c2d5h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}
	
	@Test
	public void playerTwoWinsHigherStraightUsingLow() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("Kd4d", "4c6s", "ThAd3c2d5h");
		gru.run();
		
		Assert.assertEquals(0, gru.getPlayerOneWins());
		Assert.assertEquals(1, gru.getPlayerTwoWins());
		Assert.assertEquals(0, gru.getTies());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerOneRanking());
		Assert.assertEquals(HandRank.STRAIGHT.ordinal(), gru.getPlayerTwoRanking());
	}
}
