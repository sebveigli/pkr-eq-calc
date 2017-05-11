package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Hand;
import persistence.Rank;

public class PairRangeParseTest {

	@Test
	public void correctHandTest() throws InvalidHandException {
		List<Hand> hand = new ArrayList<Hand>();
		Map<Byte, Integer> occ = new HashMap<Byte, Integer>();
		
		hand.addAll(HandParserUtil.parseRange("QQ-AA"));
		
		for (int i = 0; i < hand.size(); i++) {
			Assert.assertFalse(hand.get(i).getFirstSuit().equals(hand.get(i).getSecondSuit()));
			
			Integer prevVal = occ.get(hand.get(i).getFirstRankAsByte());
			occ.put(hand.get(i).getFirstRankAsByte(), prevVal == null ? 1 : prevVal + 1);
			
			prevVal = occ.get(hand.get(i).getSecondRankAsByte());
			occ.put(hand.get(i).getSecondRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		Assert.assertTrue(occ.get(Rank.parse('A').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('K').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('Q').getNumberFormat()) == 12);		
		Assert.assertEquals(hand.size(), 18);
	}
	
	@Test
	public void correctHandReverseTest() throws InvalidHandException {
		List<Hand> hand = new ArrayList<Hand>();
		Map<Byte, Integer> occ = new HashMap<Byte, Integer>();
		
		hand.addAll(HandParserUtil.parseRange("AA-QQ"));
		
		for (int i = 0; i < hand.size(); i++) {
			Assert.assertFalse(hand.get(i).getFirstSuit().equals(hand.get(i).getSecondSuit()));
			
			Integer prevVal = occ.get(hand.get(i).getFirstRankAsByte());
			occ.put(hand.get(i).getFirstRankAsByte(), prevVal == null ? 1 : prevVal + 1);
			
			prevVal = occ.get(hand.get(i).getSecondRankAsByte());
			occ.put(hand.get(i).getSecondRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		Assert.assertTrue(occ.get(Rank.parse('A').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('K').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('Q').getNumberFormat()) == 12);		
		Assert.assertEquals(hand.size(), 18);
	}
	
	@Test
	public void correctHandSameRangeTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("QQ-QQ");
		Map<Byte, Integer> occ = new HashMap<Byte, Integer>();
		
		
		for (int i = 0; i < hand.size(); i++) {
			Assert.assertFalse(hand.get(i).getFirstSuit().equals(hand.get(i).getSecondSuit()));
			
			Integer prevVal = occ.get(hand.get(i).getFirstRankAsByte());
			occ.put(hand.get(i).getFirstRankAsByte(), prevVal == null ? 1 : prevVal + 1);
			
			prevVal = occ.get(hand.get(i).getSecondRankAsByte());
			occ.put(hand.get(i).getSecondRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		Assert.assertTrue(occ.get(Rank.parse('Q').getNumberFormat()) == 12);
		Assert.assertEquals(hand.size(), 6);
	}
	
	@Test(expected = InvalidHandException.class) 
	public void pairRangeIncorrectCardsTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("BB-CC");
		
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test(expected = InvalidHandException.class)
	public void validAndInvalidHandTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("AA-BB");
		
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test (expected = InvalidHandException.class)
	public void invalidAndValidCardTest() throws InvalidHandException {
		List<Hand> hand = HandParserUtil.parseRange("BB-AA");
		
		Assert.assertEquals(hand.size(), 0);
	}
}
