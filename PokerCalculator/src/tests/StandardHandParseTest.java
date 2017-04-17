package tests;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Hand;

public class StandardHandParseTest {

	@Test
	public void allSuitHandsTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("AcKc, JdTd, 6s3s, 7h9h"));
		Assert.assertEquals(hand.size(), 4);
	}
	
	@Test
	public void allOffsuitHandsTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("AcKd, JhTs"));
		Assert.assertEquals(hand.size(), 2);
	}
	
	@Test(expected = InvalidHandException.class) 
	public void invalidHandWrongSuitTest() throws InvalidHandException {
		HandParserUtil.parseRange("AcKx");
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidHandWrongSuitTest2() throws InvalidHandException {
		HandParserUtil.parseRange("AxKc");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidHandWrongRankTest() throws InvalidHandException {
		HandParserUtil.parseRange("XcKs");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidHandWrongRankTest2() throws InvalidHandException {
		HandParserUtil.parseRange("AcXs");
	}
}
