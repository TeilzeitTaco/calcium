package calcium.functions.trigonometry;


public final class CosineFunction extends TrigonometricFunction {
	@Override
	public String getName() {
		return "cos";
	}

	@Override
	public double getFunctionValue(double radians) {
		return Math.cos(radians);
	}
}
