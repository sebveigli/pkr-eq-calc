package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import persistence.Hand;
import persistence.Rank;
import persistence.Suit;

public class HandParser {
	
	private static List<Hand> handsList = new ArrayList<Hand>();
	
	public static void main(String[] args) throws InvalidHandException  {
		String testHand = "AA, KK+, AKo, 33-66, 8c9d, 8c8c";
		parseHand(testHand);
		for (int i = 0; i < handsList.size(); i++) {
			System.out.println("Hands List " + i + ": " +handsList.get(i).getFirstRank() + handsList.get(i).getFirstSuit() + handsList.get(i).getSecondRank() + handsList.get(i).getSecondSuit());
		}
	}
	public HandParser(String input) throws InvalidHandException {
		parseHand(input);
	}
	
	public static void parseHand(String input) throws InvalidHandException {		
		// cases: 
		// AxBy/AxBx (4 chars)
		// AA (2 chars)
		// AA+ (3 chars)
		// ABo/ABs (3 chars)
		// AA-BB (5 chars)
		
		// Split the string to a list by comma separated values
		// Then we can go through the list, check the size of the substring and determine what cards to build
		
		List<String> handsInList = new ArrayList<String>(Arrays.asList(input.split("[\\s,]+")));
				
		char[] inputAsCharacters;
		try {
			for (int i = 0; i < handsInList.size(); i++) {
				switch (handsInList.get(i).length()) {
				// Pair
				case 2:
					inputAsCharacters = handsInList.get(i).toCharArray();
					if (inputAsCharacters[0] == inputAsCharacters[1]) {
						if (isValidCard(inputAsCharacters[0])) {
							createNewPair(inputAsCharacters[0]);
						} else throw new InvalidHandException("Invalid Hand");
					}
					break;
					
				// Pair+ or ABo/ABs
				case 3:
					inputAsCharacters = handsInList.get(i).toCharArray();
					
					// check whether the input is valid
					if (isValidCard(inputAsCharacters[0]) && isValidCard(inputAsCharacters[1])) {
						// check whether the input is AA+/ABo/ABs, otherwise throw an exception
						if ((inputAsCharacters[0] == inputAsCharacters[1]) && inputAsCharacters[2] == '+') {
							createNewPairPlus(inputAsCharacters[0]);
						} else if ((inputAsCharacters[0] != inputAsCharacters[1]) && inputAsCharacters[2] == 'o') {
							createNewOffsuitHand(inputAsCharacters[0], inputAsCharacters[1]);
						} else if ((inputAsCharacters[0] != inputAsCharacters[1]) && inputAsCharacters[2] == 's') {
							createNewSuitedHand(inputAsCharacters[0], inputAsCharacters[1]);
						} else throw new InvalidHandException("Invalid Hand");
					} else throw new InvalidHandException("Invalid Hand");
					break;
					
				// AxBy/AxBx
				case 4:
					inputAsCharacters = handsInList.get(i).toCharArray();
					// check for valid cards and suits for each input character
					if (isValidCard(inputAsCharacters[0]) && isValidSuit(inputAsCharacters[1]) && isValidCard(inputAsCharacters[2]) && isValidSuit(inputAsCharacters[3])) {
						if (!(inputAsCharacters[0] == inputAsCharacters[2] && inputAsCharacters[1] == inputAsCharacters[3])) {
							createNewHand(inputAsCharacters);
						} else throw new InvalidHandException("Invalid Hand");
					} 
					break;
					
				// AA-BB
				case 5:
					inputAsCharacters = handsInList.get(i).toCharArray();
					if (isValidCard(inputAsCharacters[0]) && 
						isValidCard(inputAsCharacters[1]) && 
						isValidCard(inputAsCharacters[3]) && 
						isValidCard(inputAsCharacters[4]) && 
						inputAsCharacters[2] == '-') {
						
						if (inputAsCharacters[0] == inputAsCharacters[1] && inputAsCharacters[3] == inputAsCharacters[4]) {
							createNewPairRange(inputAsCharacters[0], inputAsCharacters[3]);
						}
					}
					break;
					
					
				default: throw new InvalidHandException("Borken");
				}
			}
		} catch (InvalidHandException e) {
			System.out.println("Caught Exception: " + e.getMessage());
		}
	}
	
	private static void createNewHand(char[] c) {
		Hand newHand = new Hand(new Rank(c[0]), new Suit(c[1]), new Rank(c[2]), new Suit(c[3]));
		
		if (isUniqueHand(newHand)) {
			handsList.add(newHand);
		}
	}
	
	private static void createNewOffsuitHand(char c1, char c2) {
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (i != j) {
					Hand newHand = new Hand(new Rank(c1), new Suit(i), new Rank(c2), new Suit(j));
					if (isUniqueHand(newHand)) {
						handsList.add(newHand);
					}
				}
			}
		}		
	}
	
	private static void createNewSuitedHand(char c1, char c2) {
		for (int i = 1; i <= 4; i++) {
			Hand newHand = new Hand(new Rank(c1), new Suit(i), new Rank(c2), new Suit(i));
			if (isUniqueHand(newHand)) {
				handsList.add(newHand);
			}
		}
	}
	
	private static void createNewPair(char card) {
		// We need to create a range of hands for every combination of pairs
		// We have to cycle through every combination (4 Cards, 4 Suits)
		// we have n choose k combinations
		// for all combinations of cards/suits without repetition, n = 4, k = 2
	
		int n = 4;
		int k = 2;
		int[] hands = new int[2];
		int firstSuit = 0;
		int secondSuit = 0;
		boolean allCombosReached = false;
		
		for (int i = 0; i < k; ++i) {
			hands[i] = i;
		}
		
		while (!allCombosReached) {
			for (int i = 0; i < k; ++i) {
				if (i == 0) {
					firstSuit = hands[i] + 1;
				} else secondSuit = hands[i] + 1;
			}
			
			Hand newHand = new Hand(new Rank(card), new Suit(firstSuit), new Rank(card), new Suit(secondSuit));
			
			if (isUniqueHand(newHand)) {
				handsList.add(newHand);
			}
			
			int i = k - 1;
			++hands[i];
			
			while((i > 0) && (hands[i] >= n - k + 1 + i)) {
				--i;
				++hands[i];
			}
			
			if (hands[0] > n - k) {
				allCombosReached = true;
			} else {
				for (i = i + 1; i < k; ++i) {
					hands[i] = hands[i - 1] + 1;
				}
			}
		}		
	}
	
	private static void createNewPairRange(char c1, char c2) {
		Rank firstRank = new Rank(c1);
		Rank secondRank = new Rank(c2);
		
		if (firstRank.getCardInt() > secondRank.getCardInt()) {
			for (int i = 0; i <= firstRank.getCardInt() - secondRank.getCardInt(); i++) {
				createNewPair(new Rank(secondRank.getCardInt() + i).getCardChar());
			}
		} else if (firstRank.getCardInt() < secondRank.getCardInt()) {
			for (int i = 0; i <= secondRank.getCardInt() - firstRank.getCardInt(); i++) {
				createNewPair(new Rank(firstRank.getCardInt() + i).getCardChar());
			}
		} else {
			createNewPair(firstRank.getCardChar());
		}
	}
	
	// Here we expect a hand like TT+ (tens or higher)
	private static void createNewPairPlus(char cardOne) {
		Rank rankOne = new Rank(cardOne);
		
		int cardOneValue = rankOne.getCardInt();
	
		for (int i = 0; i < 14 - cardOneValue; i++) {
			createNewPair(new Rank(cardOneValue + i).getCardChar());
		}
	}
	
	private static boolean isValidCard(char inputChar) {
		if (Character.toUpperCase(inputChar) == 'A' || 
			Character.toUpperCase(inputChar) == 'K' ||
			Character.toUpperCase(inputChar) == 'Q' ||
			Character.toUpperCase(inputChar) == 'J' ||
			Character.toUpperCase(inputChar) == 'T' ||
			inputChar == '9' ||
			inputChar == '8' ||
			inputChar == '7' ||
			inputChar == '6' ||
			inputChar == '5' ||
			inputChar == '4' || 
			inputChar == '3' || 
			inputChar == '2') {
				return true;
		}
		return false;
	}
	
	private static boolean isValidSuit(char inputChar) {
		if (Character.toLowerCase(inputChar) == 'c' ||
			Character.toLowerCase(inputChar) == 'd' ||
			Character.toLowerCase(inputChar) == 'h' ||
			Character.toLowerCase(inputChar) == 's') {
				return true;
			}
		return false;
	}
	
	private static boolean isUniqueHand(Hand hand) {
		for (Hand h : handsList) {
			if (h.getFirstRank() == hand.getFirstRank() &&
				h.getFirstSuit() == hand.getFirstSuit() &&
				h.getSecondRank() == hand.getSecondRank() &&
				h.getSecondSuit() == hand.getSecondSuit()
			||
				h.getFirstRank() == hand.getSecondRank() &&
				h.getFirstSuit() == hand.getSecondSuit() &&
				h.getSecondRank() == hand.getFirstRank() &&
				h.getSecondSuit() == hand.getFirstSuit()) {
				return false;
			}
		}
		return true;
	}
}
