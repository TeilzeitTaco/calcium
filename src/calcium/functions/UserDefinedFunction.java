package calcium.functions;

import java.util.List;
import calcium.Fraction;
import calcium.Parser;
import calcium.TOKEN_TYPE;
import calcium.Token;
import calcium.Tokens;


public final class UserDefinedFunction implements Function {
	private final List<String> parameterNames;
	private final Tokens functionTokens;
	private final String name;
	
	public UserDefinedFunction(String name, List<String> parameterNames, Tokens functionTokens) {
		this.parameterNames = parameterNames;
		this.functionTokens = functionTokens;
		this.name = name;
	}
	
	public int getParameterCount() {
		return parameterNames.size();
	}
	
	public String getName() {
		return name;
	}
	
	public void call(Parser parser, List<Fraction> arguments) {
		for (int i = 0; i < parameterNames.size(); i++) {
			var parameterName = parameterNames.get(i);
			var argumentValue = arguments.get(i);
			parser.setVariable(parameterName, argumentValue);
		}
		
		var position = parser.getPosition();
		var parserTokens = parser.getTokens();
		
		// Functions resolve within braces
		parserTokens.add(position++, new Token(TOKEN_TYPE.T_BRACE_LEFT));
		for (var token : functionTokens)
			parserTokens.add(position++, token);
		parserTokens.add(position, new Token(TOKEN_TYPE.T_BRACE_RIGHT));
	}
	
	@Override
	public String toString() {
		return "%s%s = %s".formatted(name, parameterNames, functionTokens)
				.replace('[', '(')
				.replace(']', ')');
	}
}
