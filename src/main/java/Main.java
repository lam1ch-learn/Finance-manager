import cli.CommandHandler;

/**
 * Точка входа в приложение.
 */
public class Main {

	public static void main(String[] args) {
		CommandHandler commandHandler = new CommandHandler();
		commandHandler.start();
	}
}