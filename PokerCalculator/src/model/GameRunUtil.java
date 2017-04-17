package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import persistence.Board;
import persistence.Card;
import persistence.Deck;
import persistence.Hand;
import persistence.Player;

public class GameRunUtil {
	private Set<Card> generatedCards = new HashSet<Card>();
	private Board board;
	
	private Player playerOne;
	private Player playerTwo;
	
	private Deck gameDeck = new Deck();
	
	private int[] freq = {0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	// take 2 sets of hands and choice for a board too
	// generate a deck
	// remove cards from the deck
	// deal random board
	// calculate winner
	// store wins player 1 and 2

	public GameRunUtil(String playerOneRange, String playerTwoRange, String board) throws InvalidHandException {
		playerOne = new Player(HandParserUtil.parseRange(playerOneRange));
		playerTwo = new Player(HandParserUtil.parseRange(playerTwoRange));
		this.board = HandParserUtil.parseBoard(board);
		
		
	}
	
	public void runMonteCarloSimulation() throws InvalidHandException {
		gameDeck.muckCards(board.getBoard());
		
		switch (board.getBoard().size()) {
		case 0:
			generateCards(5);
			break;
		case 3:
			generateCards(2);
			break;
		case 4:
			generateCards(1);
			break;
		case 5:
			break;
		}
	}
	
	private void generateCards(int amount) {
		for (int i = 0; i < amount; i++) {
			int randDraw = ThreadLocalRandom.current().nextInt(0, gameDeck.getDeck().size());
			int current = 0;
			
			for (Card c : gameDeck.getDeck()) {
				if (current == randDraw) {
					board.addCard(c);
					gameDeck.muckCard(c);
					generatedCards.add(c);
					freq[c.getRankAsByte() - 2]++;
					break;
				}
				current++;
			}
		}
	}
	
	public Set<Card> getBoard() {
		return board.getBoard();
	}
	
	public void reset() {
		for (Card c : generatedCards) {
			gameDeck.removeFromMuck(c);
			board.removeCard(c);
		}
		
		generatedCards.clear();
	}
	
	public int getFreq(int i) {
		return freq[i];
	}
	
	public int getFreqSize() {
		return freq.length;
	}
	
	
}
