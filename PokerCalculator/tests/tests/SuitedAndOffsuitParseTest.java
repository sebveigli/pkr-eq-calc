package tests;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Card;
import persistence.Hand;
import persistence.Rank;
import persistence.Suit;

public class SuitedAndOffsuitParseTest {

	@Test
	public void suitedHandTest() throws InvalidHandException {
		List<Hand> hand = new ArrayList<Hand>();
	
		hand.addAll(HandParserUtil.parseRange("AKs"));
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('c'));
		
		Card c3 = new Card(Rank.parse('A'), Suit.parse('d'));
		Card c4 = new Card(Rank.parse('K'), Suit.parse('d'));
		
		Card c5 = new Card(Rank.parse('A'), Suit.parse('h'));
		Card c6 = new Card(Rank.parse('K'), Suit.parse('h'));
		
		Card c7 = new Card(Rank.parse('A'), Suit.parse('s'));
		Card c8 = new Card(Rank.parse('K'), Suit.parse('s'));
		
		
		Hand h1 = new Hand(c1, c2);
		Hand h2 = new Hand(c3, c4);
		Hand h3 = new Hand(c5, c6);
		Hand h4 = new Hand(c7, c8);
		
		Assert.assertTrue(hand.contains(h1));
		Assert.assertTrue(hand.contains(h2));
		Assert.assertTrue(hand.contains(h3));
		Assert.assertTrue(hand.contains(h4));
		Assert.assertEquals(hand.size(), 4);
	}
	
	@Test
	public void offsuitHandTest() throws InvalidHandException {
		List<Hand> hand = new ArrayList<Hand>();
		hand.addAll(HandParserUtil.parseRange("AKo"));
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('d'));
		
		Card c3 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c4 = new Card(Rank.parse('K'), Suit.parse('h'));
		
		Card c5 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c6 = new Card(Rank.parse('K'), Suit.parse('s'));
		
		Card c7 = new Card(Rank.parse('A'), Suit.parse('d'));
		Card c8 = new Card(Rank.parse('K'), Suit.parse('h'));
		
		Card c9 = new Card(Rank.parse('A'), Suit.parse('d'));
		Card c10 = new Card(Rank.parse('K'), Suit.parse('s'));
		
		Card c11 = new Card(Rank.parse('A'), Suit.parse('h'));
		Card c12 = new Card(Rank.parse('K'), Suit.parse('s'));
		
		// Inverse
		
		Card c13 = new Card(Rank.parse('K'), Suit.parse('c'));
		Card c14 = new Card(Rank.parse('A'), Suit.parse('d'));
		
		Card c15 = new Card(Rank.parse('K'), Suit.parse('c'));
		Card c16 = new Card(Rank.parse('A'), Suit.parse('h'));
		
		Card c17 = new Card(Rank.parse('K'), Suit.parse('c'));
		Card c18 = new Card(Rank.parse('A'), Suit.parse('s'));
		
		Card c19 = new Card(Rank.parse('K'), Suit.parse('d'));
		Card c20 = new Card(Rank.parse('A'), Suit.parse('h'));
		
		Card c21 = new Card(Rank.parse('K'), Suit.parse('d'));
		Card c22 = new Card(Rank.parse('A'), Suit.parse('s'));
		
		Card c23 = new Card(Rank.parse('K'), Suit.parse('h'));
		Card c24 = new Card(Rank.parse('A'), Suit.parse('s'));
		
		Hand h1 = new Hand(c1, c2);
		Hand h2 = new Hand(c3, c4);
		Hand h3 = new Hand(c5, c6);
		Hand h4 = new Hand(c7, c8);
		Hand h5 = new Hand(c9, c10);
		Hand h6 = new Hand(c11, c12);
		Hand h7 = new Hand(c13, c14);
		Hand h8 = new Hand(c15, c16);
		Hand h9 = new Hand(c17, c18);
		Hand h10 = new Hand(c19, c20);
		Hand h11 = new Hand(c21, c22);
		Hand h12 = new Hand(c23, c24);
		
		Assert.assertTrue(hand.contains(h1));
		Assert.assertTrue(hand.contains(h2));
		Assert.assertTrue(hand.contains(h3));
		Assert.assertTrue(hand.contains(h4));
		Assert.assertTrue(hand.contains(h5));
		Assert.assertTrue(hand.contains(h6));
		Assert.assertTrue(hand.contains(h7));
		Assert.assertTrue(hand.contains(h8));
		Assert.assertTrue(hand.contains(h9));
		Assert.assertTrue(hand.contains(h10));
		Assert.assertTrue(hand.contains(h11));
		Assert.assertTrue(hand.contains(h12));
		
		
		Assert.assertEquals(hand.size(), 12);
	}
	
	@Test(expected = InvalidHandException.class) 
	public void invalidSymbolTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("AKc");
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidRankTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("ABs");
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidRankTest2() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("BAs");
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidRankTest3() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("BAo");
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidRankTest4() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("ABo");
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidPairSuitedHand() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("AAs");
		Assert.assertEquals(hand.size(), 0);
	}

}
