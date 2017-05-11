package persistence;

import model.InvalidHandException;

public enum Suit {
	CLUBS((byte) 1, 'c'), 
	DIAMONDS((byte) 2, 'd'), 
	HEARTS((byte) 3, 'h'), 
	SPADES((byte) 4, 's');

	private static final String ALL_SUITS_PATTERN = "cdhs";

	private byte numberFormat;
	private char charFormat;

	public static Suit parse(byte numberFormat) throws InvalidHandException {
		if (numberFormat < 1 || numberFormat > 4) {
			System.out.println("Invalid Suiit");
			throw new InvalidHandException("Invalid Hand: Invalid Suit.");
		}
		return values()[numberFormat - 1];
	}

	public static Suit parse(char charFormat) throws InvalidHandException {
		int indexOfGotten = ALL_SUITS_PATTERN.indexOf(charFormat);
		if (indexOfGotten == -1) {
			System.out.println("Invalid Suit");
			throw new InvalidHandException("Invalid Hand: Invalid Suit.");
		}
		return values()[indexOfGotten];
	}

	private Suit(byte numberFormat, char shortened) {
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