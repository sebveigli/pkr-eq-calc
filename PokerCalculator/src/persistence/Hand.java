package persistence;

import java.util.HashSet;
import java.util.Set;

public class Hand {
	private Card firstCard;
	private Card secondCard;

	public Hand(Card first, Card second) {
			firstCard = first;
			secondCard = second;
	}
	

	public Set<Card> getHand() {
		Set<Card> hand = new HashSet<Card>();
		
		hand.add(firstCard);
		hand.add(secondCard);
		
		return hand;
	}
	
	
	public Card getFirstCard() {
		return this.firstCard;
	}
	
	public Card getSecondCard() {
		return this.secondCard;
	}
	
	public String getFirstRank() {
		return Byte.toString(firstCard.getRankAsByte());
	}
	
	public byte getFirstRankAsByte() {
		return firstCard.getRankAsByte();
	}
	
	@Override
	public boolean equals(Object hand) {
		if (hand == null) {
			return false;
		}
	
		
		Hand inputHand = (Hand)hand;
		
		if ((inputHand.getFirstCard().equals(getFirstCard()) && inputHand.getSecondCard().equals(getSecondCard())) ||
				inputHand.getFirstCard().equals(getSecondCard()) && inputHand.getSecondCard().equals(getFirstCard())) {
			System.out.println("Not Unique!");
			return true;
		}
		return false;
	}

}
