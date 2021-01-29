package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.Token;


public final class OperatorSub extends MonadicOrDyadicOperator {
	@Override
	public Fraction onDyadicConstraintMet(Parser parser, Token left, Token center, Token right) {
		return left.getValue().sub(right.getValue());
	}

	@Override
	public Fraction onMonadicConstraintMet(Parser parser, Token center, Token right) {
		return right.getValue().neg();
	}
}
