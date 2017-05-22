package model;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import persistence.Board;
import persistence.Card;
import persistence.Deck;
import persistence.Hand;
import persistence.Player;

public class MonteCarloSimulation {
	private Player p1;
	private Player p2;
	
	private Board b;
	
	private Deck d;
	
	private Hand h1;
	private Hand h2;
	
	private Set<Card> generated;
	
	public MonteCarloSimulation(List<Hand> p1, List<Hand> p2, Board b) throws InvalidHandException {
		this.p1 = new Player(p1);
		this.p2 = new Player(p2);
		this.b = b;
		d = new Deck();
	}
	
	public void run() throws InvalidHandException{
		for (int i = 0; i < 100000; i++) {
			generated = new HashSet<Card>();
			
			h1 = p1.getRandomHand();
			h2 = p2.getRandomHand();
			
			int counter = 0;
			
			while (h1.equals(h2) || b.equals(h1) || b.equals(h2)) {
				if (p1.getHands().size() == 1 && p2.getHands().size() == 1) {
					throw new InvalidHandException();
				}
				
				if (h1.equals(h2) && p1.getHands().size() == 1) {
					h2 = p2.getRandomHand();
				} else if (h1.equals(h2) && p2.getHands().size() == 1) {
					h1 = p1.getRandomHand();
				} else if (h1.equals(h2)) {
					h1 = p1.getRandomHand();
					h2 = p2.getRandomHand();
				} else if (b.equals(h1) && p1.getHands().size() != 1) {
					h1 = p1.getRandomHand();
				} else if (b.equals(h2) && p2.getHands().size() != 1) {
					h2 = p2.getRandomHand();
				}
				counter++;
				if (counter > 150) {
					throw new InvalidHandException();
				}
	
			}
			
			d.muckCard(h1.getFirstCard());
			d.muckCard(h1.getSecondCard());
			d.muckCard(h2.getFirstCard());
			d.muckCard(h2.getSecondCard());
			d.muckCards(b.getBoard());
			
			
			int cardsToGenerate = (5 - b.getBoard().size());
			
			for (int j = 0; j < cardsToGenerate; j++) {				
				Card c = d.dealRandomCard();
				
				b.addCard(c);
				d.muckCard(c);
				generated.add(c);
			}
			
			p1.clearMadeHand();
			p2.clearMadeHand();
			
			HandEvaluatorUtil.evaluate(h1, b, p1);
			HandEvaluatorUtil.evaluate(h2, b, p2);
			HandEvaluatorUtil.checkWinner(p1, p2, b);
			
			for (Card c: generated) {
				b.removeCard(c);
			}
			
			d.reset();
			b.reset();
		}
	}

	
	public int getPlayerOneWins() {
		return p1.getWinCount();
	}
	
	public int getPlayerTwoWins() {
		return p2.getWinCount();
	}
	
	public int getTies() {
		return p1.getTieCount();
	}
}