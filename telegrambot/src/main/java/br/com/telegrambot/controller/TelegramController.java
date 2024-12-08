package br.com.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telegrambot.service.TelegramService;

@RestController
public class TelegramController {

	@Autowired
	private TelegramService telegramService;

	@GetMapping("/send-message")
	public String sendMessage(@RequestParam String chatId, @RequestParam String message) {
		Integer messageId = telegramService.sendMessageToGroup(chatId, message);
		return "Mensagem enviada com sucesso! ID da mensagem: " + messageId;
	}
}
