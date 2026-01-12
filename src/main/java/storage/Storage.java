package storage;

import auth.User;

/**
 * Интерфейс хранилища данных пользователя.
 * Нужен для чистой архитектуры и тестирования.
 */
public interface Storage {

	void save(User user) throws Exception;

	User load(String login) throws Exception;
}