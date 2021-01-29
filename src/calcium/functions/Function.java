package calcium.functions;

import java.util.List;

import calcium.Fraction;
import calcium.Parser;


public interface Function {
	public int getParameterCount();
	public String getName();
	public void call(Parser parser, List<Fraction> arguments);
}
