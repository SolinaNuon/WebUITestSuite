import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CheckBrokenLinks extends WebUIMain {

    @Test

    void findAllAnchorLinks() {
        try {


            List<WebElement> anchorLinks = driver.findElements(By.tagName("a"));
            ExtentTest logger = extend.createTest("Test Broken Links");

            for (WebElement eachLink : anchorLinks) {
                //if(eachLink.isDisplayed()) {
                String urlLink = eachLink.getAttribute("href");
                URL link = new URL(urlLink);
                HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();

                httpConn.setRequestMethod("GET");
                httpConn.setRequestProperty("Content-Type", "application/json");
                httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                //Set the timeout for 2 seconds
                // httpConn.setConnectTimeout(3000);
                //connect using connect method
                httpConn.setRequestMethod("HEAD");
                httpConn.connect();
                //use getResponseCode() to get the response code.
                if (httpConn.getResponseCode() == 200) {
                    System.out.println(urlLink + " - " + httpConn.getResponseMessage());
                    logger.log(Status.PASS, urlLink + " | " + httpConn.getResponseMessage());
                } else if (httpConn.getResponseCode() == 404) {
                    System.out.println(urlLink + " - " + httpConn.getResponseMessage());
                    logger.log(Status.FAIL, urlLink + " | " + httpConn.getResponseMessage());
                } else if (httpConn.getResponseCode() == 500) {
                    System.out.println(urlLink + " - " + httpConn.getResponseMessage());
                    logger.log(Status.FAIL, urlLink + " | " + httpConn.getResponseMessage());
                }  else if (httpConn.getResponseCode() == 401) {
                    System.out.println(urlLink + " - " + httpConn.getResponseMessage());
                    logger.log(Status.FAIL, urlLink + " | " + httpConn.getResponseMessage());
                } else {
                    System.out.println(httpConn.getResponseCode());
                    System.out.println(urlLink + " - " + httpConn.getResponseMessage());
                    logger.log(Status.FAIL, urlLink + " | " + httpConn.getResponseMessage());
                }
                httpConn.disconnect();
          //  }
            }
        } catch (NoSuchElementException e) {
              System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    @AfterTest
    void tearDown() {
         driver.close();
         driver.quit();
        extend.flush();
    }
}
