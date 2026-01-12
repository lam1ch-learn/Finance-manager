import auth.AuthService;
import auth.User;
import org.junit.jupiter.api.Test;
import storage.FileStorage;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

	@Test
	void userCanRegisterAndLogin() {
		AuthService auth = new AuthService(new FileStorage());
		auth.register("user1", "pass1");

		User user = auth.login("user1", "pass1");
		assertNotNull(user);
	}

	@Test
	void wrongPasswordThrowsException() {
		AuthService auth = new AuthService(new FileStorage());
		auth.register("user2", "pass2");

		assertThrows(IllegalArgumentException.class,
				() -> auth.login("user2", "wrong"));
	}

	@Test
	void nonExistingUserThrowsException() {
		AuthService auth = new AuthService(new FileStorage());

		assertThrows(IllegalArgumentException.class,
				() -> auth.login("unknown", "pass"));
	}
}