package tests;

import java.util.Arrays;

import org.junit.Test;

import junit.framework.Assert;
import model.GameRunUtil;
import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Board;
import persistence.Card;

@SuppressWarnings("deprecation")
public class BoardTester {

	@Test
	public void validThreeCardBoard() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h");
		
		Assert.assertEquals(3, b.getBoard().size());
	}
	
	@Test
	public void validFourCardBoard() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h4c");
		
		Assert.assertEquals(4, b.getBoard().size());
	}
	
	@Test
	public void validFiveCardBoardFlushAndStraight() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h4c3c");
		
		Assert.assertEquals(true, b.getFlush());
		Assert.assertEquals(true, b.getStraight());
		Assert.assertEquals(5, b.getBoard().size());
	}
	
	@Test
	public void validFiveCardBoardNoFlushNoStraight() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h4c2d");
		
		Assert.assertEquals(false, b.getFlush());
		Assert.assertEquals(false, b.getStraight());
		Assert.assertEquals(5,  b.getBoard().size());
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidBoardSuit() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKx7h4c3c");
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidBoardRank() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKdXh4c3c");
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidLengthBoard() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKs");
	}
	
	@Test
	public void test() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("AA", "KK", "2d7c4h");
		
		for (int i = 0; i < 1000000; i++) {
			gru.runMonteCarloSimulation();
			
			for (Card c : gru.getBoard()) {
				System.out.print(Character.toString(c.getRankAsChar()) + Character.toString(c.getSuitAsChar()));
			}
			System.out.println("");
			gru.reset();
		}
		for (int i = 0; i < gru.getFreqSize(); i++) {
			System.out.println(i + ": " + gru.getFreq(i));
		}
		System.out.println();
		
		
	}
}
