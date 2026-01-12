package core;

import java.io.Serializable;


/**
 * Финансовая операция (доход, расход, перевод).
 */
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String category;
	private final double amount;
	private final TransactionType type;

	public Transaction(String category, double amount, TransactionType type) {
		if (category == null || category.isBlank()) {
			throw new IllegalArgumentException("Категория не может быть пустой");
		}
		if (amount <= 0) {
			throw new IllegalArgumentException("Сумма должна быть положительной");
		}
		this.category = category;
		this.amount = amount;
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public double getAmount() {
		return amount;
	}

	public TransactionType getType() {
		return type;
	}
}
