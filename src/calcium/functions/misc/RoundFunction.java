package calcium.functions.misc;

import java.util.List;

import calcium.Fraction;
import calcium.FractionFactory;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.functions.BuiltinFunction;


public final class RoundFunction extends BuiltinFunction {
	@Override
	public int getParameterCount() {
		return 1;
	}

	@Override
	public String getName() {
		return "rnd";
	}

	@Override
	public void call(Parser parser, List<Fraction> arguments) {
		var position = parser.getPosition();
		var parserTokens = parser.getTokens();
		
		var parameterDouble = arguments.get(0).toDouble();
		var roundedDouble = Math.round(parameterDouble);
		
		var newFraction = FractionFactory.fromDouble(roundedDouble);
		var newValueToken = new Token(TOKEN_TYPE.T_VALUE, newFraction);
		
		parserTokens.add(position, newValueToken);
	}
}
