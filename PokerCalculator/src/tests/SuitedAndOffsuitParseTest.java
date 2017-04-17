package tests;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Hand;

public class SuitedAndOffsuitParseTest {

	@Test
	public void suitedHandTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("AKs"));
		Assert.assertEquals(hand.size(), 4);
	}
	
	@Test
	public void offsuitHandTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("AKo"));
		Assert.assertEquals(hand.size(), 12);
	}
	
	@Test(expected = InvalidHandException.class) 
	public void invalidSymbolTest() throws InvalidHandException {
		HandParserUtil.parseRange("AKc");
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidRankTest() throws InvalidHandException {
		HandParserUtil.parseRange("ABs");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidRankTest2() throws InvalidHandException {
		HandParserUtil.parseRange("BAs");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidRankTest3() throws InvalidHandException {
		HandParserUtil.parseRange("BAo");
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidRankTest4() throws InvalidHandException {
		HandParserUtil.parseRange("ABo");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidPairSuitedHand() throws InvalidHandException {
		HandParserUtil.parseRange("AAs");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidPairOffsuitHand() throws InvalidHandException {
		HandParserUtil.parseRange("AAo");
	}
}
