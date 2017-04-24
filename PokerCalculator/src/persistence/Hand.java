package persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hand {
	private Card firstCard;
	private Card secondCard;

	public Hand(Card first, Card second) {
			firstCard = first;
			secondCard = second;
	}
	

	public List<Card> getHand() {
		List<Card> hand = new ArrayList<Card>();
		
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
	
	public String getSecondRank() {
		return Character.toString(secondCard.getRankAsChar());
	}
	
	public byte getFirstRankAsByte() {
		return firstCard.getRankAsByte();
	}
	
	public String getFirstRank() {
		return Character.toString(firstCard.getRankAsChar());
	}
	
	public byte getSecondRankAsByte() {
		return secondCard.getRankAsByte();
	}
	
	public byte getFirstSuitAsByte() {
		return firstCard.getSuitAsByte();
	}
	
	public byte getSecondSuitAsByte() {
		return secondCard.getSuitAsByte();
	}
	
	public String getFirstSuit() {
		return Character.toString(firstCard.getSuitAsChar());
	}
	
	public String getSecondSuit() {
		return Character.toString(secondCard.getSuitAsChar());
	}
	
	public List<Card> getSortedHand() {
		List<Card> sortedHand = new ArrayList<Card>();
		
		if (firstCard.getRankAsByte() > secondCard.getRankAsByte()) {
			sortedHand.add(secondCard);
			sortedHand.add(firstCard);
			return sortedHand;
		}
		sortedHand.add(firstCard);
		sortedHand.add(secondCard);
		return sortedHand;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstCard == null) ? 0 : firstCard.hashCode());
		result = prime * result + ((secondCard == null) ? 0 : secondCard.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null) 

			return false;
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Hand other = (Hand) obj;
		
		if ((other.getFirstCard().equals(this.getFirstCard()) || other.getSecondCard().equals(getSecondCard())) ||
				other.getFirstCard().equals(getSecondCard()) || other.getSecondCard().equals(getFirstCard())) {
			return true;
		}
		
		if (firstCard == null && other.firstCard != null) 
			return false;
		else if (!firstCard.equals(other.firstCard))
			return false;
		
		if (secondCard == null && other.secondCard != null) 
			return false;
		else if (!secondCard.equals(other.secondCard))
			return false;
		
		return false;
	}
	


}
