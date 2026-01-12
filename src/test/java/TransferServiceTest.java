import auth.User;
import org.junit.jupiter.api.Test;
import service.TransferService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceTest {

	@Test
	void transferAddsExpenseAndIncome() {
		User from = new User("from", "123");
		User to = new User("to", "456");

		TransferService service = new TransferService();
		service.transfer(from, to, 300);

		assertEquals(300, from.getWallet().getTotalExpenses());
		assertEquals(300, to.getWallet().getTotalIncome());
	}

	@Test
	void transferWithNegativeAmountThrowsException() {
		User from = new User("from", "123");
		User to = new User("to", "456");

		TransferService service = new TransferService();
		assertThrows(IllegalArgumentException.class,
				() -> service.transfer(from, to, -100));
	}
}