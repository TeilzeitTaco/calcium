package calcium.terminal;

import java.util.Scanner;

import calcium.Engine;
import calcium.exceptions.QuitException;


public final class Terminal {
	public static void main(String[] args) {
		final var scanner = new Scanner(System.in);
		final var engine = new Engine();
		
		System.out.println(
				"+----------------------------------------------+\n" +
				"|          Calcium Fraction Calculator         |\n" +
				"| Developed by Flesh-Network developers (2021) |\n" +
				"+----------------------------------------------+\n" +
				"| Sytax:                                       |\n" +
				"|  * frac  - Display results as fractions      |\n" +
				"|  * dec   - Display results as decimals       |\n" +
				"|  * vars  - Show all variables                |\n" +
				"|  * funcs - Show registered functions         |\n" +
				"|  * quit  - Quit the program                  |\n" +
				"+----------------------------------------------+\n");
		
		try {
			while(true) {
				System.out.print("> ");
				final var command = scanner.nextLine().strip();
				if (command.length() > 0)
					engine.executeStatement(command);
			}
			
		} catch(QuitException e) {
			System.out.println("\nGood night...");
			scanner.close();
		}
	}
}
