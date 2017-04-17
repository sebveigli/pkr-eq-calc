package persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private Set<Card> board = new HashSet<Card>();
	
	private boolean hasFlushPossible;
	private boolean hasStraightPossible;
	private boolean hasPair;
	
	
	public Board(Set<Card> board) {
		this.board = board;
		check();
	}
	
	public void check() {
		hasFlushPossible = checkFlush();
		hasStraightPossible = checkStraight();
		hasPair = checkPair();
	}
	
	private boolean checkFlush() {
		List<Card> boardAsList = new ArrayList<Card>(board);
		
		byte currentSuit;
		int sameSuitCount;
		
		for (int i = 0; i < (boardAsList.size() - 2); i++) {
			currentSuit = boardAsList.get(i).getSuitAsByte();
			sameSuitCount = 1;
			for (int j = (i + 1); j < boardAsList.size(); j++) {
				if (currentSuit == boardAsList.get(j).getSuitAsByte()) {
					sameSuitCount++;
				}
				if (sameSuitCount == 3) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkStraight() {
		List<Card> boardAsList = new ArrayList<Card>(board);
		int max;
		int min;
		
		for (int i = 0; i < (boardAsList.size() - 2); i++) {
			for (int j = i + 1; j < (boardAsList.size() - 1); j++) {
				for (int k = j + 1; k < boardAsList.size(); k++) {
					max = Math.max((int)boardAsList.get(i).getRankAsByte(), 
							Math.max((int)boardAsList.get(j).getRankAsByte(), (int)boardAsList.get(k).getRankAsByte()));
					min = Math.min((int)boardAsList.get(i).getRankAsByte(), 
							Math.min((int)boardAsList.get(j).getRankAsByte(), (int)boardAsList.get(k).getRankAsByte()));

					if ((max - min) < 5) return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkPair() {
		List<Card> boardAsList = new ArrayList<Card>(board);
		
		byte currentRank;
		
		for (int i = 0; i < boardAsList.size() - 1; i++) {
			currentRank = boardAsList.get(i).getRankAsByte();
			for (int j = i + 1; j < boardAsList.size(); j++) {
				if (boardAsList.get(j).getRankAsByte() == currentRank) return true;
			}
		}
		return false;
	}
	
	public boolean getFlush() {
		return hasFlushPossible;
	}
	
	public boolean getStraight() {
		return hasStraightPossible;
	}
	
	public boolean hasPair() {
		return hasPair;
	}
	
	public Set<Card> getBoard() {
		return board;
	}
	
	public void addCard(Card card) {
		board.add(card);
		check();
	}
	
	public void removeCard(Card c) {
		if (board.contains(c)) board.remove(c);
		check();
	}
}
