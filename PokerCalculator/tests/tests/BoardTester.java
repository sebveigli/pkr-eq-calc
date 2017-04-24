package tests;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import model.GameRunUtil;
import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Board;
import persistence.Card;

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
		
		Assert.assertEquals(true, b.hasFlush());
		Assert.assertEquals(true, b.checkStraight());
		Assert.assertEquals(5, b.getBoard().size());
	}
	
	@Test
	public void validFiveCardBoardNoFlushNoStraight() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h6c2d");
		
		Assert.assertEquals(false, b.hasFlush());
		Assert.assertEquals(false, b.checkStraight());
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

}
