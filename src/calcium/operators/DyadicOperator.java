package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public abstract class DyadicOperator extends MonadicOrDyadicOperator {
	public DyadicOperator(TOKEN_TYPE leftConstraint, TOKEN_TYPE rightConstraint) {
		super(leftConstraint, rightConstraint);
	}
	
	@Override
	public final Fraction onMonadicConstraintMet(Parser parser, Token center, Token right) {
		return null;
	};
}
