package tasks.functional;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    private WebDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities cap = DesiredCapabilities.firefox();
        FirefoxOptions options = new FirefoxOptions();
        cap.merge(options);
        options.setHeadless(true);
        this.driver = new RemoteWebDriver(new URL("http://192.168.15.9:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.15.9:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testeDeveSalvarTarefaComSucesso(){
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Tarefa 2021-1");
        driver.findElement(By.id("dueDate")).sendKeys("01/07/2021");
        driver.findElement(By.id("saveButton")).click();
        String mensagemCapturada = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Success!", mensagemCapturada);
    }

    @Test
    public void testeNaoDeveSalvarSemDescricao(){
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("dueDate")).sendKeys("10/01/2010");
        driver.findElement(By.id("saveButton")).click();
        String mensagemCapturada = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Fill the task description", mensagemCapturada);
    }

    @Test
    public void testeNaoDeveSalvarSemData(){
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Erro");
        driver.findElement(By.id("saveButton")).click();
        String mensagemCapturada = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Fill the due date", mensagemCapturada);
    }

    @Test
    public void testeNaoDeveSalvarComDataPassada(){
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Erro");
        driver.findElement(By.id("dueDate")).sendKeys("10/01/2010");
        driver.findElement(By.id("saveButton")).click();
        String mensagemCapturada = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Due date must not be in past", mensagemCapturada);
    }

    @After
    public void tearDown(){
        this.driver.quit();
    }
}
