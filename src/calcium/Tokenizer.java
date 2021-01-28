package calcium;

import java.util.LinkedList;
import java.util.regex.Pattern;



/**
 * Regex-gestützter Tokenizer.
 * 
 * @author SeiJ
 */
public final class Tokenizer {
	private static final Pattern pattern = Pattern.compile("(\\d+|[A-Za-z]+|.)");
	private LinkedList<String> stringTokenQueue;
	private LinkedList<Token> tokenQueue;
	
	public LinkedList<Token> tokenize(final String command) {
		stringTokenQueue = splitCommandIntoStringTokens(command);
		tokenQueue = new LinkedList<Token>();
		
		while(!stringTokenQueue.isEmpty()) {
			var stringToken = stringTokenQueue.removeFirst().trim();
			if ("".equals(stringToken))
				continue;
			
			else if ("+".equals(stringToken))
				tokenQueue.add(new Token(TOKEN_TYPE.T_ADD));

			else if ("-".equals(stringToken))
				tokenQueue.add(new Token(TOKEN_TYPE.T_SUB));
			
			else if ("*".equals(stringToken))
				tokenQueue.add(new Token(TOKEN_TYPE.T_MUL));
						
			else if ("/".equals(stringToken))
				tokenQueue.add(new Token(TOKEN_TYPE.T_DIV));
						
			else if ("(".equals(stringToken))
				tokenQueue.add(new Token(TOKEN_TYPE.T_BRACE_LEFT));
			
			else if (")".equals(stringToken))
				tokenQueue.add(new Token(TOKEN_TYPE.T_BRACE_RIGHT));
			
			else if ("=".equals(stringToken))
				tokenQueue.add(new Token(TOKEN_TYPE.T_ASSIGN));
			
			else if (stringToken.matches("[A-Za-z]+"))
				tokenQueue.add(new Token(TOKEN_TYPE.T_NAME, stringToken));
			
			else if (stringToken.matches("\\d+"))
				processNumericLiteral(stringToken);
			
			else throw new IllegalArgumentException("Tokenizer Error: \"%s\"".formatted(stringToken));
		}
	
		return tokenQueue;
	}
	
	private void processNumericLiteral(String stringToken) {
		if (stringTokenQueue.size() >= 2) {
			final var maybeOperator = stringTokenQueue.get(0);
			final var maybeDigitsAfterComma = stringTokenQueue.get(1);
			
			if (maybeDigitsAfterComma.matches("\\d+")) {						
				if (".".equals(stringTokenQueue.get(0))) {
					// A "digits.digits" decimal literal
					stringToken += "." + maybeDigitsAfterComma;
					removeOperatorAndDigits();
					
				} else if ("|".equals(maybeOperator)) {
					// A "digits|digits" fraction literal
					long z = Long.valueOf(stringToken);
					long n = Long.valueOf(maybeDigitsAfterComma);
					var frac = Fraction._new(z, n);
					
					tokenQueue.add(new Token(TOKEN_TYPE.T_VALUE, frac));
					removeOperatorAndDigits();
					return;
				}
			}
		}

		// A "digits" integer literal
		final var d = Double.valueOf(stringToken);
		tokenQueue.add(new Token(TOKEN_TYPE.T_VALUE, Fraction.fromDouble(d)));
	}
	
	private static LinkedList<String> splitCommandIntoStringTokens(String command) {
		var stringTokenQueue = new LinkedList<String>();
		var matcher = pattern.matcher(command);
	    while (matcher.find())
	    	stringTokenQueue.add(matcher.group());
		
		return stringTokenQueue;
	}
	
	private void removeOperatorAndDigits() {
		stringTokenQueue.remove();
		stringTokenQueue.remove();
	}
}
