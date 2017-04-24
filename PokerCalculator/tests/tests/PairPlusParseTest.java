package tests;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Hand;

public class PairPlusParseTest {

	@Test
	public void validPairRangeTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("77+"));
		Assert.assertEquals(hand.size(), 48);
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidPairRangeTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("XX+"));
		Assert.assertEquals(hand.size(), 0);
	}
	
}
