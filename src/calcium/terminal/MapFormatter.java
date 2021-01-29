package calcium.terminal;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public final class MapFormatter<K, V> {
	private final String formattedString;
	private final int longestValueLength, longestKeyLength;
	
	public MapFormatter(Map<K, V> map) {		
		longestKeyLength = Math.max(6, getLongestLength(map.keySet()));
		longestValueLength = Math.max(6, getLongestLength(map.values()));
		
		final var seperatorLine = makeSeperatorLine();
		final var sb = new StringBuffer(seperatorLine);
		
		addLine(sb, "Key", "Value");
		sb.append(seperatorLine);
		for (var entry : map.entrySet())
			addLine(sb, entry.getKey().toString(), entry.getValue().toString());
		sb.append(seperatorLine);
		
		formattedString = sb.toString();
	}
	
	@Override 
	public String toString() {
		return formattedString;
	}
	
	private void addLine(StringBuffer sb, String key, String value) {
		var paddedKey = String.format("%" + -longestKeyLength + "s", key);
		var paddedValue = String.format("%" + -longestValueLength + "s", value);
		var line = String.format("| %s | %s |\n", paddedKey, paddedValue);
		sb.append(line);
	}
	
	private int getLongestLength(Collection<?> collection) {
		int longest = 0;
		for (var entry : collection) {
			var entryStringLength = entry.toString().length();
			if (entryStringLength > longest)
				longest = entryStringLength;
		}

		return longest;
	}
	
	private String makeSeperatorLine() {
		final var sb = new StringBuffer();
		sb.append('+');
		sb.append(repeatStringNTimes("-", longestKeyLength + 2));
		sb.append('+');
		sb.append(repeatStringNTimes("-", longestValueLength + 2));
		sb.append("+\n");
		return sb.toString();
	}
	
	private String repeatStringNTimes(String s, int n) {
		return String.join("", Collections.nCopies(n, s));
	}
}
