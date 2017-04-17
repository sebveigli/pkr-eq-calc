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
		List<Card> tmpDeck = new ArrayList<Card>();
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 2; j <= 14; j++) {
				tmpDeck.add(new Card(Rank.parse((byte)j), Suit.parse((byte)i)));
			}
		}
		
		for (int i = 0; i < 7; i++) {
			tmpDeck = shuffle(tmpDeck);
		}
		
		deck = new LinkedHashSet<Card>(tmpDeck);			
	}
	
	private List<Card> shuffle(List<Card> deck) {
		Collections.shuffle(deck);
		return deck;
	}
	
	public Set<Card> getDeck() {
		return deck;
	}
	
	public void muckCards(Set<Card> c) {
		for (Card cur : c) {
			if (deck.contains(cur)) {
				deck.remove(cur);
				muckedDeck.add(cur);
			}
		}
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
	
	public void muckHand(Hand h) {
		if (deck.contains(h.getFirstCard())) {
			deck.remove(h.getFirstCard());
			muckedDeck.add(h.getFirstCard());
		}
		
		if (deck.contains(h.getSecondCard())) {
			deck.remove(h.getSecondCard());
			muckedDeck.add(h.getSecondCard());
		}
	}
	
	public void removeFromMuck(Card c) {
		if (muckedDeck.contains(c)) {
			deck.add(c);
			muckedDeck.remove(c);
		}
	}
}
