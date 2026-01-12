package service;


import core.BudgetStatus;
import core.Wallet;

import java.util.Map;

/**
 * Сервис работы с финансами пользователя.
 * Используется CLI.
 */
public class FinanceService {

	/**
	 * Проверка состояния бюджета по категории.
	 */
	public BudgetStatus checkBudget(Wallet wallet, String category) {
		return wallet.getBudgetStatus(category);
	}

	/**
	 * Проверка, превышают ли расходы доходы.
	 */
	public boolean isExpensesGreaterThanIncome(Wallet wallet) {
		return wallet.getTotalExpenses() > wallet.getTotalIncome();
	}

	/**
	 * Формирование текстовой сводки по бюджетам.
	 */
	public String buildBudgetReport(Wallet wallet) {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Double> entry : wallet.getBudgets().entrySet()) {
			String category = entry.getKey();
			double limit = entry.getValue();
			double spent = wallet.getExpensesByCategory(category);
			double remaining = limit - spent;

			sb.append("Категория: ").append(category)
					.append(" | Лимит: ").append(limit)
					.append(" | Потрачено: ").append(spent)
					.append(" | Остаток: ").append(remaining)
					.append(" | Статус: ").append(wallet.getBudgetStatus(category))
					.append("\n");
		}

		return sb.toString();
	}
}