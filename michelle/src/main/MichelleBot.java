package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MichelleBot extends TelegramLongPollingBot {

	@Override
	public String getBotUsername() {
		return "MichelleBot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			long chat_id = update.getMessage().getChatId();
			String userInputString = update.getMessage().getText();
			String s = sendCDM(userInputString);
			String escapedMsg = s.replace("_", "\\_").replace("*", "\\*").replace("[", "\\[").replace("`", "\\`");
			if (escapedMsg.length() > 1000) {
				try {
					FileUtils.writeStringToFile(new File("/tmp/txt.txt"), s);
					SendDocument sd = new SendDocument();
					sd.setChatId(chat_id);
					sd.setDocument(new File("/tmp/txt.txt"));
					try {
						execute(sd);
					} catch (TelegramApiException telegramException) {
						telegramException.printStackTrace();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println(s);
			}
		}
	}

	@Override
	public String getBotToken() {
		return "783736456:AAHh976HC9KNxeKgOgk78V-tGqS8V3b34CI";
	}

	public Boolean sendMsj(long chat_id, String txt) {
		SendMessage sm = new SendMessage();
		sm.setChatId(chat_id).setText(txt);
		sm.setParseMode("Markdown");
		try {
			execute(sm);
			return true;
		} catch (TelegramApiException telegramException) {
			telegramException.printStackTrace();
			return false;
		}
	}

	private String execCmd(String cmd) {
		Process process;
		String strOut = "";
		try {
			process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			BufferedReader buffEntrada = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String linea = "";
			while ((linea = buffEntrada.readLine()) != null) {
				strOut += linea + "\n";
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("\nEso no funciona 8(");
		}
		return strOut;
	}

	public String sendCDM(String s) {
		String salida = execCmd(s);
		return salida;
	}

	public Boolean sendError(long chat_id) {
		SendMessage sm = new SendMessage();
		sm.enableHtml(true);
		String error = "<code> Error!! </code>";
		sm.setChatId(chat_id).setText(error);
		try {
			execute(sm);
			return true;
		} catch (TelegramApiException telegramException) {
			telegramException.printStackTrace();
			return false;
		}
	}
}
