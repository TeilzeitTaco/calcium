package calcium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import calcium.exceptions.InvalidFunctionCallException;
import calcium.functions.Function;


public final class Parser {
	private final Map<String, Fraction> variables = new HashMap<>();
	private final Map<String, Function> functions = new HashMap<>();
	
	private Tokens tokens;
	private boolean changed, verbose;
	private int position;
	
	public Tokens getTokens() {
		return tokens;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setVariable(String name, Fraction value) {
		variables.put(name, value);
	}
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public Map<String, Fraction> getVariables() {
		return variables;
	}
	
	public Fraction getVariable(String name) {
		if (!variables.containsKey(name))
			throw new IllegalArgumentException("Parser Error: undefined variable " + name);
		
		return variables.get(name);
	}
	
	public void registerFunction(Function function) {
		functions.put(function.getName(), function);
	}
	
	public boolean hasFunction(String name) {
		return functions.containsKey(name);
	}
	
	public Map<String, Function> getFunctions() {
		return functions;
	}
	
	public boolean couldCallFunction(String name) {
		if (!functions.containsKey(name))
			throw new IllegalArgumentException("Parser Error: undefined function " + name);
		
		var function = functions.get(name);
		var parameterCount = function.getParameterCount();
		if (position + parameterCount >= tokens.size())
			return false;
		
		for (int i = position + 1; i < position + parameterCount + 1; i++)
			if (tokens.get(i).getTokenType() != TOKEN_TYPE.T_VALUE)
				return false;

		return true;
	}
	
	public void callFunction(String name) {
		if (!functions.containsKey(name))
			throw new IllegalArgumentException("Parser Error: undefined function " + name);
		
		var function = functions.get(name);
		var argumentList = new ArrayList<Fraction>();
		var parameterCount = function.getParameterCount();
		if (position + parameterCount >= tokens.size())
			throw new InvalidFunctionCallException();
		
		// Check & remove tokens which will be used as arguments
		while (parameterCount --> 0) {
			var argumentToken = tokens.remove(position + 1);
			if (argumentToken.getTokenType() != TOKEN_TYPE.T_VALUE)
				throw new InvalidFunctionCallException();
			
			argumentList.add(argumentToken.getValue());
		}
		
		tokens.remove(position);  // Remove the function name token
		function.call(this, argumentList);
	}
	
	public Fraction parse(Tokens tokens) {
		this.tokens = tokens;

		changed = true;
		while (changed) {
			changed = false;
			if (verbose)
				System.out.println(tokens);
			
			processAllPrecedenceLevels();
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

				if (changed = operator.onPassOver(position, this))
					return;
			}
		}
	}
	
	private TOKEN_TYPE determineIfTokenIsInLevel(PRECEDENCE_LEVEL precedenceLevel, Token token) {
		for (var tokenType : precedenceLevel.getTokenTypes())
			if (tokenType == token.getTokenType())
				return tokenType;
		return null;
	}
	
	private void checkThatResultingTokenIsAValue() {
		if (tokens.size() > 1 || tokens.getFirst().getTokenType() != TOKEN_TYPE.T_VALUE)
			throw new IllegalArgumentException("Parser Error: invalid result"); 
	}
}
