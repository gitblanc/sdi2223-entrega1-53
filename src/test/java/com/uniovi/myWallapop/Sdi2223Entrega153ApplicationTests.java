package com.uniovi.myWallapop;

import com.uniovi.myWallapop.pageobjects.*;
import org.junit.jupiter.api.*;
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
		PO_SignUpView.fillForm(driver, "uo285176@uniovi.es", "aaaa", "bbbb", "77777", "77777");
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
		PO_LoginView.fillLoginForm(driver, "uo285176@uniovi.es", "123456");
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
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.offer.empty",
				PO_Properties.getSPANISH() );
		String checkText = PO_HomeView.getP().getString("Error.offer.empty",
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
		PO_LoginView.fillLoginForm(driver, "user05@email.com", "aaa");
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
		PO_LoginView.fillLoginForm(driver, "user05@email.com", "user05");
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
		List<WebElement> elements = PO_HomeView.checkElementBy(driver, "@href", "logout");
		Assertions.assertEquals(0, elements.size());
	}

	/**
	 * [Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema.
	 */
	@Test
	@Order(11)
	void PR11(){
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}
}
