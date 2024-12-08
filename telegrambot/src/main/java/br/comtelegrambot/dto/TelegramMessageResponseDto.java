package br.comtelegrambot.dto;

public class TelegramMessageResponseDto {
	private boolean ok;
	private MessageResultDto result;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public MessageResultDto getResult() {
		return result;
	}

	public void setResult(MessageResultDto result) {
		this.result = result;
	}
}