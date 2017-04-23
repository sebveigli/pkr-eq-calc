package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
	public List<Hand> parse(String input) throws InvalidHandException {
		List<Hand> handToReturn = new ArrayList<Hand>();
		
		try {
			Card firstCard = new Card(Rank.parse(input.charAt(0)), Suit.parse(input.charAt(1)));
			Card secondCard = new Card(Rank.parse(input.charAt(2)), Suit.parse(input.charAt(3)));
			handToReturn.add(new Hand(firstCard, secondCard));
		} catch (InvalidHandException e) {
			throw new InvalidHandException();
		}
		return handToReturn;
	}

}
