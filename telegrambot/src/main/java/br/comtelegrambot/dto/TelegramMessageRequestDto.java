package br.comtelegrambot.dto;

public class TelegramMessageRequestDto {
	private final String chat_id;
	private final String text;

	public TelegramMessageRequestDto(String chatId, String text) {
		this.chat_id = chatId;
		this.text = text;
	}

	public String getChat_id() {
		return chat_id;
	}

	public String getText() {
		return text;
	}
}