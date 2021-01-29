package calcium.functions.misc;

import java.util.List;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.functions.BuiltinFunction;


public class AbsFunction extends BuiltinFunction {
	@Override
	public int getParameterCount() {
		return 1;
	}

	@Override
	public String getName() {
		return "abs";
	}

	@Override
	public void call(Parser parser, List<Fraction> arguments) {
		var position = parser.getPosition();
		var parserTokens = parser.getTokens();
		
		var parameterFraction = arguments.get(0);
		var newFraction = parameterFraction.abs();
		
		var newValueToken = new Token(TOKEN_TYPE.T_VALUE, newFraction);
		parserTokens.add(position, newValueToken);
	}
}
