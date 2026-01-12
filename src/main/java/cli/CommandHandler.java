package cli;

import auth.AuthService;
import auth.User;
import service.FinanceService;
import service.StatsService;
import service.TransferService;
import storage.CsvImporter;
import storage.FileStorage;
import storage.Storage;
import core.BudgetStatus;
import core.Wallet;
import storage.CsvExporter;

import java.util.Arrays;
import java.util.List;

/**
 * Главный обработчик CLI.
 * Управляет состоянием текущего пользователя.
 */
public class CommandHandler {

	private final InputReader input = new InputReader();

	private final Storage storage = new FileStorage();
	private final AuthService authService = new AuthService(storage);

	private final FinanceService financeService = new FinanceService();
	private final StatsService statsService = new StatsService();
	private final TransferService transferService = new TransferService();

	private User currentUser = null;

	/* =================== Запуск =================== */

	public void start() {
		while (true) {
			Menu.printMainMenu();
			int choice = input.readInt("Выберите пункт: ");

			switch (choice) {
				case 1 -> register();
				case 2 -> login();
				case 0 -> exitProgram();
				default -> System.out.println("Неверный пункт меню");
			}
		}
	}

	/* =================== Главное меню =================== */

	private void register() {
		String login = input.readString("Логин: ");
		String password = input.readString("Пароль: ");

		try {
			currentUser = authService.register(login, password);
			System.out.println("Регистрация успешна");
			userMenu();
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void login() {
		String login = input.readString("Логин: ");
		String password = input.readString("Пароль: ");

		try {
			currentUser = authService.login(login, password);
			System.out.println("Вход выполнен");
			userMenu();
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	/* =================== Меню пользователя =================== */

	private void userMenu() {
		// ГАРАНТИЯ: сюда попадаем ТОЛЬКО если currentUser != null
		while (true) {
			Menu.printUserMenu();
			int choice = input.readInt("Выберите пункт: ");

			switch (choice) {
				case 1 -> addIncome();
				case 2 -> addExpense();
				case 3 -> setBudget();
				case 4 -> showStats();
				case 5 -> showBudgets();
				case 6 -> calculateByCategories();
				case 7 -> transfer();
				case 8 -> exportCsv();
				case 9 -> importCsv();
				case 0 -> {
					logout();
					return; // ✅ ВАЖНО: выходим из userMenu
				}
				default -> System.out.println("Неверный пункт меню");
			}
		}
	}

	/* =================== Действия =================== */

	private void addIncome() {
		Wallet wallet = currentUser.getWallet();

		String category = input.readString("Категория дохода: ");
		double amount = input.readDouble("Сумма: ");

		wallet.addIncome(category, amount);
		System.out.println("Доход добавлен");
	}

	private void addExpense() {
		Wallet wallet = currentUser.getWallet();

		String category = input.readString("Категория расхода: ");
		double amount = input.readDouble("Сумма: ");

		wallet.addExpense(category, amount);
		System.out.println("Расход добавлен");

		if (wallet.getBudgets().containsKey(category)) {
			BudgetStatus status = financeService.checkBudget(wallet, category);
			Printer.printBudgetStatus(status);
		}

		if (financeService.isExpensesGreaterThanIncome(wallet)) {
			System.out.println("⚠ Внимание: расходы превышают доходы");
		}
	}

	private void setBudget() {
		Wallet wallet = currentUser.getWallet();

		String category = input.readString("Категория: ");
		double limit = input.readDouble("Лимит: ");

		wallet.setBudget(category, limit);
		System.out.println("Бюджет установлен / обновлён");
	}

	private void showStats() {
		Wallet wallet = currentUser.getWallet();

		System.out.println("Общий доход: " + wallet.getTotalIncome());
		System.out.println("Общие расходы: " + wallet.getTotalExpenses());
		System.out.println("Баланс: " + wallet.getBalance());
	}

	private void showBudgets() {
		Wallet wallet = currentUser.getWallet();

		if (wallet.getBudgets().isEmpty()) {
			System.out.println("Бюджеты не заданы");
			return;
		}

		System.out.println(financeService.buildBudgetReport(wallet));
	}

	private void calculateByCategories() {
		String inputCats = input.readString(
				"Введите категории через запятую: ");

		List<String> categories = Arrays.stream(inputCats.split(","))
				.map(String::trim)
				.toList();

		double sum = statsService.calculateExpensesForCategories(
				currentUser.getWallet(), categories);

		System.out.println("Суммарные расходы: " + sum);
	}

	private void transfer() {
		String toLogin = input.readString("Логин получателя: ");
		double amount = input.readDouble("Сумма перевода: ");

		try {
			User toUser = authService.login(
					toLogin,
					input.readString("Пароль получателя (для демо): ")
			);

			transferService.transfer(currentUser, toUser, amount);
			authService.save(toUser);

			System.out.println("Перевод выполнен");
		} catch (Exception e) {
			System.out.println("Ошибка перевода: " + e.getMessage());
		}
	}

	private void exportCsv() {
		try {
			new CsvExporter().exportWallet(
					currentUser.getWallet(),
					currentUser.getLogin() + "_export.csv"
			);
			System.out.println("Экспорт выполнен");
		} catch (Exception e) {
			System.out.println("Ошибка экспорта: " + e.getMessage());
		}
	}

	private void importCsv() {
		String file = input.readString("Имя CSV-файла: ");
		try {
			new CsvImporter().importToWallet(
					currentUser.getWallet(), file
			);
			System.out.println("Импорт выполнен");
		} catch (Exception e) {
			System.out.println("Ошибка импорта: " + e.getMessage());
		}
	}

	/* =================== Выход =================== */

	private void logout() {
		if (currentUser != null) {
			authService.save(currentUser);
			currentUser = null;
		}
		System.out.println("Выход из аккаунта");
	}

	private void exitProgram() {
		if (currentUser != null) {
			authService.save(currentUser);
		}
		System.out.println("Выход из программы");
		System.exit(0);
	}
}

