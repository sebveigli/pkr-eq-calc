package persistence;

public class Suit {
	private int suit;
	
	public Suit(int suit) {
		switch(suit) {
		case 1:
			this.suit = 1;
			break;
		case 2:
			this.suit = 2;
			break;
		case 3:
			this.suit = 3;
			break;
		case 4:
			this.suit = 4;
			break;
		}
	}
	
	public Suit(char s) {
		switch(s) {
		case 'c':
			this.suit = 1;
			break;
		case 'd':
			this.suit = 2;
			break;
		case 'h':
			this.suit = 3;
			break;
		case 's':
			this.suit = 4;
			break;
		}
	}
	
	public char getSuitChar() {
		switch (this.suit) {
		case 1:
			return 'c';
		case 2:
			return 'd';
		case 3:
			return 'h';
		case 4:
			return 's';
		}
		return 'x';
	}
	
	public int getSuitInt() {
		return this.suit;
	}
}
