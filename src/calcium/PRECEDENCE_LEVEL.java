package calcium;


/**
 * Kombiniert mehrere [TOKEN_TYPE]n zu einem "level",
 * ein Set von Operationen die mathematisch gleich gewichtet
 * sind (und unter denen von Links nach Rechts vorgegangen
 * werden soll).
 * 
 * @author SeiJ
 */
public enum PRECEDENCE_LEVEL {
	BRACES_LEVEL(TOKEN_TYPE.T_VALUE),
	RESOLUTION_LEVEL(TOKEN_TYPE.T_NAME),
	POW_LEVEL(TOKEN_TYPE.T_POW),
	DOT_LEVEL(TOKEN_TYPE.T_MUL, TOKEN_TYPE.T_DIV),
	LINE_LEVEL(TOKEN_TYPE.T_ADD, TOKEN_TYPE.T_SUB),
	ASSIGNMENT_LEVEL(TOKEN_TYPE.T_ASSIGN);
	
	private final TOKEN_TYPE[] tokenTypes;
	
	private PRECEDENCE_LEVEL(TOKEN_TYPE... tokenTypes) {
		this.tokenTypes = tokenTypes;
	}
	
	public TOKEN_TYPE[] getTokenTypes() {
		return tokenTypes;
	}
}
