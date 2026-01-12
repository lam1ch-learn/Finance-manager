package service;

import core.Wallet;

import java.util.List;

/**
 * Сервис статистики и подсчётов.
 */
public class StatsService {

	/**
	 * Общие расходы по выбранным категориям.
	 */
	public double calculateExpensesForCategories(
			Wallet wallet, List<String> categories) {

		double sum = 0;

		for (String category : categories) {
			sum += wallet.getExpensesByCategory(category);
		}

		return sum;
	}

	/**
	 * Общие доходы по выбранным категориям.
	 */
	public double calculateIncomeForCategories(
			Wallet wallet, List<String> categories) {

		double sum = 0;

		for (String category : categories) {
			sum += wallet.getIncomeByCategory(category);
		}

		return sum;
	}
}
