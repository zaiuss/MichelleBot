package main;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi bostsApi = new TelegramBotsApi();
		try {
			bostsApi.registerBot(new MichelleBot());
		} catch (TelegramApiException telAe) {
			telAe.getCause();
		}
	}
}
