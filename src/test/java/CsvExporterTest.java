import core.Wallet;
import org.junit.jupiter.api.Test;
import storage.CsvExporter;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvExporterTest {

	@Test
	void csvFileIsCreated() throws Exception {
		Wallet wallet = new Wallet();
		wallet.addIncome("Salary", 1000);

		CsvExporter exporter = new CsvExporter();
		exporter.exportWallet(wallet, "test_export.csv");

		File file = new File("data/exports/test_export.csv");
		assertTrue(file.exists());
	}
}
