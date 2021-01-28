package calcium.operators;

import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public class OperatorName implements Operator {
	@Override
	public void onPassOver(int position, Parser parser) {
		// Don't break assignments like "x = (10 + 7)"
		var tokens = parser.getTokens();
		if (position + 1 < tokens.size()) {
			var nextToken = tokens.get(position + 1);
			if (nextToken.getTokenType() == TOKEN_TYPE.T_ASSIGN)
				return;
		}

		var thisNameToken = tokens.get(position);
		var resolvedFraction = parser.getVariable(thisNameToken.getName());
		tokens.set(position, new Token(TOKEN_TYPE.T_VALUE, resolvedFraction));
	}
}
