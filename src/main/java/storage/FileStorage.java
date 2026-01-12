package storage;

import auth.User;

import java.io.*;

/**
 * Файловое хранилище пользователей.
 * Использует стандартную сериализацию Java.
 */
public class FileStorage implements Storage {

	private static final String USERS_DIR = "data/users/";

	@Override
	public void save(User user) throws Exception {
		if (user == null) {
			throw new IllegalArgumentException("User is null");
		}

		File dir = new File(USERS_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(USERS_DIR + user.getLogin()))) {
			out.writeObject(user);
		}
	}

	@Override
	public User load(String login) throws Exception {
		File file = new File(USERS_DIR + login);

		if (!file.exists()) {
			throw new FileNotFoundException("User file not found");
		}

		try (ObjectInputStream in = new ObjectInputStream(
				new FileInputStream(file))) {
			return (User) in.readObject();
		}
	}
}
