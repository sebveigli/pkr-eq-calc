package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;

public class SuitedHandTest {

	@Test
	public void correctHandTest() throws InvalidHandException {
		HandParserUtil.parseRange("AcKc, JdTd, 6s3s, Ah3h");
	}
	
	@Test
	public void multipleCorrectTest() throws InvalidHandException {
		HandParserUtil.parseRange("AA, KK");
	}
	
	@Test(expected = InvalidHandException.class) 
	public void pairIncorrectCardsTest() throws InvalidHandException {
		HandParserUtil.parseRange("");
	}
	
	@Test(expected = InvalidHandException.class)
	public void validAndInvalidCardTest() throws InvalidHandException {
		HandParserUtil.parseRange("AB");
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidAndValidCardTest() throws InvalidHandException {
		HandParserUtil.parseRange("BA");
	}
	
	@Test (expected = InvalidHandException.class)
	public void validAndInvalidHandTest() throws InvalidHandException {
		HandParserUtil.parseRange("AA, BB");
	}
}
