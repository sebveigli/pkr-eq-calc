package persistence;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import model.InvalidHandException;

public class Deck {
	private List<Card> deck;
	private List<Card> muckedDeck;
	
	private Random rnd;
	
	public Deck() throws InvalidHandException {
		deck = new ArrayList<Card>();
		muckedDeck = new ArrayList<Card>();
		
		rnd = new Random();
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 2; j <= 14; j++) {
				deck.add(new Card(Rank.parse((byte)j), Suit.parse((byte)i)));
			}
		}
		
		Collections.shuffle(deck);
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
	
	public void reset() {
		deck.addAll(muckedDeck);
		muckedDeck.clear();
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public Card dealRandomCard() {
		return deck.get(rnd.nextInt(deck.size()));
	}
}