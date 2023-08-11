import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardFormTest {

    WebDriver driver;

    @BeforeEach
    void setupTest() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;

    }

    @Test
    void testFormSuccess() throws InterruptedException {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    void testFormFailName() throws InterruptedException {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Elena");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test
    void testFormNoName() throws InterruptedException {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    void testFormFailNum() throws InterruptedException {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7910964323");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void testFormNoNum() throws InterruptedException {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

        Thread.sleep(5000);
    }

    @Test
    void testFormFailAgreement() throws InterruptedException {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());

        Thread.sleep(5000);
    }
}