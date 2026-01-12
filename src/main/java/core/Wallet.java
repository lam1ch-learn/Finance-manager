package core;

import java.io.Serializable;
import java.util.*;

/**
 * Кошелёк пользователя.
 * Хранит операции и бюджеты.
 */
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;

	private final List<Transaction> transactions = new ArrayList<>();
	private final Map<String, Double> budgets = new HashMap<>();

	/* ---------------- Операции ---------------- */

	public void addIncome(String category, double amount) {
		transactions.add(new Transaction(category, amount, TransactionType.INCOME));
	}

	public void addExpense(String category, double amount) {
		transactions.add(new Transaction(category, amount, TransactionType.EXPENSE));
	}

	public void addTransfer(String category, double amount) {
		transactions.add(new Transaction(category, amount, TransactionType.TRANSFER));
	}

	/* ---------------- Итоги ---------------- */

	public double getTotalIncome() {
		return sumByType(TransactionType.INCOME);
	}

	public double getTotalExpenses() {
		return sumByType(TransactionType.EXPENSE);
	}

	public double getBalance() {
		return getTotalIncome() - getTotalExpenses();
	}

	/* ---------------- Категории ---------------- */

	public double getExpensesByCategory(String category) {
		return transactions.stream()
				.filter(t -> t.getType() == TransactionType.EXPENSE)
				.filter(t -> t.getCategory().equals(category))
				.mapToDouble(Transaction::getAmount)
				.sum();
	}

	public double getIncomeByCategory(String category) {
		return transactions.stream()
				.filter(t -> t.getType() == TransactionType.INCOME)
				.filter(t -> t.getCategory().equals(category))
				.mapToDouble(Transaction::getAmount)
				.sum();
	}

	public Set<String> getAllCategories() {
		Set<String> categories = new HashSet<>();
		for (Transaction t : transactions) {
			categories.add(t.getCategory());
		}
		return categories;
	}

	/* ---------------- Бюджеты ---------------- */

	public void setBudget(String category, double limit) {
		if (limit <= 0) {
			throw new IllegalArgumentException("Лимит бюджета должен быть положительным");
		}
		budgets.put(category, limit);
	}

	public Map<String, Double> getBudgets() {
		return budgets;
	}

	public double getRemainingBudget(String category) {
		if (!budgets.containsKey(category)) {
			throw new IllegalArgumentException("Бюджет для категории не найден");
		}
		return budgets.get(category) - getExpensesByCategory(category);
	}

	public BudgetStatus getBudgetStatus(String category) {
		if (!budgets.containsKey(category)) {
			throw new IllegalArgumentException("Бюджет для категории не найден");
		}

		double limit = budgets.get(category);
		double spent = getExpensesByCategory(category);
		double percent = spent / limit;

		if (percent >= 1) {
			return BudgetStatus.EXCEEDED;
		} else if (percent >= 0.8) {
			return BudgetStatus.WARNING;
		} else {
			return BudgetStatus.OK;
		}
	}

	/* ---------------- Вспомогательные ---------------- */

	private double sumByType(TransactionType type) {
		double sum = 0;
		for (Transaction t : transactions) {
			if (t.getType() == type) {
				sum += t.getAmount();
			}
		}
		return sum;
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}
}

