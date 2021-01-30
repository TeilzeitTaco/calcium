package calcium;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;


/**
 * Speicherklasse für [Fraction]-Objekte.
 * Basiert auf einem "weak set" - Ein [Set], welches nicht
 * den GC blockiert.
 * 
 * @author SeiJ
 */
public class FractionStore {
	private final Set<Fraction> objectCache = Collections.newSetFromMap(new WeakHashMap<Fraction, Boolean>());
	
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
	public Fraction getObjectFromCache(final long rz, final long rn) {
		// Warum eine neue Exception anlegen wenn man passende Bordmittel hat?
		if (rn == 0)
			throw new IllegalArgumentException("Math Error: Division by zero");
		
		final var ggt = euclid(rz, rn);
		final var z = rz / ggt;
		final var n = rn / ggt;
		
		for (var frac : objectCache)
			if (frac.getNenner() == n && frac.getZähler() == z)
				return frac;  // Identisches Objekt existiert bereits.
		
		final var newFrac = new Fraction(z, n);
		objectCache.add(newFrac);
		return newFrac;
	}
}
