package persistence;

import model.InvalidHandException;

public class Rank {
	
	private int cardRank;
	
	public Rank() {
		
	}
	
	public Rank(char rank) {
		switch(rank) {
		case '2':
			this.cardRank = 2;
			break;
		case '3':
			this.cardRank = 3;
			break;
		case '4':
			this.cardRank = 4;
			break;
		case '5':
			this.cardRank = 5;
			break;
		case '6':
			this.cardRank = 6;
			break;
		case '7':
			this.cardRank = 7;
			break;
		case '8':
			this.cardRank = 8;
			break;
		case '9':
			this.cardRank = 9;
			break;
		case 'T':
			this.cardRank = 10;
			break;
		case 'J':
			this.cardRank = 11;
			break;
		case 'Q':
			this.cardRank = 12;
			break;
		case 'K':
			this.cardRank = 13;
			break;
		case 'A':
			this.cardRank = 14;
			break;
		}
	}
	
	public Rank(int rank){
		this.cardRank = rank;
	}
	
	public char getCardChar() {
		switch (this.cardRank) {
		case 2:
			return '2';
		case 3:
			return '3';
		case 4:
			return '4';
		case 5:
			return '5';
		case 6:
			return '6';
		case 7:
			return '7';
		case 8:
			return '8';
		case 9:
			return '9';
		case 10:
			return 'T';
		case 11:
			return 'J';
		case 12:
			return 'Q';
		case 13:
			return 'K';
		case 14:
			return 'A';	
		}
		return 'x';
	}
	
	public int getCardInt() {
		return this.cardRank;
	}
}
