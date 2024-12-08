package br.com.bottelegram.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.com.bottelegram.dto.TelegramMessageRequestDto;
import br.com.bottelegram.dto.TelegramMessageResponseDto;

@Service
public class TelegramService {

	@Value("${telegram.bot.token}")
	private String botToken;

	private final RestClient restClient;

	public TelegramService(RestClient restClient) {
		this.restClient = restClient;
	}

	/**
	 * Envia uma mensagem para um grupo do Telegram e retorna o ID da mensagem.
	 *
	 * @param chatId  ID do grupo ou chat no Telegram.
	 * @param message Mensagem a ser enviada.
	 * @return ID da mensagem enviada pelo Telegram.
	 */
	public Integer sendMessageToGroup(String chatId, String message) {
		String telegramApiUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage";

		try {
			TelegramMessageResponseDto response = restClient.post().uri(telegramApiUrl)
					.body(new TelegramMessageRequestDto(chatId, message)).retrieve()
					.body(TelegramMessageResponseDto.class);

			return response.getResult().getMessageId();
		} catch (Exception e) {
			System.err.println("Erro ao enviar mensagem: " + e.getMessage());
			throw new RuntimeException("Falha ao enviar mensagem para o Telegram.");
		}
	}

}
