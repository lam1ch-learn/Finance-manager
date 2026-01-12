import core.Wallet;
import org.junit.jupiter.api.Test;
import service.StatsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsServiceTest {

	@Test
	void expensesByMultipleCategories() {
		Wallet wallet = new Wallet();
		wallet.addExpense("Food", 200);
		wallet.addExpense("Taxi", 100);

		StatsService stats = new StatsService();
		double result = stats.calculateExpensesForCategories(
				wallet, List.of("Food", "Taxi"));

		assertEquals(300, result);
	}

	@Test
	void expensesIgnoreUnknownCategory() {
		Wallet wallet = new Wallet();
		wallet.addExpense("Food", 200);

		StatsService stats = new StatsService();
		double result = stats.calculateExpensesForCategories(
				wallet, List.of("Food", "Cinema"));

		assertEquals(200, result);
	}

	@Test
	void incomeByCategories() {
		Wallet wallet = new Wallet();
		wallet.addIncome("Salary", 1000);
		wallet.addIncome("Bonus", 500);

		StatsService stats = new StatsService();
		double result = stats.calculateIncomeForCategories(
				wallet, List.of("Salary"));

		assertEquals(1000, result);
	}
}

