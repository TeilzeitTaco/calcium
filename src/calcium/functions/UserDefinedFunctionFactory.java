package calcium.functions;

import java.util.ArrayList;
import java.util.regex.Pattern;

import calcium.Tokenizer;


public final class UserDefinedFunctionFactory {
	private static final Pattern pattern = Pattern.compile(
			"^ *([A-Za-z0-9]+) *\\((( *[A-Za-z0-9]+ *, *)*([A-Za-z0-9]+ *))?\\) *=(.*)$",
			Pattern.CASE_INSENSITIVE);
	
	private UserDefinedFunctionFactory() {
	}
	
	public static boolean checkIfStringIsFunctionDeclaration(String string) {
		return pattern.asMatchPredicate().test(string);
	}
	
	public static UserDefinedFunction createFunctionFromDeclaration(String functionDeclaration) {
		var matcher = pattern.matcher(functionDeclaration);
		matcher.matches();
		
		var functionBody = matcher.group(5).strip();
		var functionName = matcher.group(1).strip();
		var functionTokens = new Tokenizer().tokenize(functionBody);
		
		var parameterNames = new ArrayList<String>();
		var parameterNamesMatches = matcher.group(2);
		if (parameterNamesMatches != null)
			for (var string : parameterNamesMatches.strip().split(","))
				parameterNames.add(string.strip());
		
		return new UserDefinedFunction(
				functionName, parameterNames, functionTokens);
	}
	
}
