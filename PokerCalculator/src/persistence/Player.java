package persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Player {
	enum HandRank{HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH}
	
	private HandRank handRanking;
	
	private List<Card> madeHand = new ArrayList<Card>();
	
	private List<Hand> hands = new ArrayList<Hand>();
	private int winCount;
	private int tieCount;
	
	public Player(List<Hand> hands) {
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
		Random rnd = new Random();
		int random = rnd.nextInt(hands.size());
		
		return hands.get(random);
	}
	
}
