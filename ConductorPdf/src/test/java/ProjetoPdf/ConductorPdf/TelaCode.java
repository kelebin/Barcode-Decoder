package ProjetoPdf.ConductorPdf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class TelaCode {


	
		public static void samba() throws InterruptedException {
			System.setProperty("webdriver.chrome.driver", "C:/Users/Kevin/Downloads/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://carnaval.uol.com.br/2018/enquetes/2018/02/12/qual-foi-a-melhor-escola-na-primeira-noite-de-desfiles-no-rio-de-janeiro.htm\r\n");
			int x = 0;
			while(x <= 99) {
				Thread.sleep(2000);
				driver.findElement(By.xpath("<div class=\"call-action\" style=\"text-align: right;\">...</div>\r\n" + 
						"  (Session info: chrome=64.0.3282.167)")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("/html/body/article/div[2]/div/div[1]/div/div[2]/div[1]/div[5]/div[1]/div[9]/button")).click();
				/*String votos =	driver.findElement(By.xpath("/html/body/article/div[2]/div/div[1]/div/div[2]/div[1]/div[6]/div[1]/div[10]/span")).getText();
				System.out.println(votos);*/
				Thread.sleep(2000);
				driver.navigate().refresh();
				
			}
		}
		
		public static void main(String[] args) throws InterruptedException {
			/*System.setProperty("webdriver.chrome.driver", "C:/Users/Kevin/Downloads/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("file:///C:/Users/Kevin/Documents/kevinHTML/exemplo.html");
			*///driver.get("www.google.com");
			
			samba();
		
		}
}
