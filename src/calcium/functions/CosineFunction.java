package calcium.functions;


public class CosineFunction extends TrigonometricFunctions {
	@Override
	public String getName() {
		return "cos";
	}

	@Override
	public double getFunctionValue(double radians) {
		return Math.cos(radians);
	}
}
