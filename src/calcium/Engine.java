package calcium;


public class Engine {
	private final Parser parser = new Parser();
	private final Tokenizer tokenizer = new Tokenizer();
	
	public Fraction evaluteString(final String command) {
		try {
			var tokens = tokenizer.tokenize(command);				
			var resultingFraction = parser.parse(tokens);
			return resultingFraction;
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Parser getParser() {
		return parser;
	}
}
