package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public final class OperatorSub extends DyadicOperator {
	public OperatorSub() {
		super(TOKEN_TYPE.T_VALUE, TOKEN_TYPE.T_VALUE);
	}

	@Override
	public Fraction onConstraintsMet(Parser parser, Token left, Token center, Token right) {
		return left.getValue().sub(right.getValue());
	}
}
