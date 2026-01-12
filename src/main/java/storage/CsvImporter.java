package storage;

import core.TransactionType;
import core.Wallet;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Импорт CSV в кошелёк.
 */
public class CsvImporter {

	public void importToWallet(Wallet wallet, String filename) throws Exception {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

			String line = reader.readLine(); // пропускаем заголовок

			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");

				String category = parts[0];
				double amount = Double.parseDouble(parts[1]);
				TransactionType type = TransactionType.valueOf(parts[2]);

				if (type == TransactionType.INCOME) {
					wallet.addIncome(category, amount);
				} else if (type == TransactionType.EXPENSE) {
					wallet.addExpense(category, amount);
				}
			}
		}
	}
}
