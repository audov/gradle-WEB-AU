import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardFormTest {

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void testFormSuccess() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

        Thread.sleep(5000);
    }

    @Test
    void testFormFailName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Elena");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

        Thread.sleep(5000);
    }

    @Test
    void testFormNoName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

        Thread.sleep(5000);
    }

    @Test
    void testFormFailNum() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7910964323");
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elementList = driver.findElements(By.className("input__sub"));
        String text = elementList.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

        Thread.sleep(5000);
    }

    @Test
    void testFormNoNum() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elementList = driver.findElements(By.className("input__sub"));
        String text = elementList.get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

        Thread.sleep(5000);
    }

    @Test
    void testFormFailAgreem() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Воронина Елена");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());

        Thread.sleep(5000);
    }
}