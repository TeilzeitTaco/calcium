package calcium.operators;

import calcium.Fraction;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.Parser;


public final class OperatorAdd extends DyadicOperator {
	public OperatorAdd() {
		super(TOKEN_TYPE.T_VALUE, TOKEN_TYPE.T_VALUE);
	}

	@Override
	public Fraction onConstraintsMet(Parser parser, Token left, Token center, Token right) {
		return left.getValue().add(right.getValue());
	}
}
