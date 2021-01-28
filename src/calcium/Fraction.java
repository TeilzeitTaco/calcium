package calcium;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;



/**
 * Repräsentiert einen Bruch.
 * 
 * @author SeiJ
 */
public final class Fraction {
	// SeiJ: Meiner Meinung nach ist es gut, diese Art von kleinen
	//       Datenklassen als immutable anzulegen. Die malloc-Kosten
	//       sind vernachlässigbar und es spart Kopfschmerzen.
	//       Außerdem kann beim immutables overhead mit dem
	//       flyweight-muster mitigiert werden.
	//
	//       Komplett overengineered aber ok.
	//
	private static final ArrayList<Fraction> cache = new ArrayList<>();
	
	private static long euclid(long a, long b) {
		// https://de.wikipedia.org/wiki/Euklidischer_Algorithmus
		if (b == 0) return a;
		return euclid(b, a % b);
	}
	
	/**
	 * Durchsucht den Objektcache nach einem passendem Objekt,
	 * oder erstellt ein neues.
	 * 
	 * @param rz Ungekürzter Zähler
	 * @param rn Ungekürzter Nenner
	 * @return Ein Fraction-Objekt
	 */
	private static Fraction getObjectFromCache(final long rz, final long rn) {
		// Warum eine neue Exception anlegen wenn man passende Bordmittel hat?
		if (rn == 0)
			throw new IllegalArgumentException("Math Error: Division by zero");
		
		final var ggt = euclid(rz, rn);
		final var z = rz / ggt;
		final var n = rn / ggt;
		
		for (var frac : cache)
			if (frac.n == n && frac.z == z)
				return frac;  // Identisches Objekt existiert bereits.
		
		final var newFrac = new Fraction(z, n);
		cache.add(newFrac);
		return newFrac;
	}
	
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
		return getObjectFromCache((long) (d * factorToRemoveComma), factorToRemoveComma);
	}
	
	public static Fraction _new() {
		return getObjectFromCache(0, 1);
	}
	
	public static Fraction _new(long z) {
		return getObjectFromCache(z, 1);
	}
	
	public static Fraction _new(long z, long n) {
		return getObjectFromCache(z, n);
	}
	
	private final long z, n;
	
	private Fraction(long z, long n) {
		this.z = z;
		this.n = n;
	}
	
	///////////
	// MATHE //
	///////////
	public Fraction add(final Fraction frac) {
		final var newN = n * frac.n;
		final var newZ = (z * frac.n) + (frac.z * n);
		return getObjectFromCache(newZ, newN);
	}
	
	public Fraction sub(final Fraction frac) {
		final var newN = n * frac.n;
		final var newZ = (z * frac.n) - (frac.z * n);
		return getObjectFromCache(newZ, newN);
	}
	
	public Fraction mul(final Fraction frac) {
		return getObjectFromCache(z * frac.z, n * frac.n);
	}
	
	public Fraction div(final Fraction frac) {
		return getObjectFromCache(z * frac.n, n * frac.z);
	}
	
	public Fraction inv() {
		return getObjectFromCache(n, z);
	}
	
	public Fraction neg() {
		return getObjectFromCache(-z, n);
	}
	
	public double toDouble() {
		return z / (double) n;
	}
	
	/////////////////
	// BOILERPLATE //
	/////////////////
	@Override
	public String toString() {
		return (n > 1) ? z + "|" + n : "" + z;
	}
	
	@Override
	public boolean equals(Object object) {
		// SeiJ: Es gib für jeden Bruch nur eine Objektinstanz.
		//       deswegen ist es ok, einfach nur die refs zu vergleichen.
		return this == object;
	}
	
	public long getNenner() {
		return n;
	}
	
	public long getZähler() {
		return z;
	}
}
