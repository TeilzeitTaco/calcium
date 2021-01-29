package calcium.functions.misc;

import java.util.List;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.functions.BuiltinFunction;


public class IsPrimeFunction extends BuiltinFunction {
	@Override
	public int getParameterCount() {
		return 1;
	}

	@Override
	public String getName() {
		return "prime";
	}

	@Override
	public void call(Parser parser, List<Fraction> arguments) {
		var position = parser.getPosition();
		var parserTokens = parser.getTokens();
		
		var parameterDouble = arguments.get(0).toDouble();
		var roundedDouble = Math.round(parameterDouble);
		var doubleIndicatingIfPrime = isPrimeNumber(roundedDouble);
		
		var newFraction = Fraction.fromDouble(doubleIndicatingIfPrime);
		var newValueToken = new Token(TOKEN_TYPE.T_VALUE, newFraction);
		
		parserTokens.add(position, newValueToken);
	}
	
	private double isPrimeNumber(long candidate) {
		long counter = candidate / 2 + 1;
		while (counter --> 2)
			if (candidate % counter == 0)
				return 0;
		
		return 1;
	}
}
