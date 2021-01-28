package calcium.operators;

import calcium.Parser;


public interface Operator {
	public void onPassOver(int position, Parser parser);
}
