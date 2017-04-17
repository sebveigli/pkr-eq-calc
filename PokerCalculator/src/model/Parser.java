package model;

import java.util.Set;

import persistence.Board;
import persistence.Card;
import persistence.Hand;

public abstract class Parser {
	abstract public boolean matches(String input);	
	abstract public Set<Hand> parse(String input) throws InvalidHandException;
}
