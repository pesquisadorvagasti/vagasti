package br.com.vagaslinkedin.util;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ScrappingUtil {

	public static void aguardarEmSegundos(Integer segundos) {
		try {
			if (Objects.isNull(segundos))
				throw new RuntimeException("Informe os segundos que deseja aguardar!");
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void aguardarEmMilissegundos(Integer millissegundos) {
		try {
			if (Objects.isNull(millissegundos))
				throw new RuntimeException("Informe os segundos que deseja aguardar!");
			Thread.sleep(millissegundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void rolarParaFinalPagina(Page page) {

		for (int i = 0; i <= 10; i++) {
			rodarRodinhaMouseParaBaixo(page);
		}

	}

	public static void rodarRodinhaMouseParaBaixo(Page page) {
		page.mouse().wheel(0, 500);
	}

	public static Page abrirBrowserMaximizado(Playwright playwright) {
		Browser browser = playwright.chromium()
				.launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--start-maximized")));
		BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
		return context.newPage();
	}

	public static Page abrirBrowserPerfilUsuario() {
		Playwright playwright = Playwright.create();

		String bravePath = "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe";
		String userDataDir = "C:\\Users\\Wes\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default";

		BrowserContext context = playwright.chromium().launchPersistentContext(Paths.get(userDataDir),
				new BrowserType.LaunchPersistentContextOptions().setHeadless(false)
						.setExecutablePath(Paths.get(bravePath)).setViewportSize(null));

		Page page = context.pages().isEmpty() ? context.newPage() : context.pages().get(0);

		page.evaluate("window.moveTo(0, 0); window.resizeTo(screen.width, screen.height);");

		return page;
	}

	public static boolean palavraExisteNoTexto(String palavra, String texto) {

		String regex = "\\b" + Pattern.quote(palavra) + "\\b";

		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		return pattern.matcher(texto).find();

	}

	public static Integer extrairNumero(String texto) {

		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(texto);

		if (matcher.find()) {

			return Integer.parseInt(matcher.group());
		}
		return null;
	}

	private ScrappingUtil() {

	}

}
