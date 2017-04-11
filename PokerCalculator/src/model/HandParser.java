package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import persistence.Card;
import persistence.Hand;
import persistence.Rank;
import persistence.Suit;

public class HandParser extends Parser {

	@Override
	public boolean matches(String input) {
		// format: AxBy/AxBx
		if (input.length() == 4) {
			return true;
		}
		return false;
	}

	@Override
	public Set<Hand> parse(String input) throws InvalidHandException {
		Set<Hand> handToReturn = new HashSet<Hand>();
		
		try {
			Card firstCard = new Card(Rank.parse(input.charAt(0)), Suit.parse(input.charAt(1)));
			Card secondCard = new Card(Rank.parse(input.charAt(2)), Suit.parse(input.charAt(3)));
			handToReturn.add(new Hand(firstCard, secondCard));
		} catch (InvalidHandException e) {
			return Collections.emptySet();
		}
		return handToReturn;
	}

}
