package persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private List<Card> board = new ArrayList<Card>();
	
	private byte flushSuit;
	
	private byte pairRank;
	private byte tripRank;
	private byte quadRank;
	private byte flushSize;
	
	private boolean hasPair;
	private boolean hasTrips;
	private boolean hasQuads;
	private boolean hasFlush;
	
	
	public Board(List<Card> board) {
		this.board = board;
		
		if (this.board.size() == 5) {
			this.board.sort(Comparator.comparing(Card::getRankAsByte));
			checkPairs();
			checkFlush();
		}
	}
	
	public boolean hasFlush() {
		return hasFlush;
	}
	
	public byte getFlushSize() {
		return flushSize;
	}
	
	public boolean getPair() {
		return hasPair;
	}
	
	public boolean getTrips() {
		return hasTrips;
	}
	
	public boolean getQuads() {
		return hasQuads;
	}
	
	public byte getPairRank() {
		return pairRank;
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
					if ((byte)sameSuitCount > flushSize) {
						flushSize = (byte)sameSuitCount;
						flushSuit = currentSuit;
					}
					
				}
			}
		}
		System.out.println("flush size: " + flushSize);
	}
	
	public boolean checkStraight() {
		for (int i = 0; i < 2; i++) {
			if (board.get(i+2).getRankAsByte() - board.get(i).getRankAsByte() <= 4) {
				return true;
			}
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
						pairRank = currentRank;
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
		}
	}
	
	public void removeCard(Card c) {
		if (board.contains(c)) board.remove(c);
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
