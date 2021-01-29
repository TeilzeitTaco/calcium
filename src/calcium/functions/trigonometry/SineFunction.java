package calcium.functions.trigonometry;


public final class SineFunction extends TrigonometricFunction {
	@Override
	public String getName() {
		return "sin";
	}

	@Override
	public double getFunctionValue(double radians) {
		return Math.sin(radians);
	}
}
