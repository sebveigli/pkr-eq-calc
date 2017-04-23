package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import persistence.Hand;
import persistence.Rank;

public class PairRangeParser extends PairParser {

	@Override
	public boolean matches(String input) {
		// should be of input XX-YY
		if (input.length() == 5) {
			return ((input.charAt(0) == input.charAt(1)) && (input.charAt(3) == input.charAt(4)) && (input.charAt(2) == '-'));
		}
		return false;
	}

	@Override
	public List<Hand> parse(String input) throws InvalidHandException {
		List<Hand> handToReturn = new ArrayList<Hand>();
		
		byte firstRank = Rank.parse(input.charAt(0)).getNumberFormat();
		byte secondRank = Rank.parse(input.charAt(3)).getNumberFormat();
		
		// Determine which card is higher (to handle input: e.g. 66-33, 33-66 &
		// 66-66)
		if ((int)firstRank > (int)secondRank) {
			
			for (int i = 0; i <= firstRank - secondRank; i++) {
				String newIn = Character.toString(Rank.parse((byte)(secondRank + i)).getCharFormat());
				handToReturn.addAll(super.parse(newIn));
	
			}
		} else if (firstRank < secondRank) {
			for (int i = 0; i <= secondRank - firstRank; i++) {
				String newIn = Character.toString(Rank.parse((byte)(firstRank + i)).getCharFormat());
				handToReturn.addAll(super.parse(newIn));
			}
		} else {
			String newIn = Character.toString(Rank.parse((byte)(firstRank)).getCharFormat());
			handToReturn.addAll(super.parse(newIn));
		}
		return handToReturn;
	}

}
