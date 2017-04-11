package model;

import java.util.HashSet;
import java.util.Set;

import persistence.Hand;
import persistence.Rank;

public class PairPlusParser extends PairParser {

	@Override
	public boolean matches(String input) {
		if (input.length() == 3) {
			return ((input.charAt(0) == input.charAt(1)) && input.charAt(2) == '+');
		}
		return false;
	}

	@Override
	public Set<Hand> parse(String input) throws InvalidHandException {
		Set<Hand> handToReturn = new HashSet<Hand>();
		
		byte pairRank = Rank.parse(input.charAt(0)).getNumberFormat();
		
		for (int i = 0; i <= 14 - pairRank; i++) {
			byte b = (byte)(pairRank + i);
			String newIn = Character.toString(Rank.parse(b).getCharFormat());
			
			handToReturn.addAll(super.parse(newIn));
		}
		return handToReturn;
	}

}
