package calcium.operators;

import java.util.LinkedList;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


/**
 * Operator welcher die [TOKEN_TYPE]s links und rechts
 * von ihm überprüft.
 * 
 * @author SeiJ
 */
public abstract class DyadicOperator implements Operator {
	private final TOKEN_TYPE leftConstraint, rightConstraint;
	private LinkedList<Token> tokens;
	
	public DyadicOperator(TOKEN_TYPE leftConstraint, TOKEN_TYPE rightConstraint) {
		this.rightConstraint = rightConstraint;
		this.leftConstraint = leftConstraint;
	}
	
	@Override
	public final boolean onPassOver(int position, Parser parser) {
		// We need a left and a right hand side to apply an operator
		tokens = parser.getTokens();
		if (position < tokens.size() - 1 && position > 0) {
			var right = tokens.get(position + 1);
			var left = tokens.get(position - 1);
			var center = tokens.get(position);
			
			if (left.getTokenType() == leftConstraint && right.getTokenType() == rightConstraint) {
				var resultingFraction = onConstraintsMet(parser, left, center, right);
				tokens.set(--position, new Token(TOKEN_TYPE.T_VALUE, resultingFraction));
				removeConsumedTokens(position);
				parser.setPosition(position);
				return true;
			}
		}
		
		return false;
	}
	
	private void removeConsumedTokens(int position) {
		tokens.remove(position + 1);
		tokens.remove(position + 1);
	}
	
	public abstract Fraction onConstraintsMet(Parser parser, Token left, Token center, Token right);
}
