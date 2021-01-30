package calcium;

import java.text.NumberFormat;
import java.util.Locale;



public class FractionFactory {
	private static FractionStore fractionStore = new FractionStore();
	
	private static int getDecimalPlacesCount(final double d) {
		var numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
		String[] splitNumber = numberFormat.format(d).split("\\.");
		if (splitNumber.length == 1)
			return 0;  // This number has no digits after the decimal dot
		
		int decimalPlaces = splitNumber[1].length();
		return decimalPlaces;
	}
	
	public static Fraction fromDouble(final double d) {
		final int decimalPlaces = getDecimalPlacesCount(d);
		final long factorToRemoveComma = (long) Math.pow(10, decimalPlaces);
		return fractionStore.getObjectFromCache((long) (d * factorToRemoveComma), factorToRemoveComma);
	}
	
	public static Fraction _new() {
		return fractionStore.getObjectFromCache(0, 1);
	}
	
	public static Fraction _new(long z) {
		return fractionStore.getObjectFromCache(z, 1);
	}
	
	public static Fraction _new(long z, long n) {
		return fractionStore.getObjectFromCache(z, n);
	}
}
