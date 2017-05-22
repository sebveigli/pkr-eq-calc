package model;

import java.util.List;
import persistence.Board;
import persistence.Deck;
import persistence.Hand;
import persistence.Player;

public class ExhaustiveSimulation {
	private Player p1;
	private Player p2;
	
	private Deck d;
	
	private Board b;
	
	private Hand h1;
	private Hand h2;
	
	public ExhaustiveSimulation(List<Hand> p1, List<Hand> p2, Board b) throws InvalidHandException {
		this.p1 = new Player(p1);
		this.p2 = new Player(p2);
		this.b = b;
		d = new Deck();
	}
	
	public void run() throws InvalidHandException {
		
		int handsMade = 0;
		// run through all cards in P1's range and P2's range
		for (int i = 0; i < p1.getHands().size(); i++) {
			h1 = p1.getHands().get(i);
			for (int j = 0; j < p2.getHands().size(); j++) {
				h2 = p2.getHands().get(j);
				
				
				// added code to check for which case triggered it
					
				if (b.equals(h1)) {
					break;
				} else if (b.equals(h2)) {
					continue;
				} else if (h1.equals(h2)){
					continue;
				}
				
				handsMade++;
				
				d.reset();
				d.shuffle();
							
				// remove used cards from deck before dealing
				d.muckHand(h1);
				d.muckHand(h2);
				d.muckCards(b.getBoard());
				
				// since we need to generate the amount of cards missing from the board
				// we will only ever have cases 0, 1, 2 as monte carlo will always run from 
				switch(5 - b.getBoard().size()) {
				
				case 2:
					for (int k = 0; k < d.getDeck().size() - 1; k++) {
						for (int l = k + 1; l < d.getDeck().size(); l++) {
							if (k != l) {
							b.addCard(d.getDeck().get(k));
							b.addCard(d.getDeck().get(l));
							
							p1.clearMadeHand();
							p2.clearMadeHand();
							
							HandEvaluatorUtil.evaluate(h1, b, p1);
							HandEvaluatorUtil.evaluate(h2, b, p2);
							HandEvaluatorUtil.checkWinner(p1, p2, b);
							
							b.removeCard(d.getDeck().get(k));
							b.removeCard(d.getDeck().get(l));
							b.reset();
							}
						}
					}
					break;
					
				case 1:
					for (int k = 0; k < d.getDeck().size(); k++) {
						b.addCard(d.getDeck().get(k));
						
						p1.clearMadeHand();
						p2.clearMadeHand();
						
						HandEvaluatorUtil.evaluate(h1, b, p1);
						HandEvaluatorUtil.evaluate(h2, b, p2);
						
						HandEvaluatorUtil.checkWinner(p1, p2, b);
						b.removeCard(d.getDeck().get(k));
						b.reset();
					}
					
					break;
				
				case 0:
					p1.clearMadeHand();
					p2.clearMadeHand();
					
					HandEvaluatorUtil.evaluate(h1, b, p1);
					HandEvaluatorUtil.evaluate(h2, b, p2);
					HandEvaluatorUtil.checkWinner(p1, p2, b);
					
					b.reset();
					break;
				}
			
			}
		}
		
		if (handsMade == 0) throw new InvalidHandException();
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
	
	public int getPlayerOneHandRanking() {
		return p1.getHandRanking();
	}
	
	public int getPlayerTwoHandRanking() {
		return p1.getHandRanking();
	}
}
