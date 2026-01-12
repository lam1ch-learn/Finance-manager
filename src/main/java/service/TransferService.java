package service;

import auth.User;

/**
 * Сервис переводов между пользователями.
 */
public class TransferService {

	/**
	 * Перевод средств между пользователями.
	 */
	public void transfer(User from, User to, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Сумма перевода должна быть положительной");
		}

		from.getWallet().addExpense(
				"Перевод пользователю " + to.getLogin(),
				amount
		);

		to.getWallet().addIncome(
				"Перевод от пользователя " + from.getLogin(),
				amount
		);
	}
}
