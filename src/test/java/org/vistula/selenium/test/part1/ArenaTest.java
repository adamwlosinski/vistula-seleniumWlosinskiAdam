package org.vistula.selenium.test.part1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ArenaTest {


    private WebDriver driver;



    @BeforeClass
    public static void setDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void myFirstSeleniumTest() {
        driver = new ChromeDriver();
        driver.get("http://demo.testarena.pl/zaloguj");
        Assertions.assertThat(driver.getTitle()).contains("TestArena");
        driver.quit();
    }

    @Test
    public void letsLogin() {
        driver = new ChromeDriver();
        driver.get("http://demo.testarena.pl/zaloguj");
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login"));


        email.sendKeys("administrator@testarena.pl");
        password.sendKeys("sumXQQ72$L");
        login.click();





        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("footer")));

            Assertions.assertThat(driver.getTitle()).contains("Kokpit");

        WebElement admin = driver.findElement(By.className("header_admin"));
        admin.click();

            Assertions.assertThat(driver.getTitle()).contains("Projekty");

        driver.findElement(By.className("button_link")).click();

            Assertions.assertThat(driver.getTitle()).contains("Dodaj projekt");

        WebElement nameproject = driver.findElement(By.id("name"));
        WebElement prefix = driver.findElement(By.id("prefix"));
        WebElement description = driver.findElement(By.id("description"));
        WebElement submit = driver.findElement(By.id("save"));

                String randomName = RandomStringUtils.randomAlphabetic(10);
                String randomPrefix = RandomStringUtils.randomNumeric(6);

        nameproject.sendKeys(randomName);
        prefix.sendKeys(randomPrefix);
        description.sendKeys("Przykładowy opis");

        submit.click();

            Assertions.assertThat(driver.getTitle()).contains("Właściwości projektu");

        WebElement chosenName = driver.findElement(By.className("content_label_title"));
        WebElement chosenPrefix = driver.findElement(By.className("content_label"));

        String text;
        text = chosenName.getText();

        String textPrefix;
        textPrefix = chosenPrefix.getText();

        driver.get("http://demo.testarena.pl/administration/projects");

            Assertions.assertThat(driver.getTitle()).contains("Projekty");

        WebElement searchForProject = driver.findElement(By.id("search"));

        searchForProject.sendKeys(text);


        WebElement searchButton = driver.findElement(By.id("j_searchButton"));
        searchButton.click();

        WebElement searchedPrefix = driver.findElement(By.className("t_number"));

        String foundPrefix;
        foundPrefix = searchedPrefix.getText();

        Assert.assertEquals(textPrefix,foundPrefix);



       driver.quit();
    }


   @Test
    public void wrongLoginTest() {
        driver = new ChromeDriver();
        driver.get("http://demo.testarena.pl/zaloguj");
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login"));


        email.sendKeys("administrator@testarena.pl");
        password.sendKeys("wrongPassword");
        login.click();

        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.presenceOfElementLocated(By.className("login_form_error")));

        Assertions.assertThat(driver.getTitle()).contains("TestArena");
        driver.quit();
    }

}
