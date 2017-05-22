package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import persistence.Board;
import persistence.Hand;

public class HandParserUtil {
	// create a range of hands to return
	private final static Set<Parser> parsers = new HashSet<Parser>();
	private final static List<Hand> handsAdded = new ArrayList<Hand>();
	
	private static List<Hand> parse(String input) throws InvalidHandException {
		parsers.add(new PairParser());
		parsers.add(new OffsuitParser());
		parsers.add(new SuitedParser());
		parsers.add(new PairRangeParser());
		parsers.add(new PairPlusParser());
		parsers.add(new HandParser());
		
		String fInput = formatInput(input);
		
		for (Parser p : parsers) {
			if (p.matches(fInput)) {
				
				try {
					return p.parse(fInput);
				} catch (InvalidHandException e) {
					throw new InvalidHandException();
				}
			}
		}
		throw new InvalidHandException();
	}
	
	public static List<Hand> parseRange(String input) throws InvalidHandException {
		Set<Hand> rangeToParse = new HashSet<Hand>();
		
		String[] handsInList = input.split("[\\s,]+");
		
		for (String h : handsInList) {
			rangeToParse.addAll(parse(h));
		}
		
		return new ArrayList<Hand>(rangeToParse);
	}
	
	public static Board parseBoard(String input) throws InvalidHandException {
		BoardParser parser = new BoardParser();
		
		String fInput = formatInput(input);

		if (parser.matches(fInput)) {
			try {
				return new Board(parser.parseBoard(fInput));
			} catch (InvalidHandException e) {
				throw new InvalidHandException();
			}
		} else throw new InvalidHandException();
	}
	
	final static private String formatInput(String input) {
		return input.replaceAll("\\s","");
	}
}
