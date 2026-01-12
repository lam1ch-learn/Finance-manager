import core.BudgetStatus;
import core.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class WalletTest {

	@Test
	void incomeIsAddedCorrectly() {
		Wallet wallet = new Wallet();
		wallet.addIncome("Salary", 1000);
		assertEquals(1000, wallet.getTotalIncome());
	}

	@Test
	void expenseIsAddedCorrectly() {
		Wallet wallet = new Wallet();
		wallet.addExpense("Food", 300);
		assertEquals(300, wallet.getTotalExpenses());
	}

	@Test
	void balanceIsCalculatedCorrectly() {
		Wallet wallet = new Wallet();
		wallet.addIncome("Salary", 1000);
		wallet.addExpense("Food", 400);
		assertEquals(600, wallet.getBalance());
	}

	@Test
	void budgetRemainingIsCalculated() {
		Wallet wallet = new Wallet();
		wallet.setBudget("Food", 500);
		wallet.addExpense("Food", 200);
		assertEquals(300, wallet.getRemainingBudget("Food"));
	}

	@Test
	void budgetWarningStatus() {
		Wallet wallet = new Wallet();
		wallet.setBudget("Food", 100);
		wallet.addExpense("Food", 85);
		assertEquals(BudgetStatus.WARNING, wallet.getBudgetStatus("Food"));
	}

	@Test
	void budgetExceededStatus() {
		Wallet wallet = new Wallet();
		wallet.setBudget("Food", 100);
		wallet.addExpense("Food", 150);
		assertEquals(BudgetStatus.EXCEEDED, wallet.getBudgetStatus("Food"));
	}
}