package cli;

/**
 * Печать меню приложения.
 */
public class Menu {

	public static void printMainMenu() {
		System.out.println("""
                === Personal Finance Manager ===
                1. Регистрация
                2. Вход
                0. Выход
                """);
	}

	public static void printUserMenu() {
		System.out.println("""
                === Меню пользователя ===
                1. Добавить доход
                2. Добавить расход
                3. Установить / изменить бюджет
                4. Показать статистику
                5. Показать бюджеты
                6. Подсчёт по категориям
                7. Перевод пользователю
                8. Экспорт CSV
                9. Импорт CSV
                0. Выйти из аккаунта
                """);
	}
}
