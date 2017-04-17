package tests;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Hand;

public class PairRangeParseTest {

	@Test
	public void correctHandTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("QQ-AA"));
		Assert.assertEquals(hand.size(), 18);
	}
	
	@Test
	public void correctHandReverseTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("AA-QQ"));
		Assert.assertEquals(hand.size(), 18);
	}
	
	@Test
	public void correctHandSameRangeTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("QQ-QQ"));
		Assert.assertEquals(hand.size(), 6);
	}
	
	@Test(expected = InvalidHandException.class) 
	public void pairRangeIncorrectCardsTest() throws InvalidHandException {
		HandParserUtil.parseRange("BB-CC");
	}
	
	@Test(expected = InvalidHandException.class)
	public void validAndInvalidHandTest() throws InvalidHandException {
		HandParserUtil.parseRange("AA-BB");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidAndValidCardTest() throws InvalidHandException {
		HandParserUtil.parseRange("BB-AA");
	}
}
