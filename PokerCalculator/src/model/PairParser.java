package model;

import java.util.ArrayList;

import java.util.List;
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
	public List<Hand> parse(String input) throws InvalidHandException {
		List<Hand> range = new ArrayList<Hand>();
		
		Card p1c1 = new Card(Rank.parse(input.charAt(0)), Suit.parse('c'));
		Card p1c2 = new Card(Rank.parse(input.charAt(0)), Suit.parse('d'));
		
		Card p2c1 = new Card(Rank.parse(input.charAt(0)), Suit.parse('c'));
		Card p2c2 = new Card(Rank.parse(input.charAt(0)), Suit.parse('h'));
		
		Card p3c1 = new Card(Rank.parse(input.charAt(0)), Suit.parse('c'));
		Card p3c2 = new Card(Rank.parse(input.charAt(0)), Suit.parse('s'));
		
		Card p4c1 = new Card(Rank.parse(input.charAt(0)), Suit.parse('d'));
		Card p4c2 = new Card(Rank.parse(input.charAt(0)), Suit.parse('h'));
		
		Card p5c1 = new Card(Rank.parse(input.charAt(0)), Suit.parse('d'));
		Card p5c2 = new Card(Rank.parse(input.charAt(0)), Suit.parse('s'));
		
		Card p6c1 = new Card(Rank.parse(input.charAt(0)), Suit.parse('h'));
		Card p6c2 = new Card(Rank.parse(input.charAt(0)), Suit.parse('s'));
		
		
		range.add(new Hand(p1c1,p1c2));
		range.add(new Hand(p2c1,p2c2));
		range.add(new Hand(p3c1,p3c2));
		range.add(new Hand(p4c1,p4c2));
		range.add(new Hand(p5c1,p5c2));
		range.add(new Hand(p6c1,p6c2));
		
		return range;
	}
}
