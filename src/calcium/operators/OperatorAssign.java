package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public final class OperatorAssign extends DyadicOperator {
	public OperatorAssign() {
		super(TOKEN_TYPE.T_NAME, TOKEN_TYPE.T_VALUE);
	}

	@Override
	public Fraction onDyadicConstraintMet(Parser parser, Token left, Token center, Token right) {
		var nameToAssignTo = left.getName();
		var valueToAssign = right.getValue();
		
		parser.setVariable(nameToAssignTo, valueToAssign);
		return valueToAssign;
	}
}
