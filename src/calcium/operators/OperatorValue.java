package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public final class OperatorValue extends DyadicOperator {
	public OperatorValue() {
		super(TOKEN_TYPE.T_BRACE_LEFT, TOKEN_TYPE.T_BRACE_RIGHT);
	}

	@Override
	public Fraction onConstraintsMet(Parser parser, Token left, Token center, Token right) {
		return center.getValue();
	}
}
