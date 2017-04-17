package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import persistence.Card;
import persistence.Hand;
import persistence.Rank;
import persistence.Suit;

public class OffsuitParser extends Parser{

	@Override
	public boolean matches(String input) {
		// offsuit should match with 'ABo'
		if (input.length() == 3) {
			return ((input.charAt(0) != input.charAt(1)) && input.charAt(2) == 'o');
		} return false;
	}

	@Override
	public Set<Hand> parse(String input) throws InvalidHandException {
		Set<Hand> handToReturn = new HashSet<Hand>();
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (i != j) { 
					try {
						Card first = new Card(Rank.parse(input.charAt(0)), Suit.parse((byte)i));
						Card second = new Card(Rank.parse(input.charAt(1)), Suit.parse((byte)j));
						
						handToReturn.add(new Hand(first, second));
					} catch (InvalidHandException e) {
						throw new InvalidHandException();
					}	
				}
			}
		}
		return handToReturn;
	}
	
}
