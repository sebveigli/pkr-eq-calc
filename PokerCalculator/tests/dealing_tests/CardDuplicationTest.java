package dealing_tests;

import org.junit.Assert;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Board;
import persistence.Card;
import persistence.Deck;
import persistence.Rank;
import persistence.Suit;

public class CardDuplicationTest {
	
	@Test
	public void removingCardsFromDeckAndResetting() throws InvalidHandException {
		Deck d = new Deck();
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('h'));
		
		Assert.assertTrue(d.getDeck().contains(c1));
		Assert.assertTrue(d.getDeck().contains(c2));
		
		d.muckCard(c1);
		
		Assert.assertFalse(d.getDeck().contains(c1));
		Assert.assertTrue(d.getDeck().contains(c2));
		
		d.muckCard(c2);
		
		Assert.assertFalse(d.getDeck().contains(c1));
		Assert.assertFalse(d.getDeck().contains(c2));
		
		d.reset();
		
		Assert.assertTrue(d.getDeck().contains(c1));
		Assert.assertTrue(d.getDeck().contains(c2));
	}
	
	
}
