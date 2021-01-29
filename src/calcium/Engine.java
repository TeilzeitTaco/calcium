package calcium;

import calcium.exceptions.QuitException;
import calcium.functions.Function;
import calcium.functions.UserDefinedFunctionFactory;
import calcium.functions.misc.IsPrimeFunction;
import calcium.functions.misc.RoundFunction;
import calcium.functions.misc.SquareRootFunction;
import calcium.functions.trigonometry.CosineFunction;
import calcium.functions.trigonometry.SineFunction;
import calcium.functions.trigonometry.TangentFunction;
import calcium.terminal.MapFormatter;


public class Engine {
	private final Parser parser = new Parser();
	private final Tokenizer tokenizer = new Tokenizer();
	private boolean showResultAsDecimal;
	
	public Engine() {
		parser.registerFunction(new SineFunction());
		parser.registerFunction(new CosineFunction());
		parser.registerFunction(new TangentFunction());
		
		parser.registerFunction(new SquareRootFunction());
		parser.registerFunction(new RoundFunction());
		parser.registerFunction(new IsPrimeFunction());
	}
	
	public void executeStatement(final String command) throws QuitException {
		final var lowercaseCommand = command.toLowerCase();
		
		if ("quit".equals(lowercaseCommand))
			throw new QuitException(); 
		
		else if ("dec".equals(lowercaseCommand))
			showResultAsDecimal = true;
		
		else if ("frac".equals(lowercaseCommand))
			showResultAsDecimal = false;
		
		else if ("verbose".equals(lowercaseCommand))
			parser.setVerbose(true);
		
		else if ("quiet".equals(lowercaseCommand))
			parser.setVerbose(false);
		
		else if ("vars".equals(lowercaseCommand)) {
			var formatter = new MapFormatter<String, Fraction>(parser.getVariables());
			System.out.println(formatter);
		}
		
		else if ("funcs".equals(lowercaseCommand)) {
			var formatter = new MapFormatter<String, Function>(parser.getFunctions());
			System.out.println(formatter);
		}
		
		else if (UserDefinedFunctionFactory.checkIfStringIsFunctionDeclaration(command)) {
			try {
				var newFunction = UserDefinedFunctionFactory.createFunctionFromDeclaration(command);
				parser.registerFunction(newFunction);
			}
			
			catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		
		else {
			var resultingFraction = evaluteString(command);
			if (resultingFraction != null)
				if (showResultAsDecimal)
					System.out.println(resultingFraction.toDouble());
				else
					System.out.println(resultingFraction);
		}
	}
	
	private Fraction evaluteString(final String expression) {
		try {
			var tokens = tokenizer.tokenize(expression);				
			var resultingFraction = parser.parse(tokens);
			return resultingFraction;
		}
		
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
