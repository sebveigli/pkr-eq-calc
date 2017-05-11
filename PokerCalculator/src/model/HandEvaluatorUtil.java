package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.Board;
import persistence.Card;
import persistence.Hand;
import persistence.HandRank;
import persistence.Player;
import persistence.Rank;
import persistence.Suit;

public class HandEvaluatorUtil {
	private static List<Card> madeHand;
	
	
	private static boolean hasFlush;
	private static List<Card> flushCardsFromStraightFlush;

	
	public static void evaluate(Hand h, Board b, Player p) {
		madeHand = new ArrayList<Card>();
		p.setHandRanking(0);
		
		hasFlush = false;
		
		
		List<Card> handAndBoard = new ArrayList<Card>();
		
		handAndBoard.add(h.getFirstCard());
		handAndBoard.add(h.getSecondCard());
		handAndBoard.addAll(b.getBoard());
		handAndBoard.sort(Comparator.comparing(Card::getRankAsByte));
		
		// straight flush/royal flush = flush with a straight
		if (b.hasFlush() && b.checkStraight()) {
			if (straightFlushCheck(h, handAndBoard, b)) {
				p.setHandRanking(HandRank.STRAIGHT_FLUSH.ordinal());
				p.setMadeHand(madeHand);
				return;
			}
		}
		
		// four of a kind conditions
		if (b.hasQuads() || b.hasTrips() || b.hasPair()) {
			if (quadCheck(h, handAndBoard, b)) {
				p.setHandRanking(HandRank.FOUR_OF_A_KIND.ordinal());
				p.setMadeHand(madeHand);
				return;
			}
		}
		
		if (b.hasPair()) {
			if (fullHouseCheck(h, handAndBoard, b)) {
				p.setHandRanking(HandRank.FULL_HOUSE.ordinal());
				p.setMadeHand(madeHand);
				return;
			}
		}
		
		if (b.hasFlush()) {
			if (flushCheck(h, handAndBoard, b)) {
				p.setHandRanking(HandRank.FLUSH.ordinal());
				p.setMadeHand(madeHand);
				return;
			}
		}

		if (b.checkStraight()) {
			if (straightCheck(h, handAndBoard, b)) {
				p.setHandRanking(HandRank.STRAIGHT.ordinal());
				p.setMadeHand(madeHand);
				return;
			}
		}
		
		if (tripsCheck(h, handAndBoard, b)) {
			p.setHandRanking(HandRank.THREE_OF_A_KIND.ordinal());
			p.setMadeHand(madeHand);
			return;
		}
		
		if (twoPairCheck(h, handAndBoard, b)) {
			p.setHandRanking(HandRank.TWO_PAIR.ordinal());
			p.setMadeHand(madeHand);
			return;
		}
		
		if (pairCheck(h, handAndBoard, b)) {
			p.setHandRanking(HandRank.PAIR.ordinal());
			p.setMadeHand(madeHand);
			return;
		}
		
		for (int i = 0; i < 2; i++) {
			handAndBoard.remove(0);
		}
		p.setHandRanking(HandRank.HIGH_CARD.ordinal());
		p.setMadeHand(handAndBoard);
		return;
	}
	
	// checking for straight flush - 5 cards in a row with the same suit
	private static boolean straightFlushCheck(Hand h, List<Card> handAndBoard, Board b) {
		madeHand.clear();
	
		List<Card> flushCards = new ArrayList<Card>();
		
		Card ace = null;
		
		int failCount = 0;
		byte currentRank = 0;
		byte cardsFound = 0;
		
		// check for all the cards relevant to the flush, if we have 3 cards from 7 that do not make a flush, then we cannot have a flush so we exit this loop.
		for (Card c : handAndBoard) {
			if (c.getRankAsByte() == 14 && c.getSuitAsByte() == b.getFlushSuit()) {
				ace = c;	// check whether it's an ace of the flush suit on the board
			}
			if (c.getSuitAsByte() == b.getFlushSuit()) {
				flushCards.add(c);
			} else failCount++;
			
			if (failCount == 3) {
				break;
			}
		}
		
		// at this point, we check to make sure the size is >= 5, and from those cards we want to check whether they make a straight (5 cards in a row)
		if (flushCards.size() >= 5) {
			hasFlush = true;
			flushCardsFromStraightFlush = new ArrayList<Card>();
			flushCardsFromStraightFlush.addAll(flushCards);
			
			for (Card fc : flushCards) {
				if (currentRank == 0) {
					cardsFound++;
					currentRank = fc.getRankAsByte();
				} else {
					if (fc.getRankAsByte() == (currentRank + 1)) {
						
						currentRank++;
						cardsFound++;
					} else {
						currentRank = fc.getRankAsByte();
						cardsFound = 1;
					}
				}
			}
			
			// if the previous method didn't find 5 cards in a row, and an ace was in the set of flush cards
			// then we need to check whether the ace makes a low straight - A2345
			
			if (cardsFound < 5 && ace != null) {
				cardsFound = 0;
				currentRank = 1;
				for (Card fc : flushCards) {
					if (fc.getRankAsByte() == (currentRank + 1)) {
						currentRank++;
						cardsFound++;
					} else if (fc.getRankAsByte() != 14){
						return false;
					}
					
					if (cardsFound == 4) {
						cardsFound++;
						break;
					}
				}
			}
		}
		
		// now we check how many cards we found, and if we found more than 5, that means we have a straight flush using 6 or 7 cards, therefore
		// we must remove the lowest cards from the list - maybe we can just check for when size = 5 in the method above, and then just use that,
		// as we are using an ordered list, we can always guarantee that first 5 cards found will be the highest 5
		if (cardsFound >= 5) {
			for (int i = 0; i < (cardsFound - 5); i++) {
				flushCards.remove(0);
			}
			madeHand.addAll(flushCards);
			return true;
		} else return false;
	}
	
	private static boolean quadCheck(Hand h, List<Card> handAndBoard, Board b) {
			
		// check if board naturally has quads, if it does then we need to find the highest non quad card in the hand + board
		// to store in the madeHand list
		
		if (b.hasQuads()) {
			madeHand.clear();
			Card highCard = null;
			for (Card c: handAndBoard) {
				if (highCard == null) {
					if (c.getRankAsByte() != b.getQuadRank()) {
						highCard = c;
					} else madeHand.add(c);
				} 
				
				if (c.getRankAsByte() == b.getQuadRank()) {
					madeHand.add(c);
				} else if (c.getRankAsByte() > highCard.getRankAsByte()) {
					highCard = c;
				}
			}
			madeHand.add(highCard);
			return true;
		}
		
		 
		// checking for 3 of a kind + same card in players hand
		if (b.hasTrips()) {
			madeHand.clear();
			Card highCard = null;
			if (h.getFirstCard().getRankAsByte() == b.getTripRank() || h.getSecondCard().getRankAsByte() == b.getTripRank()) {
				for (Card c: handAndBoard) {
					if (highCard == null) {
						if (c.getRankAsByte() != b.getTripRank()) {
							highCard = c;
						} else madeHand.add(c);
					}
					
					if (c.getRankAsByte() == b.getTripRank()) {
						madeHand.add(c);
					} else if (c.getRankAsByte() > highCard.getRankAsByte()) {
						highCard = c;
					}
				}
				madeHand.add(highCard);
				return true;
			}
		}
	
		// check if board has a pair, and player has same pair in their hand
		if (b.hasPair()) {
			madeHand.clear();
			
			Card highCard = null;
			
			if (h.getFirstCard().getRankAsByte() == b.getPairRank() && h.getSecondCard().getRankAsByte() == b.getPairRank()) {
				for (Card c: handAndBoard) {
					if (highCard == null) {
						if (c.getRankAsByte() != b.getPairRank()) {
							highCard = c;
						} else madeHand.add(c);
						
					} if (c.getRankAsByte() == b.getPairRank()) {
						madeHand.add(c);
					} else if (c.getRankAsByte() > highCard.getRankAsByte()) {
						highCard = c;
					}
				}
				madeHand.add(highCard);
				return true;
			} else if (b.hasTwoPair() && h.getFirstCard().getRankAsByte() == b.getPairRank2() && h.getSecondCard().getRankAsByte() == b.getPairRank2()) {
				for (Card c: handAndBoard) {
					if (highCard == null) {
						if (c.getRankAsByte() != b.getPairRank2()) {
							highCard = c;
						} else madeHand.add(c);
						
					} if (c.getRankAsByte() == b.getPairRank2()) {
						madeHand.add(c);
					} else if (c.getRankAsByte() > highCard.getRankAsByte()) {
						highCard = c;
					}
				}
				madeHand.add(highCard);
				return true;
			}
			
			
		}
		
		// otherwise, no quads
		
		return false;
	}
	
	private static boolean fullHouseCheck(Hand h, List<Card> handAndBoard, Board b){
		madeHand.clear();
		
		// board must have at least a pair
		// has possibility of multiple trip ranks, i.e on board (KKKAA if player has A)
		// has possibility of multiple pair ranks, i.e, on board (KKAA7 if player has 77)
		
		List<Byte> tripRanks = new ArrayList<Byte>();
		List<Byte> pairRanks = new ArrayList<Byte>();
		
		Map<Byte, Integer> occ = new HashMap<Byte, Integer>();
		
		// run through hand + board, check for ammount of occurances of a rank
		for (Card c : handAndBoard) {
			Integer prevVal = occ.get(c.getRankAsByte());
			occ.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		for (Map.Entry<Byte, Integer> c : occ.entrySet()){
			if (c.getValue() == 3) {			
				tripRanks.add(c.getKey());	// 3 occurances = trips
			}
			
			if (c.getValue() == 2) {
				pairRanks.add(c.getKey()); // 2 occurances = pair
			}
		}
		
		
		// if we find multiple tripRanks
		if (tripRanks.size() > 1) {
			int minPosition = tripRanks.indexOf(Collections.min(tripRanks));
			
			// find the minimum of the trips, then add the other trips to the pair ranks
			pairRanks.add(tripRanks.get(minPosition));
			tripRanks.remove(minPosition);
		}
		
		// if we find multiple pairRanks
		if (pairRanks.size() > 1) {
			int minPosition = pairRanks.indexOf(Collections.min(pairRanks));
			
			pairRanks.remove(minPosition);
		}
		
		if (tripRanks.size() == 1 && pairRanks.size() == 1) {
			for (Card c : handAndBoard) {
				if (c.getRankAsByte() == tripRanks.get(0) || c.getRankAsByte() == pairRanks.get(0)) {
					madeHand.add(c);
				}
			}
			return true;
		}
		
		return false;
	}
	
	private static boolean flushCheck(Hand h, List<Card> handAndBoard, Board b) {
		madeHand.clear();
		
		int size;
		
		if (hasFlush) {
			size = flushCardsFromStraightFlush.size();
			for (int i = 0; i < size - 5; i++) {
				flushCardsFromStraightFlush.remove(0);
			}
			madeHand.addAll(flushCardsFromStraightFlush);
			return true;
		}
		
		for (Card c : handAndBoard) {
			if (c.getSuitAsByte() == b.getFlushSuit()) {
				madeHand.add(c);
			}
		}
		
		size = madeHand.size();
		
		
		// if flush is bigger than 5
		if (size >= 5) {
			madeHand.sort(Comparator.comparing(Card::getRankAsByte));
			
			for (int i = 0; i < size - 5; i++) {
				madeHand.remove(0);
			}
			return true;
		} else return false;
	}
	
	// checking for a straight requires us to check first for a normal straight, (23456, 89TJQ etc)
	// and if we don't find these, then we also have to check for straight (A2345).
	private static boolean straightCheck(Hand h, List<Card> handAndBoard, Board b) {
		madeHand.clear();
		
		byte prevRank = 0;
		boolean containsAce = false;
		Card ace = null;
		
		// checking through cards, see if we find a straight - if we find an ace, we save it
		
		for (Card c : handAndBoard) {
			if (c.getRankAsByte() == 14) {
				containsAce = true;
				ace = c;
			}
			
			if (prevRank == 0) {
				prevRank = c.getRankAsByte();
				madeHand.add(c);
			} else if (prevRank + 1 == c.getRankAsByte()){
				prevRank = c.getRankAsByte();
				madeHand.add(c);
			} else if (prevRank == c.getRankAsByte()){
				continue;
			} else {
				if (madeHand.size() < 5) {
					madeHand.clear();
					
					prevRank = c.getRankAsByte();
					madeHand.add(c);
				}
			}
		}
		
		// if the size >= 5, that means we have a straight - we don't need to check for ace low, because 
		int size = madeHand.size();
		
		if (size >= 5) {
			madeHand.sort(Comparator.comparing(Card::getRankAsByte));
			
			// if the straight is larger than 5 cards, then we must remove the smallest elements
			// since the list is sorted, we can safely remove the 0th element each time.
			for (int i = 0; i < size - 5; i++) {
				madeHand.remove(0);
			}
			
			return true;
			
		// if the size is not >= 5, and the board/hand combination contains an ace, we do checks for a ace-low straight
		} else if (containsAce){
			madeHand.clear();
			prevRank = 1;
			boolean aceInPlay = false;
			
			for (Card c : handAndBoard) {
				if (prevRank + 1 == c.getRankAsByte()) {
					if (prevRank == 1) aceInPlay = true;
					prevRank = c.getRankAsByte();
					madeHand.add(c);
				} else if (prevRank == c.getRankAsByte()) {
					continue;
				} else {
					if (madeHand.size() < 4) {
						aceInPlay = false;
						madeHand.clear();
						
						prevRank = c.getRankAsByte();
						madeHand.add(c);
					}
				}
			}
			
			if (aceInPlay) madeHand.add(ace);
			
			size = madeHand.size();
			
			if (size == 5) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean tripsCheck(Hand h, List<Card> handAndBoard, Board b) {
		madeHand.clear();
		
		Map<Byte, Integer> occ = new HashMap<>();
		
		byte tripRank = 0; 
		
		// map the number of occurances
		
		for (Card c : handAndBoard) {
			Integer prevVal = occ.get(c.getRankAsByte());
			
			occ.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		// check for occurrences of the rank, if it's 3 and bigger than current tripRank then update it
		for (Map.Entry<Byte, Integer> e : occ.entrySet()){
			if (e.getValue() == 3) {
				if (e.getKey() > tripRank) {
					tripRank = e.getKey();
				}
			}
		}
		
		if (tripRank != 0) {
			for (Card c : handAndBoard) {
				if (c.getRankAsByte() == tripRank) {
					madeHand.add(c);
				}
			}
			
			handAndBoard.removeAll(madeHand);
			
			for (int i = 0; i < 2; i++) {
				handAndBoard.remove(0);
			}
			
			madeHand.addAll(handAndBoard);
			return true;
		}
		
		return false;
	}
	
	private static boolean twoPairCheck(Hand h, List<Card> handAndBoard, Board b) {
		madeHand.clear();
		
		Map<Byte, Integer> pairs = new HashMap<>();
		
		List<Byte> pairRanks = new ArrayList<>();
		
		for (Card c : handAndBoard) {
			Integer prevVal = pairs.get(c.getRankAsByte());
			
			pairs.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		for (Map.Entry<Byte, Integer> c : pairs.entrySet()){
			if (c.getValue() == 2 && !pairRanks.contains(c.getKey())) {
				pairRanks.add(c.getKey());
			}
		}
		
		// check if we have 3 pairs between hand and board, 4 is not possible (7 cards)
		if (pairRanks.size() == 3) {
			int minPosition = pairRanks.indexOf(Collections.min(pairRanks));
			
			pairRanks.remove(minPosition);
		} 
		
		if (pairRanks.size() == 2) {
			for (Card c : handAndBoard) {
				if (pairRanks.contains(c.getRankAsByte())) {
					madeHand.add(c);
				}
			}
		} else return false;
		
		handAndBoard.removeAll(madeHand);
		
		for (int i = 0; i < 2; i++) {
			handAndBoard.remove(0);
		}
		
		madeHand.addAll(handAndBoard);
		
		return true;
	}

	private static boolean pairCheck(Hand h, List<Card> handAndBoard, Board b) {
		madeHand.clear();
		
		Map<Byte, Integer> pair = new HashMap<>();
		
		byte pairRank = 0;
		
		for (Card c : handAndBoard) {
			Integer prevVal = pair.get(c.getRankAsByte());
			
			pair.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
		}
		
		for (Map.Entry<Byte, Integer> c : pair.entrySet()){
			if (c.getValue() == 2) {
				pairRank = c.getKey();
			}
		}
		
		if (pairRank != 0) {
			for (Card c: handAndBoard) {
				if (c.getRankAsByte() == pairRank) {
					madeHand.add(c);
				}
			}
		} else return false;
		
		handAndBoard.removeAll(madeHand);
		
		for (int i = 0; i < 2; i++) {
			handAndBoard.remove(0);
		}
		
		madeHand.addAll(handAndBoard);
		
		return true;
	}
	
	public static void checkWinner(Player p1, Player p2, Board b) {
		if (p1.getHandRanking() > p2.getHandRanking()) {
			p1.addWin();
			return;
		} else if (p2.getHandRanking() > p1.getHandRanking()) {
			p2.addWin();
			return;
		} else {
			if (p1.getHandRanking() == HandRank.STRAIGHT_FLUSH.ordinal()) {
				boolean p1AceLowStraight = (p1.getMadeHand().get(4).getRankAsByte() == 14 && p1.getMadeHand().get(0).getRankAsByte() == 2);
				boolean p2AceLowStraight = (p2.getMadeHand().get(4).getRankAsByte() == 14 && p2.getMadeHand().get(0).getRankAsByte() == 2);
				
				if (p1AceLowStraight && !p2AceLowStraight) {
					p2.addWin();
					return;
				} else if (!p1AceLowStraight && p2AceLowStraight) {
					p1.addWin();
					return;
				}
				
				int p1Max = p1.getMadeHand().get(4).getRankAsByte();
				int p2Max = p2.getMadeHand().get(4).getRankAsByte();
				
				if (p1Max > p2Max) {
					p1.addWin();
					return;
				} else if (p2Max > p1Max) {
					
					p2.addWin();
					return;
				} else {
					
					p1.addTie();
					p2.addTie();
					return;
				}
			}
			
			if (p1.getHandRanking() == HandRank.FOUR_OF_A_KIND.ordinal()) {
				int p1Max = 0;
				int p2Max = 0;
				
				int p1QuadStrength = 0;
				int p2QuadStrength = 0;
				
				
				
				if (b.hasTwoPair()) {
					Map<Byte, Integer> occ = new HashMap<>();
					
					for (Card c : p1.getMadeHand()) {
						Integer prevVal = occ.get(c.getRankAsByte());
						
						occ.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
						
						if (occ.get(c.getRankAsByte()) >= 4) {
							p1QuadStrength = c.getRankAsByte();
						}
					}
					
					occ.clear();
					
					for (Card c : p2.getMadeHand()) {
						Integer prevVal = occ.get(c.getRankAsByte());
						
						occ.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
						
						if (occ.get(c.getRankAsByte()) >= 4) {
							p2QuadStrength = c.getRankAsByte();
						}
					}
				
					if (p1QuadStrength > p2QuadStrength) {						
						p1.addWin();
						return;
					} else if (p2QuadStrength > p1QuadStrength) {
						p2.addWin();
						return;
					}
				}
				
				
				for (Card c : p1.getMadeHand()) {
					if (!(c.getRankAsByte() == b.getPairRank())) {
						p1Max = c.getRankAsByte();
						break;
					}
				}
					
				for (Card c: p2.getMadeHand()) {
					if (!(c.getRankAsByte() == b.getPairRank())) {
						p2Max = c.getRankAsByte();
						break;
					}
				}
					
				if (p1Max > p2Max) {
					
					p1.addWin();
						return;
				} else if (p2Max > p1Max) {
					
					p2.addWin();
					return;
				} else {
					
					p1.addTie();
					p2.addTie();
					return;
				}				
			}
			
			if (p1.getHandRanking() == HandRank.FULL_HOUSE.ordinal()) {
				Map<Byte, Integer> p1Combo = new HashMap<>();
				Map<Byte, Integer> p2Combo = new HashMap<>();
				
				int p1TripRank = 0;
				int p1PairRank = 0;
				int p2TripRank = 0;
				int p2PairRank = 0;
				
				for (Card c : p1.getMadeHand()) {
					Integer prevVal = p1Combo.get(c.getRankAsByte());
					
					p1Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p1Combo.entrySet()){
					if (c.getValue() == 3) {
						p1TripRank = c.getKey();
					}
					
					if (c.getValue() == 2) {
						p1PairRank = c.getKey();
					}
				}
				
				for (Card c : p2.getMadeHand()) {
					Integer prevVal = p2Combo.get(c.getRankAsByte());
					
					p2Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p2Combo.entrySet()){
					if (c.getValue() == 3) {
						p2TripRank = c.getKey();
					}
					
					if (c.getValue() == 2) {
						p2PairRank = c.getKey();
					}
				}
				
				if (p1TripRank > p2TripRank) {
					
					p1.addWin();
					return;
				} else if (p2TripRank > p1TripRank) {
					
					p2.addWin();
					return;
				} else if (p1PairRank > p2PairRank) {
					
					p1.addWin();
					return;
				} else if (p2PairRank > p1PairRank) {
					
					p2.addWin();
					return;
				} else {
					
					p1.addTie();
					p2.addTie();
					return;
				}
			}
			
			if (p1.getHandRanking() == HandRank.FLUSH.ordinal()) {
				for (int i = 4; i >= 0; i--) {
					if (p1.getMadeHand().get(i).getRankAsByte() > p2.getMadeHand().get(i).getRankAsByte()) {
						p1.addWin();
						return;
					} else if (p2.getMadeHand().get(i).getRankAsByte() > p1.getMadeHand().get(i).getRankAsByte()) {
						p2.addWin();
						return;
					}
				}
				p1.addTie();
				p2.addTie();
				return;
			}
			
			if (p1.getHandRanking() == HandRank.STRAIGHT.ordinal()) {
				// needed to check for situations where ace low straight is made, the ordering of the cards needs to be checked.
				boolean p1AceLowStraight = (p1.getMadeHand().get(4).getRankAsByte() == 14 && p1.getMadeHand().get(0).getRankAsByte() == 2);
				boolean p2AceLowStraight = (p2.getMadeHand().get(4).getRankAsByte() == 14 && p2.getMadeHand().get(0).getRankAsByte() == 2);
				
				
				if (p1AceLowStraight && !p2AceLowStraight) {
					p2.addWin();
					return;
				} else if (p2AceLowStraight && !p1AceLowStraight) {
					p1.addWin();
					return;
				} else if (p1.getMadeHand().get(0).getRankAsByte() > p2.getMadeHand().get(0).getRankAsByte()) {
					p1.addWin();
					return;
				} else if (p2.getMadeHand().get(0).getRankAsByte() > p1.getMadeHand().get(0).getRankAsByte()) {
					p2.addWin();
					return;
				}
				
				p1.addTie();
				p2.addTie();
				return;
			}
			
			if (p1.getHandRanking() == HandRank.THREE_OF_A_KIND.ordinal()) {
				Map<Byte, Integer> p1Combo = new HashMap<>();
				Map<Byte, Integer> p2Combo = new HashMap<>();
				
				List<Byte> p1HighCards = new ArrayList<Byte>();
				List<Byte> p2HighCards = new ArrayList<Byte>();
				
				byte p1TripRank = 0;
				byte p2TripRank = 0;
				
				for (Card c : p1.getMadeHand()) {
					Integer prevVal = p1Combo.get(c.getRankAsByte());
					
					p1Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p1Combo.entrySet()){
					if (c.getValue() == 3) {
						p1TripRank = c.getKey();
					}
					
					if (c.getValue() == 1) {
						p1HighCards.add(c.getKey());
					}
				}
				
				for (Card c : p2.getMadeHand()) {
					Integer prevVal = p2Combo.get(c.getRankAsByte());
					
					p2Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p2Combo.entrySet()){
					if (c.getValue() == 3) {
						p2TripRank = c.getKey();
					}
					
					if (c.getValue() == 1) {
						p2HighCards.add(c.getKey());
					}
				}
				
				Collections.sort(p1HighCards);
				Collections.sort(p2HighCards);
				
				if (p1TripRank > p2TripRank) {
					p1.addWin();
					return;
				} else if (p2TripRank > p1TripRank) {
					p2.addWin();
					return;
				} else if (p1HighCards.get(1) > p2HighCards.get(1)){
					p1.addWin();
					return;
				} else if (p2HighCards.get(1) > p1HighCards.get(1)) {
					p2.addWin();
					return;
				} else if (p1HighCards.get(0) > p2HighCards.get(0)) {
					p1.addWin();
					return;
				} else if (p2HighCards.get(0) > p1HighCards.get(0)) {
					p2.addWin();
					return;
				}
				
				p1.addTie();
				p2.addTie();
				return;
			}
			
			if (p1.getHandRanking() == HandRank.TWO_PAIR.ordinal()) {
				Map<Byte, Integer> p1Combo = new HashMap<>();
				Map<Byte, Integer> p2Combo = new HashMap<>();
				
				List<Byte> p1Pairs = new ArrayList<>();
				List<Byte> p2Pairs = new ArrayList<>();
				
				int p1HighCard = 0;
				int p2HighCard = 0;
				
				for (Card c : p1.getMadeHand()) {
					Integer prevVal = p1Combo.get(c.getRankAsByte());
					
					p1Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p1Combo.entrySet()){
					if (c.getValue() == 2) {
						p1Pairs.add(c.getKey());
					}
					
					if (c.getValue() == 1) {
						p1HighCard = c.getKey();
					}
				}
				
				for (Card c : p2.getMadeHand()) {
					Integer prevVal = p2Combo.get(c.getRankAsByte());
					
					p2Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p2Combo.entrySet()){
					if (c.getValue() == 2) {
						p2Pairs.add(c.getKey());
					}
					
					if (c.getValue() == 1) {
						p2HighCard = c.getKey();
					}
				}
				
				Collections.sort(p1Pairs);
				Collections.sort(p2Pairs);
				
				if (p1Pairs.get(1) > p2Pairs.get(1)) {
					p1.addWin();
					return;
				} else if (p2Pairs.get(1) > p1Pairs.get(1)) {
					p2.addWin();
					return;
				} else if (p1Pairs.get(0) > p2Pairs.get(0)) {
					p1.addWin();
					return;
				} else if (p2Pairs.get(0) > p1Pairs.get(0)) {
					p2.addWin();
					return;
				} else if (p1HighCard > p2HighCard) {
					p1.addWin();
					return;
				} else if (p2HighCard > p1HighCard) {
					p2.addWin();
					return;
				}
				
				
				p1.addTie();
				p2.addTie();
				return;
			}
			
			if (p1.getHandRanking() == HandRank.PAIR.ordinal()) {
				Map<Byte, Integer> p1Combo = new HashMap<>();
				Map<Byte, Integer> p2Combo = new HashMap<>();
				
				List<Byte> p1HighCards = new ArrayList<>();
				List<Byte> p2HighCards = new ArrayList<>();
				
				int p1PairRank = 0;
				int p2PairRank = 0;
				
				for (Card c : p1.getMadeHand()) {
					Integer prevVal = p1Combo.get(c.getRankAsByte());
					
					p1Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p1Combo.entrySet()){
					if (c.getValue() == 2) {
						p1PairRank = c.getKey();
					} else if (c.getValue() == 1) {
						p1HighCards.add(c.getKey());
					}
				}
				
				for (Card c : p2.getMadeHand()) {
					Integer prevVal = p2Combo.get(c.getRankAsByte());
					
					p2Combo.put(c.getRankAsByte(), prevVal == null ? 1 : prevVal + 1);
				}
				
				for (Map.Entry<Byte, Integer> c : p2Combo.entrySet()){
					if (c.getValue() == 2) {
						p2PairRank = c.getKey();
					} else if (c.getValue() == 1) {
						p2HighCards.add(c.getKey());
					}
				}
				
				Collections.sort(p1HighCards);
				Collections.sort(p2HighCards);
				
				if (p1PairRank > p2PairRank) {
					p1.addWin();
					return;
				} else if (p2PairRank > p1PairRank) {
					p2.addWin();
					return;
				} else {
					for (int i = p1HighCards.size() - 1; i >= 0; i--) {
						if (p1HighCards.get(i) > p2HighCards.get(i)) {
							p1.addWin();
							return;
						} else if (p2HighCards.get(i) > p1HighCards.get(i)) {
							p2.addWin();
							return;
						}
					}
					
					
					p1.addTie();
					p2.addTie();
					return;
				}
			}
			
			for (int i = 4; i >= 0; i--) {
				if (p1.getMadeHand().get(i).getRankAsByte() > p2.getMadeHand().get(i).getRankAsByte()) {
					p1.addWin();
					return;
				} else if (p2.getMadeHand().get(i).getRankAsByte() > p1.getMadeHand().get(i).getRankAsByte()) {
					p2.addWin();
					return;
				}
			}
			
			p1.addTie();
			p2.addTie();
			return;
		}
	}
}
