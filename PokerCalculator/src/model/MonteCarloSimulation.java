package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import persistence.Board;
import persistence.Card;
import persistence.Deck;
import persistence.Hand;
import persistence.Player;

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
		
		System.out.println("Size: " + b.getBoard().size());
		
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
		
		getHandRank(h1, p1);
		System.out.println(p1.getHandRanking());
		
		for (Card c : p1.getMadeHand()) {
			System.out.println(Character.toString(c.getRankAsChar()) + Character.toString(c.getSuitAsChar()));
		}
	}
	
	private void getHandRank(Hand h, Player p) {
		List<Card> handAndBoard = new ArrayList<Card>();
		
		Card highCard = null;
		List<Card> madeHand = new ArrayList<Card>();
		
		handAndBoard.add(h.getFirstCard());
		handAndBoard.add(h.getSecondCard());
		handAndBoard.addAll(b.getBoard());
		
		// we want to sort the board backwards, that is, have the highest cards first, down to the lowest
		// this lets us operate better on straights/flushes to be able to find the highest combination
		handAndBoard.sort(Comparator.comparing(Card::getRankAsByte).reversed());
		
		// straight flush/royal flush = flush with a straight
		if (b.hasFlush() && b.checkStraight()) {
			List<Card> flushCards = new ArrayList<Card>();
			int failCount = 0;
			byte currentRank = 0;
			byte cardsFound = 0;
			
			// check for all the cards relevant to the flush, if we have 3 cards from 7 that do not make a flush, then we cannot have a flush so we exit this loop.
			for (Card c : handAndBoard) {
				if (c.getSuitAsByte() == b.getFlushSuit()) {
					flushCards.add(c);
				} else failCount++;
				
				if (failCount == 3) {
					break;
				}
			}
			
			// at this point, we check to make sure the size is >= 5, and from those cards we want to check whether they make a straight (5 cards in a row)
			if (flushCards.size() >= 5) {
				for (Card fc : flushCards) {
					if (currentRank == 0) {
						cardsFound++;
						currentRank= fc.getRankAsByte();
					} else {
						if (fc.getRankAsByte() == currentRank - 1) {
							currentRank--;
							cardsFound++;
						} else break;
					}
				}
			}
			
			// now we check how many cards we found, and if we found more than 5, that means we have a straight flush using 6 or 7 cards, therefore
			// we must remove the lowest cards from the list - maybe we can just check for when size = 5 in the method above, and then just use that,
			// as we are using an ordered list, we can always guarantee that first 5 cards found will be the highest 5
			if (cardsFound >= 5) {
				for (int i = 0; i < (cardsFound - 5); i++) {
					flushCards.remove(flushCards.size() - 1);
				}
					
				p.setMadeHand(flushCards);
				p.setHandRanking(8);
				return;
				}
			}
		
		
		// now we check for 4 of a kind, we have multiple ways we can achieve 4 of a kind
		// either the board has 4 of the same card, in which case we add those 4 and then find the highest non 4 of a kind card to use as our kicker
		// or, the board has 3 of a kind, and the player has the remaining card for 4 of a kind, and then we find the highest non 4 of a kind card to add as our kick
		// or, we have a pair on the board, and the player must have a pair of the same.
		
		if (b.getQuads()) {			
			for (Card c: handAndBoard) {
				if (highCard == null) {
					if (c.getRankAsByte() != b.getQuadRank()) {
						highCard = c;
						return;
					} else madeHand.add(c);
				} 
				
				if (c.getRankAsByte() == b.getQuadRank()) {
					madeHand.add(c);
				} else if (c.getRankAsByte() > highCard.getRankAsByte()) {
					highCard = c;
				}
			}
			
			madeHand.add(highCard);
			p.setMadeHand(madeHand);
			p.setHandRanking(7);
			return;
		
		// checking for 3 of a kind + same card in players hand
		} else if (b.getTrips()) {
			if (h.getFirstCard().getRankAsByte() == b.getTripRank() || h.getSecondCard().getRankAsByte() == b.getTripRank()) {
				for (Card c: handAndBoard) {
					if (highCard == null) {
						if (c.getRankAsByte() != b.getTripRank()) {
							highCard = c;
							break;
						}
					}
					
					if (c.getRankAsByte() == b.getTripRank()) {
						madeHand.add(c);
					} else if (c.getRankAsByte() > highCard.getRankAsByte()) {
						highCard = c;
					}
				}
				
				madeHand.add(highCard);
				p.setMadeHand(madeHand);
				p.setHandRanking(7);
				return;
				
			// while we are here, we can also check whether the player has a full house, if there are trips, and the player has a pair in their hand
			} else if (h.getFirstCard().getRankAsByte() == h.getSecondCard().getRankAsByte()) {
				madeHand.add(h.getFirstCard());
				madeHand.add(h.getSecondCard());
				
				for (Card c : handAndBoard) {
					if (c.getRankAsByte() == b.getTripRank()) {
						madeHand.add(c);
					}
				}
				p.setMadeHand(madeHand);
				p.setHandRanking(6);
				return;
			}
			
		// 4 of a kind, with pair on board + pair in hand of same rank
		} else if (b.getPair()) {
			if (h.getFirstCard().getRankAsByte() == b.getPairRank() && h.getSecondCard().getRankAsByte() == b.getPairRank()) {			
				for (Card c: handAndBoard) {
					if (highCard == null) {
						if (c.getRankAsByte() != b.getPairRank()) {
							highCard = c;
							return;
						}
						
					}if (c.getRankAsByte() == b.getPairRank()) {
						madeHand.add(c);
					} else if (c.getRankAsByte() > highCard.getRankAsByte()) {
						highCard = c;
					}
				}
				madeHand.add(highCard);
				p.setMadeHand(madeHand);
				p.setHandRanking(7);
				return;
				
			// here, we can also check for full house, if there is a pair on the board, and the player either has
			// one of the pair cards, plus another card on the board
			// or, the player has a pair in his hand, which corresponds with a card on the board
			} else if (h.getFirstCard().getRankAsByte() == b.getPairRank()) {				
				for (Card c: handAndBoard) {
					// make sure we aren't checking the same card against itself
					if (!(c.equals(h.getFirstCard())) && !(c.equals(h.getSecondCard())) && (c.getRankAsByte() == h.getSecondCard().getRankAsByte())) {
						madeHand.add(h.getSecondCard());
						madeHand.add(c);
						
						for (Card cs : handAndBoard) {
							if (cs.getRankAsByte() == b.getPairRank()) {
								madeHand.add(cs);
							}
						}
						System.out.println("size: " + madeHand.size());
						p.setMadeHand(madeHand);
						p.setHandRanking(6);
						return;
					}
				}
			} else if (h.getSecondCard().getRankAsByte() == b.getPairRank()) {
				for (Card c: handAndBoard) {
					if (!(c.equals(h.getSecondCard())) && !(c.equals(h.getFirstCard())) && (c.getRankAsByte() == h.getFirstCard().getRankAsByte())) {
						madeHand.add(h.getFirstCard());
						madeHand.add(c);
						
						for (Card cs : handAndBoard) {
							if (cs.getRankAsByte() == b.getPairRank()) {
								madeHand.add(cs);
							}
						}
						p.setMadeHand(madeHand);
						p.setHandRanking(6);
						return;
					}
				}
			} else if (h.getFirstCard().getRankAsByte() == h.getSecondCard().getRankAsByte()) {
				for (Card c: handAndBoard) {
					if (!(c.equals(h.getFirstCard())) && !(c.equals(h.getSecondCard())) && (c.getRankAsByte() == h.getFirstCard().getRankAsByte())) {
						madeHand.add(h.getFirstCard());
						madeHand.add(h.getSecondCard());
						madeHand.add(c);
						
						for (Card cs : handAndBoard) {
							if (cs.getRankAsByte() == b.getPairRank()) {
								madeHand.add(cs);
							}
						}
						p.setMadeHand(madeHand);
						p.setHandRanking(6);
						return;
					}
				}
			}
		}

		// Flush		
		if (b.hasFlush()) {
			switch (b.getFlushSize()) {
			case 3:
				if (h.getFirstCard().getSuitAsByte() == b.getFlushSuit() && h.getSecondCard().getSuitAsByte() == b.getFlushSuit()) {
					
					madeHand.add(h.getFirstCard());
					madeHand.add(h.getSecondCard());
					
					for (Card c : handAndBoard) {
						if (!(c.equals(h.getFirstCard())) && !(c.equals(h.getSecondCard())) && c.getSuitAsByte() == b.getFlushSuit()) {
							madeHand.add(c);
						}
					}
					
					p.setMadeHand(madeHand);
					p.setHandRanking(5);
				}
				break;
				
			case 4:
				if (h.getFirstCard().getSuitAsByte() == b.getFlushSuit() || h.getSecondCard().getSuitAsByte() == b.getFlushSuit()) {
					if (h.getFirstCard().getSuitAsByte() == h.getSecondCard().getSuitAsByte()) {
						if (h.getFirstCard().getRankAsByte() > h.getSecondCard().getRankAsByte()) {
							madeHand.add(h.getFirstCard());
							
							for (Card c : handAndBoard) {
								if (!(c.equals(h.getFirstCard())) && !(c.equals(h.getSecondCard()))) {
									madeHand.add(c);
								}
							}
							
							p.setMadeHand(madeHand);
							p.setHandRanking(5);
							return;
						} else {
							madeHand.add(h.getSecondCard());
							
							for (Card c : handAndBoard) {
								if (!(c.equals(h.getSecondCard())) && !(c.equals(h.getFirstCard()))) {
									madeHand.add(c);
								}
							}
							
							p.setMadeHand(madeHand);
							p.setHandRanking(5);
							return;
						}
					}
					
					if (h.getFirstCard().getSuitAsByte() == b.getFlushSuit()) {
						madeHand.add(h.getFirstCard());
						
						for (Card c : handAndBoard) {
							if (!(c.equals(h.getFirstCard())) && c.getSuitAsByte() == b.getFlushSuit()) {
								madeHand.add(c);
							}
						}
					} else {
						madeHand.add(h.getSecondCard());
						
						for (Card c : handAndBoard) {
							if (!(c.equals(h.getSecondCard())) && c.getSuitAsByte() == b.getFlushSuit()) {
								madeHand.add(c);
							}
						}
					}
					
					p.setMadeHand(madeHand);
					p.setHandRanking(5);
					return;
				}
			case 5:
				if (h.getFirstCard().getSuitAsByte() != b.getFlushSuit() && h.getSecondCard().getSuitAsByte() != b.getFlushSuit()) {
					for (Card c : handAndBoard) {
						if (!(c.equals(h.getFirstCard())) && !(c.equals(h.getSecondCard()))) {
							madeHand.add(c);
						}
					}
					
					p.setMadeHand(madeHand);
					p.setHandRanking(5);
					return;
					
				} else if (h.getFirstCard().getSuitAsByte() == b.getFlushSuit() && h.getSecondCard().getSuitAsByte() == b.getFlushSuit()) {
					
				}
			}
		}

		// straight
		if (b.checkStraight()) {
			// TODO: check for straight
		}
		
		// 3 of a kind
		// two pair
		// pair
		
		// high card
		return;
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

	
	//public List<Board> getBoard() {
	//	return boards;
	//}
}