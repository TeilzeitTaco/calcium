package calcium.terminal;

import java.util.Scanner;

import calcium.Engine;
import calcium.Fraction;


public final class Terminal {
	public static void main(String[] args) {
		final var scanner = new Scanner(System.in);
		final var engine = new Engine();
		boolean showResultAsDecimal = false;
		
		System.out.println(
				"+----------------------------------------------+\n" +
				"|          Calcium Fraction Calculator         |\n" +
				"| Developed by Flesh-Network developers (2021) |\n" +
				"+----------------------------------------------+\n" +
				"| Sytax:                                       |\n" +
				"|  * frac - Display results as fractions       |\n" +
				"|  * dec  - Display results as decimals        |\n" +
				"|  * vars - Show all variables                 |\n" +
				"|  * quit - Quit the program                   |\n" +
				"+----------------------------------------------+\n");
		
		while(true) {
			System.out.print("> ");
			final var command = scanner.nextLine().strip();
			final var lowercaseCommand = command.toLowerCase();
			if (command.length() == 0)
				continue;
			
			if ("quit".equals(lowercaseCommand)) {
				scanner.close();
				return;
			} 
			
			else if ("dec".equals(lowercaseCommand)) {
				showResultAsDecimal = true;
			}
			
			else if ("frac".equals(lowercaseCommand)) {
				showResultAsDecimal = false;
			}
			
			else if ("vars".equals(lowercaseCommand)) {
				var formatter = new MapFormatter<String, Fraction>(engine.getParser().getVariables());
				System.out.println(formatter);
				
			}
			
			else {
				var resultingFraction = engine.evaluteString(command);
				if (resultingFraction != null)
					if (showResultAsDecimal)
						System.out.println(resultingFraction.toDouble());
					else
						System.out.println(resultingFraction);
			}
		}
	}
}
