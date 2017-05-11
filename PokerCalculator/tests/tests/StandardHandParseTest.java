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

public class StandardHandParseTest {

	@Test
	public void allSuitHandsTest() throws InvalidHandException {
		List<Hand> hand = new ArrayList<Hand>();
		hand.addAll(HandParserUtil.parseRange("AcKc, JdTd, 6s3s, 7h9h"));
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('c'));
		
		Card c3 = new Card(Rank.parse('J'), Suit.parse('d'));
		Card c4 = new Card(Rank.parse('T'), Suit.parse('d'));
		
		Card c5 = new Card(Rank.parse('6'), Suit.parse('s'));
		Card c6 = new Card(Rank.parse('3'), Suit.parse('s'));
		
		Card c7 = new Card(Rank.parse('7'), Suit.parse('h'));
		Card c8 = new Card(Rank.parse('9'), Suit.parse('h'));
		
		Hand h1 = new Hand(c1, c2);
		Hand h2 = new Hand(c3, c4);
		Hand h3 = new Hand(c5, c6);
		Hand h4 = new Hand(c7, c8);
		
		Assert.assertEquals(hand.size(), 4);
		
		Assert.assertTrue(hand.contains(h1));
		Assert.assertTrue(hand.contains(h2));
		Assert.assertTrue(hand.contains(h3));
		Assert.assertTrue(hand.contains(h4));
		}
	
	@Test
	public void allOffsuitHandsTest() throws InvalidHandException {
		List<Hand> hand = new ArrayList<Hand>();
		
		hand.addAll(HandParserUtil.parseRange("AcKd, JhTs"));
		
		
		Card c1 = new Card(Rank.parse('A'), Suit.parse('c'));
		Card c2 = new Card(Rank.parse('K'), Suit.parse('d'));
		
		Card c3 = new Card(Rank.parse('J'), Suit.parse('h'));
		Card c4 = new Card(Rank.parse('T'), Suit.parse('s'));
		
		Hand h1 = new Hand(c1, c2);
		Hand h2 = new Hand(c3, c4);
		
		Assert.assertEquals(hand.size(), 2);
		Assert.assertTrue(hand.contains(h1));
		Assert.assertTrue(hand.contains(h2));
	
	}
	
	@Test(expected = InvalidHandException.class) 
	public void invalidHandWrongSuitTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("AcKx");
		
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidHandWrongSuitTest2() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("AxKc");
		
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidHandWrongRankTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("XcKs");
		
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidHandWrongRankTest2() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("AcXs");
	
		Assert.assertEquals(hand.size(), 0);
	}
}
