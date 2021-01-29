package calcium;

import java.util.LinkedList;
import java.util.regex.Pattern;



/**
 * Regex-gestützter Tokenizer.
 * 
 * @author SeiJ
 */
public final class Tokenizer {
	private static final String nameRegex = "[A-Za-z][A-Za-z0-9]*";
	private static final String singedDigitsRegex = "(\\+|-)?\\d+";
	private static final String unsignedDigitsRegex = "\\d+";
	private static final Pattern pattern = Pattern.compile("(" + singedDigitsRegex + "|" + nameRegex + "|.)");
	
	private LinkedList<String> stringTokenQueue;
	private Tokens tokenQueue;
	
	public Tokens tokenize(final String command) {
		stringTokenQueue = splitCommandIntoStringTokens(command);
		tokenQueue = new Tokens();
		
		stringTokenLoop: while(!stringTokenQueue.isEmpty()) {
			var stringToken = stringTokenQueue.removeFirst().trim();
			if ("".equals(stringToken))
				continue;
			
			for (var tokenType : TOKEN_TYPE.values()) {
				if (tokenType.getLiteral().equals(stringToken)) {
					tokenQueue.add(new Token(tokenType));
					continue stringTokenLoop;
				}
			}
			
			if (stringToken.matches(nameRegex))
				tokenQueue.add(new Token(TOKEN_TYPE.T_NAME, stringToken));
			
			else if (stringToken.matches(singedDigitsRegex))
				processNumericLiteral(stringToken);
			
			else throw new IllegalArgumentException("Tokenizer Error: \"%s\"".formatted(stringToken));
		}
	
		return tokenQueue;
	}
	
	private void processNumericLiteral(String stringToken) {
		if (stringTokenQueue.size() >= 2) {
			final var maybeOperator = stringTokenQueue.get(0);
			final var maybeDigitsAfterComma = stringTokenQueue.get(1);
			
			if (maybeDigitsAfterComma.matches(unsignedDigitsRegex)) {						
				if (".".equals(stringTokenQueue.get(0))) {
					// A "digits.digits" decimal literal
					stringToken += "." + maybeDigitsAfterComma;
					removeOperatorAndDigits();
					
				} else if ("|".equals(maybeOperator)) {
					// A "digits|digits" fraction literal
					long z = Long.valueOf(stringToken);
					long n = Long.valueOf(maybeDigitsAfterComma);
					var newFraction = Fraction._new(z, n);
					
					tokenQueue.add(new Token(TOKEN_TYPE.T_VALUE, newFraction));
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
		final var stringTokenQueue = new LinkedList<String>();
		final var matcher = pattern.matcher(command);
	    while (matcher.find())
	    	stringTokenQueue.add(matcher.group());
		
		return stringTokenQueue;
	}
	
	private void removeOperatorAndDigits() {
		stringTokenQueue.remove();
		stringTokenQueue.remove();
	}
}
