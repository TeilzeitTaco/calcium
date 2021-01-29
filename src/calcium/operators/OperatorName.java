package calcium.operators;

import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public class OperatorName implements Operator {
	@Override
	public boolean onPassOver(int position, Parser parser) {
		// Don't break assignments like "x = (10 + 7)"
		var tokens = parser.getTokens();
		if (position + 1 < tokens.size()) {
			var nextToken = tokens.get(position + 1);
			if (nextToken.getTokenType() == TOKEN_TYPE.T_ASSIGN)
				return false;
		}
		
		var thisNameToken = tokens.get(position);
		var name = thisNameToken.getName();
		
		if (parser.hasFunction(name)) {
			if (parser.couldCallFunction(name)) {
				parser.callFunction(name);
				return true;
				
			} else {
				// Return false because nothing has changed.
				return false;
			}
		}

		// Resolve as a variable
		var resolvedFraction = parser.getVariable(name);
		tokens.set(position, new Token(TOKEN_TYPE.T_VALUE, resolvedFraction));
		return true;
	}
}
