package chatBot.AutoMate;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends App {
	/**
	 * Rigorous Test :-)
	 * @throws InterruptedException 
	 */
	@Test
	public void shouldAnswerWithTrue() throws InterruptedException {
		try {

			HashedMap testData = ReadProperty
					.getTestData(
							System.getProperty("user.dir")
									+ "\\src\\test\\resource\\testData\\testDataAutoBot.xlsx",
							"testDataAutoBot.xlsx", "Data", "IRTC");

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery((long) .1, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated((By
					.id("corover-disha-icon"))));
			WebElement chatButton = driver.findElement(By
					.id("corover-disha-icon"));
			chatButton.click();
			WebElement iFrame = driver.findElement(By.id("corover-chat-frame"));
			wait.until(ExpectedConditions.visibilityOfElementLocated((By
					.id("corover-chat-frame"))));
			driver.switchTo().frame(iFrame);
			wait.until(ExpectedConditions.visibilityOfElementLocated((By
					.id("search"))));
			WebElement search = driver.findElement(By.id("search"));
			Thread.sleep(3000);
			search.sendKeys("Hi");
			Thread.sleep(3000);
			search.sendKeys(Keys.ENTER);
			wait.until(ExpectedConditions.visibilityOfElementLocated((By
					.xpath("//*[@title='Dislike']"))));
			Thread.sleep(3000);
			String str = driver
					.findElement(
							By.xpath("(//*[@class='Answers'])[last()]"))
					.getText();
			boolean result = driver
					.findElement(
							By.xpath("(//*[@class='Answers'])[last()]"))
					.getText()
					.contains(testData.get("EXPECTED_RESULT").toString());
			ReadProperty.writeTestData(System.getProperty("user.dir")
					+ "\\src\\test\\resource\\testData\\testDataAutoBot.xlsx",
					"testDataAutoBot.xlsx", "Data", "IRTC", str);
			assertTrue(result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
