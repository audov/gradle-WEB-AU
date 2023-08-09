import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
        driver.get("http://localhost:9999/"); // открываем тестируемую страницу
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Елена Воронина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        Thread.sleep(8000);
    }

    @Test
    void testFormFailName() throws InterruptedException {
        driver.get("http://localhost:9999/"); // открываем тестируемую страницу
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Elena Воронина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        Thread.sleep(8000);
    }

    @Test
    void testFormFailNum() throws InterruptedException {
        driver.get("http://localhost:9999/"); // открываем тестируемую страницу
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Елена Воронина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7910964323");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        Thread.sleep(8000);
    }

    @Test
    void testFormFailAgreem() throws InterruptedException {
        driver.get("http://localhost:9999/"); // открываем тестируемую страницу
        //WebElement form = driver.findElement(By.cssSelector("[root]"));
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Елена Воронина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79109643232");
        //driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        Thread.sleep(8000);
    }
}