package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	public List<Hand> parse(String input) throws InvalidHandException {
		List<Hand> handToReturn = new ArrayList<Hand>();
		
		byte pairRank = Rank.parse(input.charAt(0)).getNumberFormat();
		
		for (int i = 0; i <= 14 - pairRank; i++) {
			byte b = (byte)(pairRank + i);
			String newIn = Character.toString(Rank.parse(b).getCharFormat());
			
			handToReturn.addAll(super.parse(newIn));
		}
		return handToReturn;
	}

}
