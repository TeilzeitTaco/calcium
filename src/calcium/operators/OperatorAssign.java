package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public class OperatorAssign extends DyadicOperator {
	public OperatorAssign() {
		super(TOKEN_TYPE.T_NAME, TOKEN_TYPE.T_VALUE);
	}

	@Override
	public Fraction onConstraintsMet(Parser parser, Token left, Token center, Token right) {
		var nameToAssignTo = left.getName();
		var valueToAssign = right.getValue();
		
		parser.setVariable(nameToAssignTo, valueToAssign);
		return valueToAssign;
	}
}
