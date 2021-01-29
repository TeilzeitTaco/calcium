package calcium;

import java.util.LinkedList;


public class Tokens extends LinkedList<Token> {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		var sb = new StringBuffer();
		for (var token : this) {
			var tokenType = token.getTokenType();
			if (tokenType == TOKEN_TYPE.T_NAME)
				sb.append(token.getName());
			else if (tokenType == TOKEN_TYPE.T_VALUE)
				sb.append(token.getValue());
			else
				sb.append(tokenType.getLiteral());
			sb.append(' ');
		}
		
		return sb.toString().strip();
	}
}
