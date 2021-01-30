package calcium.functions.trigonometry;

import java.util.List;

import calcium.Fraction;
import calcium.FractionFactory;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.functions.BuiltinFunction;


public abstract class TrigonometricFunction extends BuiltinFunction {
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
		
		var newFraction = FractionFactory.fromDouble(functionValue);
		var newValueToken = new Token(TOKEN_TYPE.T_VALUE, newFraction);
		
		parserTokens.add(position, newValueToken);
	}
	
	public abstract double getFunctionValue(double radians);
}
