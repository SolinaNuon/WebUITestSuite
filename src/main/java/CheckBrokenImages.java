
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.http.client.methods.HttpGet;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckBrokenImages extends WebUIMain {


    List<WebElement> allImageEls = null;

    @Test (priority = 1)
    public void fetchAllImagesOnWeb() throws IOException {

        ExtentTest logger = extend.createTest("Test Broken Images");
        try {
            allImageEls = driver.findElements(By.tagName("img"));
            System.out.println("Total Count:" + allImageEls.size());

            // check on image source
            for(WebElement imageLink : allImageEls) {
                if (imageLink.isDisplayed() && imageLink!= null) {
                    String altExist = "";
                    if( imageLink.getAttribute("alt") == null) {
                        altExist = "No Alt attribute";
                    } else if (imageLink.getAttribute("alt").isEmpty()) {
                        altExist = "No value for Alt attribute";
                    } else {
                        altExist = imageLink.getAttribute("alt");
                    }
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpGet request = new HttpGet(imageLink.getAttribute("src"));
                    HttpResponse response = client.execute(request);
                    if(response.getStatusLine().getStatusCode() != 200) {
                        System.out.println(imageLink.getAttribute("src") + "is broken. | " + altExist);
                        logger.log(Status.FAIL, imageLink.getAttribute("src"));

                    } else {

                        URLConnection urlConnection = new URL(imageLink.getAttribute("src")).openConnection();
                        int size = urlConnection.getContentLength();
                        System.out.println("image size:"+ size);
                        System.out.println(imageLink.getAttribute("src") + "is good.");
                        logger.log(Status.PASS, imageLink.getAttribute("src") + " | " + size +"bytes | "+ altExist);
                    }
                }
            }
        }catch (NoSuchElementException e) {
            System.out.println(e);
        } catch (Exception ex){
            System.out.println(ex);
        }

    }

    @AfterTest
    void tearDown() {
       // driver.close();
       // driver.quit();
        extend.flush();
    }
}
