package calcium.functions;

import java.util.List;

import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;


public abstract class TrigonometricFunctions implements Function {
	@Override
	public final int getParameterCount() {
		return 1;
	}

	@Override
	public final void call(Parser parser, List<Fraction> arguments) {
		var position = parser.getPosition();
		var parserTokens = parser.getTokens();
		
		var parameterDouble = arguments.get(0).toDouble();
		var inRadians = Math.toRadians(parameterDouble);
		var functionValue = getFunctionValue(inRadians);
		
		var newFraction = Fraction.fromDouble(functionValue);
		var newValueToken = new Token(TOKEN_TYPE.T_VALUE, newFraction);
		
		parserTokens.add(position, newValueToken);
	}
	
	public abstract double getFunctionValue(double radians);
	
	@Override 
	public final String toString() {
		return "<builtin function>";
	}
}
