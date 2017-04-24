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
	
	private Deck gameDeck = new Deck();
	
	public GameRunUtil(String playerOneRange, String playerTwoRange, String board) throws InvalidHandException {
		playerOne.addAll(HandParserUtil.parseRange(playerOneRange));
		playerTwo.addAll(HandParserUtil.parseRange(playerTwoRange));
		this.board = HandParserUtil.parseBoard(board);
	}
	
	public int testMonte() throws InvalidHandException {
		MonteCarloSimulation mcs = new MonteCarloSimulation(playerOne, playerTwo, board);
		mcs.run();
		return mcs.getPlayerOneStrength();
	}
}
