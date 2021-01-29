package calcium.functions;


public class SineFunction extends TrigonometricFunctions {
	@Override
	public String getName() {
		return "sin";
	}

	@Override
	public double getFunctionValue(double radians) {
		return Math.sin(radians);
	}
}
