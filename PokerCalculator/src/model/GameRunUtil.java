package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import persistence.Board;
import persistence.Card;
import persistence.Deck;
import persistence.Hand;
import persistence.Player;

public class GameRunUtil {
	private List<Card> generatedCards = new ArrayList<Card>();
	private Board board;
	
	private List<Hand> playerOne = new ArrayList<Hand>();
	private List<Hand> playerTwo = new ArrayList<Hand>();
	
	private Hand h1;
	private Hand h2;
	
	private int max;
	
	private Deck gameDeck = new Deck();
	
	private int[] freq = {0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	// take 2 sets of hands and choice for a board too
	// generate a deck
	// remove cards from the deck
	// deal random board
	// calculate winner
	// store wins player 1 and 2

	public GameRunUtil(String playerOneRange, String playerTwoRange, String board) throws InvalidHandException {
		playerOne.addAll(HandParserUtil.parseRange(playerOneRange));
		playerTwo.addAll(HandParserUtil.parseRange(playerTwoRange));
		this.board = HandParserUtil.parseBoard(board);
		max = 0;
	}
	
	public int getMax() {
		return max;
	}
	
	public void testMonte() throws InvalidHandException {
		MonteCarloSimulation mcs = new MonteCarloSimulation(playerOne, playerTwo, board);
		mcs.run();
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
	
	public List<Card> getBoard() {
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
