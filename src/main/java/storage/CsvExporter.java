package storage;

import core.Transaction;
import core.Wallet;

import java.io.File;
import java.io.PrintWriter;

/**
 * Экспорт данных кошелька в CSV-файл.
 */
public class CsvExporter {

	private static final String EXPORT_DIR = "data/exports/";

	public void exportWallet(Wallet wallet, String filename) throws Exception {
		if (wallet == null) {
			throw new IllegalArgumentException("Wallet is null");
		}

		File dir = new File(EXPORT_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try (PrintWriter writer =
				     new PrintWriter(EXPORT_DIR + filename)) {

			writer.println("category,amount,type");

			for (Transaction t : wallet.getTransactions()) {
				writer.println(
						t.getCategory() + "," +
								t.getAmount() + "," +
								t.getType()
				);
			}
		}
	}
}