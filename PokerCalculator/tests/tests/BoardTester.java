package tests;

import org.junit.Assert;	
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Board;
import persistence.Card;
import persistence.Rank;
import persistence.Suit;

public class BoardTester {

	@Test
	public void noCardBoard() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("");
		
		Assert.assertEquals(0, b.getBoard().size());
	}

	@Test
	public void validThreeCardBoard() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h");
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('h'));
		Card c3 = new Card(Rank.parse('7'), Suit.parse('h'));
		
		Assert.assertEquals(3, b.getBoard().size());
		Assert.assertTrue(b.getBoard().contains(c1));
		Assert.assertTrue(b.getBoard().contains(c2));
		Assert.assertTrue(b.getBoard().contains(c3));
	}
	
	@Test
	public void validFourCardBoard() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h4c");
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('h'));
		Card c3 = new Card(Rank.parse('7'), Suit.parse('h'));
		Card c4 = new Card(Rank.parse('4'), Suit.parse('c'));
		
		
		Assert.assertEquals(4, b.getBoard().size());
		Assert.assertTrue(b.getBoard().contains(c1));
		Assert.assertTrue(b.getBoard().contains(c2));
		Assert.assertTrue(b.getBoard().contains(c3));
		Assert.assertTrue(b.getBoard().contains(c4));
	}
	
	@Test
	public void validFiveCardBoardFlushAndStraight() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h4c3c");
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('h'));
		Card c3 = new Card(Rank.parse('7'), Suit.parse('h'));
		Card c4 = new Card(Rank.parse('4'), Suit.parse('c'));
		Card c5 = new Card(Rank.parse('3'), Suit.parse('c'));
		
		
		Assert.assertEquals(5, b.getBoard().size());
		Assert.assertTrue(b.getBoard().contains(c1));
		Assert.assertTrue(b.getBoard().contains(c2));
		Assert.assertTrue(b.getBoard().contains(c3));
		Assert.assertTrue(b.getBoard().contains(c4));
		Assert.assertTrue(b.getBoard().contains(c5));
		Assert.assertTrue(b.hasFlush());
		Assert.assertTrue(b.checkStraight());
		
	}
	
	@Test
	public void validFiveCardBoardNoFlushNoStraight() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKh7h6c2d");
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('h'));
		Card c3 = new Card(Rank.parse('7'), Suit.parse('h'));
		Card c4 = new Card(Rank.parse('6'), Suit.parse('c'));
		Card c5 = new Card(Rank.parse('2'), Suit.parse('d'));
		
		Assert.assertFalse(b.hasFlush());
		Assert.assertFalse(b.checkStraight());
		Assert.assertEquals(5,  b.getBoard().size());
		Assert.assertTrue(b.getBoard().contains(c1));
		Assert.assertTrue(b.getBoard().contains(c2));
		Assert.assertTrue(b.getBoard().contains(c3));
		Assert.assertTrue(b.getBoard().contains(c4));
		Assert.assertTrue(b.getBoard().contains(c5));
		
		
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidBoardSuit() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKx7h4c3c");
		
		Assert.assertEquals(0,  b.getBoard().size());
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidBoardRank() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKdXh4c3c");
		
		Assert.assertEquals(0,  b.getBoard().size());
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidLengthBoard() throws InvalidHandException {
		Board b = HandParserUtil.parseBoard("AcKs");
		
		Assert.assertEquals(0,  b.getBoard().size());
	}

}
