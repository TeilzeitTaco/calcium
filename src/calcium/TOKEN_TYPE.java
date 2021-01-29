package calcium;

import calcium.operators.*;


public enum TOKEN_TYPE {
	// The initialization order here is significant,
	// as these types are also referenced in the operator
	// constructors and are null if they aren't already initialized.
	T_BRACE_RIGHT(")", null),
	T_BRACE_LEFT("(", null),
	
	T_VALUE("", new OperatorValue()),
	T_NAME("", new OperatorName()),
	T_ASSIGN("=", new OperatorAssign()),
	
	T_ADD("+", new OperatorAdd()),
	T_SUB("-", new OperatorSub()), 
	T_DIV("/", new OperatorDiv()),
	T_MUL("*", new OperatorMul());

	private final Operator operator;
	private final String literal;
	
	private TOKEN_TYPE(String literal, Operator operator) {
		this.operator = operator;
		this.literal = literal;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public String getLiteral() {
		return literal;
	}
	
	public boolean isMathOperator() {
		return !(
				this == T_BRACE_RIGHT ||
				this == T_BRACE_LEFT ||
				this == T_VALUE ||
				this == T_NAME);
	}
}
