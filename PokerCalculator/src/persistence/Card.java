package persistence;

public class Card {
	private Rank rank;
	private Suit suit;
	
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public byte getRankAsByte() {
		return rank.getNumberFormat();
	}
	
	public byte getSuitAsByte() {
		return suit.getNumberFormat();
	}
	
	public char getRankAsChar() {
		return rank.getCharFormat();
	}
	
	public char getSuitAsChar() {
		return suit.getCharFormat();
	}
	
	@Override
	public int hashCode()
    {
        int hash = 7;
        hash = 31 * hash + rank.getNumberFormat();
        hash = 31 * hash + suit.getNumberFormat();
        return hash;
    }
	
	@Override
	public boolean equals(Object card) {
		if (this == card)
			return true;
		if ((card == null) || (card.getClass() != this.getClass()))
			return false;

		
		Card inputCard = (Card)card;
		
		return (inputCard.getRankAsByte() == rank.getNumberFormat() && inputCard.getSuitAsByte() == suit.getNumberFormat());
	}
	
	
}
