package calcium.operators;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


/**
 * Dieser Operator, getriggert mit dem T_VALUE Tokentyp,
 * entfernt überschüssige Klammern.
 * 
 * @author SeiJ
 */
public final class OperatorValue extends DyadicOperator {
	public OperatorValue() {
		super(TOKEN_TYPE.T_BRACE_LEFT, TOKEN_TYPE.T_BRACE_RIGHT);
	}

	@Override
	public Fraction onDyadicConstraintMet(Parser parser, Token left, Token center, Token right) {
		return center.getValue();
	}
}
