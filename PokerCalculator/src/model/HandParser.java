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
		String testHand = "AA, KK+, AKo, 33-66, 8c9d, 8c8d, TT-77, QQ-QQ, AxBy";
		parseHand(testHand);
		for (int i = 0; i < handsList.size(); i++) {
			System.out.println(Character.toString(getHands().get(i).getFirstRank()) + Character.toString(getHands().get(i).getSecondRank()));
		}
	}
	public HandParser(String input) throws InvalidHandException {
		parseHand(input);
	}
	
	public static void parseHand(String input) throws InvalidHandException {		
		// We accept the following cases:
		// AxBy/AxBx (Rank, Suit, Rank, Suit: offsuit & suited) (4 chars)
		// AA (Pair) (2 chars)
		// AA+ (Pair or higher) (3 chars)
		// ABo/ABs (Rank, Rank, Suit: offsuit & suited) (3 chars)
		// AA-BB (Between 2 pairs: e.g, 22-44 = 22,33,44) (5 chars)
		
		// Split the string to a list by comma separated values, removing white spaces
		// Then we can go through the list, check the size of the substring and determine the combinations we check for.
		
		List<String> handsInList = new ArrayList<String>(Arrays.asList(input.split("[\\s,]+")));
				
		char[] inputAsCharacters;
		try {
			// Start loop through the number of substrings we have after splitting
			for (int i = 0; i < handsInList.size(); i++) {
				// Grab the i-th element's length, we can then decide what type of hand input it is
				switch (handsInList.get(i).length()) {
				// Check for a pair (AA)
				case 2:
					inputAsCharacters = handsInList.get(i).toCharArray();
					if (inputAsCharacters[0] == inputAsCharacters[1]) {		// Determine if it is actually a pair
						if (isValidCard(inputAsCharacters[0])) {			// Check whether they are valid inputs
							createNewPair(inputAsCharacters[0]);			
						} else throw new InvalidHandException("Invalid Hand: Correct format is 'AA'");
					}
					break;
					
				// Pair or Above (QQ+) or Offsuit or Suited Hand (ABo/ABs)
				case 3:
					inputAsCharacters = handsInList.get(i).toCharArray();
					
					// Check the input is valid
					if (isValidCard(inputAsCharacters[0]) && isValidCard(inputAsCharacters[1])) {
						// Check the input is AA+/ABo/ABs, otherwise throw an exception
						if ((inputAsCharacters[0] == inputAsCharacters[1]) && inputAsCharacters[2] == '+') {
							createNewPairPlus(inputAsCharacters[0]);
						} else if ((inputAsCharacters[0] != inputAsCharacters[1]) && inputAsCharacters[2] == 'o') {
							createNewOffsuitHand(inputAsCharacters[0], inputAsCharacters[1]);
						} else if ((inputAsCharacters[0] != inputAsCharacters[1]) && inputAsCharacters[2] == 's') {
							createNewSuitedHand(inputAsCharacters[0], inputAsCharacters[1]);
						} else throw new InvalidHandException("Invalid Hand: Wrong Format. Use [QQ+, AKo, AKs]");
					} else throw new InvalidHandException("Invalid Hand");
					break;
					
				// AxBy/AxBx
				case 4:
					inputAsCharacters = handsInList.get(i).toCharArray();
					// Check for valid cards and suits for each input character
					if (isValidCard(inputAsCharacters[0]) && isValidSuit(inputAsCharacters[1]) && isValidCard(inputAsCharacters[2]) && isValidSuit(inputAsCharacters[3])) {
						if (!(inputAsCharacters[0] == inputAsCharacters[2] && inputAsCharacters[1] == inputAsCharacters[3])) {
							createNewHand(inputAsCharacters);
						} else throw new InvalidHandException("Invalid Hand");
					} else throw new InvalidHandException("Invalid Hand: Invalid Cards or Suits. Use [AsKd for Ace of Spades, K of Diamonds]");
					break;
					
				// Range of Pairs (55-99 = [55, 66, 77, 88, 99]) allowed input: [55-99, 99-55, 99-99]
				case 5:
					inputAsCharacters = handsInList.get(i).toCharArray();
					// Check that the XX-YY pairs are valid cards, and the 3rd input is a '-'
					if (isValidCard(inputAsCharacters[0]) && 
						isValidCard(inputAsCharacters[1]) && 
						isValidCard(inputAsCharacters[3]) && 
						isValidCard(inputAsCharacters[4]) && 
						inputAsCharacters[2] == '-') {
						// Check for 2 pairs
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
		// Pass through input of chars, will be length 4
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
	
	// Create a new pair given the character of the card inputted (e.g, A for Ace)
	private static void createNewPair(char card) {
		
		// We need to create a range of hands for every combination of pairs
		// We have to cycle through every combination (4 Cards, 4 Suits)
		// we have n choose k combinations
		// for all combinations of cards/suits without repetition, n = 4, k = 2
	
		// General formula (n * (n - 1) * ... * (n - k + 1))/(1*2*...*k)
		
		int n = 4;		// 4 elements (4 suits in the deck [Clubs, Diamonds, Hearts, Spades])
		int k = 2;		// 2 choices (we want a pair suits without repetition)
		
		int[] nextRes = new int[] {0,1};		// nextRes[i] is the index for the number for i-th combination
		
		int firstSuit = 0;		// Variables for storing the two generated suits
		int secondSuit = 0;
		
		boolean allCombosReached = false;

		while (!allCombosReached) {
			firstSuit = nextRes[0] + 1;
			secondSuit = nextRes[1] + 1;
			
			Hand newHand = new Hand(new Rank(card), new Suit(firstSuit), new Rank(card), new Suit(secondSuit));
			
			if (isUniqueHand(newHand)) {
				handsList.add(newHand);
			}
			
			// Increment the k-1th element
			int i = k - 1; 
			++nextRes[i];
			
			while((i > 0) && (nextRes[i] >= n - k + 1 + i)) {
				--i;
				++nextRes[i];
			}
			
			// If nextRes[0] is > 2, we have found all combinations
			if (nextRes[0] > n - k) {
				allCombosReached = true;
			} else {
				for (i = i + 1; i < k; ++i) {
					nextRes[i] = nextRes[i - 1] + 1;
				}
			}
		}		
	}
	
	private static void createNewPairRange(char c1, char c2) {
		Rank firstRank = new Rank(c1);
		Rank secondRank = new Rank(c2);
		
		// Determine which card is higher (to handle input: e.g. 66-33, 33-66 & 66-66)
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
	
	public static List<Hand> getHands() {
		return HandParser.handsList;
	}
}
