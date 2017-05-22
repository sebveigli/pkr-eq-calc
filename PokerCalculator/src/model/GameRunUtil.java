package model;

import java.util.ArrayList;

import java.util.List;
import persistence.Board;
import persistence.Hand;

public class GameRunUtil {
	private Board board;
	
	private List<Hand> playerOne = new ArrayList<Hand>();
	private List<Hand> playerTwo = new ArrayList<Hand>();
	
	// used later
	private int playerOneWins;
	private int playerTwoWins;
	private int ties;
	
	// testing
	
	private int debugPlayerOneHandRanking;
	private int debugPlayerTwoHandRanking;
	
	public GameRunUtil(List<Hand> playerOneRange, List<Hand> playerTwoRange, Board board) throws InvalidHandException {
		playerOne.addAll(playerOneRange);
		playerTwo.addAll(playerTwoRange);
		this.board = board;
		
		playerOneWins = 0;
		playerTwoWins = 0;
		ties = 0;
	}
	
	public GameRunUtil(String playerOneRange, String playerTwoRange, String board) throws InvalidHandException {
		playerOne.addAll(HandParserUtil.parseRange(playerOneRange));
		playerTwo.addAll(HandParserUtil.parseRange(playerTwoRange));
		this.board = HandParserUtil.parseBoard(board);
		
		playerOneWins = 0;
		playerTwoWins = 0;
		ties = 0;
	}
	
	public void run() throws InvalidHandException {			
		final int cardsInDeck = 48; // 52 - 2 hands = 52 - 4
		final long monteCarloThreshold = 100000;
		
		long combinations = ((long)playerOne.size() * (long)playerTwo.size()) * nCk(cardsInDeck, 5 - board.getBoard().size());
		
		if (combinations <= monteCarloThreshold) {
			
			System.out.println("running es");
			
			ExhaustiveSimulation es = new ExhaustiveSimulation(playerOne, playerTwo, board);
			es.run();
			
			playerOneWins = es.getPlayerOneWins();
			playerTwoWins = es.getPlayerTwoWins();
			
			debugPlayerOneHandRanking = es.getPlayerOneHandRanking();
			debugPlayerTwoHandRanking = es.getPlayerTwoHandRanking();
			ties = es.getTies();

		} else {
			System.out.println("running mcs");
			
			MonteCarloSimulation mcs = new MonteCarloSimulation(playerOne, playerTwo, board);
			mcs.run();
			
			playerOneWins = mcs.getPlayerOneWins();
			playerTwoWins = mcs.getPlayerTwoWins();
			ties = mcs.getTies();
			
		}
	}
	
	private long nCk(int n, int k) {
		if (k == 0) return 1;
		return (n * nCk(n-1, k-1)/k);
	}
	
	public int getPlayerOneWins() {
		return this.playerOneWins;
	}
	
	public int getPlayerTwoWins() {
		return this.playerTwoWins;
	}
	
	public int getTies() {
		return this.ties;
	}
	
	public int getPlayerOneRanking() {
		return this.debugPlayerOneHandRanking;
	}
	
	public int getPlayerTwoRanking() {
		return this.debugPlayerTwoHandRanking;
	}
}
