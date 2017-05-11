package persistence;

import model.InvalidHandException;

public enum Rank {
	TWO((byte) 2, '2'), 
	THREE((byte) 3, '3'), 
	FOUR((byte) 4, '4'), 
	FIVE((byte) 5, '5'),
	SIX((byte) 6, '6'),
	SEVEN((byte) 7, '7'),
	EIGHT((byte) 8, '8'), 
	NINE((byte) 9, '9'), 
	TEN((byte) 10, 'T'), 
	JACK((byte) 11, 'J'), 
	QUEEN((byte) 12, 'Q'), 
	KING((byte) 13, 'K'), 
	ACE((byte) 14, 'A');
	
	private static final String ALL_SUITS_PATTERN = "23456789TJQKA";

	private byte numberFormat;
	private char charFormat;

	public static Rank parse(byte numberFormat) throws InvalidHandException {
		if (numberFormat < 2 || numberFormat > 14) {
			System.out.println("Invalid Rank");
			throw new InvalidHandException("Invalid Hand: Invalid Suit.");
		}
		return values()[numberFormat - 2];
	}

	public static Rank parse(char charFormat) throws InvalidHandException {
		int indexOfGotten = ALL_SUITS_PATTERN.indexOf(charFormat);

		if (indexOfGotten == -1) {
			System.out.println("Invalid Rank");
			throw new InvalidHandException("Invalid Hand: Invalid Suit.");
		}
		return values()[indexOfGotten];
	}
	
	private Rank(byte numberFormat, char shortened) {
		this.numberFormat = numberFormat;
		this.charFormat = shortened;
	}
	
	public byte getNumberFormat() {
		return numberFormat;
	}

	public char getCharFormat() {
		return charFormat;
	}
}
