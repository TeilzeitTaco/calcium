package calcium.operators;

import calcium.Parser;


public interface Operator {
	public boolean onPassOver(int position, Parser parser);
}
