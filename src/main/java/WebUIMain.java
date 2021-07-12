import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import java.time.Duration;


public class WebUIMain {
     public static WebDriver driver;
    String url="";
    public static ExtentHtmlReporter htmlReport;
   public static  ExtentReports extend;
   String h1="";

    @BeforeTest
    public void driverSetup() {

        System.setProperty("webdriver.chrome.driver", "D:\\important_settings\\selenium_settings\\chromedriver.exe");
        ;
        driver = new ChromeDriver();
        url="https://pb.cherry.agency/";
        driver.get(url);
       // driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        htmlReport = new ExtentHtmlReporter("D:\\WebUITestSuite\\src\\report\\report.html");
        extend = new ExtentReports();
        extend.attachReporter(htmlReport);
    }

}
