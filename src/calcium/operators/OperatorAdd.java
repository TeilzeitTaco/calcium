package calcium.operators;

import calcium.Fraction;
import calcium.Token;
import calcium.Parser;
import calcium.TOKEN_TYPE;


public final class OperatorAdd extends MonadicOrDyadicOperator {
	public OperatorAdd() {
		super(TOKEN_TYPE.T_VALUE, TOKEN_TYPE.T_VALUE);
	}

	@Override
	public Fraction onDyadicConstraintMet(Parser parser, Token left, Token center, Token right) {
		return left.getValue().add(right.getValue());
	}

	@Override
	public Fraction onMonadicConstraintMet(Parser parser, Token center, Token right) {
		return right.getValue();
	}
}
