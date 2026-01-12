package core;

/**
 * Статус бюджета по категории.
 */
public enum BudgetStatus {
	OK,           // меньше 80%
	WARNING,      // больше 80%
	EXCEEDED      // превышен
}

