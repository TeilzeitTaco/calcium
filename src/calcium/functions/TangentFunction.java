package calcium.functions;


public class TangentFunction extends TrigonometricFunctions {
	@Override
	public String getName() {
		return "tan";
	}

	@Override
	public double getFunctionValue(double radians) {
		return Math.tan(radians);
	}
}
