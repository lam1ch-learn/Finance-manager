package cli;

import java.util.Scanner;

/**
 * Утилита для безопасного чтения ввода пользователя.
 */
public class InputReader {

	private final Scanner scanner = new Scanner(System.in);

	public int readInt(String message) {
		while (true) {
			System.out.print(message);
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Введите корректное число");
			}
		}
	}

	public double readDouble(String message) {
		while (true) {
			System.out.print(message);
			try {
				double value = Double.parseDouble(scanner.nextLine());
				if (value <= 0) {
					throw new NumberFormatException();
				}
				return value;
			} catch (NumberFormatException e) {
				System.out.println("Введите положительное число");
			}
		}
	}

	public String readString(String message) {
		while (true) {
			System.out.print(message);
			String value = scanner.nextLine();
			if (!value.isBlank()) {
				return value;
			}
			System.out.println("Строка не может быть пустой");
		}
	}
}