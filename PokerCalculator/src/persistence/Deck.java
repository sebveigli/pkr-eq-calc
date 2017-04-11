package persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import model.InvalidHandException;

public class Deck {
	private Set<Card> deck;
	private Set<Card> muckedDeck = new HashSet<Card>();
	
	public Deck() throws InvalidHandException {
		final long start = System.nanoTime();
		List<Card> tmpDeck = new ArrayList<Card>();
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 2; j <= 14; j++) {
				tmpDeck.add(new Card(Rank.parse((byte)j), Suit.parse((byte)i)));
			}
		}
		
		tmpDeck = shuffle(tmpDeck);
		
		deck = new LinkedHashSet<Card>(tmpDeck);			
	}
	
	private List<Card> shuffle(List<Card> deck) {
		Collections.shuffle(deck);
		return deck;
	}
	
	public Set<Card> getDeck() {
		return deck;
	}
	
	public void muckCard(Card c) {
		if (deck.contains(c)) {
			deck.remove(c);
			muckedDeck.add(c);
		}
	}
	
	public Set<Card> getMuck() {
		return muckedDeck;
	}
	
}
