package persistence;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private List<Card> board;
	
	private byte flushSuit;
	
	private List<Byte> pairRanks;
	
	private byte tripRank;
	private byte quadRank;
	
	private boolean hasPair;
	private boolean hasTrips;
	private boolean hasQuads;
	private boolean hasFlush;
	private boolean hasTwoPair;
	
	
	public Board(List<Card> board) {
		this.board = board;
		
		pairRanks = new ArrayList<Byte>();
		
		if (this.board.size() == 5) {
			this.board.sort(Comparator.comparing(Card::getRankAsByte));
			checkPairs();
			checkFlush();
			checkStraight();
		}
	}
	
	public void reset() {
		hasPair = false;
		hasTrips = false;
		hasQuads = false;
		hasFlush = false;
		hasTwoPair = false;
		
		flushSuit = 0;
		tripRank = 0;
		quadRank = 0;
		
		
		pairRanks.clear();
		
		if (board.size() == 5) {
			checkPairs();
			checkFlush();
			checkStraight();
		}
	}
	
	public boolean hasFlush() {
		return hasFlush;
	}
	
	public boolean hasTwoPair() {
		return hasTwoPair;
	}
	
	public boolean hasPair() {
		return hasPair;
	}
	
	public boolean hasTrips() {
		return hasTrips;
	}
	
	public boolean hasQuads() {
		return hasQuads;
	}
	
	public byte getPairRank() {
		
		return pairRanks.get(0);
	}
	
	public byte getPairRank2() {
		return pairRanks.get(1);
	}
	
	public byte getTripRank() {
		return tripRank;
	}
	
	public byte getQuadRank() {
		return quadRank;
	}
	
	private void checkFlush() {		
		byte currentSuit;
		int sameSuitCount;
		
		for (int i = 0; i < (board.size() - 2); i++) {
			currentSuit = board.get(i).getSuitAsByte();
			sameSuitCount = 1;
			for (int j = (i + 1); j < board.size(); j++) {
				if (currentSuit == board.get(j).getSuitAsByte()) {
					sameSuitCount++;
				}
				if (sameSuitCount >= 3) {
					hasFlush = true;
					
					flushSuit = currentSuit;
					
				}
			}
		}
	}
	
	public void removeCard(Card c) {
		board.remove(c);
	}
	
	
	
	public boolean checkStraight() {
		List<Byte> noDupes = new ArrayList<Byte>();
		
		for (Card c : board) {
			if (!noDupes.contains(c.getRankAsByte())) noDupes.add(c.getRankAsByte());
		}
		
		if (noDupes.size() > 2) {
			for (int i = 0; i < (noDupes.size() - 2); i++) {
				if (board.get(i+2).getRankAsByte() - board.get(i).getRankAsByte() <= 4) {
					return true;
				} 
			}
		} else return false;
		
		// check if ace exists on the board, if yes, then let Ace = 1 and check with 2nd element
		if (checkAce() && noDupes.size() > 2) {
			if (noDupes.get(2) - noDupes.get(0) <= 3) {
				return true;
			} return false;
		}
		return false;
	}
	
	private void checkPairs() {
		byte currentRank = 0;
		byte combosFound = 0;
		
		for (Card c : board) {
			if (currentRank == 0) {
				currentRank = c.getRankAsByte();
				combosFound++;
			} else {
				if (c.getRankAsByte() == currentRank) {
					combosFound++;
					
					if (combosFound == 2) {
						hasPair = true;
						pairRanks.add(currentRank);
						
						if (pairRanks.size() == 2) {
							hasTwoPair = true;
						}
					} else if (combosFound == 3) {
						hasTrips = true;
						tripRank = currentRank;
					} else if (combosFound == 4) {
						hasQuads = true;
						quadRank = currentRank;
						break;
					}
					
				} else {
					currentRank = c.getRankAsByte();
					combosFound = 1;
				}
			}
		}
		
	}
	
	public boolean checkAce() {
		for (Card c : board) {
			if (c.getRankAsByte() == 14) {
				return true;
			}
		}
		return false;
	}
	
	public byte getFlushSuit() {
		return flushSuit;
	}
	
	public List<Card> getBoard() {
		return board;
	}
	
	public void addCard(Card card) {
		if(board.size() < 5) board.add(card);
		
		if (this.board.size() == 5) {
			this.board.sort(Comparator.comparing(Card::getRankAsByte));
			checkPairs();
			checkFlush();
			checkStraight();
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		if (obj instanceof Hand) {
			Hand h = (Hand)obj;
			
			if (board.contains(h.getFirstCard()) || board.contains(h.getSecondCard())) { return true; }
			return false;
		}
		
		if (getClass() != obj.getClass())
			return false;
		
		Board other = (Board) obj;
		
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		
		return false;
	}
}
