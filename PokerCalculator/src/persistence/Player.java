package persistence;

import java.util.HashSet;
import java.util.Set;

public class Player {

	private int handRanking;
	private Set<Hand> hands = new HashSet<Hand>();
	private int winCount;
	private int tieCount;
	
	public Player(Set<Hand> hands) {
		winCount = 0;
		tieCount = 0;
		handRanking = 0;
		this.hands = hands;
	}
	
	public int getHandRanking() {
		return handRanking;
	}	
	
	public int getWinCount() {
		return winCount;
	}
	
	public int getTieCount() {
		return tieCount;
	}
	
	public Set<Hand> getHands() {
		return hands;
	}
	
	public void setHandRanking(int newHandRank) {
		handRanking = newHandRank;
	}
	
	public void addWin() {
		winCount++;
	}
	
	public void addTie() {
		tieCount++;
	}
}
