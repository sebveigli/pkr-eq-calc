package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import model.HandParserUtil;
import model.InvalidHandException;
import persistence.Hand;
import persistence.Rank;

public class PairPlusParseTest {

	@Test
	public void validPairRangeTest() throws InvalidHandException {
		List<Hand> hand = new ArrayList<Hand>();
		Map<Byte, Integer> occ = new HashMap<Byte, Integer>();
		
		hand.addAll(HandParserUtil.parseRange("77+"));
		
		for (int i = 0; i < hand.size(); i++) {
			Assert.assertFalse(hand.get(i).getFirstSuit().equals(hand.get(i).getSecondSuit()));
			
			Integer prevVal = occ.get(hand.get(i).getFirstRankAsByte());
			occ.put(hand.get(i).getFirstRankAsByte(), prevVal == null ? 1 : prevVal + 1);
			
			prevVal = occ.get(hand.get(i).getSecondRankAsByte());
			occ.put(hand.get(i).getSecondRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		Assert.assertEquals(hand.size(), 48);
		Assert.assertTrue(occ.get(Rank.parse('A').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('K').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('Q').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('J').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('T').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('9').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('8').getNumberFormat()) == 12);
		Assert.assertTrue(occ.get(Rank.parse('7').getNumberFormat()) == 12);
		
		
		
		
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidPairRangeTest() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("XX+"));
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidPairRangeTest2() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("AX+"));
		Assert.assertEquals(hand.size(), 0);
	}
	
	@Test(expected = InvalidHandException.class)
	public void invalidPairRangeTest3() throws InvalidHandException {
		Set<Hand> hand = new HashSet<Hand>();
		hand.addAll(HandParserUtil.parseRange("XA+"));
		Assert.assertEquals(hand.size(), 0);
	}
}
