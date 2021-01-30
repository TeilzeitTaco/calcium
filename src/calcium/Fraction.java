package calcium;



/**
 * Repräsentiert einen Bruch.
 * 
 * @author SeiJ
 */
public final class Fraction {	
	private final long z, n;
	
	// Do not user outside of factory!
	public Fraction(long z, long n) {
		this.z = z;
		this.n = n;
	}
	
	public Fraction add(final Fraction frac) {
		final var newN = n * frac.n;
		final var newZ = (z * frac.n) + (frac.z * n);
		return FractionFactory._new(newZ, newN);
	}
	
	public Fraction sub(final Fraction frac) {
		final var newN = n * frac.n;
		final var newZ = (z * frac.n) - (frac.z * n);
		return FractionFactory._new(newZ, newN);
	}
	
	public Fraction mul(final Fraction frac) {
		return FractionFactory._new(z * frac.z, n * frac.n);
	}
	
	public Fraction div(final Fraction frac) {
		return FractionFactory._new(z * frac.n, n * frac.z);
	}
	
	public Fraction inv() {
		return FractionFactory._new(n, z);
	}
	
	public Fraction neg() {
		return FractionFactory._new(-z, n);
	}
	
	public Fraction abs() {
		return FractionFactory._new(Math.abs(z), Math.abs(n));
	}
	
	public double toDouble() {
		return z / (double) n;
	}

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
