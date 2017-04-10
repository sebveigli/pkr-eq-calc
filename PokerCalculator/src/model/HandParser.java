package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import persistence.Card;
import persistence.Deck;
import persistence.Hand;
import persistence.Rank;
import persistence.Suit;

public class HandParser {

	private static List<Hand> handsList = new ArrayList<Hand>();

	//public static void main(String[] args) throws InvalidHandException  {
	//	String testHand = "AKo";
	//	parseHand(testHand);
	//	for (int i = 0; i < handsList.size(); i++) {
//
		//}	//.get(i).getFirstRank());		} 
	//	System.out.println(handsList.size());
	//}
	
	public static void main(String[] args) throws InvalidHandException {
		Deck gameDeck = new Deck();
		//Deck gameDeck = new Deck();
		for (int i = 0; i < gameDeck.getDeck().size(); i++) {
			System.out.println("Deck: " + Character.toString(gameDeck.getDeck().get(i).getRankAsChar()) + Character.toString(gameDeck.getDeck().get(i).getSuitAsChar()));
		}
		for (int i = 0; i < gameDeck.getMuck().size(); i++) {
			System.out.println("Muck: " + Character.toString(gameDeck.getMuck().get(i).getRankAsChar()) + Character.toString(gameDeck.getMuck().get(i).getSuitAsChar()));
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

		// Split the string to a list by comma separated values, removing white
		// spaces
		// Then we can go through the list, check the size of the substring and
		// determine the combinations we check for.

		List<String> handsInList = new ArrayList<String>(Arrays.asList(input.split("[\\s,]+")));

		char[] inputAsCharacters;
		try {
			// Start loop through the number of substrings we have after
			// splitting
			for (int i = 0; i < handsInList.size(); i++) {
				// Grab the i-th element's length, we can then decide what type
				// of hand input it is
				switch (handsInList.get(i).length()) {
				// Check for a pair (AA)
				case 2:
					inputAsCharacters = handsInList.get(i).toCharArray();
					if (inputAsCharacters[0] == inputAsCharacters[1]) { // Determine
																		// if it
																		// is
																		// actually
																		// a
																		// pair
						if (isValidCard(inputAsCharacters[0])) { // Check
																	// whether
																	// they are
																	// valid
																	// inputs
							createNewPair(inputAsCharacters[0]);
						} else
							throw new InvalidHandException("Invalid Hand: Correct format is 'AA'");
					}
					break;

				// Pair or Above (QQ+) or Offsuit or Suited Hand (ABo/ABs)
				case 3:
					inputAsCharacters = handsInList.get(i).toCharArray();

					// Check the input is valid
					if (isValidCard(inputAsCharacters[0]) && isValidCard(inputAsCharacters[1])) {
						// Check the input is AA+/ABo/ABs, otherwise throw an
						// exception
						if ((inputAsCharacters[0] == inputAsCharacters[1]) && inputAsCharacters[2] == '+') {
							createNewPairPlus(inputAsCharacters[0]);
						} else if ((inputAsCharacters[0] != inputAsCharacters[1]) && inputAsCharacters[2] == 'o') {
							createNewOffsuitHand(inputAsCharacters[0], inputAsCharacters[1]);
						} else if ((inputAsCharacters[0] != inputAsCharacters[1]) && inputAsCharacters[2] == 's') {
							createNewSuitedHand(inputAsCharacters[0], inputAsCharacters[1]);
						} else
							throw new InvalidHandException("Invalid Hand: Wrong Format. Use [QQ+, AKo, AKs]");
					} else
						throw new InvalidHandException("Invalid Hand");
					break;

				// AxBy/AxBx
				case 4:
					inputAsCharacters = handsInList.get(i).toCharArray();
					// Check for valid cards and suits for each input character
					if (isValidCard(inputAsCharacters[0]) && isValidSuit(inputAsCharacters[1])
							&& isValidCard(inputAsCharacters[2]) && isValidSuit(inputAsCharacters[3])) {
						if (!(inputAsCharacters[0] == inputAsCharacters[2]
								&& inputAsCharacters[1] == inputAsCharacters[3])) {
							createNewHand(inputAsCharacters);
						} else
							throw new InvalidHandException("Invalid Hand");
					} else
						throw new InvalidHandException(
								"Invalid Hand: Invalid Cards or Suits. Use [AsKd for Ace of Spades, K of Diamonds]");
					break;

				// Range of Pairs (55-99 = [55, 66, 77, 88, 99]) allowed input:
				// [55-99, 99-55, 99-99]
				case 5:
					inputAsCharacters = handsInList.get(i).toCharArray();
					// Check that the XX-YY pairs are valid cards, and the 3rd
					// input is a '-'
					if (isValidCard(inputAsCharacters[0]) && isValidCard(inputAsCharacters[1])
							&& isValidCard(inputAsCharacters[3]) && isValidCard(inputAsCharacters[4])
							&& inputAsCharacters[2] == '-') {
						// Check for 2 pairs
						if (inputAsCharacters[0] == inputAsCharacters[1]
								&& inputAsCharacters[3] == inputAsCharacters[4]) {
							createNewPairRange(inputAsCharacters[0], inputAsCharacters[3]);
						}
					}
					break;

				default:
					throw new InvalidHandException("Borken1");
				}
			}
		} catch (InvalidHandException e) {
			System.out.println("Caught Exception: " + e.getMessage());
		}
	}

	private static void createNewHand(char[] c) throws InvalidHandException {
		// Pass through input of chars, will be length 4
		Card firstCard = new Card(Rank.parse(c[0]), Suit.parse(c[1]));
		Card secondCard = new Card(Rank.parse(c[2]), Suit.parse(c[3]));
		
		Hand newHand = new Hand(firstCard, secondCard);
		
		//Hand newHand = new Hand(Rank.parse(c[0]), Suit.parse(c[1]), new Rank(c[2]), Suit.parse(c[3]));

		if (isUniqueHand(newHand)) {
			handsList.add(newHand);
		} else throw new InvalidHandException("Borken Hand");
	}

	private static void createNewOffsuitHand(char c1, char c2) throws InvalidHandException {
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (i != j) {
					Card firstCard = new Card(Rank.parse(c1), Suit.parse((byte) i));
					Card secondCard = new Card(Rank.parse(c2), Suit.parse((byte)j));
					
					System.out.println("Trying to create: " + Rank.parse(c1) + Suit.parse((byte)i) + Rank.parse(c2) + Suit.parse((byte)j) );
					Hand newHand = new Hand(firstCard, secondCard);
					
					//Hand newHand = new Hand(, Suit.parse((byte) i), new Rank(c2), Suit.parse((byte) i));
					if (isUniqueHand(newHand)) {
						handsList.add(newHand);
					}
				}
			}
		}
	}

	private static void createNewSuitedHand(char c1, char c2) throws InvalidHandException {
		for (int i = 1; i <= 4; i++) {
			Card firstCard = new Card(Rank.parse(c1), Suit.parse((byte) i));
			Card secondCard = new Card(Rank.parse(c2), Suit.parse((byte) i));
			
			Hand newHand = new Hand(firstCard, secondCard);
			if (isUniqueHand(newHand)) {
				handsList.add(newHand);
			} else throw new InvalidHandException("Borken");
		}
	}

	// Create a new pair given the character of the card inputted (e.g, A for
	// Ace)
	private static void createNewPair(char card) throws InvalidHandException {

		// We need to create a range of hands for every combination of pairs
		// We have to cycle through every combination (4 Cards, 4 Suits)
		// we have n choose k combinations
		// for all combinations of cards/suits without repetition, n = 4, k = 2

		// General formula (n * (n - 1) * ... * (n - k + 1))/(1*2*...*k)

		int n = 4; // 4 elements (4 suits in the deck [Clubs, Diamonds, Hearts,
					// Spades])
		int k = 2; // 2 choices (we want a pair suits without repetition)

		int[] nextRes = new int[] { 0, 1 }; // nextRes[i] is the index for the
											// number for i-th combination

		int firstSuit = 0; // Variables for storing the two generated suits
		int secondSuit = 0;

		boolean allCombosReached = false;

		while (!allCombosReached) {
			firstSuit = nextRes[0] + 1;
			secondSuit = nextRes[1] + 1;
			//System.out.println("Card: " + Character.toString(card) + ". firstSuit & secondSuit: " + Integer.toString(firstSuit) + Integer.toString(secondSuit));

			//Hand newHand = new Hand(new Rank(card), Suit.parse((byte) firstSuit), new Rank(card),
			//		Suit.parse((byte) secondSuit));
			Card firstCard = new Card(Rank.parse(card), Suit.parse((byte)firstSuit));
			Card secondCard = new Card(Rank.parse(card), Suit.parse((byte)secondSuit));
			
			
			//System.out.println(Byte.toString(firstCard.getRankAsByte()));
			Hand newHand = new Hand(firstCard, secondCard);
			
			if (isUniqueHand(newHand)) {
				handsList.add(newHand);
			}

			// Increment the k-1th element
			int i = k - 1;
			++nextRes[i];

			while ((i > 0) && (nextRes[i] >= n - k + 1 + i)) {
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

	private static void createNewPairRange(char c1, char c2) throws InvalidHandException {
		byte firstRank = Rank.parse(c1).getNumberFormat();
		byte secondRank = Rank.parse(c2).getNumberFormat();
		
		// Determine which card is higher (to handle input: e.g. 66-33, 33-66 &
		// 66-66)
		if (firstRank > secondRank) {
			for (int i = 0; i <= firstRank - secondRank; i++) {
				createNewPair(Rank.parse((byte)(secondRank + i)).getCharFormat());
			}
		} else if (firstRank < secondRank) {
			for (int i = 0; i <= secondRank - firstRank; i++) {
				createNewPair(Rank.parse((byte)(firstRank+ i)).getCharFormat());
			}
		} else {
			createNewPair(Rank.parse((byte)(firstRank)).getCharFormat());
		}
	}

	// Here we expect a hand like TT+ (tens or higher)
	private static void createNewPairPlus(char cardOne) throws InvalidHandException {
		byte cardOneVal = Rank.parse(cardOne).getNumberFormat();
		
		
		for (int i = 0; i < 14 - (int)cardOneVal; i++) {
			//System.out.println("cardOneVal: " + Byte.toString(cardOneVal));
			//System.out.println("i: " + Integer.toString(i));
			createNewPair(Rank.parse((byte)(cardOneVal + i)).getCharFormat());
		}
	}

	private static boolean isValidCard(char inputChar) {
		if (Character.toUpperCase(inputChar) == 'A' || Character.toUpperCase(inputChar) == 'K'
				|| Character.toUpperCase(inputChar) == 'Q' || Character.toUpperCase(inputChar) == 'J'
				|| Character.toUpperCase(inputChar) == 'T' || inputChar == '9' || inputChar == '8' || inputChar == '7'
				|| inputChar == '6' || inputChar == '5' || inputChar == '4' || inputChar == '3' || inputChar == '2') {
			return true;
		}
		return false;
	}

	private static boolean isValidSuit(char inputChar) {
		if (Character.toLowerCase(inputChar) == 'c' || Character.toLowerCase(inputChar) == 'd'
				|| Character.toLowerCase(inputChar) == 'h' || Character.toLowerCase(inputChar) == 's') {
			return true;
		}
		return false;
	}

	private static boolean isUniqueHand(Hand hand) {
		//System.out.println("Size: " + handsList.size());
		for (Hand h : handsList) {
			//System.out.println("Using h:" + h.getFirstRank());
			if (h.equals(hand)) return false;
		}
		return true;
	}

	public static List<Hand> getHands() {
		return HandParser.handsList;
	}
}
