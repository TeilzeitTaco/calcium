package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.Tokens;


public abstract class MonadicOrDyadicOperator implements Operator {
	private final TOKEN_TYPE leftConstraint, rightConstraint;
	
	private Fraction resultingFraction;
	private Token left, center, right;
	private Tokens tokens;
	private int position;
	
	public MonadicOrDyadicOperator(TOKEN_TYPE leftConstraint, TOKEN_TYPE rightConstraint) {
		this.rightConstraint = rightConstraint;
		this.leftConstraint = leftConstraint;
	}

	@Override
	public boolean onPassOver(int position, Parser parser) {
		tokens = parser.getTokens();
		this.position = position;
		
		// Dyadic operator of the form "a ? b"
		if (thereIsARightToken() && thereIsALeftToken()) {
			findTokensForDyadicOperator();
			if (leftAndRightBothMeetConstraints()) {
				resultingFraction = onDyadicConstraintMet(parser, left, center, right);
				if (resultingFraction == null)
					return false;
				
				insertResultingToken();
				consumeRightToken();
				consumeLeftToken();
				return true;
			}
		}
		
		// Monadic operator of the form "?a"
		if (thereIsARightToken()) {
			findTokensForMondaicOperator();
			if (thereIsALeftToken() && !left.getTokenType().isMonadicInsignificant())
				return false;
			
			if (rightMeetsConstraint()) {
				resultingFraction = onMonadicConstraintMet(parser, center, right);
				if (resultingFraction == null)
					return false;
				
				insertResultingToken();
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
	
	private void findTokensForDyadicOperator() {
		left = tokens.get(position - 1);
		findTokensForMondaicOperator();
	}
	
	private void findTokensForMondaicOperator() {
		center = tokens.get(position);
		right = tokens.get(position + 1);
	}
	
	private boolean leftAndRightBothMeetConstraints() {
		return left.getTokenType() == leftConstraint && rightMeetsConstraint();
	}
	
	private boolean rightMeetsConstraint() {
		return right.getTokenType() == rightConstraint;
	}
	
	private void insertResultingToken() {
		tokens.set(position, new Token(TOKEN_TYPE.T_VALUE, resultingFraction));
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
