package auth;

import storage.Storage;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

	private final Map<String, User> usersCache = new HashMap<>();
	private final Storage storage;

	public AuthService(Storage storage) {
		this.storage = storage;
	}

	/**
	 * Регистрация нового пользователя
	 */
	public User register(String login, String password) {
		if (usersCache.containsKey(login)) {
			throw new IllegalArgumentException("Пользователь уже существует");
		}

		User user = new User(login, password);
		usersCache.put(login, user);
		return user;
	}

	/**
	 * Авторизация пользователя.
	 * Если пользователь есть в файле — загружается оттуда.
	 */
	public User login(String login, String password) {
		try {
			// пробуем загрузить из файла
			User loadedUser = storage.load(login);

			if (!loadedUser.checkPassword(password)) {
				throw new IllegalArgumentException("Неверный пароль");
			}

			usersCache.put(login, loadedUser);
			return loadedUser;

		} catch (Exception e) {
			// если файла нет — пробуем из памяти
			User cachedUser = usersCache.get(login);

			if (cachedUser == null) {
				throw new IllegalArgumentException("Пользователь не найден");
			}

			if (!cachedUser.checkPassword(password)) {
				throw new IllegalArgumentException("Неверный пароль");
			}

			return cachedUser;
		}
	}

	/**
	 * Сохранение пользователя (вызывается при выходе)
	 */
	public void save(User user) {
		try {
			storage.save(user);
		} catch (Exception e) {
			System.out.println("Ошибка сохранения пользователя: " + e.getMessage());
		}
	}
}