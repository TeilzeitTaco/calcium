package calcium.functions.trigonometry;


public final class TangentFunction extends TrigonometricFunction {
	@Override
	public String getName() {
		return "tan";
	}

	@Override
	public double getFunctionValue(double radians) {
		return Math.tan(radians);
	}
}
