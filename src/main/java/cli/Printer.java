package cli;

import core.BudgetStatus;

/**
 * Утилита форматированного вывода.
 */
public class Printer {

	public static void printBudgetStatus(BudgetStatus status) {
		switch (status) {
			case OK -> System.out.println("Статус бюджета: OK");
			case WARNING -> System.out.println("⚠ Бюджет использован более чем на 80%");
			case EXCEEDED -> System.out.println("❌ Бюджет превышен");
		}
	}
}
