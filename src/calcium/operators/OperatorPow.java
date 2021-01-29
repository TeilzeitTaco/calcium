package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public class OperatorPow extends DyadicOperator {
	public OperatorPow() {
		super(TOKEN_TYPE.T_VALUE, TOKEN_TYPE.T_VALUE);
	}

	@Override
	public Fraction onDyadicConstraintMet(Parser parser, Token left, Token center, Token right) {
		return Fraction.fromDouble(
				Math.pow(left.getValue().toDouble(), right.getValue().toDouble()));
	}
}
