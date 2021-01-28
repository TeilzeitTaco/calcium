package calcium;


/**
 * Ein Token, Produkt des [Tokenizer].
 * Besteht aus einem Typ und optional einem Bruch.
 * 
 * @author SeiJ
 */
public final class Token {
	private final TOKEN_TYPE tokenType;
	private final Fraction value;
	private final String name;
	
	public Token(TOKEN_TYPE tokenType) {
		this(tokenType, null, null);
	}
	
	public Token(TOKEN_TYPE tokenType, Fraction value) {
		this(tokenType, value, null);
	}
	
	public Token(TOKEN_TYPE tokenType, String name) {
		this(tokenType, null, name);
	}
	
	public Token(TOKEN_TYPE tokenType, Fraction value, String name) {
		this.tokenType = tokenType;
		this.value = value;
		this.name = name;
	}
	
	public TOKEN_TYPE getTokenType() {
		return tokenType;
	}
	
	public Fraction getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "<Token(tokenType=" + tokenType + ", value=" + value + ", name=" + name + ")>";
	}
}
