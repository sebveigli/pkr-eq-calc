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
	private List<Card> deck;
	private List<Card> muckedDeck;
	
	private static final int SHUFFLE_CONSTANT = 7;
	
	public Deck() throws InvalidHandException {
		deck = new ArrayList<Card>();
		muckedDeck = new ArrayList<Card>();
		
		for (int i = 1; i <= Suit.values().length; i++) {
			for (int j = 2; j <= Rank.values().length; j++) {
				deck.add(new Card(Rank.parse((byte)j), Suit.parse((byte)i)));
			}
		}
		
		for (int i = 0; i < SHUFFLE_CONSTANT; i++) {
			Collections.shuffle(deck);
		}	
	}
	
	public List<Card> getDeck() {
		return deck;
	}
	
	public void muckCards(List<Card> c) {
		for (Card cur : c) {
			if (deck.remove(cur)) {
				muckedDeck.add(cur);
			}
		}
	}
	
	public void muckCard(Card c) {
		if (deck.remove(c)) {
			muckedDeck.add(c);
		}
	}
	
	public List<Card> getMuck() {
		return muckedDeck;
	}
	
	public void muckHand(Hand h) {
		if (deck.remove(h.getFirstCard())) {
			muckedDeck.add(h.getFirstCard());
		}
		
		if (deck.remove(h.getSecondCard())) {
			muckedDeck.add(h.getSecondCard());
		}
	}
	
	public void removeFromMuck(Card c) {
		if (muckedDeck.remove(c)) {
			deck.add(c);
		}
	}
}