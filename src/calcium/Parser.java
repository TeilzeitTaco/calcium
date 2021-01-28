package calcium;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public final class Parser {
	private LinkedList<Token> tokens;
	private int previousTokenCount, position;
	private Map<String, Fraction> variables = new HashMap<>();
	
	public LinkedList<Token> getTokens() {
		return tokens;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public void setVariable(String name, Fraction value) {
		variables.put(name, value);
	}
	
	public Map<String, Fraction> getVariables() {
		return variables;
	}
	
	public Fraction getVariable(String name) {
		if (!variables.containsKey(name))
			throw new IllegalArgumentException("Parser Error: undefined variable " + name);
		
		return variables.get(name);
	}
	
	public Fraction parse(LinkedList<Token> tokens) {
		previousTokenCount = tokens.size() + 1;
		this.tokens = tokens;

		while (previousTokenCount > 1) {
			processAllPrecedenceLevels();
			checkIfThereAreLeftovers();
		}
			
		checkThatResultingTokenIsAValue();
		var resultingFraction = tokens.getFirst().getValue();
		setVariable("ans", resultingFraction);
		
		return resultingFraction;
	}
	
	private void processAllPrecedenceLevels() {
		for (var precedenceLevel : PRECEDENCE_LEVEL.values()) {
			for (position = 0; position < tokens.size(); position++) {				
				var tokenType = determineIfTokenIsInLevel(precedenceLevel, tokens.get(position));
				if (tokenType == null)
					continue;
				
				var operator = tokenType.getOperator();
				if (operator == null)
					continue;
				
				operator.onPassOver(position, this);
			}
		}
	}
	
	private TOKEN_TYPE determineIfTokenIsInLevel(PRECEDENCE_LEVEL precedenceLevel, Token token) {
		for (var tokenType : precedenceLevel.getTokenTypes())
			if (tokenType == token.getTokenType())
				return tokenType;
		return null;
	}
	
	private void checkIfThereAreLeftovers() {
		if (previousTokenCount == (previousTokenCount = tokens.size()))
			throw new IllegalArgumentException("Parser Error: leftover tokens");
	}
	
	private void checkThatResultingTokenIsAValue() {
		var resultingToken = tokens.getFirst();
		if (resultingToken.getTokenType() != TOKEN_TYPE.T_VALUE)
			throw new IllegalArgumentException("Parser Error: invalid result"); 
	}
}
