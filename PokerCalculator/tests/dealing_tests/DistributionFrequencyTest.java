package dealing_tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import model.InvalidHandException;
import persistence.Card;
import persistence.Deck;

public class DistributionFrequencyTest {
	
	@Test
	public void distributionFrequencyTest() throws InvalidHandException {
		Deck d = new Deck();
		
		Map<Card, Integer> occ = new HashMap<Card, Integer>();
		
		for (int i = 0; i < 1000000; i++) {
			Card c = d.dealRandomCard();
			Integer prevVal = occ.get(c);
			occ.put(c, prevVal == null ? 1 : prevVal + 1);
		}
		
		for (Map.Entry<Card, Integer> e : occ.entrySet()){
			System.out.println(Character.toString(e.getKey().getRankAsChar()) + Character.toString(e.getKey().getSuitAsChar()) + ": \n" + e.getValue());
		}
	}
}
