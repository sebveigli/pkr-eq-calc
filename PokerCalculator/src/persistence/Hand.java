package persistence;

public class Hand {
	private Rank firstRank;
	private Rank secondRank;
	private Suit firstSuit;
	private Suit secondSuit;
	
	public Hand() {
		
	}
	
	public Hand(Rank rank1, Suit suit1, Rank rank2, Suit suit2) {
		this.firstRank = rank1;
		this.firstSuit = suit1;
		this.secondRank = rank2;
		this.secondSuit = suit2;
	}
	
	public char getFirstRank() {
		return firstRank.getCardChar();
	}
	
	public char getFirstSuit() {
		return firstSuit.getSuitChar();
	}
	
	public char getSecondRank() {
		return secondRank.getCardChar();
	}
	
	public char getSecondSuit() {
		return secondSuit.getSuitChar();
	}
}
