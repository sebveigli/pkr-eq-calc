package model;

import java.util.HashSet;
import java.util.Set;

import persistence.Card;
import persistence.Hand;
import persistence.Rank;
import persistence.Suit;

public class PairParser extends Parser{

	@Override
	public boolean matches(String input) {
		if (input.length() == 2) {
			char[] inputAsChar = input.toCharArray();
			if (inputAsChar[0] == inputAsChar[1]) return true;
		} return false;
	}

	@Override
	public Set<Hand> parse(String input) throws InvalidHandException {
		Set<Hand> range = new HashSet<Hand>();
		// We need to create a range of hands for every combination of pairs
		// We have to cycle through every combination (4 Cards, 4 Suits)
		// we have n choose k combinations
		// for all combinations of cards/suits without repetition, n = 4, k = 2
		
		// General formula (n * (n - 1) * ... * (n - k + 1))/(1*2*...*k)

		int n = 4; 	// 4 elements (4 suits in the deck [Clubs, Diamonds, Hearts,
					// 									Spades])
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
				Card firstCard = new Card(Rank.parse(input.charAt(0)), Suit.parse((byte)firstSuit));
				Card secondCard = new Card(Rank.parse(input.charAt(0)), Suit.parse((byte)secondSuit));
					
					
				//System.out.println(Byte.toString(firstCard.getRankAsByte()));
				Hand newHand = new Hand(firstCard, secondCard);
					
				range.add(newHand);

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
	return range;
	}

}
