package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import persistence.Card;
import persistence.Rank;
import persistence.Suit;

public class BoardParser {

	public boolean matches(String input) {
		// format: AxBy/AxBx
			if (input.length() == 0 || input.length() == 6 || input.length() == 8 || input.length() == 10) {
				return true;
			}
			return false;
	}

	public List<Card> parseBoard(String input) throws InvalidHandException {
		List<Card> cardsToReturn = new ArrayList<Card>();
		
		if (input.length() >= 6) {
			Card firstCard = new Card(Rank.parse(input.charAt(0)), Suit.parse(input.charAt(1)));
			Card secondCard = new Card(Rank.parse(input.charAt(2)), Suit.parse(input.charAt(3)));
			Card thirdCard = new Card(Rank.parse(input.charAt(4)), Suit.parse(input.charAt(5)));
			
			cardsToReturn.add(firstCard);
			cardsToReturn.add(secondCard);
			cardsToReturn.add(thirdCard);
		} 
		
		if (input.length() >= 8) {
			Card fourthCard = new Card(Rank.parse(input.charAt(6)), Suit.parse(input.charAt(7)));
			
			cardsToReturn.add(fourthCard);
		}
		
		if (input.length() == 10) {
			Card fifthCard = new Card(Rank.parse(input.charAt(8)), Suit.parse(input.charAt(9)));
			cardsToReturn.add(fifthCard);
		}
		
		return cardsToReturn;
	}
}
