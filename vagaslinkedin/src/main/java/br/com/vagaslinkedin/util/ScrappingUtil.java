package br.com.vagaslinkedin.util;

import java.util.List;
import java.util.Objects;

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

	public static void rolarParaFinalPagina(Page page) {

		for (int i = 0; i <= 10; i++) {
			rodarRodinhaMouseParaBaixo(page);
			ScrappingUtil.aguardarEmSegundos(1);
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

	private ScrappingUtil() {

	}

}
