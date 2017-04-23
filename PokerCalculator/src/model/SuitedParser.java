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

public class SuitedParser extends Parser {

	@Override
	public boolean matches(String input) {
		// suited should match with 'ABo'
		if (input.length() == 3) {
			return ((input.charAt(0) != input.charAt(1)) && input.charAt(2) == 's');
		} return false;
	}

	@Override
	public List<Hand> parse(String input) throws InvalidHandException {
		List<Hand> handToReturn = new ArrayList<Hand>();
		
		for (int i = 1; i <= 4; i++) {
			try {
				Card firstCard = new Card(Rank.parse(input.charAt(0)), Suit.parse((byte) i));
				Card secondCard = new Card(Rank.parse(input.charAt(1)), Suit.parse((byte) i));
				
				handToReturn.add(new Hand(firstCard, secondCard));
			} catch (InvalidHandException e) {
				throw new InvalidHandException();
			}	
		}
		return handToReturn;
	}

}
