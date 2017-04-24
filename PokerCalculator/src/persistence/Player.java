package persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Player {	
	private HandRank handRanking;
	
	private List<Card> madeHand;
	
	private List<Hand> hands;
	private int winCount;
	private int tieCount;
	
	private Random rnd = new Random();
	
	public Player(List<Hand> hands) {
		this.madeHand = new ArrayList<Card>();
		this.hands = new ArrayList<Hand>();
		
		winCount = 0;
		tieCount = 0;
		handRanking = HandRank.HIGH_CARD;
		this.hands = hands;
	}
	
	public void setMadeHand(List<Card> cards) {
		madeHand.addAll(cards);
	}
	
	public List<Card> getMadeHand() {
		return madeHand;
	}

	
	public int getWinCount() {
		return winCount;
	}
	
	public int getTieCount() {
		return tieCount;
	}
	
	public List<Hand> getHands() {
		return hands;
	}
	
	public void setHandRanking(int newHandRank) {
		handRanking = HandRank.values()[newHandRank];
	}
	
	public int getHandRanking() {
		return handRanking.ordinal();
	}
	
	public void addWin() {
		winCount++;
	}
	
	public void addTie() {
		tieCount++;
	}
	
	public Hand getRandomHand() {
		int random = rnd.nextInt(hands.size());
		
		return hands.get(random);
	}
	
}
