package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import persistence.Hand;

public class HandParserUtil {
	// create a range of hands to return
	private final static Set<Parser> parsers = new HashSet<Parser>();
	
	private static Set<Hand> parse(String input) throws InvalidHandException {
		parsers.add(new PairParser());
		parsers.add(new OffsuitParser());
		parsers.add(new SuitedParser());
		parsers.add(new PairRangeParser());
		parsers.add(new PairPlusParser());
		
		String fInput = formatInput(input);
		
		for (Parser p : parsers) {
			if (p.matches(fInput)) {
				try {
					return p.parse(fInput);
				} catch (InvalidHandException e) {
					return Collections.emptySet();
				}
			}
		}
		return Collections.emptySet();
	}
	
	public static Set<Hand> parseRange(String input) throws InvalidHandException {
		Set<Hand> rangeToParse = new HashSet<Hand>();
		
		Set<String> handsInList = new HashSet<String>(Arrays.asList(input.split("[\\s,]+")));
		
		Iterator<String> it = handsInList.iterator();
		
		while (it.hasNext()) {
			rangeToParse.addAll(parse(it.next()));
		}
		return rangeToParse;
	}
	
	final static private String formatInput(String input) {
		return input.replaceAll("\\s","");
	}
}
