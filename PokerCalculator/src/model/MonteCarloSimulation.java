package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import persistence.Board;
import persistence.Card;
import persistence.Deck;
import persistence.Hand;
import persistence.Player;
import persistence.Rank;

public class MonteCarloSimulation {
	// implement fully later
	
	private Player p1;
	private Player p2;
	
	private Board b;
	
	
	
	//public static run(Player p1, Player p2, Hand h1, Hand h2)
	public MonteCarloSimulation(List<Hand> p1, List<Hand> p2, Board b) {
		this.p1 = new Player(p1);
		this.p2 = new Player(p2);
		this.b = b;
	}
	
	public void run() throws InvalidHandException{
		Hand h1;
		Hand h2;
		Deck d = new Deck();
		
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
		
		for (int i = 0; i < cardsToGenerate; i++) {
			int randDraw = ThreadLocalRandom.current().nextInt(0, d.getDeck().size());
			int current = 0;
			
			for (Card c : d.getDeck()) {
				if (current == randDraw) {
					b.addCard(c);
					d.muckCard(c);
					break;
				}
				current++;
			}
		}
		
		
		HandEvaluatorUtil.evaluate(h1, b, p1);
		//HandEvaluatorUtil.evaluate(h2, b, p2);
		
		for (Card c: p1.getMadeHand()) {
			System.out.println(Character.toString(c.getRankAsChar()));
		}
		
		
	}
	
	
	
	public int getPlayerOneWins() {
		return p1.getWinCount();
	}
	
	public int getPlayerTwoWins() {
		return p2.getWinCount();
	}
	
	public int getPlayerOneTies() {
		return p1.getTieCount();
	}
	
	public int getPlayerTwoTies() {
		return p2.getTieCount();
	}

	public int getPlayerOneStrength() {
		return p1.getHandRanking();
	}
	
	public int getPlayerTwoStrength() {
		return p2.getHandRanking();
	}
}