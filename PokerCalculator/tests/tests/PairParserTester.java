package tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Hand;
import persistence.Rank;

public class PairParserTester {

	@Test
	public void correctHandTest() throws InvalidHandException {
		List<Hand> handRange = HandParserUtil.parseRange("AA");
		Map<Byte, Integer> occ = new HashMap<Byte, Integer>();
		
		for (int i = 0; i < handRange.size(); i++) {
			Assert.assertTrue(handRange.get(i).getFirstRank().equals("A"));
			Assert.assertTrue(handRange.get(i).getSecondRank().equals("A"));
			
			Assert.assertFalse(handRange.get(i).getFirstSuit().equals(handRange.get(i).getSecondSuit()));
			Integer prevVal = occ.get(handRange.get(i).getFirstRankAsByte());
			occ.put(handRange.get(i).getFirstRankAsByte(), prevVal == null ? 1 : prevVal + 1);
			
			prevVal = occ.get(handRange.get(i).getSecondRankAsByte());
			occ.put(handRange.get(i).getSecondRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		Assert.assertTrue(handRange.size() == 6);
		Assert.assertTrue(occ.get(Rank.parse('A').getNumberFormat()) == 12);
	}
	
	@Test
	public void multipleCorrectTest() throws InvalidHandException {
		List<Hand> handRange = HandParserUtil.parseRange("AA, KK");
		Map<Byte, Integer> occ = new HashMap<Byte, Integer>();
		
		for (int i = 0; i < handRange.size(); i++) {
			Assert.assertTrue(handRange.get(i).getFirstRank().equals("A") || handRange.get(i).getFirstRank().equals("K"));
			Assert.assertTrue(handRange.get(i).getSecondRank().equals("A")|| handRange.get(i).getSecondRank().equals("K"));	
			Assert.assertFalse(handRange.get(i).getFirstSuit().equals(handRange.get(i).getSecondSuit()));
			Integer prevVal = occ.get(handRange.get(i).getFirstRankAsByte());
			occ.put(handRange.get(i).getFirstRankAsByte(), prevVal == null ? 1 : prevVal + 1);
			
			prevVal = occ.get(handRange.get(i).getSecondRankAsByte());
			occ.put(handRange.get(i).getSecondRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		Assert.assertTrue(handRange.size() == 12);
		Assert.assertTrue(occ.get(Rank.parse('A').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('K').getNumberFormat()) == 12);
	}
	
	@Test(expected = InvalidHandException.class) 
	public void pairIncorrectCardsTest() throws InvalidHandException {
		List<Hand> handRange = HandParserUtil.parseRange("BB");
		
		Assert.assertEquals(0, handRange.size());
	}
	
	@Test(expected = InvalidHandException.class)
	public void validAndInvalidCardTest() throws InvalidHandException {
		List<Hand> handRange = HandParserUtil.parseRange("AB");
		
		Assert.assertEquals(0, handRange.size());
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidAndValidCardTest() throws InvalidHandException {
		List<Hand> handRange = HandParserUtil.parseRange("BA");
		
		Assert.assertEquals(0, handRange.size());
	}
	
	@Test (expected = InvalidHandException.class)
	public void validAndInvalidHandTest() throws InvalidHandException {
		List<Hand> handRange = HandParserUtil.parseRange("AA, BB");
		
		Assert.assertEquals(0, handRange.size());
	}
}
