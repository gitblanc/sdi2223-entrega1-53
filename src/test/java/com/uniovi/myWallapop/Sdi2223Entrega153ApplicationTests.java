package com.uniovi.myWallapop;

import com.uniovi.myWallapop.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega153ApplicationTests {

	static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	//static String Geckodriver = "C:\\Users\\uo277369\\Desktop\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
	static String Geckodriver = "C:\\Users\\mines\\Desktop\\wallapop\\geckodriver-v0.30.0-win64.exe";
	//static String Geckodriver = "C:\\Users\\aaron\\Desktop\\UNI\\tercero\\SEGUNDO\\SDI\\Practica\\SDI-P5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
	//static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";
	//static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
//static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";
//Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox, Geckodriver);
	static String URL = "http://localhost:8090";
	public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckodriver);
		driver = new FirefoxDriver();
		return driver;
	}

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUp(){
		driver.navigate().to(URL);
	}
	//Después de cada prueba se borran las cookies del navegador
	@AfterEach
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}
	//Antes de la primera prueba
	@BeforeAll
	static public void begin() {}
	//Al finalizar la última prueba
	@AfterAll
	static public void end() {
//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/**
	 * [Prueba1] Registro de Usuario con datos válidos. Quizás falle en ejecuciones consecutivas sin reiniciar la aplicación
	 * porque no se reinicia la base de datos.
	 */
	@Test
	@Order(1)
	void PR01(){
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "emailvalido@gmail.com", "aaaa", "bbbb", "77777", "77777");
		String checkText = "Este es tu perfil en MyWallapop";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

		// Si se llega hasta aquí la prueba se ha superado, así que borramos el usuario nuevo
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
		PO_AdminView.checkElementByKey(driver, "users.list.subtitle", 0);

		PO_AdminView.deleteUsers(driver, 15);

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos).
	 */
	@Test
	@Order(2)
	void PR02(){
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "", "", "", "77777", "77777");
		String checkText = "Regístrese como usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	/**
	 * [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña inválida).
	 */
	@Test
	@Order(3)
	void PR03(){
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "emailvalido2@gmail.com", "aaaa", "bbbb", "77777", "66666");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "error.signup.passwordandconfirmpasswordifferent",
				PO_Properties.getSPANISH());
		String checkText = PO_View.getP().getString("error.signup.passwordandconfirmpasswordifferent", PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	/**
	 * [Prueba4] Registro de Usuario con datos inválidos (email existente).
	 */
	@Test
	@Order(4)
	void PR04(){
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "user05@email.com", "aaaa", "bbbb", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "error.signup.emailAlreadyExists",
				PO_Properties.getSPANISH());
		String checkText = PO_View.getP().getString("error.signup.emailAlreadyExists", PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	/**
	 * [Prueba5] Inicio de sesión con datos válidos (administrador).
	 */
	@Test
	@Order(5)
	void PR05(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
		PO_AdminView.checkElementBy(driver, "text", "admin@email.com");
		PO_AdminView.checkElementByKey(driver, "users.list.subtitle", 0);

		List<WebElement> result = PO_HomeView.checkElementBy(driver, "id", "tableUsers");
		String checkText = "tableUsers";
		Assertions.assertEquals(checkText , result.get(0).getAttribute("id"));
	}

	/**
	 * [Prueba6] Inicio de sesión con datos válidos (usuario estándar).
	 */
	@Test
	@Order(6)
	void PR06(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
		PO_PrivateView.checkElementBy(driver, "text", "user01@email.com");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		List<WebElement> result = PO_HomeView.checkElementBy(driver, "id", "tableOffers");
		String checkText = "tableOffers";
		Assertions.assertEquals(checkText , result.get(0).getAttribute("id"));
	}

	/**
	 * [Prueba7] Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos).
	 */
	@Test
	@Order(7)
	void PR07(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "", "");
		// Se debe indicar que el campo está vacío
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "error.login.userorpasswordincorrect",
				PO_Properties.getSPANISH() );
		String checkText = PO_HomeView.getP().getString("error.login.userorpasswordincorrect",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	/**
	 * [Prueba8] Inicio de sesión con datos inválidos (usuario estándar, email existente, pero contraseña
	 * incorrecta).
	 */
	@Test
	@Order(8)
	void PR08(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user05@email.com", "passworwfegwedp");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "error.login.userorpasswordincorrect",
				PO_Properties.getSPANISH() );
		String checkText = PO_HomeView.getP().getString("error.login.userorpasswordincorrect",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	/**
	 * [Prueba9] Hacer clic en la opción de salir de sesión y comprobar que se redirige a la página de inicio de
	 * sesión (Login).
	 */
	@Test
	@Order(9)
	void PR09(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user05@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		List<WebElement> result = PO_LoginView.getText(driver, PO_Properties.getSPANISH(), "login.message");
		Assertions.assertEquals(result.get(0).getText(),
				PO_HomeView.getP().getString("login.message", PO_Properties.getSPANISH()));
	}

	/**
	 * [Prueba10] Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
	 */
	@Test
	@Order(10)
	void PR10(){
		String checkText = PO_View.getP().getString("disconnect.message", 0);
		PO_View.checkTextNotPresent(driver, checkText);
	}

	/**
	 * [Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema.
	 */
	@Test
	@Order(11)
	void PR11(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
		List<WebElement> usersList = PO_View.checkElementBy(driver, "free", "//tbody/tr");

		Assertions.assertEquals(15, usersList.size());
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba12] Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza
	 * y dicho usuario desaparece.
	 */
	@Test
	@Order(12)
	void PR12(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

		WebElement firstUserBeforeDeletion = PO_AdminView.getUsersList(driver).get(0);
		// La primera celda de la fila de un usuario es el correo
		String firstUserBeforeDeletionEmail = firstUserBeforeDeletion.findElement(By.tagName("td")).getText();
		PO_AdminView.deleteUsers(driver, 0);
		Assertions.assertNotEquals(firstUserBeforeDeletionEmail, PO_AdminView.getUsersList(driver).get(0).findElement(By.tagName("td")).getText());

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba13] Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza
	 * y dicho usuario desaparece.
	 */
	@Test
	@Order(13)
	void PR13(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

		List<WebElement> users = PO_AdminView.getUsersList(driver);
		int indexLastUser = users.size() - 1;
		String lastUserBeforeDeletionEmail = users.get(indexLastUser).findElement(By.tagName("td")).getText();
		PO_AdminView.deleteUsers(driver, indexLastUser);
		users = PO_AdminView.getUsersList(driver);
		indexLastUser = users.size() - 1;
		Assertions.assertNotEquals(lastUserBeforeDeletionEmail,
				users.get(indexLastUser).findElement(By.tagName("td")).getText());

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba14] Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos
	 * usuarios desaparecen.
	 */
	@Test
	@Order(14)
	void PR14(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

		List<WebElement> users = PO_AdminView.getUsersList(driver);

		String email1 = users.get(1).findElement(By.tagName("td")).getText();
		String email3 = users.get(3).findElement(By.tagName("td")).getText();
		String email7 = users.get(7).findElement(By.tagName("td")).getText();

		PO_AdminView.deleteUsers(driver, 1, 3, 7);
		users = PO_AdminView.getUsersList(driver);

		for (WebElement user: users) {
			String emailUser = user.findElement(By.tagName("td")).getText();
			Assertions.assertNotEquals(emailUser, email1);
			Assertions.assertNotEquals(emailUser, email3);
			Assertions.assertNotEquals(emailUser, email7);
		}

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba15] Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar el botón Enviar.
	 * Comprobar que la oferta sale en el listado de ofertas de dicho usuario.
	 */
	@Test
	@Order(15)
	void PR15(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user11@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAddOfferOption(driver);
		//Ahora vamos a rellenar la oferta.
		String checkText = "Oferta nueva 1";
		PO_PrivateView.fillFormAddOffer(driver, checkText, "uwhfguwie", "100");
		//Esperamos a que se muestren los enlaces de paginación de la lista de ofertas publicadas
		List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
		//Nos vamos a la última página
		elements.get(3).click();
		//Comprobamos que aparece la oferta en la página
		elements = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, elements.get(0).getText());

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}



	/**
	 * [Prueba16] Ir al formulario de alta de oferta, rellenarla con datos inválidos (precio negativo) y pulsar el
	 * botón Enviar. Comprobar que se muestra el mensaje de campo inválido.
	 */
	@Test
	@Order(16)
	void PR16(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user11@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAddOfferOption(driver);
		//Ahora vamos a rellenar la oferta.
		PO_PrivateView.fillFormAddOffer(driver, "Oferta nueva 1", "uwhfguwie", "-25");
		// Comprobamos que sale el error de precio negativo
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.offer.price.negative",
				PO_Properties.getSPANISH() );

		String checkText = PO_HomeView.getP().getString("Error.offer.price.negative",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}



	/**
	 * [Prueba17] Mostrar el listado de ofertas para dicho usuario y comprobar que se muestran todas los que
	 * existen para este usuario.
	 */
	@Test
	@Order(17)
	void PR17(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user12@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickPostedOffersOption(driver);
//Comprobamos que salen todas las del usuario.
		List<WebElement> offersList = PO_View.checkElementBy(driver, "free", "//tbody/tr");
		Assertions.assertEquals(5, offersList.size());
		// Vamos a la siguiente página, es decir, la última
		List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
		elements.get(3).click();
		offersList = PO_View.checkElementBy(driver, "free", "//tbody/tr");
		Assertions.assertEquals(5, offersList.size());

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba18] Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar que la lista se actualiza y
	 * que la oferta desaparece.
	 */
	@Test
	@Order(18)
	void PR18(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user11@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickPostedOffersOption(driver);
		List<WebElement> firstOfferRow = PO_View.checkElementBy(driver, "free", "//tbody/tr[1]/td");
		String title = firstOfferRow.get(0).getText();

		WebElement deleteLink = firstOfferRow.get(firstOfferRow.size()-1).findElement(By.tagName("a"));
		deleteLink.click();

		firstOfferRow = PO_View.checkElementBy(driver, "free", "//tbody/tr[1]/td");
		Assertions.assertNotEquals(title, firstOfferRow.get(0).getText());

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba19] Ir a la lista de ofertas, borrar la última oferta de la lista, comprobar que la lista se actualiza y
	 * que la oferta desaparece.
	 */
	@Test
	@Order(19)
	void PR19(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user11@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickPostedOffersOption(driver);

		// Navegamos a la última página
		PO_PaginationView.clickLastPage(driver);

		// Metemos en una lista las celdas de la última fila
		List<WebElement> offersRows = PO_View.checkElementBy(driver, "free", "//tbody/tr");
		List<WebElement> lastOfferRow = offersRows.get(offersRows.size()-1).findElements(By.tagName("td"));
		String title = lastOfferRow.get(0).getText();

		// Pinchamos en eliminar
		WebElement deleteLink = lastOfferRow.get(lastOfferRow.size()-1).findElement(By.tagName("a"));
		deleteLink.click();

		// Navegamos a la última página
		PO_PaginationView.clickLastPage(driver);

		// Metemos en una lista las celdas de la última fila
		offersRows = PO_View.checkElementBy(driver, "free", "//tbody/tr");
		lastOfferRow = offersRows.get(offersRows.size()-1).findElements(By.tagName("td"));

		// Comprobamos que los títulos sean distintos
		Assertions.assertNotEquals(title, lastOfferRow.get(0).getText());

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba20] Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
	 * corresponde con el listado de las ofertas existentes en el sistema
	 */
	@Test
	@Order(20)
	void PR20(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user11@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAllOffersOption(driver);
		PO_AllOffersView.writeIntoSearchBar(driver, "");

		// Comprobamos que salen todas las ofertas del sistema
		List<WebElement> tableRows = PO_View.checkElementBy(driver, "free", "//tbody/tr");
		Assertions.assertEquals(5, tableRows.size());
		PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]").get(2).click();
		// Mientras el 4º page-link (3) no sea el enlace hacia la última página
		while (!PO_PaginationView.isLastPage(driver)) {
			tableRows = PO_View.checkElementBy(driver, "free", "//tbody/tr");
			Assertions.assertEquals(5, tableRows.size());
			PO_PaginationView.clickNextPage(driver);
		}
		// Estamos en la última página
		tableRows = PO_View.checkElementBy(driver, "free", "//tbody/tr");

		Assertions.assertEquals(3, tableRows.size());

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba21] Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
	 * muestra la página que corresponde, con la lista de ofertas vacía
	 */
	@Test
	@Order(21)
	void PR21(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user11@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAllOffersOption(driver);
		PO_AllOffersView.writeIntoSearchBar(driver, "noexistenoexistenoexiste");

		// Comprobamos que la lista está vacía
		PO_PrivateView.getText(driver, 0, "home.offer.emptylist");

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba22] Sobre una búsqueda determinada (a elección del desarrollador), comprar una oferta que deja
	 * un saldo positivo en el contador del comprador. Comprobar que el contador se actualiza correctamente
	 * en la vista del comprador.
	 */
	@Test
	@Order(22)
	void PR22(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user11@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAllOffersOption(driver);
		PO_AllOffersView.writeIntoSearchBar(driver, "Oferta-user04-n2");
		PO_AllOffersView.buyFirstOffer(driver);

		// Comprobamos que el contador se actualiza
		//  //*[@id="myNavbar"]/div[2]/ul
		String saldoAhora = PO_View.checkElementBy(driver, "free", "//*[@id=\"myNavbar\"]/div[2]/ul").get(0).getText();

		Assertions.assertEquals("80.0 €", saldoAhora);

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba23] Sobre una búsqueda determinada (a elección del desarrollador), comprar una oferta que deja
	 * un saldo 0 en el contador del comprador. Comprobar que el contador se actualiza correctamente en la
	 * vista del comprador.
	 */
	@Test
	@Order(23)
	void PR23(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user12@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAllOffersOption(driver);
		PO_AllOffersView.writeIntoSearchBar(driver, "Oferta-user10-n10");
		PO_AllOffersView.buyFirstOffer(driver);

		// Comprobamos que el contador se actualiza
		//  //*[@id="myNavbar"]/div[2]/ul
		String saldoAhora = PO_View.checkElementBy(driver, "free", "//*[@id=\"myNavbar\"]/div[2]/ul").get(0).getText();

		Assertions.assertEquals("0.0 €", saldoAhora);

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba24] Sobre una búsqueda determinada (a elección del desarrollador), intentar comprar una oferta
	 * que esté por encima de saldo disponible del comprador. Y comprobar que se muestra el mensaje de
	 * saldo no suficiente.
	 */
	@Test
	@Order(24)
	void PR24(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user13@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAllOffersOption(driver);
		// Primero compramos una oferta que nos deje con saldo insuficiente para la siguiente compra
		PO_AllOffersView.writeIntoSearchBar(driver, "Oferta-user11-n10");
		PO_AllOffersView.buyFirstOffer(driver);
		// Intentamos comprar
		PO_AllOffersView.writeIntoSearchBar(driver, "Oferta-user11-n8");
		PO_AllOffersView.buyFirstOffer(driver);

		// Comprobamos que se muestra el mensaje de saldo no suficiente
		PO_AllOffersView.checkElementByKey(driver, "error.buy.offer.amount", 0);

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	/**
	 * [Prueba25] Ir a la opción de ofertas compradas del usuario y mostrar la lista. Comprobar que aparecen
	 * las ofertas que deben aparecer.
	 */
	@Test
	@Order(25)
	void PR25(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "user14@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);
		PO_PrivateView.clickAllOffersOption(driver);
		// Primero compramos una oferta que nos deje con saldo insuficiente para la siguiente compra
		String stringCheck = "Oferta-user11-n2";
		PO_AllOffersView.writeIntoSearchBar(driver, stringCheck);
		PO_AllOffersView.buyFirstOffer(driver);

		PO_PrivateView.clickOfferOption(driver, "bought");
		List<WebElement> offer = PO_View.checkElementBy(driver, "free", "//tbody/tr/td");
		Assertions.assertEquals(stringCheck, offer.get(0).getText());


		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}



	/**
	 * [Prueba29] Visualizar al menos cuatro páginas en español/inglés/español (comprobando que algunas de
	 * las etiquetas cambian al idioma correspondiente).
	 */
	@Test
	@Order(29)
	void PR29(){
		PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish", 0, 1);

		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

		PO_LoginView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish", 0, 1);

		PO_LoginView.fillLoginForm(driver, "user14@email.com", "user01");
		PO_View.checkElementByKey(driver, "offer.list.subtitle", 0);

		PO_PrivateView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish", 0, 1);

		PO_PrivateView.clickAllOffersOption(driver);

		PO_AllOffersView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish", 0, 1);

		PO_PrivateView.clickOption(driver, "logout", "class", "btn btn-primary");
	}
}