package calcium.functions;

import java.util.List;
import calcium.Fraction;
import calcium.Parser;
import calcium.Token;


public class UserDefinedFunction implements Function {
	private final List<String> parameterNames;
	private final List<Token> functionTokens;
	private final String name;
	
	public UserDefinedFunction(String name, List<String> parameterNames, List<Token> functionTokens) {
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
		for (var token : functionTokens)
			parserTokens.add(position++, token);
	}
	
	@Override
	public String toString() {
		return "%s%s = ".formatted(name, parameterNames)
				.replace('[', '(')
				.replace(']', ')');
	}
}
