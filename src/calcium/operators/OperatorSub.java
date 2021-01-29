package calcium.operators;

import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.Tokens;


public final class OperatorSub implements Operator {
	private Token right, left;
	private Tokens tokens;
	private int position;

	@Override
	public boolean onPassOver(int position, Parser parser) {
		tokens = parser.getTokens();
		this.position = position;
		
		// Dyadic minus, "a - b"
		if (thereIsARightToken() && thereIsALeftToken()) {
			findTokensForDyadicMinus();
			if (leftAndRightAreBothValues()) {
				var resultingFraction = left.getValue().sub(right.getValue());
				tokens.set(position, new Token(TOKEN_TYPE.T_VALUE, resultingFraction));
				consumeRightToken();
				consumeLeftToken();
				return true;
			}
		}
		
		// Monadic minus "-a"
		if (thereIsARightToken()) {
			findTokensForMondaicMinus();
			if (thereIsALeftToken() && !left.getTokenType().isMathOperator())
				return false;
			
			if (rightIsValue()) {
				var resultingFraction = right.getValue().neg();
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
}
