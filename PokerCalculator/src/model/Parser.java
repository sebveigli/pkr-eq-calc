package model;

import java.util.List;

import persistence.Hand;

public abstract class Parser {
	abstract public boolean matches(String input);	
	abstract public List<Hand> parse(String input) throws InvalidHandException;
}
