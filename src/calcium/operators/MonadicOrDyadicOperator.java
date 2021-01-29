package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.Tokens;


public abstract class MonadicOrDyadicOperator implements Operator {
	private Token left, center, right;
	private Tokens tokens;
	private int position;

	@Override
	public boolean onPassOver(int position, Parser parser) {
		tokens = parser.getTokens();
		this.position = position;
		
		// Dyadic operator, "a ? b"
		if (thereIsARightToken() && thereIsALeftToken()) {
			findTokensForDyadicMinus();
			if (leftAndRightAreBothValues()) {
				var resultingFraction = onDyadicConstraintMet(parser, left, center, right);
				tokens.set(position, new Token(TOKEN_TYPE.T_VALUE, resultingFraction));
				consumeRightToken();
				consumeLeftToken();
				return true;
			}
		}
		
		// Monadic operator "?a"
		if (thereIsARightToken()) {
			findTokensForMondaicMinus();
			if (thereIsALeftToken() && !left.getTokenType().isMathOperator())
				return false;
			
			if (rightIsValue()) {
				var resultingFraction = onMonadicConstraintMet(parser, center, right);
				tokens.set(position, new Token(TOKEN_TYPE.T_VALUE, resultingFraction));
				consumeRightToken();
				return true;
			}
		}
		
		return false;
	}
	
	private boolean thereIsARightToken() {
		return position < tokens.size() - 1;
	}
	
	private boolean thereIsALeftToken() {
		return position > 0;
	}
	
	private void findTokensForDyadicMinus() {
		left = tokens.get(position - 1);
		findTokensForMondaicMinus();
	}
	
	private void findTokensForMondaicMinus() {
		center = tokens.get(position);
		right = tokens.get(position + 1);
	}
	
	private boolean leftAndRightAreBothValues() {
		return left.getTokenType() == TOKEN_TYPE.T_VALUE && rightIsValue();
	}
	
	private boolean rightIsValue() {
		return right.getTokenType() == TOKEN_TYPE.T_VALUE;
	}
	
	private void consumeLeftToken() {
		tokens.remove(left);
	}
	
	private void consumeRightToken() {
		tokens.remove(right);
	}
	
	public abstract Fraction onDyadicConstraintMet(Parser parser, Token left, Token center, Token right);
	public abstract Fraction onMonadicConstraintMet(Parser parser, Token center, Token right);
}
