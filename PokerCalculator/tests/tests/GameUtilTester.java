package tests;

import java.util.ArrayList;

import java.util.List;

import org.junit.Test;

import org.junit.Assert;
import model.GameRunUtil;
import model.InvalidHandException;
import persistence.Board;
import persistence.Card;
import persistence.Hand;

public class GameUtilTester {
	
	// same cards in player 1 range (only 1 hand) as on board, no alternatives so throw exception
	@Test
	public void invalidHandInBoardTest() throws InvalidHandException {
		GameRunUtil gru = new GameRunUtil("QhTs", "22", "2dAc4h6s5h"); 
		
		System.out.println(gru.testMonte());
	}
}
